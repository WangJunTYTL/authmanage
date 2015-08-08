package com.peaceful.auth.sdk.conf;

import com.peaceful.auth.sdk.util.Method;

/**
 * @author WangJun <wangjuntytl@163.com>
 * @version 1.0 15/8/8
 * @since 1.6
 */

public interface OPEN {

    @Method(value = "/findVCS.do")
    public String getVcsNum();
}
