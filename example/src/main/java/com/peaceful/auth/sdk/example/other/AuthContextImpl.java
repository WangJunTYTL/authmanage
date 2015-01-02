package com.peaceful.auth.sdk.example.other;

import com.peaceful.auth.spring.AuthContext;

/**
 * Created by wangjun on 15/1/2.
 */
public class AuthContextImpl extends AuthContext {
    @Override
    public String getCurrentUser() {
        return RequestContext.getCurrentUser();
    }
}
