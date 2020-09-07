package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.documents.UserDocument;
import com.cvbotunion.cvbotserver.repositories.UserRepository;
import com.cvbotunion.cvbotserver.utils.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserRepository userRepository;

    @GetMapping("/")
    private ResponseEntity<ResponseWrapper> getCurrentUserDetail(Authentication authentication){
        UserDocument userDocument = this.userRepository.findByUsername(authentication.getName());
        if(userDocument != null){
            userDocument.setPassword(null);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "User Created", (Serializable) userDocument));
        }else{
            return ResponseEntity.ok().body(new ResponseWrapper(true, "User Not Found", 10404));
        }
    }

}
