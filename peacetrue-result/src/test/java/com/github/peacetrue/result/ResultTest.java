package com.github.peacetrue.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
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
@SpringBootTest(classes = {
        WebMvcAutoConfiguration.class,
        ResultAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        UserController.class
})
@EnableWebMvc
public class ResultTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private ResultBuilder resultBuilder;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getPageSuccess() throws Exception {
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
    public void getPageError() throws Exception {
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
    public void getDataSuccess() throws Exception {
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
    public void getDataError() throws Exception {
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

    @Test
    public void postDataSuccess() throws Exception {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        Result result = resultBuilder.build();
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", user.getName())
                .param("password", user.getPassword())
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
        User user = new User();
        user.setName("nam");
        user.setPassword("pas");
        Result result = resultBuilder.build(ResultType.error.group(), new Object[]{2});
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("name", user.getName())
                .param("password", user.getPassword())
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data").isArray())
        ;
    }


    @Test
    public void requestBodyDataSuccess() throws Exception {
        User user = random(User.class);
        Result result = resultBuilder.build();
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(user))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data.name").value(user.getName()))
                .andExpect(jsonPath("$.data.password").value(user.getPassword()))
        ;
    }

    @Test
    public void requestBodyDataError() throws Exception {
        User user = random(User.class);
        String errorChar = "{xxx";
        Result result = resultBuilder.build(ErrorType.argument_format_mismatch.name(), new Object[]{errorChar, 1, 2});
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(errorChar + objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data[0]").value("x"))
        ;
    }

    @Test
    public void voidAsResult() throws Exception {
        Result result = resultBuilder.build();
        this.mockMvc.perform(post("/return/void")
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data").doesNotExist())
        ;
    }

    @Test
    public void stringAsDataResult() throws Exception {
        String data = random(String.class);
        DataResult<String> result = resultBuilder.build(data);
        this.mockMvc.perform(post("/return/string?input={0}", result.getData())
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data").value(result.getData()))
        ;
    }

}
