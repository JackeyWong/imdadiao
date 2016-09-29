package cn.janefish.imdadiao.protocol;

import android.util.Log;

import com.google.gson.annotations.Expose;

import org.json.JSONObject;

import java.util.Iterator;

import cn.janefish.imdadiao.signTools.AESOperator;

/**
 * Created by wangjie on 16/7/17.
 */
public abstract class BaseResponse extends ProtoPackSupport {
    public static final int CODE_SUCC = 200;
    public static final int CODE_FAIL = 404;
    private static final String TAG = BaseResponse.class.getSimpleName();
    @SignatureField
    @Expose
    protected String responseId;

    @Expose
    protected int code;

    public static <T extends BaseResponse> T parseFrom(String jsonStr, Class<T> clz) throws ProtoPackingException {
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            String body = jsonObj.optString(BODY_FIELD_NAME, null);
            if (null == body) {
                throw new ProtoPackingException("body is null");
            }

            String decryptBody = AESOperator.getInstance().decrypt(body);
            // jsonObj.put(BODY_FIELD_NAME, decryptBody);

            JSONObject bodyJson = new JSONObject(decryptBody);
            Iterator<String> it = bodyJson.keys();
            while (it.hasNext()) {
                String key = it.next();
                jsonObj.put(key, bodyJson.get(key));
            }

            Log.e(TAG, "parse json : " + jsonObj.toString());
            T result = GsonHolder.getInst().get().fromJson(jsonObj.toString(), clz);
//            if (!result.isValidate()) {
//                throw new ProtoPackingException("sign is invalidate.");
//            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProtoPackingException("parse json fail");
        }
    }

    public String getResponseId() {
        return responseId;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return getCode() == CODE_SUCC;
    }
}
