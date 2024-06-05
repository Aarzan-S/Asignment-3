package org.ais.repository;

import org.ais.model.Staff;
import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffRepository {
    private static StaffRepository instance;

    public static StaffRepository getInstance() {
        if (instance == null) {
            instance = new StaffRepository();
        }
        return instance;
    }

    public String doesUserExist(Staff user) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select staff_id from staff where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString(1).split("-")[0];
                }
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validatePassword(Staff user) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select password from staff where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
//                    return HashUtil.verifyHash(user.getPassword(), resultSet.getString(1));
                    return user.getPassword().equals(resultSet.getString(1));
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
