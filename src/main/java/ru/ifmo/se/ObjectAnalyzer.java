package ru.ifmo.se;

import ru.ifmo.se.annotations.*;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ObjectAnalyzer {
    static Map<String, String> columns; //name, type
    static Map<String, String> values; //name, value

    public static Map<String, String> getColumns(Class object) throws NotAvailableForJORMClass{
        columns = new TreeMap<>();
        if (!object.isAnnotationPresent(JORM.class)){
            throw new NotAvailableForJORMClass("Class " + object.toString() + " is not annotated with @JORM");
        }
        Field[] fields = object.getDeclaredFields();
        String type;
        for (Field field: fields){
            if (!field.isAnnotationPresent(JORMIgnoredColumn.class)){
                type = field.getType().getSimpleName();
                switch (type) {
                    case "long":
                    case "int":
                    case "byte":
                        columns.put(field.getName(), "int");
                        break;
                    case "String":
                        columns.put(field.getName(), "text");
                }
            }
        }
        if (columns.isEmpty()){
            throw new NotAvailableForJORMClass("No fields can be presented as columns");
        }
        return columns;
    }

    static public Map<String, String> getValues(Object object) throws NotAvailableForJORMClass{
        values = new TreeMap<>();
        ObjectAnalyzer.getColumns(object.getClass());
        Field[] fields = object.getClass().getDeclaredFields();
        boolean makePrivate;

        for (Map.Entry<String, String> column: columns.entrySet()){
            String name = column.getKey();
            String type = column.getValue();
            for (Field field: fields){
                makePrivate = false;
                try{
                   field.get(object);
                } catch (IllegalAccessException e){
                    field.setAccessible(true);
                    makePrivate = true;
                }
                System.out.println(name);
                try {
                    if (field.getName().equals(name)) {
                        System.out.println(field.getName() + " checked");
                        if (type.equals("text"))
                            try {
                                values.put(field.getName(), "'" + field.get(object).toString() + "'");
                            }catch (NullPointerException e){
                                values.put(field.getName(), "null");
                            }
                        else
                            try {
                                values.put(field.getType().getSimpleName(), field.get(object).toString());
                            }catch (NullPointerException e){
                                values.put(field.getName(), "null");
                            }
                    }
                    if (makePrivate)
                        field.setAccessible(false);
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }

        return values;
    }
}
