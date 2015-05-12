package com.arturmkrtchyan.kafka.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class ApacheMirrorLocatorTest {

    @Test
    public void locatorShouldReturnMirror() {
        ApacheMirrorLocator locator = new ApacheMirrorLocator();
        assertNotNull("Apache Mirror Locator should return Mirror as a String", locator.locate());
    }

    @Test
    public void locatorShouldReturnHttpMirror() {
        ApacheMirrorLocator locator = new ApacheMirrorLocator();
        assertTrue("Apache Mirror Locator should return Http Mirror as a String", locator.locate().startsWith("http"));
    }
}
