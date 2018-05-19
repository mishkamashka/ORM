package ru.ifmo.se.exceptions;

public class NotAvailableForJORMClass extends java.lang.Exception {
    String reason;

    public NotAvailableForJORMClass(String reason){
        this.reason = reason;
    }

    @Override
    public String toString() {
        return super.toString() + this.reason;
    }
}
