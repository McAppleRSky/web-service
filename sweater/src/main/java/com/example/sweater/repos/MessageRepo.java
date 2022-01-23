package com.example.sweater.repos;

import com.example.sweater.domain.MessageAndAuthor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<MessageAndAuthor, Long> {

    // https://docs.spring.io/spring-data/jpa/docs/1.6.0.RELEASE/reference/html/jpa.repositories.html
    List<MessageAndAuthor> findByTag(String tag);

}



