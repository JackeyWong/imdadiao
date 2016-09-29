package cn.janefish.imdadiao;

import android.test.ApplicationTestCase;
import android.util.Log;
import android.widget.Toast;

import cn.janefish.imdadiao.protocol.ProtocolCode;
import cn.janefish.imdadiao.protocol.RequestEngine;
import cn.janefish.imdadiao.protocol.entity.UserLoginRequest;
import cn.janefish.imdadiao.protocol.entity.UserLoginResponse;
import cn.janefish.imdadiao.signTools.DeviceFactory;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<ImDadiaoApp> {
    public ApplicationTest() {
        super(ImDadiaoApp.class);
    }
    public void testLogin() {
        DeviceFactory.init(mContext);
        UserLoginRequest login = new UserLoginRequest();
        login.setRequestId(ProtocolCode.REQ_LOGIN);
        login.setLoginPhone("18511761612");
        login.setPassWord("123456");

        RequestEngine.getInst().request("base/login", login, new RequestEngine.ResponseCallback<UserLoginResponse>() {
            @Override
            public void onSuccess(final UserLoginResponse result) {
                Toast.makeText(mContext, "register success ! " + result.toString(), Toast.LENGTH_LONG).show();
                Log.e("wwwtest", 8989898+"");
            }

            @Override
            public void onError() {

            }
        });
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}