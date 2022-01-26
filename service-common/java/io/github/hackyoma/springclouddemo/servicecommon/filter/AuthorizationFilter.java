package io.github.hackyoma.springclouddemo.servicecommon.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.hackyoma.springclouddemo.common.RequestUtil;
import io.github.hackyoma.springclouddemo.common.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * AuthorizationFilter
 *
 * @author hackyo
 * @version 2022/1/14
 */
@Order(1700)
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private final static AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private final String serviceId;
    private final SecurityProperties securityProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public AuthorizationFilter(@Value("${spring.application.name}") String serviceId,
                               SecurityProperties securityProperties,
                               RestTemplate restTemplate) {
        this.serviceId = serviceId;
        this.securityProperties = securityProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain) throws ServletException, IOException {
        String path = request.getServletPath();
        List<String> whitelist = securityProperties.getAllWhitelist(serviceId, request.getMethod(), true);
        if (whitelist.stream().anyMatch(wl -> PATH_MATCHER.match(wl, path))) {
            chain.doFilter(request, response);
            return;
        }

        LinkedMultiValueMap<String, String> queryUserAllInfoHeaders = new LinkedMultiValueMap<>(1);
        queryUserAllInfoHeaders.add(RequestUtil.USER_ID, request.getHeader(RequestUtil.USER_ID));
        JSONObject userInfo = restTemplate.exchange("http://user-service//user/all_info",
                HttpMethod.GET, new HttpEntity<>(queryUserAllInfoHeaders), JSONObject.class).getBody();
        if (userInfo != null) {
            JSONArray permissions = userInfo.getJSONArray("permissions");
            for (int i = 0; i < permissions.size(); i++) {
                JSONObject permission = permissions.getJSONObject(i);
                String service = permission.getString("service");
                String url = permission.getString("url");
                if (serviceId.equals(service) && PATH_MATCHER.match(url, path)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        response.sendError(HttpStatus.FORBIDDEN.value());
    }

}
