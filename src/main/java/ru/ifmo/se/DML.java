package ru.ifmo.se;

import ru.ifmo.se.exceptions.NotAvailableForJORMClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class DML {
    private Connection connection;

    DML(Connection connection){
        this.connection = connection;
    }

    public ResultSet select(Class table) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery("select * from " + table.getSimpleName());
        return set;
    }

    public ResultSet select(Class table, String[] columns) throws SQLException{
        StringBuilder builder = new StringBuilder("select ");
        if (columns.length > 0) {
            for (String string : columns)
                builder.append(string + ",");
            builder.deleteCharAt(builder.length() - 1).append(" from " + table.getSimpleName() + ";");
        } else
            builder.append("* from " + table.getSimpleName() + ";");
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(builder.toString());
        return set;
    }

    public ResultSet select(Class table, String[] columns, String conditions[]) throws SQLException{
        StringBuilder builder = new StringBuilder("select ");
        if (columns.length > 0) {
            for (String string : columns)
                builder.append(string + ",");
            builder.deleteCharAt(builder.length()-1).append(" from " + table.getSimpleName() + " where ");
        } else
            builder.append("* from " + table.getSimpleName() + " where ");
        if (conditions.length > 0) {
            for (String string : conditions)
                builder.append(string + " and ");
            builder.delete(builder.length() - 4, builder.length()).append(";");
        } else
            builder.delete(builder.length() - 6, builder.length()).append(";");
        Statement statement = connection.createStatement();
        System.out.println(builder.toString());
        ResultSet set = statement.executeQuery(builder.toString());
        return set;
    }

    public void insert(Object object) throws NotAvailableForJORMClass, SQLException{
        StringBuilder builder = new StringBuilder("insert into " + object.getClass().getSimpleName() + " values (DEFAULT,");
        Map<String, String> values = ObjectAnalyzer.getValues(object);
        for (Map.Entry<String, String> column: values.entrySet()){
            builder.append(column.getValue() + ",");
        }
        builder.deleteCharAt(builder.length()-1).append(");");
        Statement statement = connection.createStatement();
        statement.execute(builder.toString());
    }

    public void delete(Class table, int id) throws SQLException{

    }

    public ArrayList<ArrayList<Object>> toArrayList (ResultSet set) throws SQLException{
        ArrayList<ArrayList<Object>> resultArray = new ArrayList<>();
        ResultSetMetaData rsmd = set.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        ArrayList<Object> row;
        while (set.next()){
            row = new ArrayList<>();
            for (int i = 0; i < columnsNumber; i++)
                row.add(set.getObject(i+1));
            resultArray.add(row);
        }
        return resultArray;
    }

    public void printResult(ArrayList<ArrayList<Object>> resultArray) {
        ArrayList<Object> row;
        for (int i = 0; i < resultArray.size(); i++){
            row = resultArray.get(i);
            for (int k = 0; k < row.size(); k++){
                try{
                    System.out.print(row.get(k).toString() + " ");
                } catch (NullPointerException e){
                    System.out.print("null ");
                }
            }
            System.out.println();
        }
    }
}
