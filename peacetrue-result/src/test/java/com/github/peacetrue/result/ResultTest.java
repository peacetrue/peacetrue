package com.github.peacetrue.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * the tests for {@code Result}
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
@EnableWebMvc
@EnableAutoConfiguration
@ComponentScan
public class ResultTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private ResultBuilder resultBuilder;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void pageSuccess() throws Exception {
        User user = UserController.users.get(0);
        Result result = resultBuilder.build();
        this.mockMvc.perform(get("/user/view?id={0}", 1L)
                .accept(MediaType.TEXT_HTML)
        )
                .andExpect(model().attribute("code", result.getCode()))
                .andExpect(model().attribute("message", result.getMessage()))
                .andExpect(model().attribute("data", user))
        ;

    }

    @Test
    public void pageError() throws Exception {
        Long id = 0L;
        Result result = resultBuilder.build("EntityNotFoundException", new Object[]{id, User.class});
        this.mockMvc.perform(get("/user/view?id={0}", id)
                .accept(MediaType.TEXT_HTML)
        )
                .andExpect(model().attribute("code", result.getCode()))
                .andExpect(model().attribute("message", result.getMessage()))
                .andExpect(model().attribute("data", id))
        ;

    }

    @Test
    public void dataSuccess() throws Exception {
        User user = UserController.users.get(0);
        Result result = resultBuilder.build();
        this.mockMvc.perform(get("/user/view?id={0}", 1L)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data.id").value(user.getId()))
        ;
    }

    @Test
    public void dataError() throws Exception {
        Long id = 0L;
        Result result = resultBuilder.build("EntityNotFoundException", new Object[]{id, User.class});
        this.mockMvc.perform(get("/user/view?id={0}", id)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data").value(id))
        ;

    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void postDataSuccess() throws Exception {
        User user = random(User.class);
        Result result = resultBuilder.build();
        this.mockMvc.perform(post("/user")
                .content(objectMapper.writeValueAsBytes(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data.name").value(user.getName()))
                .andExpect(jsonPath("$.data.password").value(user.getPassword()))
        ;
    }

    @Test
    public void postDataError() throws Exception {
        User user = random(User.class);
        String errorChar = "{xxx";
        Result result = resultBuilder.build(ErrorType.argument_format_mismatch.name(), new Object[]{errorChar, 1, 2});
        String content = errorChar + objectMapper.writeValueAsString(user);
        System.out.println(content);
        this.mockMvc.perform(post("/user")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data[0]").value("x"))
        ;
    }

    @Test
    public void validDataSuccess() throws Exception {
        User user = random(User.class);
        Result result = resultBuilder.build(ResultType.error.group(), new Object[]{2});
        this.mockMvc.perform(post("/user")
                .content(objectMapper.writeValueAsBytes(user))
                .param("id", user.getId().toString())
                .param("name", user.getName())
                .param("password", user.getPassword())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data").isArray())
        ;
    }

    @Test
    public void returnVoid() throws Exception {
        Result result = resultBuilder.build();
        this.mockMvc.perform(post("/user/void")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
        ;
    }

}
