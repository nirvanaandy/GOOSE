package com.leima.demo;


import com.google.gson.*;
import com.leima.demo.Entity.Product;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.http.HttpResponse;

@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class testHttpLibs {


    private static HttpURLConnection http = null;
    private static String urlStr = "http://localhost:8080/product/postproduct1";
    private static URL url;


    @BeforeAll
    public static void setupConnection () throws IOException {
        System.out.println("In Before");
        url = new URL(urlStr);
        http = (HttpURLConnection) url.openConnection();
        System.out.println(http.getURL());
    }


    @Test
    public void testPost(){
//        try {
//            url = new URL("http://localhost:8080/product/postproduct1");
//        } catch (MalformedURLException e) {
//            Assert.fail("URL exception "+e.getMessage());
//        }
        try{
            //http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type","application/json");
        http.setRequestProperty("Accept","application/json");
        http.connect();

        //String body = "{id:303,name:baoma,price:33.3}";
//            JsonObject body = new JsonObject();
//            body.addProperty("id","33");
//            body.addProperty("name","BMW");
//            body.addProperty("price","33.33");

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(http.getOutputStream(), "UTF-8"));
            bw.write(testJson());
            bw.close();

        int responseCode = http.getResponseCode();
        Assert.assertEquals(responseCode, HttpURLConnection.HTTP_OK);
        InputStream in = http.getInputStream();
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            while((line = reader.readLine())!=null){
                sb.append(line);
            }
            System.out.println(sb.toString());
        }
    } catch (IOException e) {
        //e.printStackTrace();
        Assert.fail("IOException occurred. "+e.getMessage());
    }

    }

    //@Disabled
    //@Test
    public String testJson(){
        String body = "{id:303,name:baoma,price:33.3}";
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Product p = gson.fromJson(body,Product.class);
        System.out.println(p);

        body = gson.toJson(p);
        System.out.println("String: "+body);

        JsonElement e = JsonParser.parseString(body);
                //new JsonParser();
        //JsonElement je = (jp).parse(body);


        return body;
    }

    @Disabled
    @Test
    public void testGet(){
        //System.out.println("In test");
        try {
            String str = urlStr+"?id=33";
            URL url2 = new URL(str);
            http = (HttpURLConnection) url2.openConnection();

            http.setRequestMethod("GET");
            http.connect();
            int responseCode = http.getResponseCode();
            Assert.assertEquals(responseCode, HttpURLConnection.HTTP_OK);
            InputStream in = http.getInputStream();
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

                while((line = reader.readLine())!=null){
                    sb.append(line);
                }
                System.out.println(sb.toString());
            }
        } catch (IOException e) {
            //e.printStackTrace();
            Assert.fail("IOException occurred. "+e.getStackTrace());
        }
    }

    @AfterAll
    public static void close(){
        http.disconnect();
    }
}
