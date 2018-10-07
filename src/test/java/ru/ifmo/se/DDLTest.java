package ru.ifmo.se;

import org.junit.Test;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class DDLTest {
    static private boolean tableExists = false;

    @Test
    public void testCreatingTable() { //need to tunnel first ssh -L localhost:9999:"pg":5432 user@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "user", "password");
            DDL ddl = new DDL(connection.getConnection());
            if (!tableExists) {
                assertTrue(ddl.createTable(Car.class) == 0);
            }
            else
                assertTrue(ddl.createTable(Car.class) == -1);
            tableExists = true;
        } catch (SQLException | NotAvailableForJORMClass e){
            e.printStackTrace();
        }
    }

    @Test
    public void testDroppingTable(){
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "user", "password");
            DDL ddl = new DDL(connection.getConnection());
            if (tableExists) {
                assertTrue(ddl.dropTable(Car.class) == 0);
                tableExists = false;
            }
            else
                assertTrue(ddl.dropTable(Car.class) == -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
