package com.bluecedar.service.events.web;

import com.bluecedar.service.events.core.LogEventResponse;
import com.bluecedar.service.events.core.LogEventService;
import com.bluecedar.service.events.domain.LogEvent;
import com.bluecedar.service.events.util.WrapperPagination;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logevents")
public class LogEventController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final LogEventService service;

    public LogEventController(LogEventService logEventService) {
        service = logEventService;
    }

    @GetMapping
    public ResponseEntity<WrapperPagination<LogEventResponse>> getAll(
            @RequestHeader HttpHeaders headers,
            @QuerydslPredicate(root = LogEvent.class) Predicate predicate,
            @PageableDefault(sort = {"timestamp"}, direction = Sort.Direction.DESC) Pageable pageable) {
        logger.trace("GET all events");
        Page<LogEventResponse> page = service.getAllResources(
                "test-resource-owner", predicate, Pageable.unpaged());
        WrapperPagination<LogEventResponse> paginatedResponse = new WrapperPagination<>(
                page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
        return new ResponseEntity<>(paginatedResponse, HttpStatus.OK);
    }
}
