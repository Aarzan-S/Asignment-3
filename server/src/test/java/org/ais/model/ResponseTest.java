package org.ais.model;

import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDateTime;

public class ResponseTest extends TestCase {

    public void testGetMessage() {
        Response response = new Response("User Logged in", LocalDateTime.now(), "SUCCESS");
        Assertions.assertEquals(response.getMessage(), "User Logged in");
    }

    public void testSetStatus() {
        Response response = new Response("User Logged in", LocalDateTime.now(), "SUCCESS");
        Assertions.assertEquals(response.getStatus(), "SUCCESS");
    }
}