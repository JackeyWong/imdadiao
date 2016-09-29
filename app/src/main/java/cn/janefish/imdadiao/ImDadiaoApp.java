package cn.janefish.imdadiao;

import android.app.Application;

import cn.janefish.imdadiao.protocol.VolleyHolder;
import cn.janefish.imdadiao.signTools.DeviceFactory;

/**
 * Created by wangjie on 16/7/17.
 */
public class ImDadiaoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        doInitModels();
    }

    private void doInitModels() {
        VolleyHolder.getInst().init(this);
        DeviceFactory.init(this);
    }
}
