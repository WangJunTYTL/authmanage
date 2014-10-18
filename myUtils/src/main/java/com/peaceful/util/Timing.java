package com.peaceful.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印程序执行时间，你可以在上下文环境中调用
 *
 * Date 14-9-9.
 * Author WangJun
 * Email wangjuntytl@163.com
 */
public class Timing {
    static Logger logger = LoggerFactory.getLogger(Timing.class);
    public static final ThreadLocal<Timing> current = new ThreadLocal<Timing>();
    private String name;
    private long start;

    private Timing(String name) {
        this.name = name;
        this.start = System.currentTimeMillis();
    }

    public static void printStart(String name) {
        current.set(new Timing(name));
        logger.info("----------------" + current.get().name + "---------------------------");
    }

    public static void printCurrent() {
        if (current.get() != null)
            logger.info("--------------已用时:" + (System.currentTimeMillis() - current.get().start) + "millisecond---------------------------");
    }

    public static void printEnd() {
        if (current.get() != null)
            logger.info("--------------" + current.get().name + "共用时:" + (System.currentTimeMillis() - current.get().start) + "millisecond---------------------------");
    }

}
