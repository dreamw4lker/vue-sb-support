package com.betanet.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EventTypeSum {
    private Integer eventTypeId;
    private String eventTypeName;
    private Integer sum;
}
