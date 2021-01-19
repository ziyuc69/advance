package com.ziyu.mapstruct.quickstart;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author ziyu
 */
public class QuickStartTest {

    @Test
    public void shouldMapCarToDto() {
        //given
        Car car = new Car( "Morris", 5, CarType.SEDAN );

        //when
        CarDto carDto = CarMapper.INSTANCE.carToCarDto( car );

        //then
        Assert.assertNotNull(carDto);
        Assert.assertEquals("Morris", carDto.getMake());
        Assert.assertEquals(5, carDto.getSeatCount());
        Assert.assertEquals("SEDAN", carDto.getType());
    }
}
