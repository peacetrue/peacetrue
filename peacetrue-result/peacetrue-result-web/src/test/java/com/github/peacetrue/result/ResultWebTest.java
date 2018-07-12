package com.github.peacetrue.result;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * tests for web support
 *
 * @author xiayx
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {
                MessageSourceAutoConfiguration.class,
                ResultBuilderAutoConfiguration.class,
                ResultWebAutoConfiguration.class,
                UserController.class
        },
        properties = {
                "logging.level.root=trace",
                "spring.messages.basename=messages",
        }
)
@AutoConfigureWebMvc
@AutoConfigureMockMvc
public class ResultWebTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ResultBuilder resultBuilder;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getPage() throws Exception {
        User user = UserController.users.get(0);
        Result result = resultBuilder.success();
        this.mockMvc.perform(get("/user/view?id={0}", 1L)
                .accept(MediaType.TEXT_HTML)
        )
                .andExpect(model().attribute("code", result.getCode()))
                .andExpect(model().attribute("message", result.getMessage()))
                .andExpect(model().attribute("data", user))
        ;

    }

    @Test
    public void getData() throws Exception {
        User user = UserController.users.get(0);
        Result result = resultBuilder.success();
        this.mockMvc.perform(get("/user/view?id={0}", 1L)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data.id").value(user.getId()))
        ;
    }

    @Test
    public void postData() throws Exception {
        User user = new User();
        user.setName("name");
        user.setPassword("password");
        Result result = resultBuilder.success();
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
    public void postDataBody() throws Exception {
        User user = random(User.class);
        Result result = resultBuilder.success();
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
    public void voidAsResult() throws Exception {
        Result result = resultBuilder.success();
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
        DataResult<String> result = resultBuilder.success(random(String.class));
        this.mockMvc.perform(post("/return/string?input={0}", result.getData())
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(jsonPath("$.code").value(result.getCode()))
                .andExpect(jsonPath("$.message").value(result.getMessage()))
                .andExpect(jsonPath("$.data").value(result.getData()))
        ;
    }

}
