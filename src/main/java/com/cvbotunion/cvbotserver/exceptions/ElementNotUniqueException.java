package com.cvbotunion.cvbotserver.exceptions;

import java.io.Serializable;

public class ElementNotUniqueException extends Exception implements Serializable {
    @Override
    public String getMessage() {
        return "Element is not unique in this list.";
    }
}
