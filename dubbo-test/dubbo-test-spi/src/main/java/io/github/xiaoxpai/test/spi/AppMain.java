package io.github.xiaoxpai.test.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class AppMain {
    public static void main(String[] args) {
        //1、
        // JDK SPI 的入口方法，ServiceLoader.load()
        // 在 ServiceLoader.load() 方法中，
        // 首先会尝试获取当前使用的 ClassLoader（获取当前线程绑定的 ClassLoader，查找失败后使用 SystemClassLoader），然后调用 reload() 方法
        // 在 reload() 方法中，首先会清理 providers 缓存（LinkedHashMap 类型的集合），该缓存用来记录 ServiceLoader 创建的实现对象，
        // 其中 Key 为实现类的完整类名，Value 为实现类的对象。之后创建 LazyIterator 迭代器，用于读取 SPI 配置文件并实例化实现类对象。
        ServiceLoader<Log> serviceLoader = ServiceLoader.load(Log.class);
        //1.1、
        // ServiceLoader.reload() 方法的具体实现
        // // 缓存，用来缓存 ServiceLoader创建的实现对象
        //
        //private LinkedHashMap<String,S> providers = new LinkedHashMap<>();
        //
        //public void reload() {
        //
        //    providers.clear(); // 清空缓存
        //
        //    lookupIterator = new LazyIterator(service, loader); // 迭代器
        //
        //}
        Iterator<Log> iterator = serviceLoader.iterator();
        //1.2、
        // main() 方法中使用的迭代器底层就是调用了 ServiceLoader.LazyIterator 实现的。
        // Iterator 接口有两个关键方法：hasNext() 方法和 next() 方法。
        // 这里的 LazyIterator 中的next() 方法最终调用的是其 nextService() 方法，hasNext() 方法最终调用的是 hasNextService() 方法
        // LazyIterator.hasNextService() 方法，该方法主要负责查找 META-INF/services 目录下的 SPI 配置文件，并进行遍历，大致实现如下所示：
        //private static final String PREFIX = "META-INF/services/";

        //Enumeration<URL> configs = null;
        //Iterator<String> pending = null;
        //String nextName = null;

        //private boolean hasNextService() {
        //    if (nextName != null) {
        //        return true;
        //    }
        //
        //    if (configs == null) {
        //
        //        // PREFIX前缀与服务接口的名称拼接起来，就是META-INF目录下定义的SPI配
        //
        //        // 置文件(即示例中的META-INF/services/com.xxx.Log)
        //
        //        String fullName = PREFIX + service.getName();
        //
        //        // 加载配置文件
        //
        //        if (loader == null)
        //
        //            configs = ClassLoader.getSystemResources(fullName);
        //
        //        else
        //
        //            configs = loader.getResources(fullName);
        //
        //    }
        //
        //    // 按行SPI遍历配置文件的内容
        //
        //    while ((pending == null) || !pending.hasNext()) {
        //        if (!configs.hasMoreElements()) {
        //            return false;
        //        }
        //        // 解析配置文件
        //        pending = parse(service, configs.nextElement());
        //    }
        //    nextName = pending.next(); // 更新 nextName字段
        //    return true;
        //}

        //2
        //在 hasNextService() 方法中完成 SPI 配置文件的解析之后，再来看 LazyIterator.nextService() 方法
        // ，该方法负责实例化 hasNextService() 方法读取到的实现类，其中会将实例化的对象放到 providers 集合中缓存起来，核心实现如下所示：
        //private S nextService() {
        //
        //    String cn = nextName;
        //
        //    nextName = null;
        //
        //    // 加载 nextName字段指定的类
        //
        //    Class<?> c = Class.forName(cn, false, loader);
        //
        //    if (!service.isAssignableFrom(c)) { // 检测类型
        //
        //        fail(service, "Provider " + cn  + " not a subtype");
        //
        //    }
        //
        //    S p = service.cast(c.newInstance()); // 创建实现类的对象
        //
        //    providers.put(cn, p); // 将实现类名称以及相应实例对象添加到缓存
        //
        //    return p;
        //
        //}

        //3、
        //ServiceLoader.iterator() 方法拿到的迭代器是如何实现的，这个迭代器是依赖 LazyIterator 实现的一个匿名内部类
        //public Iterator<S> iterator() {
        //
        //    return new Iterator<S>() {
        //
        //        // knownProviders用来迭代providers缓存
        //
        //        Iterator<Map.Entry<String,S>> knownProviders
        //
        //            = providers.entrySet().iterator();
        //
        //        public boolean hasNext() {
        //
        //            // 先走查询缓存，缓存查询失败，再通过LazyIterator加载
        //
        //            if (knownProviders.hasNext())
        //
        //                return true;
        //
        //            return lookupIterator.hasNext();
        //
        //        }
        //
        //        public S next() {
        //
        //            // 先走查询缓存，缓存查询失败，再通过 LazyIterator加载
        //
        //            if (knownProviders.hasNext())
        //
        //                return knownProviders.next().getValue();
        //
        //            return lookupIterator.next();
        //
        //        }
        //
        //        // 省略remove()方法
        //
        //    };
        //
        //}
        while (iterator.hasNext()) {

            Log log = iterator.next();

            log.log("JDK SPI");
            //Log4j2:JDK SPI
            //Logback:JDK SPI
        }
    }
}
