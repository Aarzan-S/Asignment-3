package org.ais.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUtil {
    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost", "root", "root");
            System.out.println("Successfully connected to database");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/AIS-R-DB", "root", "root");
            System.out.println("Successfully connected to AIS-R-DB schema");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void initializeDB() throws Exception {
        Connection connection = connectDb();
        if (connection == null) {
            throw new Exception("Could not connect to database.");
        }
        String dbCreateSql = "create schema if not exists `AIS-R-DB`";
        String recruitSQL = "create table if not exists `AIS-R-DB`.recruit(" +
                " id int auto_increment primary key not null, first_name varchar(50), last_name varchar(50)," +
                " address varchar(255), phone_number varchar(50), email_address varchar(100)," +
                " username varchar(50), password varchar(255), interview_date varchar(100)," +
                " highest_qualification varchar(50), department varchar(50), location varchar(50),  " +
                " recruited_by varchar(50), recruited_on varchar(100) )";

        String staffSQL = "create table if not exists `AIS-R-DB`.staff(" +
                " id int auto_increment primary key not null, first_name varchar(50), last_name varchar(50)," +
                " address varchar(255), phone_number varchar(50), email_address varchar(100)," +
                " username varchar(50), password varchar(255), staff_id varchar(50)," +
                " position_type varchar(50) ,management_level varchar(50), branch varchar(50))";

        try {

            connection.prepareStatement(dbCreateSql).execute();
            connection.prepareStatement(recruitSQL).execute();
            connection.prepareStatement(staffSQL).execute();
            loadStaffData(connection);
//            loadRecruitData(connection);
        } catch (SQLException ex) {
            System.out.println();
        } finally {
            connection.close();
        }
        System.out.println("DB initialization complete.");
    }

    private static void loadStaffData(Connection connection) {
        String sql = "insert into `AIS-R-DB`.staff (first_name, last_name, address, phone_number, email_address, username, password, staff_id, " +
                " position_type, management_level, branch) values(?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            BufferedReader reader = new BufferedReader(new FileReader("staff.csv"));
            reader.lines().skip(1).forEach(row -> {
                String[] data = row.split(",");
                try {
                    String[] name = data[0].split(" ");
                    preparedStatement.setString(1, name[0]);
                    preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                    preparedStatement.setString(3, data[1]);
                    preparedStatement.setLong(4, Long.parseLong(data[2]));
                    preparedStatement.setString(5, data[3]);
                    preparedStatement.setString(6, data[4]);
                    preparedStatement.setString(7, data[5]);
                    preparedStatement.setString(8, data[6]);
                    preparedStatement.setString(9, data[7]);
                    preparedStatement.setString(10, data[8]);
                    preparedStatement.setString(11, data[9]);
                    preparedStatement.addBatch();
                } catch (SQLException ignored) {
                }
            });
            preparedStatement.executeBatch();
            System.out.println("Staff data saved to database.");
        } catch (IOException ex) {
            System.out.println("Could not read staff csv file");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadRecruitData(Connection connection) {
        String sql = "insert into `AIS-R-DB`.recruit (first_name, last_name, address, phone_number, email_address, username, password," +
                " interview_date, highest_qualification, department, location, recruited_by, recruited_on)" +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            BufferedReader reader = new BufferedReader(new FileReader("recruit.csv"));
            reader.lines().skip(1).forEach(row -> {
                String[] data = row.split(",");
                try {
                    String[] name = data[0].split(" ");
                    preparedStatement.setString(1, name[0]);
                    preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                    preparedStatement.setString(3, data[1]);
                    preparedStatement.setLong(4, Long.parseLong(data[2]));
                    preparedStatement.setString(5, data[3]);
                    preparedStatement.setString(6, data[4]);
                    preparedStatement.setString(7, data[5]);
                    preparedStatement.setObject(8, data[6]);
                    preparedStatement.setString(9, data[7]);
                    preparedStatement.setString(10, data[8]);
                    preparedStatement.setString(11, data[9]);
                    preparedStatement.setString(12, data[10]);
                    preparedStatement.setObject(13, data[11]);
                    preparedStatement.addBatch();
                } catch (SQLException ignored) {
                    System.out.println(ignored);
                }
            });
            preparedStatement.executeBatch();
            System.out.println("Recruit data saved to database.");
        } catch (IOException ex) {
            System.out.println("Could not read recruit csv file");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}