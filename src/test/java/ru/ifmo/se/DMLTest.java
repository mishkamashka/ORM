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
    public void testSelectColumns() {
        String[] strings = {"id", "serialNumber"};
        try{
            ResultSet set = dml.select(Car.class, strings);
            dml.printResult(dml.toArrayList(set));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectCond() {
        String[] strings = {"id", "serialNumber"};
        String[] cond = {"id > 2", "serialNumber > 0"};
        try{
            ResultSet set = dml.select(Car.class, strings, cond);
            dml.printResult(dml.toArrayList(set));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectCondNoCol() {
        String[] strings = {};
        String[] cond = {"id > 2", "serialNumber > 0"};
        try{
            ResultSet set = dml.select(Car.class, strings, cond);
            dml.printResult(dml.toArrayList(set));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSelectColumnNoCond() {
        String[] strings = {"id", "serialNumber"};
        String[] cond = {};
        try{
            ResultSet set = dml.select(Car.class, strings, cond);
            dml.printResult(dml.toArrayList(set));
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertNoVarSet() {
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

    @Test
    public void testDelete() {
        try{
            dml.delete(Car.class, 1);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteWithConditions() {
        String[] cond = {"id > 3", "serialNumber = 0"};
        try{
            dml.delete(Car.class, cond);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
