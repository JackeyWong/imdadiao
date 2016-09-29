package cn.janefish.imdadiao.protocol.entity;

import cn.janefish.imdadiao.protocol.BaseRequest;
import cn.janefish.imdadiao.protocol.ExtractIntoContainer;

/**
 * Created by wangjie on 16/7/17.
 */
public class UserRegisterRequest extends BaseRequest {

    @ExtractIntoContainer
    private String loginPhone;

    @ExtractIntoContainer
    private String passWord;

    public String getLoginPhone() {
        return loginPhone;
    }

    public void setLoginPhone(String loginPhone) {
        this.loginPhone = loginPhone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
