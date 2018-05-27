package ru.ifmo.se;

import ru.ifmo.se.annotations.JORM;
import ru.ifmo.se.annotations.JORMIgnoredColumn;

@JORM
public class Car {
    private String manufacturer;
    private String model;
    private int serialNumber;

    @JORMIgnoredColumn
    private int[] array;

    Car(){

    }

    Car(int serialNumber, String model, String m){
        manufacturer = m;
        this.model = model;
        this.serialNumber = serialNumber;
    }
}
