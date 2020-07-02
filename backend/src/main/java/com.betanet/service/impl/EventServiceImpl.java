package com.betanet.service.impl;

import com.betanet.domain.Event;
import com.betanet.domain.QEvent;
import com.betanet.domain.bean.EventBean;
import com.betanet.domain.bean.YearSimpleSelectBean;
import com.betanet.repository.EngineerRepository;
import com.betanet.repository.EventRepository;
import com.betanet.repository.EventTypeRepository;
import com.betanet.repository.PlaceRepository;
import com.betanet.service.api.EventService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EngineerRepository engineerRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @PersistenceContext
    private EntityManager em;

    private QEvent qEvent = QEvent.event;

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Page<Event> getEvents(Pageable pageable, String dateFrom, String dateTo) {
        Pageable withSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createDate");
        BooleanBuilder where = new BooleanBuilder();
        where.and(qEvent.deleted.isFalse());
        LocalDateTime dateTimeFrom = LocalDateTime.of(LocalDate.parse(dateFrom, DATE_TIME_FORMATTER), LocalTime.MIN);
        LocalDateTime dateTimeTo = LocalDateTime.of(LocalDate.parse(dateTo, DATE_TIME_FORMATTER), LocalTime.MAX);
        where.and(qEvent.createDate.between(dateTimeFrom, dateTimeTo));
        return eventRepository.findAll(where, withSort);
    }

    @Override
    public List<YearSimpleSelectBean> getEventYears() {
        JPAQuery<Event> queryMin = new JPAQuery<>(em);
        Event minEvent = queryMin.from(qEvent)
                .where(qEvent.deleted.isFalse())
                .orderBy(new OrderSpecifier<>(Order.ASC, qEvent.createDate))
                .fetchFirst();

        JPAQuery<Event> queryMax = new JPAQuery<>(em);
        Event maxEvent = queryMax.from(qEvent)
                .where(qEvent.deleted.isFalse())
                .orderBy(new OrderSpecifier<>(Order.DESC, qEvent.createDate))
                .fetchFirst();

        List<YearSimpleSelectBean> yearSimpleSelectBeans = new ArrayList<>();

        if (minEvent == null || maxEvent == null) {
            return yearSimpleSelectBeans;
        }

        LocalDateTime minDateTime = minEvent.getCreateDate();
        LocalDateTime maxDateTime = maxEvent.getCreateDate();
        
        if (minDateTime.isAfter(maxDateTime)) {
            return yearSimpleSelectBeans;
        }

        int indx = 0;
        for (int i = minDateTime.getYear(); i <= maxDateTime.getYear(); i++) {
            yearSimpleSelectBeans.add(new YearSimpleSelectBean(indx, String.valueOf(i)));
            indx++;
        }
        return yearSimpleSelectBeans;
    }

    @Override
    public boolean createEvent(EventBean eventBean) {
        try {
            if (eventBean == null || eventBean.getEngineerId() == null
                    || eventBean.getEventTypeId() == null || eventBean.getPlaceId() == null) {
                return false;
            }
            Event event = toEntity(null, eventBean);
            eventRepository.save(event);
            return true;
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
    }
    
    

    private Event toEntity(Event entity, EventBean bean) {
        if (entity == null) {
            entity = new Event();
        } else {
            entity.setUpdateDate(LocalDateTime.now());
        }
        entity.setEngineer(engineerRepository.getOne(bean.getEngineerId()));
        entity.setEventDate(LocalDateTime.of(LocalDate.parse(bean.getEventDate(), DATE_TIME_FORMATTER), LocalTime.MIN));
        entity.setTimeSpent(bean.getTimeSpent());
        entity.setEventType(eventTypeRepository.getOne(bean.getEventTypeId()));
        entity.setPlace(placeRepository.getOne(bean.getPlaceId()));
        entity.setDescription(bean.getDescription());
        return entity;
    }
}
