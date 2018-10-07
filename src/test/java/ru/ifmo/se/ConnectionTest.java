package ru.ifmo.se;

import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {

    @Test
    public void test() {
        String s = "a";
        int i = 0;
        System.out.println(s.getClass().getSimpleName());
    }

    @Test
    public void testCreatingHelios() { //need to tunnel first ssh -L localhost:9999:"pg":5432 user@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "user", "password");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUsingConnectionCreate() {
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "user", "password");
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate("create table test();");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testUsingConnectionDrop() { //need to tunnel first ssh -L localhost:9999:"pg":5432 user@se.ifmo.ru -p 2222
        try{
            PSQLConnection connection = new PSQLConnection("localhost", 9999, "studs", "user", "password");
            Statement statement = connection.getConnection().createStatement();
            statement.executeUpdate("drop table test;");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
