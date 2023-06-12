package com.bluecedar.service.events.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class LogEvent {
    @Column(name = "resourceownerid")
    private String resourceOwnerId;
    @Column(name = "bcnrequestid")
    private String bcnRequestId;
    @Id
    private String event;
    private String message;
    private String service;
    @Column(name = "ts")
    private String timestamp;

    @Column(name = "dt")
    private String date;  // yyyy-mm-dd
}
