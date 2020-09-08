package com.cvbotunion.cvbotserver.controllers.requests.user;

import java.io.Serializable;

public class UserOptedOutTwitterNotificationModificationRequest implements Serializable {
    private String userID;
    private String twitterUsername;

    public String getUserID() {
        return userID;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public UserOptedOutTwitterNotificationModificationRequest(String userID, String twitterUsername) {
        this.userID = userID;
        this.twitterUsername = twitterUsername;
    }
}
