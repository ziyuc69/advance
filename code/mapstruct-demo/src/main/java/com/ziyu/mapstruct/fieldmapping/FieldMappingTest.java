package com.ziyu.mapstruct.fieldmapping;

import org.junit.Test;

/**
 * @author ziyu
 */
public class FieldMappingTest {

    @Test
    public void showFieldMapping() {
        Apple apple = new Apple("red");
        Person person = new Person();
        person.setName("zhangsan");
        person.setApple(apple);

        PersonMapper personMapper = PersonMapper.INSTANCE;
        PersonDto fromPerson = personMapper.fromPerson(person);
        System.out.println(fromPerson);

        Person toPerson = personMapper.toPerson(fromPerson);
        System.out.println(toPerson);
    }
}
