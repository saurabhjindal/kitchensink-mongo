package org.jboss.as.quickstarts.kitchensink.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class MemberNotFoundException extends RuntimeException{

    private final String detailedMessage;

    public MemberNotFoundException(String errorMessage, String detailedMessage){
        super(errorMessage);
        this.detailedMessage = detailedMessage;
    }

    public MemberNotFoundException(Long memberId){
        super("Member Id: " + memberId + " Not found");
        detailedMessage = getMessage();
    }


}
