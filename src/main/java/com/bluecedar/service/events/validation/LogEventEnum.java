package com.bluecedar.service.events.validation;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum LogEventEnum {
    @JsonProperty("WORKFLOW_STARTED")
    BCLOGEVENT_WORKFLOW_STARTED,
    @JsonProperty("WORKFLOW_SUCCESS")
    BCLOGEVENT_WORKFLOW_SUCCESS,
    @JsonProperty("WORKFLOW_CANCELED")
    BCLOGEVENT_WORKFLOW_CANCELED,
    @JsonProperty("WORKFLOW_FAILED")
    BCLOGEVENT_WORKFLOW_FAILED,
    @JsonProperty("WORKFLOW_DELETED")
    BCLOGEVENT_WORKFLOW_DELETED
}