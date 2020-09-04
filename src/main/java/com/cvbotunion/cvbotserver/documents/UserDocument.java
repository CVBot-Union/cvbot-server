package com.cvbotunion.cvbotserver.documents;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class UserDocument {

    @Indexed(unique = true)
    private String username;
    private String password;
    private List<String> associatedRTGroupID;

    public UserDocument(String username, String password) {
        this.username = username;
        this.password = password;
        this.associatedRTGroupID = new ArrayList<>();
    }

    public void associateNewRTGroup(String groupID) {
        this.associatedRTGroupID.add(groupID);
    }
    
    public void disassociateNewRTGroup(String groupID) {
        this.associatedRTGroupID.remove(groupID);
    }

    public List<String> getAssociatedRTGroupID() {
        return this.associatedRTGroupID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() { return this.password; }

    public String getUsername() {
        return this.username;
    }

}
