package cn.janefish.imdadiao.protocol;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wangjie on 16/7/17.
 */
public class VolleyHolder implements IHolder<RequestQueue> {

    private static volatile RequestQueue sVolley = null;

    private static final VolleyHolder inst = new VolleyHolder();
    public static VolleyHolder getInst() {
        return inst;
    }

    @Override
    public void init(Object... initParam) {
        if (sVolley == null) {
            synchronized (VolleyHolder.class) {
                if (sVolley == null) {
                    Object p = (initParam != null && initParam.length > 0) ? initParam[0] : null;
                    if (p != null && p instanceof Context) {
                        sVolley = Volley.newRequestQueue((Context) p);
                        sVolley.start();
                    } else {
                        throw new IllegalArgumentException("first initParam must be context");
                    }
                }
            }
        }
    }

    @Override
    public RequestQueue get() {
        return sVolley;
    }
}
