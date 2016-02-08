package com.travel.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.travel.app.domain.People;
import com.travel.app.service.PeopleService;
import com.travel.app.web.rest.util.HeaderUtil;
import com.travel.app.web.rest.util.PaginationUtil;
import com.travel.app.web.rest.dto.PeopleDTO;
import com.travel.app.web.rest.mapper.PeopleMapper;
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
 * REST controller for managing People.
 */
@RestController
@RequestMapping("/api")
public class PeopleResource {

    private final Logger log = LoggerFactory.getLogger(PeopleResource.class);
        
    @Inject
    private PeopleService peopleService;
    
    @Inject
    private PeopleMapper peopleMapper;
    
    /**
     * POST  /peoples -> Create a new people.
     */
    @RequestMapping(value = "/peoples",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PeopleDTO> createPeople(@RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        log.debug("REST request to save People : {}", peopleDTO);
        if (peopleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("people", "idexists", "A new people cannot already have an ID")).body(null);
        }
        PeopleDTO result = peopleService.save(peopleDTO);
        return ResponseEntity.created(new URI("/api/peoples/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("people", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /peoples -> Updates an existing people.
     */
    @RequestMapping(value = "/peoples",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PeopleDTO> updatePeople(@RequestBody PeopleDTO peopleDTO) throws URISyntaxException {
        log.debug("REST request to update People : {}", peopleDTO);
        if (peopleDTO.getId() == null) {
            return createPeople(peopleDTO);
        }
        PeopleDTO result = peopleService.save(peopleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("people", peopleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /peoples -> get all the peoples.
     */
    @RequestMapping(value = "/peoples",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<PeopleDTO>> getAllPeoples(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Peoples");
        Page<People> page = peopleService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/peoples");
        return new ResponseEntity<>(page.getContent().stream()
            .map(peopleMapper::peopleToPeopleDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /peoples/:id -> get the "id" people.
     */
    @RequestMapping(value = "/peoples/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PeopleDTO> getPeople(@PathVariable String id) {
        log.debug("REST request to get People : {}", id);
        PeopleDTO peopleDTO = peopleService.findOne(id);
        return Optional.ofNullable(peopleDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /peoples/:id -> delete the "id" people.
     */
    @RequestMapping(value = "/peoples/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePeople(@PathVariable String id) {
        log.debug("REST request to delete People : {}", id);
        peopleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("people", id.toString())).build();
    }
}
