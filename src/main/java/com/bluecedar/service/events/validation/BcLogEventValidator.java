package com.bluecedar.service.events.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// Spring does not seem to support the JsonProperty serialization to enum:
// https://github.com/spring-projects/spring-framework/issues/25327
// Add a custom validator for validating the event string to enum. For the request input, values in BcLogEvent
// **without** the BCLOGEVENT_ prefix of the enum are accepted. For example, a string of "WORKFLOW_STARTED" would be
// accepted for a query for the event BCLOGEVENT_WORKFLOW_STARTED
public class BcLogEventValidator implements ConstraintValidator<BcLogEvent, String> {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            mapper.readValue("\"" + value + "\"", LogEventEnum.class);
        } catch (JsonProcessingException e) {
            return false;
        }
        return true;
    }
}
