package com.betanet.lynxsupport.repository;

import com.betanet.lynxsupport.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer>, QuerydslPredicateExecutor<Place> {
}
