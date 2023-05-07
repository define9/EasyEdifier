package cn.tomisme.easyedifier.hook.feature;

public interface IHook {
    String getClassName();

    boolean hookHandle(Class<?> clz, ClassLoader loader);
}
