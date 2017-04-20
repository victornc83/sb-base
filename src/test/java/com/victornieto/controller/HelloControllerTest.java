package com.victornieto.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by victor.nieto.castan on 20/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class HelloControllerTest {

    @InjectMocks
    private HelloController h ;

    @Test
    public void helloWorld(){
        assertEquals(h.helloWorld(),"Hello World!!");
    }

}