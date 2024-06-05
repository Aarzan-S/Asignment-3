package org.ais.repository;

import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class AdminLogRepository {
    private static AdminLogRepository instance;

    public static AdminLogRepository getInstance() {
        if (instance == null) {
            instance = new AdminLogRepository();
        }
        return instance;
    }

    public void logAction(Map<String, String> details){
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "insert into admin_log (admin_username, action_type, recruit_username, timestamp) values(?,?,?,now())";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, details.get("user"));
                preparedStatement.setString(2, details.get("action"));
                preparedStatement.setString(3, details.get("recruit"));
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
