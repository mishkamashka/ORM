package ru.ifmo.se;

import org.junit.Test;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.sql.SQLException;

public class DDLTest {
    @Test
    public void testCreatingTable() { //need to tunnel first ssh -L localhost:9999:"pg":5432 s243877@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "s243877", "joc574");
            DDL ddl = new DDL(connection.getConnection());
            ddl.createTable(new Car("A","B",123));
        } catch (SQLException e){
            e.printStackTrace();
        } catch (NotAvailableForJORMClass e){
            e.printStackTrace();
        }
    }

}
