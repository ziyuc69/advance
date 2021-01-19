package com.ziyu.mapstruct.quickstart;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T14:18:59+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class CarMapperImpl implements CarMapper {

    @Override
    public CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setSeatCount( car.getNumberOfSeats() );
        carDto.setMake( car.getMake() );
        if ( car.getType() != null ) {
            carDto.setType( car.getType().name() );
        }

        return carDto;
    }
}
