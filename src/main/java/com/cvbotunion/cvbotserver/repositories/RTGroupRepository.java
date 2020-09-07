package com.cvbotunion.cvbotserver.repositories;

import com.cvbotunion.cvbotserver.documents.RTGroupDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RTGroupRepository extends MongoRepository<RTGroupDocument, String> {

    RTGroupDocument findByGroupName(String groupName);
    Optional<RTGroupDocument> findById(String id);

    @Query(value = "{}", fields = "{ 'groupMembers' : 0 }")
    List<RTGroupDocument> queryAllGroups();
}
