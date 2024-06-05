package org.ais.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagementStaffTest extends TestCase {

    @Test
    public void testSetBranch() {
        ManagementStaff staff = new ManagementStaff();
        staff.setBranch("Sydney");
        Assertions.assertEquals("Sydney", staff.getBranch());
    }
}