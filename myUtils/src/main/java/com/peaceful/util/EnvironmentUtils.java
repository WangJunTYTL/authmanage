
package com.peaceful.util;

import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


/**
 * Provide insight into the runtime environment.
 *
 */
public class EnvironmentUtils {

    private final  static  org.slf4j.Logger logger = LoggerFactory.getLogger(EnvironmentUtils.class);
    public static class Entry {
        private String k;
        private String v;
        public Entry(String k, String v) {
            this.k = k;
            this.v = v;
        }
        public String getKey() { return k; }
        public String getValue() { return v; }
        
        @Override
        public String toString() {
            return k + "=" + v;
        }
    }

    private static void put(ArrayList<Entry> l, String k, String v) {
        l.add(new Entry(k,v));
    }

    public static List<Entry> list() {
        ArrayList<Entry> l = new ArrayList<Entry>();

        try {
            put(l, "host.name",
                InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException e) {
            put(l, "host.name", "<NA>");
        }

        put(l, "java.version",
                System.getProperty("java.version", "<NA>"));
        put(l, "java.vendor",
                System.getProperty("java.vendor", "<NA>"));
        put(l, "java.home",
                System.getProperty("java.home", "<NA>"));
        put(l, "java.class.path",
                System.getProperty("java.class.path", "<NA>"));
        put(l, "java.library.path",
                System.getProperty("java.library.path", "<NA>"));
        put(l, "java.io.tmpdir",
                System.getProperty("java.io.tmpdir", "<NA>"));
        put(l, "java.compiler",
                System.getProperty("java.compiler", "<NA>"));
        put(l, "os.name",
                System.getProperty("os.name", "<NA>"));
        put(l, "os.arch",
                System.getProperty("os.arch", "<NA>"));
        put(l, "os.version",
                System.getProperty("os.version", "<NA>"));
        put(l, "user.name",
                System.getProperty("user.name", "<NA>"));
        put(l, "user.home",
                System.getProperty("user.home", "<NA>"));
        put(l, "user.dir",
                System.getProperty("user.dir", "<NA>"));
        
        return l;
    }
    
    public static void report() {
        List<Entry> env = EnvironmentUtils.list();
        for (Entry e : env) {
           Util.report(e.toString());
        }
    }
}
