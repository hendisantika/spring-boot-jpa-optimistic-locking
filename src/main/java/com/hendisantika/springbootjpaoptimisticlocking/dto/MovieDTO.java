package com.hendisantika.springbootjpaoptimisticlocking.dto;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jpa-optimistic-locking
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-21
 * Time: 03:30
 * To change this template use File | Settings | File Templates.
 */
public class MovieDTO implements Serializable {

    private Integer id;
    private Integer version;
    private String title;
    private Integer rating;

    public Integer getId() {
        return id;
    }

    public MovieDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public MovieDTO setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MovieDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public MovieDTO setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
