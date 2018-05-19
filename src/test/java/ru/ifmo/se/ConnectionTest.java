package ru.ifmo.se;

import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

    @Test
    public void testCreatingHelios() { //need to tunnel first ssh -L localhost:9999:"pg":5432 s243877@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "s243877", "joc574");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUsingConnectionCreate() { //need to tunnel first ssh -L localhost:9999:"pg":5432 s243877@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "s243877", "joc574");
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate("create table test();");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUsingConnectionDrop() { //need to tunnel first ssh -L localhost:9999:"pg":5432 s243877@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "s243877", "joc574");
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate("drop table test;");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
