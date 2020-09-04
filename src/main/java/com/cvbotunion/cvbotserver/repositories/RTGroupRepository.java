package com.cvbotunion.cvbotserver.repositories;

import com.cvbotunion.cvbotserver.documents.RTGroupDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RTGroupRepository extends MongoRepository<RTGroupDocument, String> {

    RTGroupDocument findByGroupName(String groupName);

    @Query(value = "{ }", fields = "{ 'twitterAccounts' : 0 }")
    List<RTGroupDocument> queryAllGroups();
}
