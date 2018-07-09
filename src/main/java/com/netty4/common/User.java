package com.netty4.common;

/**
 * 封装简单用户数据，用于绑定管道
 *
 * @author brandon
 * Created by brandon on 2018/7/9.
 */
public class User {

    private Long id;

    private String password;

    private Integer enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public User(Long id, String password, Integer enabled) {
        this.id = id;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
