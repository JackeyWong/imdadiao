package cn.janefish.imdadiao.protocol;

/**
 * Created by wangjie on 16/7/17.
 */
public interface IHolder<T> {
    void init(Object... initParam);

    T get();
}
