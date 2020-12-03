package com.github.peacetrue.enums;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EnumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void findOne() throws Exception {
        this.mockMvc.perform(get("/enums?name={0}", "applications")
                .accept(MediaType.APPLICATION_JSON)
        );

    }

    @Test
    public void findSome() throws Exception {
        this.mockMvc.perform(get("/enums?names={0}&names={1}", "applications", "applications2")
                .accept(MediaType.APPLICATION_JSON)
        );
    }

}
