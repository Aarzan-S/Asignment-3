package org.ais.repository;

import org.ais.model.AdminStaff;
import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository {
    private static AdminRepository instance;

    public static AdminRepository getInstance() {
        if (instance == null) {
            instance = new AdminRepository();
        }
        return instance;
    }

    public void register(AdminStaff adminStaff) {
        String staffId = generateAdminStaffId();
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "insert into staff (first_name, last_name, address, phone_number, email_address, username, password, staff_id, " +
                    " position_type) values(?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = adminStaff.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, adminStaff.getAddress());
                preparedStatement.setLong(4, adminStaff.getPhoneNumber());
                preparedStatement.setString(5, adminStaff.getEmail());
                preparedStatement.setString(6, adminStaff.getUsername());
                preparedStatement.setString(7, adminStaff.getPassword());
                preparedStatement.setString(8, staffId);
                preparedStatement.setString(9, adminStaff.getPositionType());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(AdminStaff adminStaff, int id) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "update staff set first_name = ?, last_name= ?, address= ?, phone_number= ?, email_address= ?," +
                    " username= ?, password= ?, position_type = ? where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = adminStaff.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, adminStaff.getAddress());
                preparedStatement.setLong(4, adminStaff.getPhoneNumber());
                preparedStatement.setString(5, adminStaff.getEmail());
                preparedStatement.setString(6, adminStaff.getUsername());
                preparedStatement.setString(7, adminStaff.getPassword());
                preparedStatement.setString(8, adminStaff.getPositionType());
                preparedStatement.setInt(9, id);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesUserExists(AdminStaff adminStaff) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select username from staff where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, adminStaff.getUsername());
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generateAdminStaffId() {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "SELECT CONCAT('Admin-', COUNT(*) + 1) FROM staff";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(1);
                    }
                    return "Admin-1";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Admin-1";
    }

    public AdminStaff getAdminDetails(String username) {
        AdminStaff adminStaff = null;
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "SELECT id, CONCAT(first_name, ' ', last_name) as full_name, address, phone_number, email_address," +
                    " username, password, staff_id, position_type from staff WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    adminStaff = new AdminStaff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getLong(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9)
                    );
                }
                return adminStaff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminStaff;
    }
}