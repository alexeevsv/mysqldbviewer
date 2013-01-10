package com.smdslim;

import java.sql.*;

public class DataBaseConnection {

    Connection con;

    public DataBaseConnection(String serverAddress, String userName, String password, String dbName) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://" + serverAddress + ":3306/" + dbName, userName, password);
    }

    public void query(String sql) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
        }
    }
}
