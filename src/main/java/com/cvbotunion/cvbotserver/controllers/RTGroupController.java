package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.controllers.requests.rtgroups.NewGroupRequest;
import com.cvbotunion.cvbotserver.documents.RTGroupDocument;
import com.cvbotunion.cvbotserver.repositories.RTGroupRepository;
import com.mongodb.MongoWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rtgroups")
public class RTGroupController {

    @Autowired
    private RTGroupRepository rtGroupRepository;

    @GetMapping("/")
    private List<RTGroupDocument> getAllRTGroups() {
        return this.rtGroupRepository.queryAllGroups();
    }

    @GetMapping("/detail")
    private RTGroupDocument getGroupByName(@RequestParam(value = "group_name") String groupName) {
        return this.rtGroupRepository.findByGroupName(groupName);
    }

    @PostMapping("/new")
    @ResponseBody
    private String createNewRTGroup(@RequestBody NewGroupRequest newGroupRequest) {
        try {
            this.rtGroupRepository.save(new RTGroupDocument(newGroupRequest.getGroupName(),newGroupRequest.getTwitterAccounts()));
            return "Saved";
        }catch (MongoWriteException e){
            return String.valueOf(e.getCode());
        }
    }

}
