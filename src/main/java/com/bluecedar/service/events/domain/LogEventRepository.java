package com.bluecedar.service.events.domain;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface LogEventRepository extends QuerydslPredicateExecutor<LogEvent>,
        CrudRepository<LogEvent, UUID>, QuerydslBinderCustomizer<EntityPath<LogEvent>> {

    Page<LogEvent> findAll(Predicate predicate, Pageable pageable);

    @Override
    default void customize(@Nonnull QuerydslBindings bindings, @NonNull EntityPath<LogEvent> root) {}
}
