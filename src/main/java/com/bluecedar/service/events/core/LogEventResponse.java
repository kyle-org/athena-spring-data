package com.bluecedar.service.events.core;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString(callSuper = true)
public class LogEventResponse {
    @JsonAlias({"resourceownerid"})
    private String resourceOwnerId;
    @JsonAlias({"bcnrequestid"})
    private String bcnRequestId;
    private String event;
    private String message;
    private String service;
    @JsonAlias({"ts"})
    private LocalDateTime timestamp;
    @JsonAlias({"dt"})
    private String date;
}
