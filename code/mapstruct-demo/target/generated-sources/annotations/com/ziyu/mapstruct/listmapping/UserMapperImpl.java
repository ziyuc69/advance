package com.ziyu.mapstruct.listmapping;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T14:37:54+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class UserMapperImpl implements ClassRoomMapper {

    @Override
    public ClassRoomDto classRoomToClassRoomDto(ClassRoom classRoom) {
        if ( classRoom == null ) {
            return null;
        }

        ClassRoomDto classRoomDto = new ClassRoomDto();

        return classRoomDto;
    }
}
