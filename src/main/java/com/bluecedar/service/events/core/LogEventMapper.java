package com.bluecedar.service.events.core;

import com.bluecedar.service.events.domain.LogEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogEventMapper {
    LogEvent requestToResource(LogEventRequest request);

    @Mapping(target = "timestamp", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LogEventResponse resourceToResponse(LogEvent logEvent);

    default UUID map(String value) {
        return UUID.fromString(value);
    }
}
