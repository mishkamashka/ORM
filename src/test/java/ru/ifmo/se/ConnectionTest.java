package ru.ifmo.se;

import org.junit.Test;

import java.sql.SQLException;

public class ConnectionTest {

    @Test
    public void testCreatingHelios() { //need to tunnel first ssh -L localhost:9999:"pg":5432 s243877@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "s243877", "joc574");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
