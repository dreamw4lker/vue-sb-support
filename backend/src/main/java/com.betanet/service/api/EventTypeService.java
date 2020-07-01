package com.betanet.service.api;

import com.betanet.domain.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventTypeService {

    Page<EventType> getEventTypes(Pageable pageable);
}
