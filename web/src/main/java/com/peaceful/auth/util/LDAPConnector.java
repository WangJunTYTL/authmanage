package com.peaceful.auth.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;


/**
 * Created by IntelliJ IDEA.
 * User: chaohua.luo
 * Date: 12-4-9
 * Time: 下午4:51
 * To change this template use File | Settings | File Templates.
 */
public class LDAPConnector {

    private static final Logger LOG = LoggerFactory.getLogger(LDAPConnector.class);

    private final static String FILE_LDAP_CONFIG = "ldap.properties";
    private static LDAPConnector instance;

    private String url;
    private String bindDN;
    private String bindPassword;
    private String baseDN;

    private final Map<String, String> env = new Hashtable<String, String>();
    private final Control[] SORT_CONTROLS = new SortControl[1];

    private String userChineseName;

    private LDAPConnector() {
        try {
            Properties properties = PropertyUtil.load(FILE_LDAP_CONFIG);
            url = properties.getProperty("LDAP_URL");
            bindDN = properties.getProperty("BIND_DN");
            bindPassword = properties.getProperty("LDAP_PWD");
            baseDN = properties.getProperty("BASE_DN");

            SORT_CONTROLS[0] = new SortControl("sAMAccountName", Control.CRITICAL);

            env.put(Context.PROVIDER_URL, url + baseDN);
            env.put(Context.SECURITY_PRINCIPAL, bindDN);
            env.put(Context.SECURITY_CREDENTIALS, bindPassword);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put("com.sun.jndi.ldap.connect.timeout", "1000");
        } catch (Exception e) {
            LOG.warn("LDAP认证配置失败，请检查配置文件：{}", FILE_LDAP_CONFIG, e);
        }
    }

    public static synchronized LDAPConnector getInstance() {
        if (instance == null)
            instance = new LDAPConnector();
        return instance;
    }

    public boolean validateUser(String username, String password) {
        boolean passed = false;
        DirContext context = null;
        try {
            String userDN = this.getUserDN(username);
            // set up environment for creating initial context
            Map<String, String> userEnv = new Hashtable<String, String>();
            userEnv.put(Context.PROVIDER_URL, url + bindDN);
            userEnv.put(Context.SECURITY_PRINCIPAL, userDN + "," + baseDN);
            userEnv.put(Context.SECURITY_CREDENTIALS, password);
            userEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
            userEnv.put("com.sun.jndi.ldap.connect.timeout", "1000");
            userEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

            context = new InitialDirContext((Hashtable)userEnv);
            if (context != null) {
                passed = true;
                String chineseName = userDN.substring(userDN.indexOf("CN=") + 3, userDN.indexOf(','));
                setUserChineseName(chineseName);
            }
        } catch (NamingException e) {
            LOG.warn("用户名或密码错误，请重新输入！", e);
        } finally {
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException e) {
                    LOG.warn("用户验证LDAP连接关闭失败", e);
                }
            }
        }
        return passed;
    }

    public String getUserDN(String username) {
        String userDN = null;
        LdapContext ldapContext = null;
        try {
            // create initial context
            ldapContext = new InitialLdapContext((Hashtable)env, SORT_CONTROLS);
            ldapContext.setRequestControls(SORT_CONTROLS);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(sAMAccountName=" + username + ")";
            NamingEnumeration<?> answer = ldapContext.search("", filter, controls);
            if (answer.hasMore()) {
                userDN = ((NameClassPair) answer.nextElement()).getName();
            }
        } catch (NamingException e) {
            LOG.warn("用户{}不存在", username);
        } finally {
            if (ldapContext != null) {
                try {
                    ldapContext.close();
                } catch (NamingException e) {
                    LOG.warn("获取UN的LDAP连接关闭失败", e);
                }
            }
        }

        return userDN;
    }

    public String getUserChineseName() {
        return userChineseName;
    }

    public void setUserChineseName(String userChineseName) {
        this.userChineseName = userChineseName;
    }
}