package com.smdslim;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class DataBaseConnection {

    Connection con;
    ArrayList<ArrayList<String>> queryResult;
    ArrayList<String> tableHeaders;

    public DataBaseConnection(String serverAddress, String userName, String password, String dbName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://" + serverAddress + ":3306/" + dbName, userName, password);
    }

    public void query(String sql) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int columnsAmount = resultSet.getMetaData().getColumnCount();
        setHeaders(resultSet.getMetaData(), columnsAmount);
        fetchData(resultSet, columnsAmount);
    }

    public void setHeaders(ResultSetMetaData meta, int columnsAmount) throws SQLException{
        tableHeaders = new ArrayList<String>();
        for (int i = 1; i <= columnsAmount; i++) {
            tableHeaders.add(meta.getColumnName(i));
        }
    }

    public void fetchData(ResultSet resultSet, int columnsAmount) throws SQLException{
        queryResult = new ArrayList<ArrayList<String>>();
        int counter = 0;
        while (resultSet.next()) {
            queryResult.add(new ArrayList<String>());
            for (int i = 1; i <= columnsAmount; i++) {
                queryResult.get(counter).add(resultSet.getString(i));
            }
            counter++;
        }
    }
}
