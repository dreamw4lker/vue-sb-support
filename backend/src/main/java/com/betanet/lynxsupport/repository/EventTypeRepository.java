package com.betanet.lynxsupport.repository;

import com.betanet.lynxsupport.domain.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer>, QuerydslPredicateExecutor<EventType> {
}
