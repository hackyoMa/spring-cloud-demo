## Spring Cloud Demo

基于Spring Boot 2.6和Spring Cloud 2021.0的Spring Cloud Demo

### 快速开始

> * Consul (注册中心\配置中心)
> > * docker run -d --name=consul -p 8500:8500 -v $PWD/data/:/consul/data/ \\  
      -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt":true,"telemetry":{"disable_compat_1.9":true}}' \\  
      consul:1.11 agent -server -ui -bootstrap -client='0.0.0.0'
> * Zipkin (服务链路追踪)
> > * docker run -d -p 9411:9411 --name=zipkin hackyo/zipkin:2
> * Sentinel (流量防卫)
> > * docker run -d -p 9500:8080 --name sentinel hackyo/sentinel:1.8
> * Service
> > * run AdminServiceApplication/GatewayServiceApplication/UserServiceApplication

------

### 组件说明

> * 外部请求统一通过Spring Cloud Gateway来访问内部服务
> * 网关接收到请求后，从Consul获取可用服务
> * 网关进行用户鉴权，然后转发用户信息到后端，并对后端返回值进行二次封装
> * 所有服务流量均使用Sentinel进行控制
> * 微服务之间通过RestTemplate进行通信
> * Consul对服务配置进行统一管理
> * Spring Cloud Sleuth监控服务的使用信息
> * Zipkin监控服务间的调用信息
> * Spring Boot Admin监控服务的运行状态和属性等相关信息
> * 服务集成了统一异常管理和权限认证

------

### 相关地址

> * 直接请求: http://localhost:18881/hi?name=666
> * 通过gateway请求: http://localhost:18884/hi-service/hi?name=666
> * consul: http://localhost:8500
> * zipkin: http://localhost:9411/zipkin
> * spring boot admin: http://localhost:18885
> * sentinel (请求后才能在控制台看到服务): http://localhost:9500

------

### Consul配置文件key/value
> * ./config.yml: config/application/data
> * ./${service-name}/resources/config.yml: config/${service-name}/data
