package ru.ifmo.se;

import org.junit.Before;
import org.junit.Test;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DMLTest {
    PSQLConnection connection;
    DML dml;
    DDL ddl;

    @Before
    public void connect(){
        try {
            connection = new PSQLConnection("localhost", 9999, "studs", "s243877", "joc574");
            ddl = new DDL(connection.getConnection());
            try {
                ddl.createTable(Car.class);
            } catch (NotAvailableForJORMClass e) {
                e.printStackTrace();
            }
            dml = new DML(connection.getConnection());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        String s = "5";
        //int i = (Integer) s;
    }

    @Test
    public void testSelectNoParam() {
        try{
            ResultSet set = dml.select(Car.class);
            dml.printResult(dml.toArrayList(set));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertNoVariabSet() {
        try{
            dml.insert(new Car());
        } catch (SQLException | NotAvailableForJORMClass e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        try{
            dml.insert(new Car(5, "g", "merc"));
        } catch (SQLException | NotAvailableForJORMClass e){
            e.printStackTrace();
        }
    }
}
