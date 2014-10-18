package com.peaceful.util;

import org.junit.Test;

import java.util.Date;

public class PasswordUtilsTest {

    @Test
    public void testGetPasswordObject() throws Exception {
        PasswordObject passwordObject = PasswordUtils.getPasswordObject(DateUtils.getAddDayDate(new Date(),-4),30);
        Util.report(passwordObject.expireRemainDay);
        Util.report(passwordObject.isExpire);
    }
}