package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.documents.UserDocument;
import com.cvbotunion.cvbotserver.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    private UserDocument getCurrentUserDetail(Authentication authentication){
        UserDocument userDocument = this.userRepository.findByUsername(authentication.getName());
        if(userDocument != null){
            userDocument.setPassword(null);
            return userDocument;
        }else{
            return null;
        }
    }

}
