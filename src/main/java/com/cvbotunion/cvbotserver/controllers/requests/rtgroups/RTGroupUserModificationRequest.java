package com.cvbotunion.cvbotserver.controllers.requests.rtgroups;

import java.io.Serializable;

public class RTGroupUserModificationRequest implements Serializable {
    private final String groupID;
    private final String userID;

    public RTGroupUserModificationRequest(String groupID, String userID) {
        this.groupID = groupID;
        this.userID = userID;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getUserID() {
        return userID;
    }
}
