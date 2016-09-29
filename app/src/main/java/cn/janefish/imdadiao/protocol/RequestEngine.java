package cn.janefish.imdadiao.protocol;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by wangjie on 16/7/17.
 */
public class RequestEngine {

    public static final String SERVICE_TEST = "http://182.92.156.26/PhoneBook/";
    private static final RequestEngine inst = new RequestEngine();
    private static final String TAG = RequestEngine.class.getSimpleName();
    private final Handler mHandler;
    private final Handler mMainHandler;

    private RequestEngine() {
        HandlerThread t = new HandlerThread("network");
        t.start();
        mHandler = new Handler(t.getLooper());
        mMainHandler = new Handler(Looper.myLooper());
    }

    public static RequestEngine getInst() {
        return inst;
    }

    public <T extends BaseResponse> void request(final String path,
                                                 final ProtoPackSupport request,
                                                 final ResponseCallback<T> responseCB) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    addRequest(path, request, responseCB);
                } catch (ProtoPackingException e) {
                    doErrorOnMainThread(responseCB);
                }
            }
        });
    }

    private <T extends BaseResponse> void addRequest(String path,
                                                     ProtoPackSupport request,
                                                     ResponseCallback<T> responseCB) throws ProtoPackingException {
        final String requestBody = request.build();
        final String url = SERVICE_TEST + path;
        final ResponseCallback<T> callback = responseCB;
        final Class<T> responseClz = parseResponseEntity(callback);

        JsonRequest<T> jsonRequest = new JsonRequest<T>(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<T>() {

                    @Override
                    public void onResponse(T response) {
                        if (response.isSuccess()) {
                            doSuccessOnMainThread(callback, response);
                        } else {
                            doErrorOnMainThread(callback);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        doErrorOnMainThread(callback);
                    }
                }
        ) {
            @Override
            protected Response<T> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(BaseResponse.parseFrom(jsonString, responseClz),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (ProtoPackingException e1) {
                    return Response.error(new ParseError(e1));
                }
            }
        };

        VolleyHolder.getInst().get().add(jsonRequest);

    }

    private <T extends BaseResponse> void doSuccessOnMainThread(ResponseCallback<T> callback, T result) {
        final ResponseCallback cb = callback;
        final T response = result;

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                cb.onSuccess(response);
            }
        });
    }

    private <T extends  BaseResponse> void doErrorOnMainThread(ResponseCallback<T> callback) {
        final ResponseCallback cb = callback;

        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                cb.onError();
            }
        });
    }


    private <T extends BaseResponse> Class<T> parseResponseEntity(ResponseCallback<T> callback) {
        Type[] types = callback.getClass().getGenericInterfaces();
        for (Type t : types) {
            if (t instanceof ParameterizedType) {
                if (((ParameterizedType) t).getRawType() == ResponseCallback.class) {
                    Type[] arg = ((ParameterizedType) t).getActualTypeArguments();
                    return arg.length > 0 ? ((Class<T>) arg[0]) : null;
                }
            }
        }
        return null;
    }

    public interface ResponseCallback<T extends BaseResponse> {

        void onSuccess(T result);

        void onError();
    }
}
