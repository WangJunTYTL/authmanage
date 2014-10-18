package com.peaceful.auth.api;

import com.peaceful.auth.constant.GlobleConstant;
import com.peaceful.config.api.MyAppConfigs;
import com.peaceful.util.AppConfigsUtils;

/**
 * Created by wangjun on 14-8-12.
 */
public final class PublicServiceURL {

    public java.lang.String user_session_out_time = null;
    public java.lang.String system_session_out_time = null;
    public java.lang.String system_id = null;
    public java.lang.String service_address = null;
    public static java.lang.String codes_prefix = "http://10.10.65.108:8412"; // 正是发布地址
    public static java.lang.String help_doc = codes_prefix + "/help.do";
    public java.lang.String success_init_info = null;
    public static java.lang.String create_session_exception = help_doc + "#createSessionException";
    public java.lang.String config_info = help_doc + "#config";
    public java.lang.String system_info = null;
    public java.lang.String user_info = null;
    public java.lang.String system_check = null;
    public java.lang.String identification_email = null;
    public java.lang.String find_vcs = null;
    public java.lang.String insert_user = null;
    public java.lang.String update_user = null;
    public java.lang.String insert_role = null;
    public java.lang.String update_role = null;
    public java.lang.String find_roles = null;
    public java.lang.String find_menus = null;
    public java.lang.String find_users = null;
    public java.lang.String find_resources = null;

    public PublicServiceURL() {
        MyAppConfigs MY_APP_CONFIGS = AppConfigsUtils.getMyAppConfigs("auth.properties");
        user_session_out_time = MY_APP_CONFIGS.getString(GlobleConstant.USER_SESSION_OUT_TIME);
        system_session_out_time = MY_APP_CONFIGS.getString(GlobleConstant.SYSTEM_SESSION_OUT_TIME);
        system_id = MY_APP_CONFIGS.getString(GlobleConstant.SYSTEM_ID);
        service_address = MY_APP_CONFIGS.getString(GlobleConstant.AUTH_SERVICE_ADDRESS); //服务地址
        codes_prefix = service_address;
        help_doc = codes_prefix + "/help.do";
        success_init_info = "AuthService  be successfully initialized. See " + help_doc + " for more information";
        create_session_exception = help_doc + "#createSessionException";
        config_info = help_doc + "#config";
        system_info = codes_prefix + "/findSystem.do?systemId=" + system_id; //获得系统信息
        user_info = codes_prefix + "/findUser.do?systemId=" + system_id + "&userEmail="; // 获得用户信息
        system_check = codes_prefix + "/systemIsExist.do?systemId=" + system_id;// 检测服务是否注册
        identification_email = codes_prefix + "/identification.do";// 账号认证
        find_vcs = codes_prefix + "/findVCS.do"; // 版本信息
        insert_user = codes_prefix + "/addUser.do";// 添加新用户
        update_user = codes_prefix + "/updateUser.do";// 更新用户
        insert_role = codes_prefix + "/addRole.do";// 添加角色
        update_role = codes_prefix + "/updateRole.do";// 更新角色
        find_roles = codes_prefix + "/getRoles.do?systemId=" + system_id;// 所有角色
        find_menus = codes_prefix + "/getMenus.do?systemId=" + system_id;// 所有菜单
        find_users = codes_prefix + "/getUsers.do?systemId=" + system_id;// 所有用户
        find_resources = codes_prefix + "/getResources.do?systemId=" + system_id;// 所有资源

    }

}
