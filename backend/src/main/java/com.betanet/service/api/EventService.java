package com.betanet.service.api;

import com.betanet.domain.Event;
import com.betanet.domain.bean.EventBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
    Page<Event> getEvents(Pageable pageable, String dateFrom, String dateTo);

    boolean createEvent(EventBean eventBean);
}
