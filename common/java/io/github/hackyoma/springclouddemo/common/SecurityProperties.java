package io.github.hackyoma.springclouddemo.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * SecurityProperties
 *
 * @author hackyo
 * @version 2022/1/18
 */
@ConfigurationProperties("security")
@Component
public class SecurityProperties {

    private final static String ALL_METHOD = "ALL";
    private Map<String, List<String>> globalWhitelist;
    private Map<String, Map<String, List<String>>> whitelist;
    private Map<String, Map<String, List<String>>> internalWhitelist;

    public List<String> getWhitelist(String serviceId, String method, boolean internal) {
        serviceId = serviceId.toLowerCase(Locale.ROOT);
        method = method.toUpperCase(Locale.ROOT);
        Map<String, Map<String, List<String>>> whitelist;
        if (internal) {
            whitelist = this.internalWhitelist;
        } else {
            whitelist = this.whitelist;
        }
        List<String> wl = new ArrayList<>();
        if (!CollectionUtils.isEmpty(whitelist) && whitelist.containsKey(serviceId)) {
            Map<String, List<String>> serviceWhitelist = whitelist.get(serviceId);
            if (serviceWhitelist.containsKey(method)) {
                wl.addAll(serviceWhitelist.get(method));
            }
            if (serviceWhitelist.containsKey(ALL_METHOD)) {
                wl.addAll(serviceWhitelist.get(ALL_METHOD));
            }
        }
        return wl;
    }

    public void setWhitelist(Map<String, Map<String, List<String>>> whitelist) {
        this.whitelist = whitelist;
    }

    public void setInternalWhitelist(Map<String, Map<String, List<String>>> internalWhitelist) {
        this.internalWhitelist = internalWhitelist;
    }

    public List<String> getGlobalWhitelist(String method) {
        method = method.toUpperCase(Locale.ROOT);
        List<String> wl = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.globalWhitelist)) {
            if (this.globalWhitelist.containsKey(method)) {
                wl.addAll(this.globalWhitelist.get(method));
            }
            if (this.globalWhitelist.containsKey(ALL_METHOD)) {
                wl.addAll(this.globalWhitelist.get(ALL_METHOD));
            }
        }
        return wl;
    }

    public void setGlobalWhitelist(Map<String, List<String>> globalWhitelist) {
        this.globalWhitelist = globalWhitelist;
    }

    public List<String> getAllWhitelist(String serviceId, String method, boolean internal) {
        List<String> whitelist = this.getGlobalWhitelist(method);
        whitelist.addAll(this.getWhitelist(serviceId, method, false));
        if (internal) {
            whitelist.addAll(this.getWhitelist(serviceId, method, true));
        }
        return whitelist;
    }

}
