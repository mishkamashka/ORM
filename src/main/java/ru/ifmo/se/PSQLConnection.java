package ru.ifmo.se;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PSQLConnection {
    private java.sql.Connection connection;
    private PGSimpleDataSource dataSource;

    public PSQLConnection(String serverName, int port, String bdName, String user, String password) throws SQLException{
        dataSource = new PGSimpleDataSource();

        dataSource.setServerName(serverName);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(bdName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        this.connection = dataSource.getConnection();
        Statement stmt=connection.createStatement();
        ResultSet rs=stmt.executeQuery("select * from person");
        while(rs.next()){
            System.out.println(rs.getString(2));
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
