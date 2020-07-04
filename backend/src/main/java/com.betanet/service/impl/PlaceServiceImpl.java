package com.betanet.service.impl;

import com.betanet.domain.Place;
import com.betanet.domain.QPlace;
import com.betanet.repository.PlaceRepository;
import com.betanet.service.api.PlaceService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    private QPlace qPlace = QPlace.place;

    @Override
    public Page<Place> getPlaces(Pageable pageable) {
        Pageable withSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "id");
        BooleanBuilder where = new BooleanBuilder();
        where.and(qPlace.deleted.isFalse());
        return placeRepository.findAll(where, withSort);
    }
}
