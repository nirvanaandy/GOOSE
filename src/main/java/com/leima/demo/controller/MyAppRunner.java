package com.leima.demo.controller;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyAppRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("foo="+args.getOptionValues("foo"));
        System.out.println("heart="+args.getOptionValues("heart"));
    }
}
