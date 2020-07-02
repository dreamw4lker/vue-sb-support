package com.betanet.service.api;

import com.betanet.domain.Event;
import com.betanet.domain.bean.EventBean;
import com.betanet.domain.bean.YearSimpleSelectBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    Page<Event> getEvents(Pageable pageable, String dateFrom, String dateTo);

    List<YearSimpleSelectBean> getEventYears();

    boolean createEvent(EventBean eventBean);
}
