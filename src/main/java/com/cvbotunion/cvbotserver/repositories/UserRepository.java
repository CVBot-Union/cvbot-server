package com.cvbotunion.cvbotserver.repositories;

import com.cvbotunion.cvbotserver.documents.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    UserDocument findByUsername(String username);
    Optional<UserDocument> findById(String id);
}
