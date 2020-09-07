package com.cvbotunion.cvbotserver.controllers.requests.twitteruser;

public class TwitterUserGroupModificationRequest {
    private final String username;
    private final String groupID;

    public TwitterUserGroupModificationRequest(String username, String groupID) {
        this.username = username;
        this.groupID = groupID;
    }

    public String getUsername() {
        return username;
    }

    public String getGroupID() {
        return groupID;
    }
}
