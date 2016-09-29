package cn.janefish.imdadiao.protocol.entity;

import cn.janefish.imdadiao.protocol.BaseRequest;
import cn.janefish.imdadiao.protocol.ExtractIntoContainer;

/**
 * Created by wangjie on 16/7/21.
 */
public class UserInfoRequest extends BaseRequest{
    @ExtractIntoContainer
    private String headImgAddres;
    @ExtractIntoContainer
    private String loginPhone;
    @ExtractIntoContainer
    private String passWord;
    @ExtractIntoContainer
    private String nickeName;
    @ExtractIntoContainer
    private String constellation;
    @ExtractIntoContainer
    private int age;
    @ExtractIntoContainer
    private String trade;
    /**
     * 个性签名
     * */
    @ExtractIntoContainer
    private String declaration;
    /**
     * 距离
     * */
    @ExtractIntoContainer
    private int distance;

    public String getHeadImgAddres() {
        return headImgAddres;
    }

    public void setHeadImgAddres(String headImgAddres) {
        this.headImgAddres = headImgAddres;
    }

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

    public String getNickeName() {
        return nickeName;
    }

    public void setNickeName(String nickeName) {
        this.nickeName = nickeName;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
