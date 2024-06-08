package org.ais.repository;

import org.ais.model.Staff;
import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * This class handles all the database related operation for authentication for Admin and Management staffs
 */
public class StaffRepository {
    private static StaffRepository instance;
    /**
     * Creates the object of this class if not already exists
     * @return object
     */
    public static StaffRepository getInstance() {
        if (instance == null) {
            instance = new StaffRepository();
        }
        return instance;
    }
    /**
     * Check if user with the provided username exists or not
     * if exits return 'Recruit' as user role ese empty string
     * @param user
     * @return boolean
     */
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
    /**
     * Validate password against stored password in database
     * @param user
     * @return
     */
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
