package com.github.peacetrue.result;

import com.github.peacetrue.result.exception.ResultExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.jackson.ResultJacksonExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.mysql.ResultMysqlExceptionAutoConfiguration;
import com.github.peacetrue.result.exception.Parameter;
import com.github.peacetrue.result.exception.web.ResultWebExceptionAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.MessageSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * the tests for {@code Result}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultExceptionAutoConfiguration.class,
                ResultJacksonExceptionAutoConfiguration.class,
                ResultMysqlExceptionAutoConfiguration.class,
                ResultWebExceptionAutoConfiguration.class,
                ResultWebAutoConfiguration.class,
                ExceptionTestController.class
        },
        properties = {
                "logging.level.root=info",
                "spring.messages.basename=messages",
                "peacetrue.result.codes.NotNull=001",
        }
)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class ResultWebExceptionTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResultBuilder resultBuilder;

    @Test
    public void MissingServletRequestParameterException() throws Exception {
        Result result = resultBuilder.build(
                "MissingServletRequestParameterException",
                new Object[]{"name", "String"},
                new Parameter<>("name", "String")
        );
        this.mockMvc.perform(get("/MissingServletRequestParameterException")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(FailureType.parameter_missing.name()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
        ;
    }


    @Test
    public void MissingPathVariableException() throws Exception {
        Result result = resultBuilder.build(
                "MissingPathVariableException",
                new Object[]{"number", Integer.class},
                new Parameter<>("number", Integer.class)
        );
        this.mockMvc.perform(get("/MissingPathVariableException")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(FailureType.server_error.name()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
        ;
    }

    @Test
    public void MethodArgumentTypeMismatchException() throws Exception {
        Result result = resultBuilder.build(
                "MethodArgumentTypeMismatchException",
                new Object[]{"number", Integer.class, "a"},
                new Parameter<>("number", Integer.class, "a")
        );

        this.mockMvc.perform(get("/MethodArgumentTypeMismatchException?number=a")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(FailureType.parameter_type_mismatch.name()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
        ;
    }

    @Test
    public void MethodArgumentConversionNotSupportedException() throws Exception {
        Result result = resultBuilder.build(
                "MethodArgumentConversionNotSupportedException",
                new Object[]{"user", User.class, "10"},
                new Parameter<>("user", User.class, "10")
        );

        this.mockMvc.perform(get("/MethodArgumentConversionNotSupportedException?user=10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(FailureType.server_error.name()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
        ;
    }

    @Test
    public void BindException() throws Exception {
        Result result = resultBuilder.build(
                "BindException",
                new Object[]{1}
        );

        this.mockMvc.perform(get("/BindException?name=jack")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(FailureType.parameter_error.name()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andDo(result1 -> System.out.println(result1.getResponse().getContentAsString()))
        ;

    }


}
