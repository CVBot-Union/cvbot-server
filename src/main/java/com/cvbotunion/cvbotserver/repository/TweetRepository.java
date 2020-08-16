package com.cvbotunion.cvbotserver.repository;

import com.cvbotunion.cvbotserver.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<Tweet, String> {
}
