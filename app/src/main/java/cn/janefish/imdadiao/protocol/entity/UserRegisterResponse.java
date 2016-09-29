package cn.janefish.imdadiao.protocol.entity;

import cn.janefish.imdadiao.protocol.BaseResponse;
import cn.janefish.imdadiao.protocol.ExtractIntoContainer;

/**
 * Created by wangjie on 16/7/20.
 */
public class UserRegisterResponse extends BaseResponse {

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    @Override
    public String toString() {
        return "UserRegisterResponse{" +
                "respCode=" + respCode +
                ", respMsg='" + respMsg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
