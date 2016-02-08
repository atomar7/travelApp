package com.travel.app.service;

import com.travel.app.domain.Bucket;
import com.travel.app.web.rest.dto.BucketDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing Bucket.
 */
public interface BucketService {

    /**
     * Save a bucket.
     * @return the persisted entity
     */
    public BucketDTO save(BucketDTO bucketDTO);

    /**
     *  get all the buckets.
     *  @return the list of entities
     */
    public Page<Bucket> findAll(Pageable pageable);

    /**
     *  get the "id" bucket.
     *  @return the entity
     */
    public BucketDTO findOne(String id);

    /**
     *  delete the "id" bucket.
     */
    public void delete(String id);
}
