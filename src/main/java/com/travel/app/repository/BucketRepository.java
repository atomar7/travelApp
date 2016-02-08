package com.travel.app.repository;

import com.travel.app.domain.Bucket;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Bucket entity.
 */
public interface BucketRepository extends MongoRepository<Bucket,String> {

}
