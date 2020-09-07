package com.cvbotunion.cvbotserver.repositories;

import com.cvbotunion.cvbotserver.documents.TwitterUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TwitterUserRepository extends MongoRepository<TwitterUserDocument, String> {

    TwitterUserDocument findByUsername(String username);

    @Query(value = "{}", fields = "{ 'groupIDs' : 0 }")
    List<TwitterUserDocument> queryAllTwitterUsers();

}
