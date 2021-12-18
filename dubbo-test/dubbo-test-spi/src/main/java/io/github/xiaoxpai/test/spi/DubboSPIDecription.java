package io.github.xiaoxpai.test.spi;

public class DubboSPIDecription {
    //扩展点：通过 SPI 机制查找并加载实现的接口（又称“扩展接口”）。前文示例中介绍的 Log 接口、com.mysql.cj.jdbc.Driver 接口，都是扩展点。
    //扩展点实现：实现了扩展接口的实现类。

    //Dubbo SPI 不仅解决了上述资源浪费的问题，还对 SPI 配置文件扩展和修改。
    //
    //首先，Dubbo 按照 SPI 配置文件的用途，将其分成了三类目录。
    //
    //META-INF/services/ 目录：该目录下的 SPI 配置文件用来兼容 JDK SPI 。
    //META-INF/dubbo/ 目录：该目录用于存放用户自定义 SPI 配置文件。
    //META-INF/dubbo/internal/ 目录：该目录用于存放 Dubbo 内部使用的 SPI 配置文件。
    //然后，Dubbo 将 SPI 配置文件改成了 KV 格式，例如：
    //
    //dubbo=org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol
    //其中 key 被称为扩展名（也就是 ExtensionName），当我们在为一个接口查找具体实现类时，可以指定扩展名来选择相应的扩展实现。例如，这里指定扩展名为 dubbo，Dubbo SPI 就知道我们要使用：org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol 这个扩展实现类，只实例化这一个扩展实现即可，无须实例化 SPI 配置文件中的其他扩展实现类。
    //
    //使用 KV 格式的 SPI 配置文件的另一个好处是：让我们更容易定位到问题。假设我们使用的一个扩展实现类所在的 jar 包没有引入到项目中，那么 Dubbo SPI 在抛出异常的时候，会携带该扩展名信息，而不是简单地提示扩展实现类无法加载。这些更加准确的异常信息降低了排查问题的难度，提高了排查问题的效率。
    // Dubbo 并没有直接使用 JDK SPI 机制，而是借鉴其思想，实现了自身的一套 SPI 机制

}
