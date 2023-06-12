package com.bluecedar.service.events.core;

import com.bluecedar.service.events.validation.BcLogEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LogEventRequest {
    private String resourceOwnerId;
    @BcLogEvent
    private String event;
    private boolean unpaged;

    public String getResourceOwnerId() {
        if(resourceOwnerId != null) {
            return URLDecoder.decode(resourceOwnerId, StandardCharsets.UTF_8);
        }
        return null;
    }
}
