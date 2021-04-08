package com.betanet.lynxsupport.controller;

import com.betanet.lynxsupport.domain.Event;
import com.betanet.lynxsupport.domain.bean.EventBean;
import com.betanet.lynxsupport.domain.bean.EventListBean;
import com.betanet.lynxsupport.service.api.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EventController {

    private final int REQUEST_LIST_MAX_SIZE = 10000;

    @Autowired
    private EventService eventService;

    @PostMapping("/event/create")
    public ResponseEntity createEvent(@RequestBody EventBean eventBean) {
        return ResponseEntity.ok().body(eventService.createEvent(eventBean));
    }

    @GetMapping(path = "/event/list")
    public ResponseEntity<?> getEvents(
            @RequestParam(name = "dateFrom") String dateFrom, 
            @RequestParam(name = "dateTo") String dateTo
    ) {
        Pageable pageable = PageRequest.of(0, REQUEST_LIST_MAX_SIZE);
        Page<Event> events = eventService.getEvents(pageable, dateFrom, dateTo);

        Map result = new HashMap<>();
        result.put("events", events.getContent()
                .stream()
                .map(item -> new EventListBean(
                        item.getId(),
                        item.getCreateDate(),
                        item.getEventDate(),
                        item.getEngineer().getFio(),
                        item.getTimeSpent(),
                        item.getEventType().getEventType(),
                        item.getDescription()
                ))
                .collect(Collectors.toList()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
