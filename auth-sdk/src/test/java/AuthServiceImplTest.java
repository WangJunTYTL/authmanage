import com.peaceful.auth.sdk.Impl.AuthServiceImpl;
import com.peaceful.auth.sdk.api.AuthService;
import com.peaceful.auth.sdk.exception.CreateSessionException;
import com.peaceful.auth.data.domain.JSONMenu;
import com.peaceful.auth.data.domain.JSONRole;
import com.peaceful.auth.data.domain.JSONSystem;
import com.peaceful.auth.data.domain.JSONUser;
import com.peaceful.auth.sdk.util.Util;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class AuthServiceImplTest {
    AuthService service = null;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.junit.Before
    public void setUp() throws Exception {
//        service = AuthServiceImpl.getAuthService();
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testGetSystem() throws Exception {
        JSONSystem system = service.getSystem();
        logger.info("system name is {} ", system.name);
        logger.info("role size is {} ", system.roles.size());
    }

    @org.junit.Test
    public void testGetUser() throws Exception {
        JSONUser user = service.getUser("admin");
        logger.info("user emial is {} ", user.email);
        logger.info("menus is {} ", user.menus);
        logger.info("resources is {} ", user.resources);
    }

    @org.junit.Test
    public void testGetRoles() throws Exception {

    }

    @org.junit.Test
    public void testGetMenu() throws Exception {
        JSONUser user = service.getUser("jun.wang");
        logger.info("1");
    }

    @org.junit.Test
    public void testGetMenuString() throws Exception {
        String menu = service.getMenu("createJob", "onclick=\"alert('hello')\"", 1, "jun.wang");
        String menu1 = service.getMenu("createJob", "onclick=\"alert('hello')\"", 3, "jun.wang");
        logger.info(menu);
        logger.info(menu1);
    }

    @org.junit.Test
    public void getAuthService() {
        try {
            AuthService authService = AuthServiceImpl.getAuthService();
        } catch (CreateSessionException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testRequestCheck() throws Exception {
        service.requestCheck("/", "jun.wang");
    }

    @org.junit.Test
    public void identificationEmail() throws Exception {
        Util.report(service.identificationEmail("jun.wang", "www") + "");
    }

    @org.junit.Test
    public void insertUser() {
        JSONUser user = new JSONUser();
        user.email = "www";
        user.name = "啊啊啊";
        service.insertUser(user);
    }

    @org.junit.Test
    public void updateUser() {
        JSONUser user = new JSONUser();
        user.id = 92;
        user.email = "aaabbb";
        user.name = "啊啊啊";
        user.operator = "junit";
        user.isdel = 1;
        Integer[] roleIds = {27, 28, 29};
        service.updateUser(user, true, roleIds);
    }


    @org.junit.Test
    public void updateRole() {
        JSONRole role = new JSONRole();
        role.id = 27;
        role.name = "你大大爷";
        role.operator = "junit";
        role.isdel = 1;
        List<JSONMenu> menus = service.getMenusOfSystem();
        Integer[] menuIds = new Integer[menus.size()];
        for (int i = 0; i < menus.size(); i++)
            menuIds[i] = menus.get(i).getId();
        service.updateRole(role, true, menuIds);
    }

    @org.junit.Test
    public void getMenusOfSystem() {
        Util.report("menus size is " + service.getMenusOfSystem().size());
    }

    @org.junit.Test
    public void getUsersOfSystem() {
        Util.report("users size is " + service.getUsersOfSystem().size());
    }


    @org.junit.Test
    public void getRolesOfSystem() {
        Util.report("roles size is " + service.getRolesOfSystem().size());
    }

    @org.junit.Test
    public void getResourcesOfSystem() {
        Util.report("resources size is " + service.getResourcesOfSystem().size());
    }


    String res = "@{\"message\":\"下单成功\",\"data\":{\"bookingId\":\"b7056b75bdea0457a057151d3cb357de\",\"bookingType\":\"01003\",\"orderTime\":\"1421132083\",\"timeout\":\"90\"},\"code\":\"0\"}";

    @Test
    public void tes() {
        res = res.substring(1, res.length());
        Util.report(res);
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(res);
        String code = (String) jsonObject.get("code");
        System.out.print(code);
    }

    @Test
    public void teas() {
        int chari2;

    }

}