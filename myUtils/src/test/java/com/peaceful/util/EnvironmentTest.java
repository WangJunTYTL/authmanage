package com.peaceful.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testList() throws Exception {

    }

    @Test
    public void testLogEnv() throws Exception {
          EnvironmentUtils.report();
    }
}