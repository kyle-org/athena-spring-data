package com.bluecedar.service.events.core;

import com.bluecedar.service.events.domain.LogEvent;
import com.bluecedar.service.events.domain.LogEventRepository;
import com.bluecedar.service.events.domain.QLogEvent;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogEventService {
    private static final Logger logger = LoggerFactory.getLogger(LogEventService.class);
    public final LogEventMapper resourceMapper;
    private final LogEventRepository repository;

    public LogEventService(LogEventRepository repository,
                           LogEventMapper mapper) {
        this.resourceMapper = mapper;
        this.repository = repository;
    }

    public Page<LogEventResponse> getAllResources(String resourceOwnerId, Predicate predicate, Pageable pageable) {
        BooleanBuilder predicateBuilder = new BooleanBuilder();
        predicateBuilder.and(QLogEvent.logEvent.resourceOwnerId.eq(resourceOwnerId)).and(predicate);

        Page<LogEvent> configs = repository.findAll(predicateBuilder.getValue(), pageable);
        System.out.println("-------START Page LogEvent");
        configs.stream().forEach(System.out::println);
        System.out.println("------- END Page LogEvent");
        return configs.map(resourceMapper::resourceToResponse);
    }
}
