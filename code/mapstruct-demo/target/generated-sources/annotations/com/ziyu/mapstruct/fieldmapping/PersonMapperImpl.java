package com.ziyu.mapstruct.fieldmapping;

import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T15:18:16+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class PersonMapperImpl implements PersonMapper {

    private final AppleMapper appleMapper = Mappers.getMapper( AppleMapper.class );

    @Override
    public Person toPerson(PersonDto personDto) {
        if ( personDto == null ) {
            return null;
        }

        Person person = new Person();

        person.setApple( appleMapper.toApple( personDto.getAppleDto() ) );
        person.setName( personDto.getName() );

        return person;
    }

    @Override
    public PersonDto fromPerson(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDto personDto = new PersonDto();

        personDto.setAppleDto( appleMapper.fromApple( person.getApple() ) );
        personDto.setName( person.getName() );

        return personDto;
    }
}
