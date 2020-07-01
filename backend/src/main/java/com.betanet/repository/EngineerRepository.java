package com.betanet.repository;

import com.betanet.domain.Engineer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineerRepository extends JpaRepository<Engineer, Integer>, QuerydslPredicateExecutor<Engineer> {
}
