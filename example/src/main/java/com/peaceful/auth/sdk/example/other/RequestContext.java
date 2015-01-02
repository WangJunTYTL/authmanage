package com.peaceful.auth.sdk.example.other;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjun on 15/1/2.
 */
public class RequestContext {

    private static ThreadLocal<String> currentUser = new ThreadLocal<String>();

    public static void setCurrentUser(String username){
        currentUser.set(username);
    }

    public static String getCurrentUser(){
        return currentUser.get();
    }




}
