package com.betanet.lynxsupport.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class EventListBean {
    private Integer id;
    private LocalDateTime createDate;
    private LocalDateTime eventDate;
    private String fio;
    private Integer timeSpent;
    private String eventType;
    private String description;
}
