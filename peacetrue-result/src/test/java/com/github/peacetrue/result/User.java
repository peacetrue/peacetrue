package com.github.peacetrue.result;


//import com.github.peacetrue.validation.Layer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@UserIdentity(name = "admin", password = "admin")
//tag::class[]
public class User {
//end::class[]

    //tag::class[]
    private Long id;
    @NotNull
    @Size(min = 4, max = 10)
    private String name;
    @NotNull
    @Size(min = 4, max = 10)
    private String password;
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


//tag::class[]
}
//end::class[]
