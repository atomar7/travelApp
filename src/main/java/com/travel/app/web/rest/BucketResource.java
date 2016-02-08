package com.travel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.travel.app.domain.Bucket;
import com.travel.app.service.BucketService;
import com.travel.app.web.rest.util.HeaderUtil;
import com.travel.app.web.rest.util.PaginationUtil;
import com.travel.app.web.rest.dto.BucketDTO;
import com.travel.app.web.rest.mapper.BucketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Bucket.
 */
@RestController
@RequestMapping("/api")
public class BucketResource {

    private final Logger log = LoggerFactory.getLogger(BucketResource.class);
        
    @Inject
    private BucketService bucketService;
    
    @Inject
    private BucketMapper bucketMapper;
    
    /**
     * POST  /buckets -> Create a new bucket.
     */
    @RequestMapping(value = "/buckets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BucketDTO> createBucket(@RequestBody BucketDTO bucketDTO) throws URISyntaxException {
        log.debug("REST request to save Bucket : {}", bucketDTO);
        if (bucketDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bucket", "idexists", "A new bucket cannot already have an ID")).body(null);
        }
        BucketDTO result = bucketService.save(bucketDTO);
        return ResponseEntity.created(new URI("/api/buckets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bucket", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buckets -> Updates an existing bucket.
     */
    @RequestMapping(value = "/buckets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BucketDTO> updateBucket(@RequestBody BucketDTO bucketDTO) throws URISyntaxException {
        log.debug("REST request to update Bucket : {}", bucketDTO);
        if (bucketDTO.getId() == null) {
            return createBucket(bucketDTO);
        }
        BucketDTO result = bucketService.save(bucketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bucket", bucketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buckets -> get all the buckets.
     */
    @RequestMapping(value = "/buckets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<BucketDTO>> getAllBuckets(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Buckets");
        Page<Bucket> page = bucketService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buckets");
        return new ResponseEntity<>(page.getContent().stream()
            .map(bucketMapper::bucketToBucketDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /buckets/:id -> get the "id" bucket.
     */
    @RequestMapping(value = "/buckets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BucketDTO> getBucket(@PathVariable String id) {
        log.debug("REST request to get Bucket : {}", id);
        BucketDTO bucketDTO = bucketService.findOne(id);
        return Optional.ofNullable(bucketDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /buckets/:id -> delete the "id" bucket.
     */
    @RequestMapping(value = "/buckets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBucket(@PathVariable String id) {
        log.debug("REST request to delete Bucket : {}", id);
        bucketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bucket", id.toString())).build();
    }
}
