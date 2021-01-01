package com.leima.demo;

import com.leima.demo.Entity.Product;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import org.junit.Ignore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    RestTemplate restTemplate = null;

    @BeforeEach
    public void setup(){
        restTemplate = new RestTemplate();
        System.out.println("help1");
        System.out.println("help2");
    }

/*
    @Ignore
    @Test
    public void testGet_Product1(){
        String url = "http://localhost:8080/product/getproduct1";
        String result = restTemplate.getForObject(url,String.class);
        System.out.println(result);
        Assert.hasText(result,"return null");

        Product product = restTemplate.getForObject(url, Product.class);
        System.out.println(product);
        Assert.notNull(product,"Get null product");

        ResponseEntity<Product> re = restTemplate.getForEntity(url, Product.class);

        System.out.println("ResponseEntity: "+re);
        Assert.isTrue(re.getStatusCode().equals(HttpStatus.OK), "No Response");
    }

    @Ignore
    @Test
    public void testGetProduct1S(){

        String url = "http://localhost:8080/product/getproduct1";

        MultiValueMap header = new LinkedMultiValueMap();
        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> re = new HttpEntity<>(header);

        ResponseEntity<Product> rp = restTemplate.exchange(url, HttpMethod.GET, re,Product.class);
        System.out.println("Exchange: "+rp);
        Assert.isTrue(rp.getStatusCode().equals(HttpStatus.OK), "Wrong response");

        String exeResult = restTemplate.execute(url, HttpMethod.GET, request -> {
            request.getHeaders().add(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);
        },(response)->{
            InputStream body = response.getBody();
            byte[] bytes = new byte[body.available()];
            body.read(bytes);
            return new String(bytes);
        });
        System.out.println("Execute: "+exeResult);
        Assert.hasText(exeResult,"return null");
    }

    @Ignore
    @Test
    public void testGetWithParam(){

        String url = "http://localhost:8080/product/getproduct2?id={id}";

        ResponseEntity<Product> rp = restTemplate.getForEntity(url, Product.class, 101);
        System.out.println("WithParam: "+rp);

        Map<String,Object> urlVar = new HashMap<>();
        urlVar.put("id",102);
        Product p = restTemplate.getForObject(url,Product.class,urlVar);
        System.out.println("WithMap: "+p);

    }

    @Ignore
    @Test
    public void testPostProduct(){

        String url = "http://localhost:8080/product/postproduct1";

        Product p = new Product(201,"Mac",111.1);

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_JSON_VALUE));
        header.put(HttpHeaders.ACCEPT,Arrays.asList(MediaType.APPLICATION_JSON_VALUE));

//        MultiValueMap<String, Object> newMap = new LinkedMultiValueMap<>();
//        newMap.add("id", p.getId());
//        newMap.add("name",p.getName());
//        newMap.add("price",p.getPrice());


        HttpEntity<Product> request = new HttpEntity<>(p,header);
        ResponseEntity<Product> rp = restTemplate.exchange(url, HttpMethod.POST,request, Product.class);
        System.out.println("Post： "+ rp);
    }
*/
    @Ignore
    @Disabled
    @Test
    public void testWebClient(){

        String baseUrl = "http://localhost:8080";
        String subUrl = "/product/postproduct1";

        WebClient client1 = WebClient.builder().baseUrl(baseUrl)
                            .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                            .defaultUriVariables(Collections.singletonMap("url",baseUrl))
                            .build();

        //WebClient client1 = WebClient.create(baseUrl);

        WebClient.RequestBodySpec uri1 = client1.method(HttpMethod.POST)
                                        .uri(subUrl);

        Product p = new Product(201,"Mac",111.1);

        BodyInserter<Object, ReactiveHttpOutputMessage> inserter1 = BodyInserters.fromObject(p);
        WebClient.ResponseSpec response = uri1.body(inserter1)
                                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .accept(MediaType.APPLICATION_JSON).retrieve();


        Mono<Product> result = response.bodyToMono(Product.class);
        System.out.println(result.block());
    }
}
