package com.travel.app.web.rest.mapper;

import com.travel.app.domain.*;
import com.travel.app.web.rest.dto.BlogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Blog and its DTO BlogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BlogMapper {

    BlogDTO blogToBlogDTO(Blog blog);

    Blog blogDTOToBlog(BlogDTO blogDTO);
}
