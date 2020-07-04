package com.betanet.lynxsupport.controller;

import com.betanet.lynxsupport.service.api.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ResultController {

    @Autowired
    private EventService eventService;

    @GetMapping(path = "/results/list")
    public ResponseEntity<?> getResults(
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "quarter") Integer quarter
    ) {

        Map result = new HashMap<>();
        result.put("results", eventService.getResults(year, quarter));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
