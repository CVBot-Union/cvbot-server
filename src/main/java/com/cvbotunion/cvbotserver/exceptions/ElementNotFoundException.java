package com.cvbotunion.cvbotserver.exceptions;

import java.io.Serializable;

public class ElementNotFoundException extends Exception implements Serializable {

    @Override
    public String getMessage() {
        return "Element does not exist in this list";
    }

}
