package com.leima.demo.controller;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.leima.demo.Entity.Product;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.annotation.WebFilter;

@RestController
@RequestMapping("/product")
public class MainController {

    @GetMapping("/help")
    @ResponseStatus(HttpStatus.OK)
    public String processHelp(@RequestParam(name="name",required = false,defaultValue = "leima")
                              String name, Model model){
        model.addAttribute("name", name);

        return "Greeting";

    }

    @GetMapping("/getproduct1")
    public Product get_product1(){
        return new Product(1, "FirstProduct",11.11);
    }

    @GetMapping("/getproduct2")
    public Product get_product2(@RequestParam("id") Integer id){
        return new Product(id,"SecondProduct",22.22);
    }

    @PostMapping("/postproduct1")
    public Product postProduct2(@RequestBody Product product){
        System.out.println(product.getId());
        return new Product(product.getId()+1,product.getName()+"_A",product.getPrice()+10.0);
    }
}
