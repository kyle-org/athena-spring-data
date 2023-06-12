package com.bluecedar.service.events.core;

import com.bluecedar.service.events.domain.LogEventRepository;
import com.bluecedar.service.events.domain.QLogEvent;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;

@SpringBootTest
class LogEventServiceTest {

    private static final String TEST_RESOURCE_OWNER = "test-resource-owner";

    @Autowired
    private LogEventRepository repository;
    @Autowired
    private LogEventMapper logEventMapper;

    private LogEventService logEventServiceUnderTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logEventServiceUnderTest = new LogEventService(repository, logEventMapper);
    }

    @Test
    void testGetAllResources() {
        // Setup
        QLogEvent qLogEvent = QLogEvent.logEvent;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qLogEvent.event.eq("WORKFLOW_STARTED")).and(qLogEvent.resourceOwnerId.eq(TEST_RESOURCE_OWNER));

        Pageable pageable;
        if (true) {
            Sort sort = JpaSort.unsafe(Sort.Direction.DESC, "timestamp");
            pageable = PageRequest.of(1, 10, sort);
        } else {
            pageable = Pageable.unpaged();
        }

        // Run the test
        final Page<LogEventResponse> results = logEventServiceUnderTest.getAllResources(TEST_RESOURCE_OWNER, builder,
                pageable);

        Assertions.assertTrue(results.getSize() > 0);
        System.out.println("-------START Page LogEventResponse");
        results.stream().forEach(System.out::println);
        System.out.println("-------END Page LogEventResponse");
    }
}
