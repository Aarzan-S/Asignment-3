package org.ais.model;

public class AdminStaff extends Staff{
    private String positionType;

    /**
     * Empty constructor for AdminStaff
     */
    public AdminStaff() {
        super();
    }

    /**
     * Parameterized constructor containing all fields
     *
     * @param fullName
     * @param address
     * @param phoneNumber
     * @param email
     * @param username
     * @param password
     * @param staffId
     * @param positionType
     */
    public AdminStaff(int id, String fullName, String address, Long phoneNumber, String email, String username, String password,
                      String staffId, String positionType) {
        super(id, fullName, address, phoneNumber, email, username, password, staffId);
        this.positionType = positionType;
    }

    /**
     * Getter methods to retrieve fields value
     * @return respective fields value
     */
    public String getPositionType() {
        return positionType;
    }

    /**
     * Setter methods to set fields value
     */
    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    /**
     * Custom command separated toString implementation of AdminStaff
     * @return comma separated string representing AdminStaff object
     */
    @Override
    public String toString() {
        return String.join(",",  super.toString(), positionType, "N/A", "N/A");

    }
}