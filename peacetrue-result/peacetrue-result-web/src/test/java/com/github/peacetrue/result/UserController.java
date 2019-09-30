package com.github.peacetrue.result;

import com.github.peacetrue.result.exception.DataResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static List<User> users = new ArrayList<>(Collections.singletonList(
            new User(1L, "admin", "admin")
    ));
    @Autowired(required = false)
    private ResultBuilder resultBuilder;

    @GetMapping("/user/view")
    public String view(Model model, Long id) {
        Optional<User> first = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            //设置用户数据
            ResultWebUtils.bind(model, first.get());
        } else {
            //抛出自定义异常
            throw new DataResultException(resultBuilder.build("EntityNotFoundException", new Object[]{id, User.class}, id));
        }
        return "user/detail";
    }
//end::class[]

    private long index = users.size();

    @WithoutResultWrapper
    @PostMapping("/user")
    public User add(@Valid User user) {
        logger.info("user:{}", user);
        user.setId(index++);
        users.add(user);
        return user;
    }

    @WithoutResultWrapper
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User addByRequestBody(@RequestBody User user) {
        logger.info("user:{}", user);
        user.setId(index++);
        users.add(user);
        return user;
    }

    @WithoutResultWrapper
    @PostMapping(value = "/return/void")
    public void returnVoid() {
        logger.info("returnVoid");
    }

    @WithoutResultWrapper
    @PostMapping(value = "/return/string")
    public String returnString(String input) {
        return input;
    }

}
