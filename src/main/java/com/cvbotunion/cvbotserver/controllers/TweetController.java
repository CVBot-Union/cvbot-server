package com.cvbotunion.cvbotserver.controllers;

import com.cvbotunion.cvbotserver.documents.TweetDocument;
import com.cvbotunion.cvbotserver.repositories.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping("/")
    public List<TweetDocument> getLatestTweet() {
        return this.tweetRepository.findAll();
    }
}
