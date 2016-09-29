package cn.janefish.imdadiao.protocol;

/**
 * Created by wangjie on 16/7/17.
 */
public interface ProtocolCode {
    /*查询首页信息用户信息 测试*/
    public static final String REQ_100001 = "REQ_100001";
    public static final String RESP_100001 = "RESP_100001";


    /*登录*/
    public static final String REQ_LOGIN = "REQ_100002";
    public static final String RESP_LOGIN = "RESP_100002";

    /*注册*/
    public static final String REQ_REGIST = "REQ_100003";
    public static final String RESP_REGIST = "RESP_100003";
    /**
     * 短信验证码
     */
    public static final String REQ_100004 = "REQ_100004";
    public static final String RESP_100004 = "RESP_100004";
    /**
     * 保存用户信息
     */
    public static final String REQ_100005 = "REQ_100005";
    public static final String RESP_100005 = "RESP_100005";
}
