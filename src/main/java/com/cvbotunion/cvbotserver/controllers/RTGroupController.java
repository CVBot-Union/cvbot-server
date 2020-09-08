package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.controllers.requests.rtgroups.NewGroupRequest;
import com.cvbotunion.cvbotserver.controllers.requests.rtgroups.RTGroupUserModificationRequest;
import com.cvbotunion.cvbotserver.documents.RTGroupDocument;
import com.cvbotunion.cvbotserver.exceptions.ElementNotFoundException;
import com.cvbotunion.cvbotserver.exceptions.ElementNotUniqueException;
import com.cvbotunion.cvbotserver.repositories.RTGroupRepository;
import com.cvbotunion.cvbotserver.utils.ResponseWrapper;
import com.mongodb.MongoWriteException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/rtgroups")
public class RTGroupController {

    @Resource
    private RTGroupRepository rtGroupRepository;

    @GetMapping("/")
    private ResponseEntity<ResponseWrapper> getAllRTGroups() {
        return ResponseEntity.ok().body(new ResponseWrapper(false, "Fetched", (Serializable) this.rtGroupRepository.queryAllGroups()));
    }

    @GetMapping("/detail")
    private ResponseEntity<ResponseWrapper> getGroupByName(@RequestParam(value = "group_name") String groupName) {
        return ResponseEntity.ok().body(new ResponseWrapper(false, "Fetched", this.rtGroupRepository.findByGroupName(groupName)));
    }

    @PostMapping("/new")
    private ResponseEntity<ResponseWrapper> createNewRTGroup(@RequestBody NewGroupRequest newGroupRequest) {
        try {
            this.rtGroupRepository.save(new RTGroupDocument(newGroupRequest.getGroupName(),newGroupRequest.getGroupMembers(),newGroupRequest.getLeaders()));
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Saved", String.format("{\"groupName\": \"%s\"}",newGroupRequest.getGroupName())));
        }catch (MongoWriteException e){
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Duplicate Key", e));
        }
    }

    @PostMapping("/member/remove")
    private ResponseEntity<ResponseWrapper> removeGroupMemberFromGroup(@RequestBody RTGroupUserModificationRequest rtGroupUserModificationRequest) {
        try {
            Optional<RTGroupDocument> pendingDocument = this.rtGroupRepository.findById(rtGroupUserModificationRequest.getGroupID());
            if(pendingDocument.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching group found.", 10404));
            }
            RTGroupDocument document = pendingDocument.get();
            document.removeGroupMember(rtGroupUserModificationRequest.getUserID());
            this.rtGroupRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false,"Removed", document));
        } catch (ElementNotFoundException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Found", e));
        }
    }

    @PostMapping("/member/join")
    private ResponseEntity<ResponseWrapper> addNewGroupMember(@RequestBody RTGroupUserModificationRequest rtGroupUserModificationRequest) {
        try{
            Optional<RTGroupDocument> pendingDocument = this.rtGroupRepository.findById(rtGroupUserModificationRequest.getGroupID());
            if(pendingDocument.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching group found.", 10404));
            }
            RTGroupDocument document = pendingDocument.get();
            document.addNewGroupMember(rtGroupUserModificationRequest.getUserID());
            this.rtGroupRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Joined", document));
        } catch (ElementNotUniqueException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Unique", e));
        }
    }

    @PostMapping("/leader/remove")
    private ResponseEntity<ResponseWrapper> removeGroupLeaderFromGroup(@RequestBody RTGroupUserModificationRequest rtGroupUserModificationRequest) {
        try {
            Optional<RTGroupDocument> pendingDocument = this.rtGroupRepository.findById(rtGroupUserModificationRequest.getGroupID());
            if(pendingDocument.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching group found.", 10404));
            }
            RTGroupDocument document = pendingDocument.get();
            if(document.getLeaders().size() <= 1) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Group Leader is <= 1 person", 10410));
            }
            document.removeLeader(rtGroupUserModificationRequest.getUserID());
            this.rtGroupRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false,"Removed", document));
        } catch (ElementNotFoundException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Found", e));
        }
    }

    @PostMapping("/leader/join")
    private ResponseEntity<ResponseWrapper> addNewGroupLeader(@RequestBody RTGroupUserModificationRequest rtGroupUserModificationRequest) {
        try{
            Optional<RTGroupDocument> pendingDocument = this.rtGroupRepository.findById(rtGroupUserModificationRequest.getGroupID());
            if(pendingDocument.isEmpty()) {
                return ResponseEntity.badRequest().body(new ResponseWrapper(true, "No matching group found.", 10404));
            }
            RTGroupDocument document = pendingDocument.get();
            document.addNewLeader(rtGroupUserModificationRequest.getUserID());
            this.rtGroupRepository.save(document);
            return ResponseEntity.ok().body(new ResponseWrapper(false, "Joined", document));
        } catch (ElementNotUniqueException e) {
            return ResponseEntity.badRequest().body(new ResponseWrapper(true, "Element Not Unique", e));
        }
    }

}
