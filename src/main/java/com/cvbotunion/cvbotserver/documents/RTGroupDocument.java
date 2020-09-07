package com.cvbotunion.cvbotserver.documents;

import com.cvbotunion.cvbotserver.exceptions.ElementNotFoundException;
import com.cvbotunion.cvbotserver.exceptions.ElementNotUniqueException;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "rt_groups")
public class RTGroupDocument extends AbstractDocument{

    @Indexed(unique = true)
    private String groupName;
    private List<String> leaders;
    private List<String> groupMembers;

    public RTGroupDocument() {
    }

    public RTGroupDocument(String groupName) {
        this.groupName = groupName;
    }

    public RTGroupDocument(String groupName, List<String> groupMembers, List<String> leaders) {
        this.groupName = groupName;
        this.groupMembers = groupMembers;
        this.leaders = leaders;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getLeaders() {
        return leaders;
    }

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addNewLeader(String userID) throws ElementNotUniqueException {
        if(this.leaders.contains(userID)) throw new ElementNotUniqueException();
        this.leaders.add(userID);
    }

    public void addNewGroupMember(String userID) throws ElementNotUniqueException {
        if(this.groupMembers.contains(userID)) throw new ElementNotUniqueException();
        this.groupMembers.add(userID);
    }

    public void removeLeader(String userID) throws ElementNotFoundException {
        if(!this.leaders.contains(userID)) throw new ElementNotFoundException();
        this.leaders.remove(userID);
    }

    public void removeGroupMember(String userID) throws ElementNotFoundException {
        if(!this.groupMembers.contains(userID)) throw new ElementNotFoundException();
        this.groupMembers.remove(userID);
    }
}
