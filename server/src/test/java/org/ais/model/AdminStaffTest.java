package org.ais.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdminStaffTest extends TestCase {

    @Test
    public void testSetPositionType() {
        AdminStaff adminStaff = new AdminStaff();
        adminStaff.setPositionType("Full Time");
        Assertions.assertEquals("Full Time", adminStaff.getPositionType());
    }

    @Test
    public void testSetUsername() {
        AdminStaff adminStaff = new AdminStaff();
        adminStaff.setUsername("alexa");
        Assertions.assertEquals("alexa", adminStaff.getUsername());
    }
}