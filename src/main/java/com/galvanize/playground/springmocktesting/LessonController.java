package com.galvanize.playground.springmocktesting;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;
import wiremock.com.jayway.jsonpath.spi.json.JsonOrgJsonProvider;
import wiremock.net.minidev.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock-lessons")
public class LessonController {

    public LessonRepository repository;

    public LessonController()
    {

    }
    public LessonController(LessonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Lesson> all()
    {
        System.out.println("Inside All Method ");
        return this.repository.findAll();
    }

    @PostMapping
    public Lesson save (@RequestBody Lesson lesson )
    {
        System.out.println("inside create");
        try{
            return this.repository.save(lesson);

        }catch (Exception e)
        {
            System.out.println("Exceptin is "+e);
        }
        return null;

    }

    @DeleteMapping
    public void delete(@RequestBody Lesson lesson)
    {
            this.repository.delete(lesson);

    }

    @PutMapping
    public Lesson update(@RequestBody Lesson lesson)
    {
        return this.repository.save(lesson);
    }

    @RequestMapping(value = "/getlessons", method = RequestMethod.GET)
    public boolean getLessons() throws MalformedURLException {

        HttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet("http://localhost:1234/get-lessons");
        request.addHeader("Accept", "application/json");
        try {

            httpResponse = httpClient.execute(request);
            System.out.println("Status code is "+httpResponse.getStatusLine().getStatusCode());
            String jsonResponse = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(jsonResponse);
            Map<String, String> map = new HashMap<String,String>();
            map.put("lesson",jsonResponse);


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
