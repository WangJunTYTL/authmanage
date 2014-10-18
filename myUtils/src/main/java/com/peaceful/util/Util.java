package com.peaceful.util;


import java.util.Objects;

/**
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
        System.err.println("MY_PEACEFUL_APP: " + msg);
    }

    static final public void report(boolean msg) {
        System.err.println("MY_PEACEFUL_APP: " + msg);
    }

    static final public void report(Integer msg) {
        System.err.println("MY_PEACEFUL_APP: " + msg);
    }

    static final public void report(int msg) {
        System.err.println("MY_PEACEFUL_APP: " + msg);
    }
    static final public void report(Object object) {
        System.err.println("MY_PEACEFUL_APP: " + Objects.toString(object,null));
    }
}
