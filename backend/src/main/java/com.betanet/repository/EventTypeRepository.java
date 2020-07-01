package com.betanet.repository;

import com.betanet.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer>, QuerydslPredicateExecutor<EventType> {
}
