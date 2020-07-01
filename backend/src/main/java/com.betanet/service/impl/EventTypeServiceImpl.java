package com.betanet.service.impl;

import com.betanet.domain.EventType;
import com.betanet.domain.QEventType;
import com.betanet.repository.EventTypeRepository;
import com.betanet.service.api.EventTypeService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    private QEventType qEventType = QEventType.eventType1;

    @Override
    public Page<EventType> getEventTypes(Pageable pageable) {
        Pageable withSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "eventType");
        BooleanBuilder where = new BooleanBuilder();
        where.and(qEventType.deleted.isFalse());
        return eventTypeRepository.findAll(where, withSort);
    }
}
