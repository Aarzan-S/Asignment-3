package org.ais.repository;

import org.ais.exception.CustomException;
import org.ais.model.Recruit;
import org.ais.util.databaseAccess.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
/**
 * This class handles all the database related operation for management such as register, update
 */
public class RecruitRepository {
    private static RecruitRepository instance;
    /**
     * Creates the object of this class if not already exists
     * @return object
     */
    public static RecruitRepository getInstance() {
        if (instance == null) {
            instance = new RecruitRepository();
        }
        return instance;
    }

    /**
     * Fetches all recruit details
     * @return
     * @throws SQLException
     */
    public LinkedList<Recruit> fetchAllRecruits() throws SQLException {
        LinkedList<Recruit> recruitList = new LinkedList<>();
        Connection connection = DBUtil.getConnection();
        if (connection == null) {
            throw new CustomException("Could not connect to database.");
        }
        String sql = "select id, CONCAT(first_name, ' ', last_name) as full_name, address, phone_number, email_address, " +
                "username, interview_date, highest_qualification, department, location, recruited_by, recruited_on " +
                "from recruit group by location, address, phone_number, email_address,username, interview_date," +
                "highest_qualification, department, location, recruited_by, recruited_on, first_name, last_name, id " +
                " order by last_name desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Recruit recruit = new Recruit(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("address"),
                        resultSet.getLong("phone_number"),
                        resultSet.getString("email_address"),
                        resultSet.getString("username"),
                        "",
                        resultSet.getObject("interview_date", LocalDate.class),
                        resultSet.getString("highest_qualification"),
                        resultSet.getString("department"),
                        resultSet.getString("location"),
                        resultSet.getString("recruited_by"),
                        resultSet.getObject("recruited_on", LocalDate.class)
                );
                recruitList.add(recruit);
            }
            return recruitList;
        } catch (SQLException e) {
            throw new CustomException("Could npt fetch recruit list");
        } finally {
            connection.close();
        }
    }

    /**
     * Updates recruit details only location and department
     * @param recruit
     * @param id
     */
    public void update(Recruit recruit, int id) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "update recruit set department = ? , location = ? where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, recruit.getDepartment());
                preparedStatement.setString(2, recruit.getLocation());
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches recruit history
     * @return
     */
    public LinkedList<Recruit> fetchRecruitHistory() {
        LinkedList<Recruit> recruitList = new LinkedList<>();
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select CONCAT(first_name, ' ', last_name) as full_name, phone_number, email_address, username," +
                    " recruited_by, recruited_on from recruit group by location, phone_number, email_address, username," +
                    "  recruited_by, recruited_on, first_name, last_name order by last_name desc";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Recruit recruit = new Recruit(
                            resultSet.getString(1),
                            resultSet.getLong(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getObject(6, LocalDate.class)
                    );
                    recruitList.add(recruit);
                }
            }
            return recruitList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Saves recruit details
     * @param recruit
     * @return
     */
    public boolean register(Recruit recruit) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "insert into recruit (first_name, last_name, address, phone_number, email_address, username, password," +
                    " interview_date, highest_qualification, department, location, recruited_by, recruited_on)" +
                    " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = recruit.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, recruit.getAddress());
                preparedStatement.setLong(4, recruit.getPhoneNumber());
                preparedStatement.setString(5, recruit.getEmail());
                preparedStatement.setString(6, recruit.getUsername());
                preparedStatement.setString(7, recruit.getPassword());
                preparedStatement.setObject(8, recruit.getInterviewDate());
                preparedStatement.setString(9, recruit.getHighestQualification());
                preparedStatement.setString(10, recruit.getDepartment());
                preparedStatement.setString(11, recruit.getLocation());
                preparedStatement.setString(12, recruit.getRecruitedBy());
                preparedStatement.setObject(13, recruit.getRecruitedOn());
                return preparedStatement.executeUpdate() == 1;
            }
        } catch (SQLException e) {
            throw new CustomException("Failed to register recruit : " + e.getMessage());
        }
    }

    /**
     * Update recruit details by recruit
     * @param recruit
     * @param id
     */
    public void updateDetails(Recruit recruit, int id) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "update recruit set first_name = ?, last_name = ?, address = ?, phone_number= ?, email_address =?, username=?," +
                    " password =?, highest_qualification = ? where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = recruit.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, recruit.getAddress());
                preparedStatement.setLong(4, recruit.getPhoneNumber());
                preparedStatement.setString(5, recruit.getEmail());
                preparedStatement.setString(6, recruit.getUsername());
                preparedStatement.setString(7, recruit.getPassword());
                preparedStatement.setString(8, recruit.getHighestQualification());
                preparedStatement.setInt(9, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles update of recruit details by admin staff
     * @param recruit
     * @param id
     */
    public void updateDetailsByStaff(Recruit recruit, int id) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "update recruit set first_name = ?, last_name = ?, address = ?, phone_number= ?, email_address =?, username=?," +
                    " highest_qualification = ?, location =?, department =? where id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String[] name = recruit.getFullName().split(" ");
                preparedStatement.setString(1, name[0]);
                preparedStatement.setString(2, name.length > 1 ? name[1] : "");
                preparedStatement.setString(3, recruit.getAddress());
                preparedStatement.setLong(4, recruit.getPhoneNumber());
                preparedStatement.setString(5, recruit.getEmail());
                preparedStatement.setString(6, recruit.getUsername());
                preparedStatement.setString(7, recruit.getHighestQualification());
                preparedStatement.setString(8, recruit.getLocation());
                preparedStatement.setString(9, recruit.getDepartment());
                preparedStatement.setInt(10, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Check if user with the provided username exists or not
     * @param recruit
     * @return boolean
     */
    public boolean doesUserExists(Recruit recruit) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select username from recruit where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, recruit.getUsername());
                return preparedStatement.executeQuery().next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if user with the provided username exists or not
     * if exits return 'Recruit' as user role ese empty string
     * @param user
     * @return boolean
     */
    public String doesUserExist(Recruit user) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select username from recruit where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return "Recruit";
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
    public boolean validatePassword(Recruit user) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select password from recruit where username = ?";
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

    /**
     * Fetches recruit details by username
     * @param username
     * @return
     */
    public Recruit getRecruitDetails(String username) {
        Recruit recruit = null;
        String sql = "select id, CONCAT(first_name, ' ', last_name) as full_name, address, phone_number, email_address, " +
                "username, password,interview_date, highest_qualification, department, location, recruited_by, recruited_on " +
                "from recruit where username = ?";
        try (Connection connection = DBUtil.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    recruit = new Recruit(
                            resultSet.getInt("id"),
                            resultSet.getString("full_name"),
                            resultSet.getString("address"),
                            resultSet.getLong("phone_number"),
                            resultSet.getString("email_address"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getObject("interview_date", LocalDate.class),
                            resultSet.getString("highest_qualification"),
                            resultSet.getString("department"),
                            resultSet.getString("location"),
                            resultSet.getString("recruited_by"),
                            resultSet.getObject("recruited_on", LocalDate.class)
                    );
                }
                return recruit;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recruit;
    }
    
        
     /**
     * Check if user with the provided username exists or not
     * if exits return recruitId otherwise 0
     * @param username
     * @return boolean
     */
    public int getRecruitId(String username) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select id from recruit where username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    
    public boolean validateOTP(int recruitId, int otp) {
        boolean isValid = false;
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select otp from recruit_otp_log where is_valid <> false and recruit_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, recruitId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    isValid = resultSet.getInt(1) == otp;
                }
                return isValid;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }
    public void invalidateOtp(int recruitId, int otp){
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "update recruit_otp_log set is_valid = false, last_updated = now() where recruit_id = ? and otp = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, recruitId);
                preparedStatement.setInt(2, otp);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
