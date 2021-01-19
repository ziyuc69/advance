package com.ziyu.mapstruct.iterablemapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ziyu
 */
@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping( source = "myStrings", target = "targets")
    Target toTarget(Source s);
}
