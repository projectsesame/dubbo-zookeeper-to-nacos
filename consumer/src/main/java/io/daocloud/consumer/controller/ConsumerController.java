package io.daocloud.consumer.controller;

import io.daocloud.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @DubboReference
    private HelloService helloService;

    @GetMapping("/sayHello")
    @ResponseBody
    public String sayHello(@RequestParam String name) {
        return "say " + helloService.sayHello(name);
    }
}
