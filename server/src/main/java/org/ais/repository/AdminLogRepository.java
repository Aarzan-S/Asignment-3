package org.ais.repository;

import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * This class handles all the database related operation for admin log actions
 */
public class AdminLogRepository {
    private static AdminLogRepository instance;

    /**
     * Creates the object of this class if not already exists
     *
     * @return object
     */
    public static AdminLogRepository getInstance() {
        if (instance == null) {
            instance = new AdminLogRepository();
        }
        return instance;
    }

    /**
     * Inserts new row in database for the admin activity in admin_log table
     *
     * @param details
     */
    public void logAction(Map<String, String> details) {
        int adminId = getAdminId(details.get("user"));
        int recruitId = getRecruitId(details.get("recruit"));
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "insert into admin_log (admin_id, action_type, recruit_id, timestamp) values(?,?,?,now())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, adminId);
                preparedStatement.setString(2, details.get("action"));
                preparedStatement.setInt(3, recruitId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getAdminId(String username) {
        int id = 1;
        if (username != null || !username.isEmpty()) {
            try (Connection connection = DBUtil.getConnection()) {
                String sql = "SELECT id FROM staff WHERE username = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            id = resultSet.getInt(1);
                        }
                        return id;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    private int getRecruitId(String username) {
        int id = 1;
        if (username != null || !username.isEmpty()) {

            try (Connection connection = DBUtil.getConnection()) {
                String sql = "SELECT id FROM recruit WHERE username = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            id = resultSet.getInt(1);
                        }
                        return id;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return id;
    }
}
