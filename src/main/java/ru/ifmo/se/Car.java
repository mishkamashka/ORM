package ru.ifmo.se;
//it's for testing

import ru.ifmo.se.annotations.JORM;
import ru.ifmo.se.annotations.JORMIgnoredColumn;

@JORM
public class Car {
    private String manufacturer;
    private String model;
    private int serialNumber;

    Car(String m, String model, int i){
        manufacturer = m;
        this.model = model;
        serialNumber = i;
    }

//    @JORMIgnoredColumn
//    private int[] array;

}
