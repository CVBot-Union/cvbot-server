package com.cvbotunion.cvbotserver.controller;

import com.cvbotunion.cvbotserver.model.Tweet;
import com.cvbotunion.cvbotserver.repository.TweetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    private final TweetRepository tweetRepository;

    public TweetController(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    @GetMapping("/")
    public List<Tweet> getLatestTweet() {
        return this.tweetRepository.findAll();
    }
}
