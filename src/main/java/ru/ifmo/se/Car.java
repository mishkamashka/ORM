package ru.ifmo.se;
//it's for testing

import ru.ifmo.se.annotations.JORM;
import ru.ifmo.se.annotations.JORMIgnoredColumn;

@JORM
public class Car {
    private String manufacturer;
    private String model;
    private long serialNumber;

    @JORMIgnoredColumn
    private int[] array;

}
