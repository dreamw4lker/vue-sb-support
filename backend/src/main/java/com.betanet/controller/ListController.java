package com.betanet.controller;

import com.betanet.domain.Engineer;
import com.betanet.domain.bean.EngineerSimpleListBean;
import com.betanet.service.EngineerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ListController {

    @Autowired
    private EngineerService engineerService;

    @GetMapping(path = "/list/engineers")
    public ResponseEntity<?> getEngineers() {
        Pageable pageable = PageRequest.of(0, 15); //Pageable.unpaged();
        Page<Engineer> engineers = engineerService.getEngineers(pageable);

        Map result = new HashMap<>();
        result.put("engineers", engineers.getContent()
                .stream()
                .map(item -> new EngineerSimpleListBean(item.getId(), item.getFio()))
                .collect(Collectors.toList()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
