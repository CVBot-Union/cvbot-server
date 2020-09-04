package com.cvbotunion.cvbotserver.documents;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "rt_groups")
public class RTGroupDocument extends AbstractDocument{

    @Indexed(unique = true)
    private String groupName;
    private List<String> leaders;
    private List<String> twitterAccounts;

    public RTGroupDocument() {
    }

    public RTGroupDocument(String groupName) {
        this.groupName = groupName;
    }

    public RTGroupDocument(String groupName, List<String> twitterAccounts) {
        this.groupName = groupName;
        this.twitterAccounts = twitterAccounts;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<String> getLeaders() {
        return leaders;
    }

    public List<String> getTwitterAccounts() {
        return twitterAccounts;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void addNewLeader(String userID) {
        this.leaders.add(userID);
    }

    public void addNewTwitterAccount(String twitterAccount) {
        this.twitterAccounts.add(twitterAccount);
    }

    public void removeLeader(String userID) {
        this.leaders.remove(userID);
    }

    public void removeTwitterAccounts(String twitterAccount) {
        this.twitterAccounts.remove(twitterAccount);
    }
}
