package cn.janefish.imdadiao.protocol.entity;

import cn.janefish.imdadiao.protocol.BaseResponse;
import cn.janefish.imdadiao.protocol.ExtractIntoContainer;

/**
 * Created by wangjie on 16/7/21.
 */
public class UserInfoResponse extends BaseResponse {
    @ExtractIntoContainer
    private int respCode;

    @ExtractIntoContainer
    private String respMsg;

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
}
