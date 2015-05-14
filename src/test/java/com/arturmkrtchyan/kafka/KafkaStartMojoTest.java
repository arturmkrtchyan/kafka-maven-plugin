package com.arturmkrtchyan.kafka;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public class KafkaStartMojoTest extends AbstractMojoTestCase {
    /**
     * {@inheritDoc}
     */
    protected void setUp() throws Exception {
        // required
        super.setUp();
    }

    /**
     * {@inheritDoc}
     */
    protected void tearDown() throws Exception {
        // required
        super.tearDown();

    }

    /**
     * @throws Exception if any
     */
    public void testSomething() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom.xml");
        assertNotNull(pom);
        assertTrue(pom.exists());

        KafkaStartMojo kafkaMojo = (KafkaStartMojo) lookupMojo("start", pom);
        assertNotNull(kafkaMojo);
        kafkaMojo.execute();
    }
}