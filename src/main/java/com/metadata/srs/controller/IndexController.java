package com.metadata.srs.controller;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")

public class IndexController {

    @Autowired
    Environment environment;

    // @Autowired
    // private RegistrationService registrationService;

    @GetMapping("/")
    public String index() {
       // String hostAddress = InetAddress.getLoopbackAddress().getHostAddress();
       // String hostName = InetAddress.getLoopbackAddress().getHostName();
        //System.out.println(hostAddress + "<>" + hostName);
        return "Welcome to courses registration <b><a href=/swagger-ui/index.html>APIs</a>";
    }

}
