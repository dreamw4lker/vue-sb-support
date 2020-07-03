package com.betanet.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResultBean {
    private Integer engineerId;
    private String engineerFio;
    private List<EventTypeSum> eventTypes;
    private Long quarterSum;
}