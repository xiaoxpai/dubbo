package io.github.xiaoxpai.test.spi;

public class SPIAnnotation {
    //1 @SPI注解修饰时，就表示该接口是扩展接口
    //2 org.apache.dubbo.rpc.Protocol 接口就是一个扩展接口：
        //@SPI(value = "dubbo", scope = ExtensionScope.FRAMEWORK)
        //public interface Protocol {
         //public class DubboProtocol extends AbstractProtocol {  //实现类
    //3 例如，在通过 Dubbo SPI 加载 Protocol 接口实现时，如果没有明确指定扩展名，
    // 则默认会将 @SPI 注解的 value 值作为扩展名，即加载 dubbo 这个扩展名对应的
    // org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol 这个扩展实现类，相关的 SPI 配置文件在 dubbo-rpc-dubbo 模块中
}
