package com.cvbotunion.cvbotserver.repositories;

import com.cvbotunion.cvbotserver.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    UserDocument findByUsername(String username);
}
