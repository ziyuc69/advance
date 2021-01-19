package com.ziyu.mapstruct.fieldmapping;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T15:15:50+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class AppleMapperImpl implements AppleMapper {

    @Override
    public Apple toApple(AppleDto appleDto) {
        if ( appleDto == null ) {
            return null;
        }

        String color = null;

        color = appleDto.getColor();

        Apple apple = new Apple( color );

        return apple;
    }

    @Override
    public AppleDto fromApple(Apple apple) {
        if ( apple == null ) {
            return null;
        }

        String color = null;

        color = apple.getColor();

        AppleDto appleDto = new AppleDto( color );

        return appleDto;
    }
}
