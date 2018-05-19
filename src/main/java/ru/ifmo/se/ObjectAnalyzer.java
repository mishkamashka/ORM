package ru.ifmo.se;

import ru.ifmo.se.annotations.*;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
public class ObjectAnalyzer {
    static Map<String, String> columns;

    public static Map<String, String> getColumns(Object object) throws NotAvailableForJORMClass{
        Class cl = object.getClass();
        columns = new HashMap<String, String>();
        if (!cl.isAnnotationPresent(JORM.class)){
            throw new NotAvailableForJORMClass("Class " + cl.toString() + " is not annotated with @JORM");
        }
        Field[] fields = cl.getDeclaredFields();
        for (Field field: fields){
            if (!field.isAnnotationPresent(JORMIgnoredColumn.class)){
                if (field.getType().equals(String.class))
                    columns.put(field.getName(), "text");
                else
                    columns.put(field.getName(), field.getType().getSimpleName());
            }
        }
        if (columns.isEmpty()){
            throw new NotAvailableForJORMClass("No fields can be presented as columns");
        }
        return columns;
    }
}
