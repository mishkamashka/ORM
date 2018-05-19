package ru.ifmo.se;

import ru.ifmo.se.annotations.JORM;
import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class DDL {
    Map<String, String> columns = new HashMap<String, String>(); //name, type
    Connection connection;
    StringBuilder builder;

    public DDL(Connection connection){
        this.connection = connection;
    }

    public void createTable(Object object) throws NotAvailableForJORMClass, SQLException{
        columns = ObjectAnalyzer.getColumns(object);
        builder = new StringBuilder("CREATE TABLE " + object.getClass().getSimpleName() + "(id serial primary key, ");
        for (Map.Entry<String, String> column: columns.entrySet()) {
            builder.append(column.getKey() + " " + column.getValue() + ", ");
        }
        builder.delete(builder.toString().length()-2,builder.toString().length()).append(");");
        System.out.println(builder.toString());
        Statement statement = connection.createStatement();
        statement.executeUpdate(builder.toString());
    }

}
