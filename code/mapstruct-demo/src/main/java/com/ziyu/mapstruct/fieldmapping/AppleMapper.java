package com.ziyu.mapstruct.fieldmapping;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * @author ziyu
 */
@Mapper
public interface AppleMapper {

    Apple toApple(AppleDto appleDto);

    @InheritInverseConfiguration
    AppleDto fromApple(Apple apple);
}
