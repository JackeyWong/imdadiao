package cn.janefish.imdadiao.protocol;

/**
 * Created by wangjie on 16/7/18.
 */
public class ProtoPackingException extends Exception {
    public ProtoPackingException() {
        super();
    }

    public ProtoPackingException(String message) {
        super(message);
    }

    public ProtoPackingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtoPackingException(Throwable cause) {
        super(cause);
    }
}
