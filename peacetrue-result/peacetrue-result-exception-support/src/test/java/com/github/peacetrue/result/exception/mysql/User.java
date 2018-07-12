package com.github.peacetrue.result.exception.mysql;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
//tag::class[]
public class User {
//end::class[]

    @Id
    @GeneratedValue
    //tag::class[]
    private Long id;
    @NotNull
    @Size(min = 4, max = 10)
    @Column(unique = true)
    private String name;
    @NotNull
    @Size(min = 4, max = 10)
    private String password;
    private Date createdTime;
    //end::class[]

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

//tag::class[]
}
//end::class[]
