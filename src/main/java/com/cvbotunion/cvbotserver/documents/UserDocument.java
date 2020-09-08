package com.cvbotunion.cvbotserver.documents;

import com.cvbotunion.cvbotserver.exceptions.ElementNotFoundException;
import com.cvbotunion.cvbotserver.exceptions.ElementNotUniqueException;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class UserDocument extends AbstractDocument {

    @Indexed(unique = true)
    private String username;
    private String password;
    private List<String> optedOutNotificationTwitterUserIDs;

    public UserDocument(String username, String password) {
        this.username = username;
        this.password = password;
        this.optedOutNotificationTwitterUserIDs = new ArrayList<>();
    }

    public void addNewOptedOutNotificationTwitterUserID(String userID) throws ElementNotUniqueException {
        if(this.optedOutNotificationTwitterUserIDs.contains(userID)) throw new ElementNotUniqueException();
        this.optedOutNotificationTwitterUserIDs.add(userID);
    }

    public void removeOptedOutNotificationTwitterUserID(String userID) throws ElementNotFoundException {
        if(!this.optedOutNotificationTwitterUserIDs.contains(userID)) throw new ElementNotFoundException();
        this.optedOutNotificationTwitterUserIDs.remove(userID);
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

    public List<String> getOptedOutNotificationTwitterUserIDs() {
        return optedOutNotificationTwitterUserIDs;
    }

}
