package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.controllers.requests.auth.AuthLoginRequest;
import com.cvbotunion.cvbotserver.controllers.requests.auth.AuthRegisterRequest;
import com.cvbotunion.cvbotserver.controllers.responses.AuthLoginResponse;
import com.cvbotunion.cvbotserver.documents.UserDocument;
import com.cvbotunion.cvbotserver.repositories.UserRepository;
import com.cvbotunion.cvbotserver.utils.JWTTokenUtil;
import com.cvbotunion.cvbotserver.utils.ResponseWrapper;
import com.mongodb.MongoWriteException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JWTTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthLoginRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDocument userDetails = this.userRepository.findByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthLoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> createNewUser(@RequestBody AuthRegisterRequest authRegisterRequest){
        String hashedPassword = BCrypt.hashpw(authRegisterRequest.getPassword(), BCrypt.gensalt());
        try{
            UserDocument document = new UserDocument(authRegisterRequest.getUsername(), hashedPassword);
            this.userRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false,"Saved", String.format("{\"id\": \"%s\"}",document.getUsername())));
        }catch (MongoWriteException e){
            return ResponseEntity.badRequest().body(new ResponseWrapper(true,"Duplicate Key", e));
        }

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
