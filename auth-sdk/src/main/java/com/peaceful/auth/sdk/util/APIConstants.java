package com.peaceful.auth.sdk.util;

/**
 * author:dengxiaming@edaijia-staff.cn crate_time:2014年5月1日 -下午3:41:19
 * description:
 */

public class APIConstants {

    /**
     * 上线使用的url
     */
    public static final String API_URL = "http://api.edaijia.cn/rest?";
    /**
     * 测试使用的url
     */
    public static final String TEST_API_URL = "http://api.d.edaijia.cn/rest?";

    /**
     * 订单轨迹 *
     */
    public static final String METHOD_DRIVER_TRACE = "c.order.trace";

    /**
     * 根据gps查位置信息
     */
    public static final String METHOD_GPS_LOCATION = "c.gps.location";

    public static final String METHOD_USER_INFO = "open.utils.user_info";

    public static final String METHOD_C_MY_INFO_VIEW="c.my.infoview";
    /**
     * 获取所有城市
     */
    public static final String METHOD_ALL_CITY = "c.city.list";
    
    /**
     * 绑定优惠券
     */
    public static final String METHOD_BIND_BONUS="open.utils.bind_bonus";
    
    /**
     * 开通城市价格，包括日间单和夜间单
     */
    public static final String METHOD_CITY_PRICE = "c.city.pricelist";


    /**
     * 优惠券信息
     */
    public static final String METHOD_BONUS_INFO = "open.coupon.bonus_code_info";


    /**
     * 优惠券自动发放绑券与更改订单信息
     */
    public static final String METHOD_BONUS_TRIGGER = "open.coupon.order_bonus";


    /**
     * 查看某个活动的优惠券发放数量
     */
    public static final String METHOD_BONUS_COUNT="open.coupon.count";
    
    /**
     * 根据城市id获取价格列表
     */
    public static final String METHOD_GET_PRICE = "c.city.pricelist";
    /**
     * 909活动推送消息
     */
    public static final String METHOD_PUSH_909 = "activity.notify";
    
    /**
     * 日间单
     */
    public static final int DAY_ORDER = 2;
    
    /**
     * 普通单
     */
    public static final int COMMON_ORDER = 1;
}
