package com.ziyu.mapstruct.fieldmapping;

/**
 * @author ziyu
 */
public class Person {

    private String name;
    private Apple apple;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", apple=" + apple +
                '}';
    }
}
