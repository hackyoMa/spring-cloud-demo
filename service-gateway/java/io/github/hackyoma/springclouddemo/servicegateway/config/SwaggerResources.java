package io.github.hackyoma.springclouddemo.servicegateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;

/**
 * SwaggerResources
 *
 * @author hackyo
 * @version 2021/5/31
 */
@Component
public class SwaggerResources implements SwaggerResourcesProvider {

    private final Set<String> excludeServices;
    private final RouteLocator routeLocator;

    @Autowired
    public SwaggerResources(RouteLocator routeLocator,
                            @Value("${swagger.exclude-services}") String[] excludeServices) {
        this.routeLocator = routeLocator;
        this.excludeServices = Set.of(excludeServices);
    }

    @Override
    public List<SwaggerResource> get() {
        Set<String> routeHosts = new TreeSet<>();
        routeLocator.getRoutes()
                .filter(route -> route.getUri().getHost() != null
                        && !route.getUri().getHost().isEmpty()
                        && !excludeServices.contains(route.getUri().getHost()))
                .subscribe(route -> routeHosts.add(route.getUri().getHost()));
        List<SwaggerResource> resources = new ArrayList<>(routeHosts.size());
        routeHosts.forEach(routeHost -> {
            String url = "/" + routeHost.toLowerCase() + "/v3/api-docs";
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setUrl(url);
            swaggerResource.setName(routeHost);
            resources.add(swaggerResource);
        });
        return resources;
    }

}
