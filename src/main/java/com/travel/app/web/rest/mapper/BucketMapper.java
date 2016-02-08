package com.travel.app.web.rest.mapper;

import com.travel.app.domain.*;
import com.travel.app.web.rest.dto.BucketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bucket and its DTO BucketDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BucketMapper {

    BucketDTO bucketToBucketDTO(Bucket bucket);

    Bucket bucketDTOToBucket(BucketDTO bucketDTO);
}
