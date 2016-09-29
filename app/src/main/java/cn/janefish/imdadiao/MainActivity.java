package cn.janefish.imdadiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import cn.janefish.imdadiao.protocol.ProtocolCode;
import cn.janefish.imdadiao.protocol.RequestEngine;
import cn.janefish.imdadiao.protocol.entity.UserLoginRequest;
import cn.janefish.imdadiao.protocol.entity.UserRegisterResponse;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RequestEngine mRequestEng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mRequestEng = RequestEngine.getInst();
    }



    @Override
    protected void onDestroy() {
        Log.e("wwww", "destroy------");
        super.onDestroy();
    }

    public void onRegist(View view) {
        UserLoginRequest userRegRequest = new UserLoginRequest();
        userRegRequest.setRequestId(ProtocolCode.REQ_REGIST);
        userRegRequest.setLoginPhone("18511761612");
        userRegRequest.setPassWord("123456");
        mRequestEng.request("base/reg", userRegRequest, new RequestEngine.ResponseCallback<UserRegisterResponse>() {

            @Override
            public void onSuccess(final UserRegisterResponse result) {
                Toast.makeText(getApplicationContext(), "register success ! " + result.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "register success !");
            }

            @Override
            public void onError() {
                Log.e(TAG, "register fail !");
            }
        });
    }

    public void onLogin(View view) {
        startActivity(new Intent(this, TestActivity.class));
//        UserLoginRequest login = new UserLoginRequest();
//        login.setRequestId(ProtocolCode.REQ_LOGIN);
//        login.setLoginPhone("18511761612");
//        login.setPassWord("123456");
//        mRequestEng.request("base/login", login, new RequestEngine.ResponseCallback<UserLoginResponse>() {
//            @Override
//            public void onSuccess(final UserLoginResponse result) {
//                Toast.makeText(getApplicationContext(), "register success ! " + result.toString(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
    }
}
