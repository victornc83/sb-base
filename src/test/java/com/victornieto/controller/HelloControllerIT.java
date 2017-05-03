package com.victornieto.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by victor.nieto.castan on 20/04/2017.
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
public class HelloControllerIT {

    @Value("${service.endpoint}")
    private String endpoint ;

    private URL base ;

    @Before
    public void setUp() throws Exception {
        this.base = new URL(endpoint + "/api/v1/");
    }

    @Test
    public void getHello() throws Exception {
        RestTemplate template = new RestTemplate() ;
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class) ;
        assertThat(response.getBody(), equalTo("Hello World!!")) ;
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getBye() throws Exception {
        RestTemplate template = new RestTemplate() ;
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class) ;
        assertThat(response.getBody(), equalTo("Bye World!!"));
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
}
