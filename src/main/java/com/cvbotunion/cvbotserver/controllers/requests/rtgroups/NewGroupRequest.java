package com.cvbotunion.cvbotserver.controllers.requests.rtgroups;

import java.io.Serializable;
import java.util.List;

public class NewGroupRequest implements Serializable {
    private final String groupName;
    private final List<String> groupMembers;
    private final List<String> leaders;

    public NewGroupRequest(String groupName, List<String> groupMembers, List<String> leaders) {
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.leaders = leaders;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public List<String> getLeaders() {
        return leaders;
    }
}
