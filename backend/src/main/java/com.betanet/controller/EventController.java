package com.betanet.controller;

import com.betanet.domain.bean.EventBean;
import com.betanet.service.api.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/event/create")
    public ResponseEntity createEvent(@RequestBody EventBean eventBean) {
        return ResponseEntity.ok().body(eventService.createEvent(eventBean));
    }
}
