package com.betanet.lynxsupport.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Table(name = "events", schema = "main")
@Getter
@Entity
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private Engineer engineer;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "time_spent")
    private Integer timeSpent;

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @Column(name = "create_date")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "deleted")
    private boolean deleted;
}
