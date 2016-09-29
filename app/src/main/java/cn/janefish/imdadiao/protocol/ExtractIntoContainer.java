package cn.janefish.imdadiao.protocol;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * target for sign field.
 * <p>
 * Created by wangjie on 16/7/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExtractIntoContainer {
    String value() default ProtoPackSupport.BODY_FIELD_NAME;
}
