package org.jboss.as.quickstarts.kitchensink.dto.error;

import lombok.Builder;
import lombok.Data;
import org.jboss.as.quickstarts.kitchensink.constants.ExceptionTypeEnum;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ExceptionResponse {

    private ExceptionTypeEnum exceptionType;
    private Map<String, String> fieldErrors = new HashMap<>();
    private String errorMessage;


}
