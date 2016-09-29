package cn.janefish.imdadiao.protocol;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import cn.janefish.imdadiao.signTools.AESOperator;
import cn.janefish.imdadiao.signTools.SignUtils;
import cn.janefish.imdadiao.util.ReflectUtil;

/**
 * 协议支持类
 * Created by wangjie on 16/7/17.
 */
public abstract class ProtoPackSupport {

    protected static final String BODY_FIELD_NAME = "body";
    private static final String TAG = ProtoPackSupport.class.getSimpleName();
    @Expose
    @SignatureField
    protected Date time;
    @Expose
    @SerializedName(BODY_FIELD_NAME)
    @SignatureField
    @Container(BODY_FIELD_NAME)
    private String body;
    @Expose
    private String sign;
    private PackingCallback<ProtoPackSupport> _bodyPackingCB = new PackingCallback<ProtoPackSupport>() {

        private HashMap<String, List<Field>> extractMap;
        private HashMap<String, Field> containerMap;

        @Override
        public void onPackStart(ProtoPackSupport src) {
            containerMap = new HashMap<>(5);
            extractMap = new HashMap<>();
        }

        @Override
        public void onPacking(ProtoPackSupport src, Field packField) {

            Container containerField = packField.getAnnotation(Container.class);
            ExtractIntoContainer extField = packField.getAnnotation(ExtractIntoContainer.class);

            if (containerField != null && extField != null) {
                throw new RuntimeException("The two @"
                        + containerField.getClass().getName() + " and @"
                        + extField.getClass().getName() + " can't coexist in " + packField.toString());
            }

            if (containerField != null) {
                Field prevExist = containerMap.put(containerField.value(), packField);
                if (prevExist != null) {
                    throw new RuntimeException("Repeated container : " + containerField.value()
                            + ", from " + packField.toString()
                            + " and " + prevExist.toString());
                }
            }

            if (extField != null) {
                String containerName = extField.value();
                List<Field> extList = extractMap.get(containerName);
                if (extList == null) {
                    extList = new LinkedList<>();
                }
                extList.add(packField);
                extractMap.put(containerName, extList);
            }
        }

        @Override
        public void onPackFinish(ProtoPackSupport src) {
            try {
                for (String container :
                        containerMap.keySet()) {
                    Field containerField = containerMap.get(container);
                    JSONObject json = new JSONObject();
                    for (Field sub :
                            extractMap.get(container)) {
                        json.put(sub.getName(), sub.get(src));
                    }
                    containerField.set(src, json.toString());
                }
                Log.e(TAG, GsonHolder.getInst().get().toJson(src));
            } catch (Exception e) {
                e.printStackTrace();
                // not reach
            }
        }
    };
    private final Set<PackingCallback> mPackingCallbackSet = new HashSet<>(3);
    /**
     * this field is read only, don't modify.
     */
    private List<Field> _declaredFieldList;

    {
        List<Field> fields = ReflectUtil.getAllDeclaredFields(this.getClass());
        _declaredFieldList = Collections.unmodifiableList(fields);
        addPackingCallback(_bodyPackingCB);
    }

    protected void addPackingCallback(PackingCallback cb) {
        synchronized (mPackingCallbackSet) {
            mPackingCallbackSet.add(cb);
        }
    }

    protected void removePackingCallback(PackingCallback cb) {
        synchronized (mPackingCallbackSet) {
            mPackingCallbackSet.remove(cb);
        }
    }

    public String build() throws ProtoPackingException {
        if (time == null)
            time = new Date();

        startExtractPacking();
        doEncryptBody();
        doSignPkg();
        return GsonHolder.getInst().get().toJson(this);
    }

    private void doEncryptBody() {
        final String body = getBody();
        if (body == null) {
            throw new RuntimeException("encrypt body error, is null");
        }
        this.body = AESOperator.getInstance().encrypt(body);
    }

    private void startExtractPacking() {
        synchronized (mPackingCallbackSet) {
            _fireOnPackStart();

            for (Field field : _declaredFieldList) {
                try {
                    field.setAccessible(true);
                    _fireOnPacking(this, field);
                } catch (Exception e) {
                    e.printStackTrace();
                    // not reach
                }
            }

            _fireOnPackFinish();
        }
    }

    private void _fireOnPackStart() {
        _firePackingCallback(new onCallback() {
            @Override
            public void doCallback(PackingCallback cb) {
                cb.onPackStart(ProtoPackSupport.this);
            }
        });
    }

    private void _fireOnPackFinish() {
        _firePackingCallback(new onCallback() {
            @Override
            public void doCallback(PackingCallback cb) {
                cb.onPackFinish(ProtoPackSupport.this);
            }
        });
    }

    private void _fireOnPacking(final ProtoPackSupport owner, final Field field) {
        _firePackingCallback(new onCallback() {
            @Override
            public void doCallback(PackingCallback cb) {
                cb.onPacking(owner, field);
            }
        });
    }

    private void _firePackingCallback(onCallback onCall) {
        for (final PackingCallback cb : mPackingCallbackSet) {
            onCall.doCallback(cb);
        }
    }

    private void doSignPkg() {
        this.sign = generateSign();
    }

    private String generateSign() {
        try {
            SortedMap<Object, Object> sortedMap = new TreeMap<>();
            for (Field field : _declaredFieldList) {
                SignatureField signField = field.getAnnotation(SignatureField.class);
                if (signField != null) {
                    field.setAccessible(true);
                    sortedMap.put(field.getName(), field.get(this));
                }
            }
            return SignUtils.sha1Sign(sortedMap);
        } catch (Exception e) {
            // no op
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValidate() {
        return this.sign != null && this.sign.equals(generateSign());
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public String getSign() {
        return sign;
    }

    public interface PackingCallback<T extends ProtoPackSupport> {
        void onPackStart(T src);

        void onPacking(T src, Field packField);

        void onPackFinish(T src);
    }

    private interface onCallback {
        void doCallback(PackingCallback cb);
    }
}
