package com.leima.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leima.demo.Entity.Product;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SpringBootTest
public class testHttpClient {

#This is used for a code review
    public static CloseableHttpClient httpClient;
    public static CloseableHttpResponse response;

    @BeforeAll
    public static void setup(){
        httpClient = HttpClients.createDefault();
        System.out.println("In Before");
    }


    @Test
    public void testGet(){

        String url = "http://localhost:8083/product/getproduct2?id=45";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Content-Type","application/json");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000)
                                        .setConnectionRequestTimeout(1000)
                                        .setSocketTimeout(10000).build();
        httpGet.setConfig(requestConfig);
        try {

            response = httpClient.execute(httpGet);

            HttpEntity entity = response.getEntity();

            //System.out.println(EntityUtils.toString(entity));
            ObjectMapper mapper = new ObjectMapper();
            Product p = mapper.readValue(EntityUtils.toString(entity), Product.class);
            System.out.println(p);
        } catch (IOException e) {
            Assert.fail("HttpGet failed "+e.getMessage());
        }
    }

    @Disabled
    @Test
    public void testPost() {

        String url = "http://localhost:8083/product/postproduct1";
        HttpPost httpPost = new HttpPost(url);

        httpPost.setHeader("Content-Type","application/json");
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).build();
        httpPost.setConfig(requestConfig);
        try {
            ObjectMapper mapper = new ObjectMapper();
            Product p = new Product(44,"PORSH",55.5);
            String s = mapper.writeValueAsString(p);
            httpPost.setEntity(new StringEntity(s));
            //httpPost.setEntity(new StringEntity("{\"id\":303,\"name\":\"baoma\",\"price\":33.3}"));
            //StringBuilder sb = new StringBuilder();


        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            e.printStackTrace();
        }

        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterAll
    public static void close(){
        try {
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
