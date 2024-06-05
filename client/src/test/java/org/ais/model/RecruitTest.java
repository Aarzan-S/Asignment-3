package org.ais.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecruitTest extends TestCase {

    @Test
    public void testGetUsername() {
        Recruit recruit = new Recruit();
        recruit.setUsername("alexa");
        Assertions.assertEquals("alexa", recruit.getUsername());
    }
}