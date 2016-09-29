package cn.janefish.imdadiao.protocol;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Created by wangjie on 16/7/17.
 */
public class GsonHolder implements IHolder<Gson> {

    private static volatile Gson sGson;
    private static final GsonHolder inst = new GsonHolder();
    private ExclusionStrategy mSerialExclusion = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(Expose.class) == null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    public static GsonHolder getInst() {
        return inst;
    }

    @Override
    public void init(Object... initParam) {
        if (sGson == null) {
            synchronized (GsonHolder.class) {
                if (sGson == null) {
                    sGson = new GsonBuilder()
                            .addSerializationExclusionStrategy(mSerialExclusion)
                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
                            .create();
                }
            }
        }
    }

    @Override
    public Gson get() {
        init();
        return sGson;
    }
}
