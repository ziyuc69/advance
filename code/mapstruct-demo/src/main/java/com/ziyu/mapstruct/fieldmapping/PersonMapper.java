package com.ziyu.mapstruct.fieldmapping;

import com.ziyu.mapstruct.listmapping.OrderItemMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ziyu
 */
@Mapper(uses = { AppleMapper.class })
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(source = "appleDto", target = "apple")
    Person toPerson(PersonDto personDto);

    @Mapping(source = "apple", target = "appleDto")
    @InheritInverseConfiguration
    PersonDto fromPerson(Person person);
}
