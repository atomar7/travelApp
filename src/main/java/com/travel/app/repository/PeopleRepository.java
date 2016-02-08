package com.travel.app.repository;

import com.travel.app.domain.People;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the People entity.
 */
public interface PeopleRepository extends MongoRepository<People,String> {

}
