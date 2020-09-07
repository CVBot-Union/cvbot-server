package com.cvbotunion.cvbotserver.controllers.requests.twitteruser;

import java.io.Serializable;
import java.util.List;

public class NewTwitterUserRequest implements Serializable {
    private final String username;
    private final String inAppNickname;
    private final List<String> groupIDs;

//    public NewTwitterUserRequest(String username, String inAppNickname) {
//        this.username = username;
//        this.inAppNickname = inAppNickname;
//        this.groupIDs = new ArrayList<>();
//    }

    public NewTwitterUserRequest(String username, String inAppNickname, List<String> groupIDs) {
        this.username = username;
        this.inAppNickname = inAppNickname;
        this.groupIDs = groupIDs;
    }

    public String getUsername() {
        return username;
    }

    public String getInAppNickname() {
        return inAppNickname;
    }

    public List<String> getGroupIDs() {
        return groupIDs;
    }
}
