package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.controllers.requests.twitteruser.NewTwitterUserRequest;
import com.cvbotunion.cvbotserver.controllers.requests.twitteruser.TwitterUserGroupModificationRequest;
import com.cvbotunion.cvbotserver.documents.TwitterUserDocument;
import com.cvbotunion.cvbotserver.exceptions.ElementNotFoundException;
import com.cvbotunion.cvbotserver.exceptions.ElementNotUniqueException;
import com.cvbotunion.cvbotserver.repositories.TwitterUserRepository;
import com.cvbotunion.cvbotserver.utils.ResponseWrapper;
import com.mongodb.MongoWriteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@CrossOrigin
@RequestMapping("/twitter_users")
public class TwitterUserController {

    @Resource
    private TwitterUserRepository twitterUserRepository;

    @GetMapping("/")
    private ResponseEntity<ResponseWrapper> getAllTwitterUsers() {
        return ResponseEntity.ok().body(new ResponseWrapper(false, "Fetched", (Serializable) this.twitterUserRepository.queryAllTwitterUsers()));
    }

    @GetMapping("/detail")
    private ResponseEntity<ResponseWrapper> getTwitterUserDetail(@RequestParam(value = "username") String username) {
        return ResponseEntity.ok().body(new ResponseWrapper(false, "Fetched", this.twitterUserRepository.findByUsername(username)));
    }

    @PostMapping("/new")
    private ResponseEntity<ResponseWrapper> createNewTwitterUser(@RequestBody NewTwitterUserRequest newTwitterUserRequest){
        try{
            this.twitterUserRepository.save(new TwitterUserDocument(newTwitterUserRequest.getUsername(), newTwitterUserRequest.getInAppNickname(), newTwitterUserRequest.getGroupIDs()));
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Saved", String.format("{\"username\": \"%s\"}",newTwitterUserRequest.getUsername())));
        }catch (MongoWriteException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Duplicate Key", e));
        }
    }

    @PostMapping("/remove")
    private ResponseEntity<ResponseWrapper> removeTwitterUserFromGroup(@RequestBody TwitterUserGroupModificationRequest twitterUserGroupModificationRequest) {
        try{
            TwitterUserDocument document = this.twitterUserRepository.findByUsername(twitterUserGroupModificationRequest.getUsername());
            document.removeFromGroup(twitterUserGroupModificationRequest.getGroupID());
            this.twitterUserRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false,"Removed", document));
        } catch (ElementNotFoundException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Found", e));
        }
    }

    @PostMapping("/join")
    private ResponseEntity<ResponseWrapper> joinTwitterUserToNewGroup(@RequestBody TwitterUserGroupModificationRequest twitterUserGroupModificationRequest) {
        try{
            TwitterUserDocument document = this.twitterUserRepository.findByUsername(twitterUserGroupModificationRequest.getUsername());
            document.addToNewGroup(twitterUserGroupModificationRequest.getGroupID());
            this.twitterUserRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Joined", document));
        } catch (ElementNotUniqueException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Unique", e));
        }
    }

}
