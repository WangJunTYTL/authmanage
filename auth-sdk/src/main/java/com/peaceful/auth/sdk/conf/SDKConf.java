package com.peaceful.auth.sdk.conf;

import com.peaceful.auth.sdk.constant.AUTH_SDK;
import com.peaceful.auth.sdk.util.AppConfigs;
import com.peaceful.auth.sdk.util.AppConfigsImpl;

/**
 * Created by wangjun on 14-8-12.
 */
public class SDKConf {

    public static java.lang.String user_session_out_time = null;
    public static java.lang.String client_cache_valid_time = null;
    public static java.lang.String system_session_out_time = null;
    public static java.lang.String system_id = null;
    public static java.lang.String service_address = null;
    public static java.lang.String add = "http://10.10.65.108:8412"; // 正是发布地址
    public static java.lang.String help_doc = add + "/help.do";
    public static java.lang.String success_init_info = null;
    public static java.lang.String create_session_exception = help_doc + "#createSessionException";
    public static java.lang.String config_info = help_doc + "#config";
    public static java.lang.String system_basic_info = null;
    public static java.lang.String system_all_info = null;
    public static java.lang.String appkey = null;
    public static java.lang.String secret = null;
    public static java.lang.String token = null;
    public static java.lang.String user_info = null;
    public static java.lang.String identification_email = null;
    public static java.lang.String find_vcs = null;
    public static java.lang.String insert_user = null;
    public static java.lang.String update_user = null;
    public static java.lang.String insert_role = null;
    public static java.lang.String update_role = null;
    public static java.lang.String find_roles = null;
    public static java.lang.String find_menus = null;
    public static java.lang.String find_users = null;
    public static java.lang.String find_resources = null;

    public SDKConf() {
        AppConfigs MY_APP_CONFIGS = AppConfigsImpl.getMyAppConfigs("auth.properties");
        user_session_out_time = MY_APP_CONFIGS.getString(AUTH_SDK.USER_SESSION_OUT_TIME);
        system_session_out_time = MY_APP_CONFIGS.getString(AUTH_SDK.SYSTEM_SESSION_OUT_TIME);
        client_cache_valid_time = MY_APP_CONFIGS.getString(AUTH_SDK.CLIENT_CACHE_VALID_TIME);
        system_id = MY_APP_CONFIGS.getString(AUTH_SDK.SYSTEM_ID);
        appkey = MY_APP_CONFIGS.getString(AUTH_SDK.SYSTEM_APPKEY);
        secret = MY_APP_CONFIGS.getString(AUTH_SDK.SYSTEM_SECRET);
        service_address = MY_APP_CONFIGS.getString(AUTH_SDK.AUTH_SERVICE_ADDRESS); //服务地址
        add = service_address;
        help_doc = add + "/help.do";
        success_init_info = "AuthService  be successfully initialized. See " + help_doc + " for more information";
        create_session_exception = help_doc + "#createSessionException";
        config_info = help_doc + "#config";
        system_all_info = add + "/findSystem.do?systemId=" + system_id; //获得系统信息
        user_info = add + "/findUser.do?systemId=" + system_id + "&userEmail="; // 获得用户信息
        system_basic_info = add + "/token.do";// 获取通信token
        identification_email = add + "/identification.do";// 账号认证
        find_vcs = add + "/findVCS.do"; // 版本信息
        insert_user = add + "/addUser.do";// 添加新用户
        update_user = add + "/updateUser.do";// 更新用户
        insert_role = add + "/addRole.do";// 添加角色
        update_role = add + "/updateRole.do";// 更新角色
        find_roles = add + "/getRoles.do?systemId=" + system_id;// 所有角色
        find_menus = add + "/getMenus.do?systemId=" + system_id;// 所有菜单
        find_users = add + "/getUsers.do?systemId=" + system_id;// 所有用户
        find_resources = add + "/getResources.do?systemId=" + system_id;// 所有资源

    }


}
