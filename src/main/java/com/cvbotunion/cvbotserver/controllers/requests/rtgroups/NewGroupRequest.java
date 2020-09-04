package com.cvbotunion.cvbotserver.controllers.requests.rtgroups;

import java.io.Serializable;
import java.util.List;

public class NewGroupRequest implements Serializable {
    private String groupName;
    private List<String> twitterAccounts;

    public NewGroupRequest(String groupName, List<String> twitterAccounts) {
        this.groupName = groupName;
        this.twitterAccounts = twitterAccounts;
    }

    public NewGroupRequest(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getTwitterAccounts() {
        return twitterAccounts;
    }
}
