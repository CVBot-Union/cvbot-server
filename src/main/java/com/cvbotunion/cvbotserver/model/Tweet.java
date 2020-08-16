package com.cvbotunion.cvbotserver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collation = "tweet")
public class Tweet {
    @Id
    private String id;
    private String user;
    @Indexed(direction = IndexDirection.ASCENDING)
    private Date tweetDate;
    private String original_content;

    private boolean isRetweet;
    private String retweetId;

    private List<String> medias;
}
