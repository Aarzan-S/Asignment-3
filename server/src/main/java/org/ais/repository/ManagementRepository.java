package org.ais.repository;

import org.ais.model.ManagementStaff;
import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
/**
 * This class handles all the database related operation for management such as register, update
 */
public class ManagementRepository {
    private static ManagementRepository instance;
    /**
     * Creates the object of this class if not already exists
     * @return object
     */
    public static ManagementRepository getInstance() {
        if (instance == null) {
            instance = new ManagementRepository();
        }
        return instance;
    }

    /**
     * Adds management details to database
     * @param mngStaff
     */
    public void register(ManagementStaff mngStaff) {
        String staffId = generateManagementStaffId();
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "insert into staff (first_name, last_name, address, phone_number, email_address, username," +
                    " password, staff_id, management_level, branch) values(?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = mngStaff.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, mngStaff.getAddress());
                preparedStatement.setLong(4, mngStaff.getPhoneNumber());
                preparedStatement.setString(5, mngStaff.getEmail());
                preparedStatement.setString(6, mngStaff.getUsername());
                preparedStatement.setString(7, mngStaff.getPassword());
                preparedStatement.setString(8, staffId);
                preparedStatement.setString(9, mngStaff.getLevel());
                preparedStatement.setString(10, mngStaff.getBranch());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches all management staff details
     * @return LinkedList<ManagementStaff>
     */
    public LinkedList<ManagementStaff> fetchManagementStaff() {
        LinkedList<ManagementStaff> mngStaffList = new LinkedList<>();
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select id, CONCAT(first_name, ' ', last_name) as full_name, address, phone_number, email_address," +
                    " username, management_level, branch from staff where staff_id like 'Management%' ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    ManagementStaff mngStaff = new ManagementStaff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getLong(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            "",
                            "",
                            resultSet.getString(7),
                            resultSet.getString(8)
                    );
                    mngStaffList.add(mngStaff);
                }
            }
            return mngStaffList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check if user with the provided username exists or not
     * @param mngStaff
     * @return boolean
     */
    public boolean doesUserExists(ManagementStaff mngStaff) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select username from staff where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mngStaff.getUsername());
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * Generates unique staff id
     * @return
     */
    public String generateManagementStaffId() {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "SELECT CONCAT('Management-', COUNT(*) + 1) FROM staff";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString(1);
                    }
                    return "Management-1";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Management-1";
    }

    /**
     * Update management staff deaths
     * @param mngStaff
     * @param id
     */
    public void update(ManagementStaff mngStaff, int id) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "update staff set first_name = ?, last_name= ?, address= ?, phone_number= ?, email_address= ?," +
                    " username= ?, password= ?, management_level= ?, branch= ? where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = mngStaff.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, mngStaff.getAddress());
                preparedStatement.setLong(4, mngStaff.getPhoneNumber());
                preparedStatement.setString(5, mngStaff.getEmail());
                preparedStatement.setString(6, mngStaff.getUsername());
                preparedStatement.setString(7, mngStaff.getPassword());
                preparedStatement.setString(8, mngStaff.getLevel());
                preparedStatement.setString(9, mngStaff.getBranch());
                preparedStatement.setInt(10, id);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Fetches management staff deatils by username
     * @param mngStaff
     * @return
     */
    public ManagementStaff getManagementDetails(String username) {
        ManagementStaff mngStaff = null;
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "SELECT id, CONCAT(first_name, ' ', last_name) as full_name, address, phone_number, email_address," +
                    " username, password, staff_id, management_level, branch from staff WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    mngStaff = new ManagementStaff(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getLong(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getString(10)
                    );
                }
                return mngStaff;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mngStaff;
    }
}