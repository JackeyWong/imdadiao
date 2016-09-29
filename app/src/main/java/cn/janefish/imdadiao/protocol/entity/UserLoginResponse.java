package cn.janefish.imdadiao.protocol.entity;

import cn.janefish.imdadiao.protocol.BaseResponse;
import cn.janefish.imdadiao.protocol.ExtractIntoContainer;

/**
 * Created by wangjie on 16/7/20.
 */
public class UserLoginResponse extends BaseResponse {

    @ExtractIntoContainer
    private int respCode;

    @ExtractIntoContainer
    private String respMsg;

    @ExtractIntoContainer
    private String token;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "respCode=" + respCode +
                ", respMsg='" + respMsg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
