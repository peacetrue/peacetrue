package com.github.peacetrue.sign.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * the controller of {@link User}
 *
 * @author xiayx
 */
//tag::class[]
@Controller
@RequestMapping("/user")
public class UserController {
    private static Long id = 1L;
    private static List<User> users = new ArrayList<>(Collections.singletonList(
            new User(id++, "admin", "admin")
    ));

    @ResponseBody
    @GetMapping
    public User get(@RequestBody @SignData Long id) {
        Optional<User> first = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        return first.orElse(null);
    }

    @ResponseBody
    @PostMapping
    public User post(@RequestBody @SignData User user) {
        user.setId(id++);
        users.add(user);
        return user;
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = objectMapper.writeValueAsString(users.get(0));
        User user = objectMapper.readValue(value.getBytes(StandardCharsets.UTF_8), User.class);
        System.out.println(user);
    }

//end::class[]


}
