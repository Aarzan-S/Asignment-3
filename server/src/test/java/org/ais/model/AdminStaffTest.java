package org.ais.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdminStaffTest extends TestCase {

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