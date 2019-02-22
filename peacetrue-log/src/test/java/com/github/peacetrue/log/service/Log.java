package com.github.peacetrue.log.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//tag::class[]
@Entity
public class Log extends AbstractLog<Long, Long, Long> {
    @Id
    @GeneratedValue
    public Long getId() {
        return super.getId();
    }
}
//end::class[]