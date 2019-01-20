package com.hendisantika.springbootjpaoptimisticlocking.repository;

import com.hendisantika.springbootjpaoptimisticlocking.domain.Movie;
import org.springframework.data.repository.CrudRepository;

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
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Movie findByTitle(String title);
}