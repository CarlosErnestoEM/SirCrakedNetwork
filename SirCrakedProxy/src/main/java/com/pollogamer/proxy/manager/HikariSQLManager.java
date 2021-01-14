package com.pollogamer.proxy.manager;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class HikariSQLManager {

    private final Map<String, HikariDataSource> hikariDataSources;
    private String host;
    private int port;
    private String user;
    private String pass;
    private String database;

    public HikariSQLManager(String database) {
        this.hikariDataSources = new HashMap<>();
        this.database = database;
        host = "158.69.253.240";
        port = 3306;
        user = "root";
        pass = "knowe";
        getDataSource();
    }

    public HikariDataSource getDataSource() {
        if (hikariDataSources.containsKey(database)) {
            return hikariDataSources.get(database);
        }
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setMaximumPoolSize(8);
        hikariDataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikariDataSource.addDataSourceProperty("serverName", host);
        hikariDataSource.addDataSourceProperty("port", port);
        hikariDataSource.addDataSourceProperty("databaseName", database);
        hikariDataSource.addDataSourceProperty("user", user);
        hikariDataSource.addDataSourceProperty("password", pass);
        hikariDataSources.put(database, hikariDataSource);
        return hikariDataSource;
    }


    public Connection openConnection() {
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createTable(String table) {
        Connection connection = null;
        try {
            connection = openConnection();
            Statement state = connection.createStatement();
            state.executeUpdate(table);
        } catch (SQLException e) {
            System.out.println("La tabla no se ha podido crear");
            e.printStackTrace();
        }
    }

    public void Update(String qry) {
        Connection connection = null;
        try {
            connection = openConnection();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(qry);
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Connection connection = null;

    public ResultSet Query(String qry) {
        ResultSet rs = null;
        try {
            if (connection == null || connection.isClosed()) {
                connection = openConnection();
            }

            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery(qry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rs;
    }
}