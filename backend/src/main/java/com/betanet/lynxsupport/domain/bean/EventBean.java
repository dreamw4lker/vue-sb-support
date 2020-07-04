package com.betanet.lynxsupport.domain.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventBean {
    private Integer id;
    private Integer engineerId;
    private String eventDate;
    private Integer timeSpent;
    private Integer eventTypeId;
    private Integer placeId;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}