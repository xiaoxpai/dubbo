# 实现远程调用的方式
Http接口（web接口、RestTemplate+Okhttp）、Feign、RPC调用（Dubbo、Socket编程）、Webservice。

# 什么是Feign？
Feign是Spring Cloud提供的一个声明式的伪Http客户端，它使得调用远程服务就像调用本地服务一样简单，只需要创建一个接口并添加一个注解即可。
Nacos注册中心很好的兼容了Feign，Feign默认集成了Ribbon，所以在Nacos下使用Fegin默认就实现了负载均衡的效果。

# 什么是Dubbo？
Dubbo是阿里巴巴开源的基于Java的高性能RPC分布式服务框架，致力于提供高性能和透明化的RPC远程服务调用方案，以及SOA服务治理方案。
Spring-cloud-alibaba-dubbo是基于SpringCloudAlibaba技术栈对dubbo技术的一种封装，目的在于实现基于RPC的服务调用。

# Feign与Dubbo的对比
Feign与Dubbo功能上有很多类似的地方，因为都是专注于远程调用这个动作。比如注册中心解耦、负载均衡、失败重试熔断、链路监控等。
Dubbo除了注册中心需要进行整合，其它功能都自己实现了，而Feign大部分功能都是依赖全家桶的组件来实现的。Dubbo小而专一，专注于远程调用。而Spring全家桶而言，远程调用只是一个重要的功能而已。

# 协议支持方面：
Feign更加优雅简单。Feign是通过REST API实现的远程调用，基于Http传输协议，服务提供者需要对外暴露Http接口供消费者调用，服务粒度是http接口级的。通过短连接的方式进行通信，不适合高并发的访问。Feign追求的是简洁，少侵入（因为就服务端而言，在SpringCloud环境下，不需要做任何额外的操作，而Dubbo的服务端需要配置开放的Dubbo接口)。
Dubbo方式更灵活。Dubbo是通过RPC调用实现的远程调用，支持多传输协议(Dubbo、Rmi、http、redis等等)，可以根据业务场景选择最佳的方式，非常灵活。默认的Dubbo协议：利用Netty，TCP传输，单一、异步、长连接，适合数据量小、高并发和服务提供者远远少于消费者的场景。Dubbo通过TCP长连接的方式进行通信，服务粒度是方法级的。
从协议层选择看，Dubbo是配置化的，更加灵活。Dubbo协议更适合小数据高并发场景。

# 通信性能方面：

从通信的性能上来分析，SpringCloud的通信采用Openfeign（feign）组件。
Feign基于Http传输协议，底层实现是rest。从OSI 7层模型上来看rest属于应用层。
在高并发场景下性能不够理想，成为性能瓶颈（虽然他是基于Ribbon以及带有熔断机制可以防止雪崩），需要改造。具体需要改造的内容需要时再研究。
Dubbo框架的通信协议采用RPC协议，属于传输层协议，性能上自然比rest高。提升了交互的性能，保持了长连接，高性能。
Dubbo性能更好，比如支持异步调用、Netty性能更好。Dubbo主要是配置而无需改造。
 |             | RPC                            | REST            |
| ----------- | ------------------------------ | --------------- |
| 耦合性      | 强耦合                         | 松耦合          |
| 消息协议    | 二进制 thrift/protobuf         | 文本 xml、jason |
| 通信协议    | TCP                            | HTTP            |
| 接口契约IDL | thrift/protobuf                | swagger         |
| 开发调试    | 消息不可读                     | 可读，可调试    |
| 对外开放    | 一般作为内部各个系统的通信框架 | 对接外部系统    |

 
 

# 负载均衡方面：
Feign默认使用Ribbon作为负载均衡的组件。
Dubbo和Ribbon（Feign默认集成Ribbon）都支持负载均衡策略，但是Dubbo支持的更灵活。
Dubbo和Ribbon对比：
Ribbon的负载均衡策略：随机、规则轮询、空闲策略、响应时间策略。
Dubbo的负载均衡策略：Dubbo支持4种算法，随机、权重轮询、最少活跃调用数、一致性Hash策略。而且算法里面引入权重的概念。
Dubbo可以使用路由策略，然后再进行负载均衡。
Dubbo配置的形式不仅支持代码配置，还支持Dubbo控制台灵活动态配置。

Dubbo负载均衡的算法可以精准到某个服务接口的某个方法，而Ribbon的算法是Client级别的。Ribbon需要进行全局配置，个性化配置比较麻烦。

# 容错机制方面：
Feign默认使用Hystix作为服务熔断的组件。Hystix提供了服务降级，服务熔断，依赖隔离，监控（Hystrix Dashboard）等功能。Feign是利用熔断机制来实现容错的，与Dubbo处理的方式不一样。
Dubbo支持多种容错策略，FailOver、FailFast、Failsafe、FailBack、Aviailable、Broadcast、Forking策略等，以及Mock。也引入了retry次数，timeout等配置参数。Dubbo自带了失败重试的功能。

# 总结

Dubbo支持更多功能、更灵活、支持高并发的RPC框架。

SpringCloud全家桶里面（Feign、Ribbon、Hystrix），特点是非常方便。Ribbon、Hystrix、Feign在服务治理中，配合Spring Cloud做微服务，使用上有很多优势，社区也比较活跃，看将来更新发展。

业务发展影响着架构的选型，当服务数量不是很大时，使用普通的分布式RPC架构即可，当服务数量增长到一定数据，需要进行服务治理时，就需要考虑使用流式计算架构。Dubbo可以方便的做更精细化的流量调度，服务结构治理的方案成熟，适合生产上使用，虽然Dubbo是尘封后重新开启，但这并不影响其技术价值。
如果项目对性能要求不是很严格，可以选择使用Feign，它使用起来更方便。
如果需要提高性能，避开基于Http方式的性能瓶颈，可以使用Dubbo。
Dubbo Spring Cloud的出现，使得Dubbo既能够完全整合到Spring Cloud的技术栈中，享受Spring Cloud生态中的技术支持和标准化输出，又能够弥补Spring Cloud中服务治理这方面的短板。

---
<br><br><br>
引用参考来源：https://www.cnblogs.com/ying-z/p/14781757.html