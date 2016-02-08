package com.travel.app.web.rest.mapper;

import com.travel.app.domain.*;
import com.travel.app.web.rest.dto.PeopleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity People and its DTO PeopleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PeopleMapper {

    PeopleDTO peopleToPeopleDTO(People people);

    People peopleDTOToPeople(PeopleDTO peopleDTO);
}
