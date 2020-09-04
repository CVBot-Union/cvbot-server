package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.controllers.requests.auth.AuthLoginRequest;
import com.cvbotunion.cvbotserver.controllers.requests.auth.AuthRegisterRequest;
import com.cvbotunion.cvbotserver.controllers.responses.AuthLoginResponse;
import com.cvbotunion.cvbotserver.documents.UserDocument;
import com.cvbotunion.cvbotserver.repositories.UserRepository;
import com.cvbotunion.cvbotserver.utils.JWTTokenUtil;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
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
    public ResponseEntity<?> createNewUser(@RequestBody AuthRegisterRequest authRegisterRequest){
        String hashedPassword = BCrypt.hashpw(authRegisterRequest.getPassword(), BCrypt.gensalt());
        try{
            this.userRepository.save(new UserDocument(authRegisterRequest.getUsername(), hashedPassword));
            return ResponseEntity.ok().build();
        }catch (MongoWriteException e){
            return ResponseEntity.badRequest().body(e.getCode());
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
