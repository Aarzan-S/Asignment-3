package org.ais.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This test class will test different methods of AdminStaff class
 */
class AdminStaffTest {

    /**
     * Tests getter and setter of AdminStaff class
     */
    @Test
    void setPositionType() {
        AdminStaff adminStaff = new AdminStaff();
        adminStaff.setPositionType("Full Time");
        Assertions.assertEquals("Full Time", adminStaff.getPositionType());
    }

    @Test
    void setUsername() {
        AdminStaff adminStaff = new AdminStaff();
        adminStaff.setUsername("alexa");
        Assertions.assertEquals("alexa", adminStaff.getUsername());
    }

}