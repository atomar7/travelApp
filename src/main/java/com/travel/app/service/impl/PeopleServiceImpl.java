package com.travel.app.service.impl;

import com.travel.app.service.PeopleService;
import com.travel.app.domain.People;
import com.travel.app.repository.PeopleRepository;
import com.travel.app.web.rest.dto.PeopleDTO;
import com.travel.app.web.rest.mapper.PeopleMapper;
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
 * Service Implementation for managing People.
 */
@Service
public class PeopleServiceImpl implements PeopleService{

    private final Logger log = LoggerFactory.getLogger(PeopleServiceImpl.class);
    
    @Inject
    private PeopleRepository peopleRepository;
    
    @Inject
    private PeopleMapper peopleMapper;
    
    /**
     * Save a people.
     * @return the persisted entity
     */
    public PeopleDTO save(PeopleDTO peopleDTO) {
        log.debug("Request to save People : {}", peopleDTO);
        People people = peopleMapper.peopleDTOToPeople(peopleDTO);
        people = peopleRepository.save(people);
        PeopleDTO result = peopleMapper.peopleToPeopleDTO(people);
        return result;
    }

    /**
     *  get all the peoples.
     *  @return the list of entities
     */
    public Page<People> findAll(Pageable pageable) {
        log.debug("Request to get all Peoples");
        Page<People> result = peopleRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one people by id.
     *  @return the entity
     */
    public PeopleDTO findOne(String id) {
        log.debug("Request to get People : {}", id);
        People people = peopleRepository.findOne(id);
        PeopleDTO peopleDTO = peopleMapper.peopleToPeopleDTO(people);
        return peopleDTO;
    }

    /**
     *  delete the  people by id.
     */
    public void delete(String id) {
        log.debug("Request to delete People : {}", id);
        peopleRepository.delete(id);
    }
}
