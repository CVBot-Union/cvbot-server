package com.cvbotunion.cvbotserver.repositories;

import com.cvbotunion.cvbotserver.documents.TweetDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<TweetDocument, String> {
}
