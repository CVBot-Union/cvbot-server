package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.controllers.requests.user.UserOptedOutTwitterNotificationModificationRequest;
import com.cvbotunion.cvbotserver.documents.TwitterUserDocument;
import com.cvbotunion.cvbotserver.documents.UserDocument;
import com.cvbotunion.cvbotserver.exceptions.ElementNotFoundException;
import com.cvbotunion.cvbotserver.exceptions.ElementNotUniqueException;
import com.cvbotunion.cvbotserver.repositories.TwitterUserRepository;
import com.cvbotunion.cvbotserver.repositories.UserRepository;
import com.cvbotunion.cvbotserver.utils.ResponseWrapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private TwitterUserRepository twitterUserRepository;

    @GetMapping("/")
    private ResponseEntity<ResponseWrapper> getCurrentUserDetail(Authentication authentication){
        UserDocument userDocument = this.userRepository.findByUsername(authentication.getName());
        if(userDocument != null){
            userDocument.setPassword(null);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "User Fetched", userDocument));
        }else{
            return ResponseEntity.ok().body(new ResponseWrapper(true, "User Not Found", 10404));
        }
    }

    @PostMapping("/opted_out_notification/add")
    private ResponseEntity<ResponseWrapper> 添加用户到不推送列表(@RequestBody UserOptedOutTwitterNotificationModificationRequest userOptedOutTwitterNotificationModificationRequest) {
        try {
            Optional<UserDocument> pendingDocument = this.userRepository.findById(userOptedOutTwitterNotificationModificationRequest.getUserID());
            TwitterUserDocument twitterUserDocument = this.twitterUserRepository.findByUsername(userOptedOutTwitterNotificationModificationRequest.getTwitterUsername());
            if(pendingDocument.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching user found.", 10404));
            }
            if(twitterUserDocument == null) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching twitter user found.", 10404));
            }
            UserDocument document = pendingDocument.get();
            document.addNewOptedOutNotificationTwitterUserID(userOptedOutTwitterNotificationModificationRequest.getTwitterUsername());
            this.userRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Joined", document));
        } catch (ElementNotUniqueException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Unique", e));
        }
    }

    @PostMapping("/opted_out_notification/remove")
    private ResponseEntity<ResponseWrapper> removeTwitterUserFromOptedOutNotification(@RequestBody UserOptedOutTwitterNotificationModificationRequest userOptedOutTwitterNotificationModificationRequest) {
        try{
            Optional<UserDocument> pendingDocument = this.userRepository.findById(userOptedOutTwitterNotificationModificationRequest.getUserID());
            if(pendingDocument.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching user found.", 10404));
            }
            UserDocument document = pendingDocument.get();
            document.removeOptedOutNotificationTwitterUserID(userOptedOutTwitterNotificationModificationRequest.getTwitterUsername());
            this.userRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Joined", document));
        } catch (ElementNotFoundException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Unique", e));
        }
    }

}
