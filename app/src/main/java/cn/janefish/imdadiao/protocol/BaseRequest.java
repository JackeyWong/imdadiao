package cn.janefish.imdadiao.protocol;

import com.google.gson.annotations.Expose;

import cn.janefish.imdadiao.signTools.DeviceFactory;

/**
 * Created by wangjie on 16/7/17.
 */
public abstract class BaseRequest extends ProtoPackSupport {

    private static final String DEVICE_FIELD_NAME = "device";

    @SignatureField
    @Expose
    protected String requestId;

    @Container(DEVICE_FIELD_NAME)
    @SignatureField
    @Expose
    private String device;

    @ExtractIntoContainer(DEVICE_FIELD_NAME)
    private String devicesId;
    @ExtractIntoContainer(DEVICE_FIELD_NAME)
    private int devicePlatform;
    @ExtractIntoContainer(DEVICE_FIELD_NAME)
    private String deviceBrand;
    @ExtractIntoContainer(DEVICE_FIELD_NAME)
    private int deviceOSVersion;

    {
        DeviceFactory device = DeviceFactory.getInstance();
        devicesId = device.getDeviceUuid().toString();
        devicePlatform = device.getPlatform();
        deviceBrand = device.getBrand();
        deviceOSVersion = device.getOSVersion();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
