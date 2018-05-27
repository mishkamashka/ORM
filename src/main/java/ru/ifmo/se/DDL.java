package ru.ifmo.se;

import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class DDL {
    Map<String, String> columns = new HashMap<>(); //name, type
    Connection connection;
    StringBuilder builder;

    public DDL(Connection connection){
        this.connection = connection;
    }

    public int createTable(Class object) throws NotAvailableForJORMClass, SQLException{
        columns = ObjectAnalyzer.getColumns(object);
        builder = new StringBuilder("create table " + object.getSimpleName() + "(id serial primary key, ");
        for (Map.Entry<String, String> column: columns.entrySet()) {
            builder.append(column.getKey() + " " + column.getValue() + ", ");
        }
        builder.delete(builder.length()-2,builder.length()).append(");");
        Statement statement = connection.createStatement();
        System.out.println(builder.toString());
        try{
            statement.executeUpdate(builder.toString());
            return 0;
        } catch (SQLException e){
            return -1;
        }
    }

    public int dropTable(Class object) throws SQLException{
        Statement statement = connection.createStatement();
        try{
            statement.executeUpdate("drop table " + object.getSimpleName() + ";");
            return 0;
        } catch (SQLException e){
            return -1;
        }
    }

}
