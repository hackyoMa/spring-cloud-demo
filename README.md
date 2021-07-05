# Spring Cloud Demo

## 组件

基于Spring Boot 2.4.5和Spring Cloud 2020.0.2的Spring Cloud Demo，其中含有下列组件的样例：
> * Consul（自行安装，注册中心、配置中心）
> > * docker run -d -p 8500:8500 -v $PWD/data/:/consul/data/ -e CONSUL_BIND_INTERFACE=eth0 --name=consul consul:1.10.0
> * Zipkin（自行安装，服务链路追踪）
> > * docker run -d -p 9411:9411 --name=zipkin hackyo/zipkin:2
> * Sentinel（自行安装，流量防卫）
> > * docker run -d -p 9500:8080 --name sentinel hackyo/sentinel:1.8
> * RestTemplate（服务消费者）
> * Spring Cloud Gateway（路由网关）
> * Spring Boot Admin（服务监控中心）

------

## 组件说明

> * 外部或内部非Spring Cloud项目统一通过API网关（Spring Cloud Gateway）来访问内部服务
> * 网关接收到请求后，从注册中心（Consul）获取可用服务
> * RestTemplate进行负载均衡后，分发到具体实例
> * 所有流量均使用Sentinel进行控制
> * 微服务之间通过RestTemplate进行通信
> * Consul对服务配置进行统一管理
> * Spring Cloud Sleuth监控服务的使用信息
> * Zipkin监控服务间的调用信息
> * Spring Boot Admin监控服务的运行状态和属性等相关信息
> * 在网关处聚合所有服务的Swagger

------

## 相关地址

> * 直接消费服务：http://localhost:18881/hi?name=666
> * 通过RestTemplate消费服务：http://localhost:18883/hi?name=666
> * 通过Spring Cloud Gateway消费：http://localhost:18884/service-hi/hi?name=666
> * Zipkin信息地址：http://localhost:9411/zipkin
> * Spring Boot Admin服务地址：http://localhost:18885
> * Sentinel控制台（需要调用相关接口后才能在控制台看到数据）：http://localhost:9500
> * Swagger UI：http://localhost:18884/swagger-ui/index.html

------

## 配置中心配置地址

指的是各服务在Consul中Key/Value的Key值，配置示例在config.yml和project/resources/config.yml中
> * application（全局配置）：config/application/data
> * service-admin：config/service-admin/data
> * service-gateway：config/service-gateway/data
