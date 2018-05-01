package com.github.peacetrue.associate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 被关联实体类
 *
 * @author xiayx
 */
@Entity
// tag::class[]
public class Associated {
    // end::class[]
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// tag::class[]
    private Long id;
    private String code;
    private String name;
// end::class[]


    public Associated() {
    }

    public Associated(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

// tag::class[]
}
// end::class[]