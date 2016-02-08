package com.travel.app.service;

import com.travel.app.domain.People;
import com.travel.app.web.rest.dto.PeopleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

/**
 * Service Interface for managing People.
 */
public interface PeopleService {

    /**
     * Save a people.
     * @return the persisted entity
     */
    public PeopleDTO save(PeopleDTO peopleDTO);

    /**
     *  get all the peoples.
     *  @return the list of entities
     */
    public Page<People> findAll(Pageable pageable);

    /**
     *  get the "id" people.
     *  @return the entity
     */
    public PeopleDTO findOne(String id);

    /**
     *  delete the "id" people.
     */
    public void delete(String id);
}
