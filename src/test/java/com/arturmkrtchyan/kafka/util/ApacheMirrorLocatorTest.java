/*
 * Copyright 2015 Artur Mkrtchyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
