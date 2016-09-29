package cn.janefish.imdadiao.common;

import android.os.Handler;
import android.os.Looper;

import java.util.WeakHashMap;

/**
 * Created by wangjie on 16/8/23.
 */
public class IntervalHandlerManager {

    private static final IntervalHandlerManager sInst = new IntervalHandlerManager();
    private final WeakHashMap<Runnable, Integer> mIntervalMap;

    private IntervalHandlerManager() {
        mIntervalMap = new WeakHashMap<Runnable, Integer>(5);

    }
    public static IntervalHandlerManager getInstance() {
        return sInst;
    }

    public void postInterval(Runnable callback, int interval) {
        IntervalMessage msg = new IntervalMessage(new Handler(Looper.myLooper()), callback, interval);
        synchronized (callback) {
            Integer oInterval = mIntervalMap.get(callback);
            if (oInterval == null) {
                mIntervalMap.put(callback, msg.interval);
                msg.target.postDelayed(msg.callback, interval);
            } else if (oInterval > msg.interval) {
                msg.target.removeCallbacks(msg.callback);
                mIntervalMap.put(callback, msg.interval);
                msg.target.postDelayed(msg.callback, msg.interval);
            }
        }
    }

}

class IntervalMessage {
    final int interval;
    final Handler target;
    final Runnable callback;

    public IntervalMessage(Handler target, Runnable callback, int interval) {
        this.target = target;
        this.callback = callback;
        this.interval = interval;
    }

    @Override
    public int hashCode() {
        return target.hashCode() + 2 * callback.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof IntervalMessage)) {
            return false;
        }
        IntervalMessage m = (IntervalMessage) o;
        return target == m.target
                && callback == m.callback;
    }
}