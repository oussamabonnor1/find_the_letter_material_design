package com.company;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Oussama on 26/01/2017.
 */
public class CNX {
    public static Connection connection = null;

    public static Connection dbConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(String.valueOf("jdbc:sqlite:src/com/company/Data Base/Find_The_Word.sqlite"));
            System.out.println("Connection established");

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
