package com.wang.supermarket.model;

import lombok.Data;

import java.util.Date;
@Data
public class User {
    private String username;
    private String password;
    private Integer id;
    private Integer sex;
    private Integer age;
    private String mobile;
    private String address;
    private Date create_time;
    private Date update_time;
    private Integer is_del;
    private Integer role;


    public Integer getRole() {
        return role;
    }

    public Integer getIs_del() {
        return is_del;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSex() {
        return sex;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
