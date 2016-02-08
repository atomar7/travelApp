package com.travel.app.service.impl;

import com.travel.app.service.BucketService;
import com.travel.app.domain.Bucket;
import com.travel.app.repository.BucketRepository;
import com.travel.app.web.rest.dto.BucketDTO;
import com.travel.app.web.rest.mapper.BucketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Bucket.
 */
@Service
public class BucketServiceImpl implements BucketService{

    private final Logger log = LoggerFactory.getLogger(BucketServiceImpl.class);
    
    @Inject
    private BucketRepository bucketRepository;
    
    @Inject
    private BucketMapper bucketMapper;
    
    /**
     * Save a bucket.
     * @return the persisted entity
     */
    public BucketDTO save(BucketDTO bucketDTO) {
        log.debug("Request to save Bucket : {}", bucketDTO);
        Bucket bucket = bucketMapper.bucketDTOToBucket(bucketDTO);
        bucket = bucketRepository.save(bucket);
        BucketDTO result = bucketMapper.bucketToBucketDTO(bucket);
        return result;
    }

    /**
     *  get all the buckets.
     *  @return the list of entities
     */
    public Page<Bucket> findAll(Pageable pageable) {
        log.debug("Request to get all Buckets");
        Page<Bucket> result = bucketRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one bucket by id.
     *  @return the entity
     */
    public BucketDTO findOne(String id) {
        log.debug("Request to get Bucket : {}", id);
        Bucket bucket = bucketRepository.findOne(id);
        BucketDTO bucketDTO = bucketMapper.bucketToBucketDTO(bucket);
        return bucketDTO;
    }

    /**
     *  delete the  bucket by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Bucket : {}", id);
        bucketRepository.delete(id);
    }
}
