package com.betanet.lynxsupport.service.impl;

import com.betanet.lynxsupport.domain.EventType;
import com.betanet.lynxsupport.domain.QEventType;
import com.betanet.lynxsupport.repository.EventTypeRepository;
import com.betanet.lynxsupport.service.api.EventTypeService;
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
        Pageable withSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "id");
        BooleanBuilder where = new BooleanBuilder();
        where.and(qEventType.deleted.isFalse());
        return eventTypeRepository.findAll(where, withSort);
    }
}
