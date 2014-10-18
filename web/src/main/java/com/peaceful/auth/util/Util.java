package com.peaceful.auth.util;


/**
 *
 * An internal utility class.
 *
 * @author Wang Jun
 */
public class Util {

    static final public void report(String msg, Throwable t) {
        System.err.println(msg);
        System.err.println("Reported exception:");
        t.printStackTrace();
    }

    static final public void report(String msg) {
        System.err.println("AUTH_SERVICE: " +msg);
    }
}
