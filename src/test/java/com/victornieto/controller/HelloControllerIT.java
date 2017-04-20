package com.victornieto.controller;

import com.victornieto.SbBaseApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by victor.nieto.castan on 20/04/2017.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SbBaseApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerIT {
    @LocalServerPort
    private int port ;

    private URL base ;

    @Autowired
    private TestRestTemplate template ;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/api/v1/");
    }

    @Test
    public  void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class) ;
        assertThat(response.getBody(), equalTo("Hello World!!")) ;
    }
}
