package com.github.peacetrue.result;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * tests for web exceptions
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                ExceptionTestController.class
        },
        properties = {
                "logging.level.root=info",
        }
)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class WebExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void MissingServletRequestParameterException() throws Exception {
        this.mockMvc.perform(get("/MissingServletRequestParameterException"))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }


    @Test
    public void MissingPathVariableException() throws Exception {
        this.mockMvc.perform(get("/MissingPathVariableException"))
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void MethodArgumentTypeMismatchException() throws Exception {
        this.mockMvc.perform(get("/MethodArgumentTypeMismatchException?number=a"))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(""));

        this.mockMvc.perform(get("/MethodArgumentTypeMismatchException/a"))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void MethodArgumentConversionNotSupportedException() throws Exception {
        this.mockMvc.perform(get("/MethodArgumentConversionNotSupportedException?user="))
                .andExpect(status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    public void BindException() throws Exception {
        this.mockMvc.perform(get("/BindException"))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

}
