package com.betanet.lynxsupport.service.impl;

import com.betanet.lynxsupport.domain.Engineer;
import com.betanet.lynxsupport.domain.Event;
import com.betanet.lynxsupport.domain.QEngineer;
import com.betanet.lynxsupport.domain.QEvent;
import com.betanet.lynxsupport.domain.bean.EventBean;
import com.betanet.lynxsupport.domain.bean.EventTypeSum;
import com.betanet.lynxsupport.domain.bean.ResultBean;
import com.betanet.lynxsupport.domain.bean.YearSimpleSelectBean;
import com.betanet.lynxsupport.repository.EngineerRepository;
import com.betanet.lynxsupport.repository.EventRepository;
import com.betanet.lynxsupport.repository.EventTypeRepository;
import com.betanet.lynxsupport.repository.PlaceRepository;
import com.betanet.lynxsupport.service.api.EngineerService;
import com.betanet.lynxsupport.service.api.EventService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
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
import java.util.stream.Collectors;

import static com.querydsl.core.types.PathMetadataFactory.forProperty;

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
    private QEngineer qEngineer = QEngineer.engineer;

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

    @Override
    public List<ResultBean> getResults(Integer year, Integer quarter) {
        int startMonth;
        int endMonth;
        int endDay;
        List<ResultBean> resultBeanList = new ArrayList<>();

        switch (quarter) {
            case 1:
                startMonth = 1;
                endMonth = 3;
                endDay = 31;
                break;
            case 2:
                startMonth = 4;
                endMonth = 6;
                endDay = 30;
                break;
            case 3:
                startMonth = 7;
                endMonth = 9;
                endDay = 30;
                break;
            case 4:
                startMonth = 10;
                endMonth = 12;
                endDay = 31;
                break;
            default:
                return resultBeanList;
        }

        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.of(year, startMonth, 1), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.of(year, endMonth, endDay), LocalTime.MAX);

        //TODO: move to service and autowire ?
        BooleanBuilder engineersWhere = new BooleanBuilder();
        engineersWhere.and(qEngineer.deleted.isFalse());
        Iterable<Engineer> engineers = engineerRepository.findAll(engineersWhere,
                new OrderSpecifier<>(Order.ASC, qEngineer.fio));

        engineers.forEach(engineer -> {
            JPAQuery<Event> query = new JPAQuery<>(em);
            BooleanBuilder where = new BooleanBuilder();
            where.and(qEvent.deleted.isFalse());
            where.and(qEvent.eventDate.between(startDateTime, endDateTime));
            where.and(qEvent.engineer.id.eq(engineer.getId()));

            List<EventTypeSum> eventTypeSumList = query
                    .select(qEvent.eventType.id, qEvent.eventType.eventType, qEvent.timeSpent.sum())
                    .from(qEvent)
                    .where(where)
                    .groupBy(qEvent.eventType.id, qEvent.eventType.eventType)
                    .orderBy(new OrderSpecifier<>(Order.ASC, qEvent.eventType.id))
                    .fetch()
                    .stream()
                    .map(item -> new EventTypeSum(
                            item.get(qEvent.eventType.id),
                            item.get(qEvent.eventType.eventType),
                            item.get(qEvent.timeSpent.sum())
                    ))
                    .collect(Collectors.toList());
            Long quarterSum = 0L;
            for (EventTypeSum eventTypeSum : eventTypeSumList) {
                switch (eventTypeSum.getEventTypeId()) {
                    case 3:
                    case 4:
                        quarterSum += eventTypeSum.getSum();
                        break;
                    case 1:
                    case 2:
                        quarterSum -= eventTypeSum.getSum();
                }
            }
            resultBeanList.add(new ResultBean(engineer.getId(), engineer.getFio(), eventTypeSumList, quarterSum));
        });

        return resultBeanList;
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
