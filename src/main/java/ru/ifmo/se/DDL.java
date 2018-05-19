package ru.ifmo.se;

import ru.ifmo.se.annotations.JORM;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.util.HashMap;
import java.util.Map;


public class DDL {
    Map<String, String> columns = new HashMap<String, String>();

    public void createTable(Object object) throws NotAvailableForJORMClass{
        columns = ObjectAnalyzer.getColumns(object);
        String dbname = object.getClass().getSimpleName();
    }

}
