package com.github.peacetrue.sign.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * tests for server sign
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringBootConfiguration
//@EnableAutoConfiguration
//@ComponentScan
//@WebAppConfiguration
public class ServerSignTest {


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private ServerSignProperties signProperties;
    @Autowired
    private ObjectMapper objectMapper;
//    @Autowired
//    private ClientSignService clientSignService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void findUser() throws Exception {
        String id = "xiamen";
        String secret = signProperties.getApps().get(id);
//        String content = objectMapper.writeValueAsString(clientSignService.sign(1L, id, secret));
        this.mockMvc.perform(
                get("/user/view")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
        )
                .andExpect(jsonPath("$").exists());
    }


}