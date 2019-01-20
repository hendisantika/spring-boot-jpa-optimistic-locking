package com.hendisantika.springbootjpaoptimisticlocking.dto;

import com.hendisantika.springbootjpaoptimisticlocking.domain.Movie;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-jpa-optimistic-locking
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-01-21
 * Time: 03:31
 * To change this template use File | Settings | File Templates.
 */
public class Movies {
    public static MovieDTO of(Movie movie) {
        return new MovieDTO()
                .setId(movie.getId())
                .setVersion(movie.getVersion())
                .setTitle(movie.getTitle())
                .setRating(movie.getRating());
    }

    public static Movie of(MovieDTO movie) {
        return new Movie()
                .setId(movie.getId())
                .setVersion(movie.getVersion())
                .setTitle(movie.getTitle())
                .setRating(movie.getRating());
    }
}
