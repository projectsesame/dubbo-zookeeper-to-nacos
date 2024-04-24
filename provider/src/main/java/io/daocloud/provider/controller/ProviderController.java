package io.daocloud.provider.controller;

import io.daocloud.provider.config.SystemConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ProviderController {

    @Value(value = "${system.demo:fail}")
    public String state;

    public final SystemConfig systemConfig;

    public ProviderController(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    @GetMapping("/health")
    public String Health() {
        return "success";
    }

    @GetMapping("/dynamic-config")
    public String State() {
        return state;
    }

    @GetMapping("/dynamic-config-system")
    public SystemConfig System() {
        return systemConfig;
    }
}
