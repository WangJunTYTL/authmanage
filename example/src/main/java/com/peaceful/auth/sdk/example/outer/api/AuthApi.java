package com.peaceful.auth.sdk.example.outer.api;

import com.peaceful.auth.Impl.AuthServiceImpl;
import com.peaceful.auth.api.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wangjun on 15/1/2.
 */
public class AuthApi {

    private static Logger logger = LoggerFactory.getLogger(AuthApi.class);

    public static AuthService getAuthService() {
        return AuthServiceImpl.getAuthService();

    }
}
