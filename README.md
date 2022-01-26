## Spring Cloud Demo

Spring Cloud Demo based on Spring Boot 2.6 and Spring Cloud 2021.0

### Quick start

> * Consul (registration center\configuration center)
> > * docker run -d --name=consul -p 8500:8500 -v $PWD/data/:/consul/data/ \\  
      -e 'CONSUL_LOCAL_CONFIG={"skip_leave_on_interrupt":true,"telemetry":{"disable_compat_1.9":true}}' \\  
      consul:1.11 agent -server -ui -bootstrap -client='0.0.0.0'
> * Zipkin (service link tracking)
> > * docker run -d -p 9411:9411 --name=zipkin hackyo/zipkin:2
> * Sentinel (traffic defense)
> > * docker run -d -p 9500:8080 --name sentinel hackyo/sentinel:1.8
> * Service
> > * run AdminServiceApplication/GatewayServiceApplication/UserServiceApplication

------

### Component description

> * External requests uniformly access internal services through Spring Cloud Gateway
> * After the gateway receives the request, it obtains the available services from Consul
> * The gateway performs user authentication, then forwards the user information to the backend, and re-encapsulates the return value from the backend
> * All service traffic is controlled using Sentinel
> * Communication between microservices through RestTemplate
> * Consul for unified management of service configuration
> * Usage information of Spring Cloud Sleuth monitoring service
> * Zipkin monitors call information between services
> * Spring Boot Admin monitors the running status and properties of services and other related information
> * The service integrates unified exception management, authority authentication, i18n, liquibase

------

### Related address

> * request: http://localhost:18881/user/_login (the content of body is username and password，the default is admin)
> * request via gateway: http://localhost:18884/user-service/user/_login (body as above)
> * consul: http://localhost:8500
> * zipkin: http://localhost:9411/zipkin
> * spring boot admin: http://localhost:18885
> * sentinel (please request first, sentinel/sentinel): http://localhost:9500

------

### Consul config key/value
> * ./config.yml: config/application/data
> * ./${service-name}/resources/config.yml: config/${service-name}/data
