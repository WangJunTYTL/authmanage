package com.peaceful.config.util;

import com.peaceful.util.StringUtils;
import com.peaceful.util.Util;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilsTest {

    @Test
    public void testIndexFrom() throws Exception {
        Util.report(StringUtils.indexFrom("abcd", "dd") + "");
    }

    @Test
    public void testContains() throws Exception {
        Util.report(StringUtils.contains("abcd", "ad") + "");
    }
}