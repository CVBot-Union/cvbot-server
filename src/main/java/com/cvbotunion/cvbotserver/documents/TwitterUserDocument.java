package com.cvbotunion.cvbotserver.documents;

import com.cvbotunion.cvbotserver.exceptions.ElementNotFoundException;
import com.cvbotunion.cvbotserver.exceptions.ElementNotUniqueException;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "twitter_users")
public class TwitterUserDocument extends AbstractDocument{
    @Indexed(unique = true)
    private String username; // screen_name
    private String inAppNickname;
    private List<String> groupIDs;

    public TwitterUserDocument() { }

    public TwitterUserDocument(String username, String inAppNickname, List<String> groupIDs) {
        this.username = username;
        this.inAppNickname = inAppNickname;
        this.groupIDs = groupIDs;
    }

    public void addToNewGroup(String groupID) throws ElementNotUniqueException {
        if(this.groupIDs.contains(groupID)) throw new ElementNotUniqueException();
        this.groupIDs.add(groupID);
    }

    public void removeFromGroup(String groupID) throws ElementNotFoundException {
        if(!this.groupIDs.contains(groupID)) throw new ElementNotFoundException();
        this.groupIDs.remove(groupID);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getGroupIDs() {
        return groupIDs;
    }

    public String getInAppNickname() {
        return inAppNickname;
    }

    public void setInAppNickname(String inAppNickname) {
        this.inAppNickname = inAppNickname;
    }
}
