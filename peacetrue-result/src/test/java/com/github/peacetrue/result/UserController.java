package com.github.peacetrue.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.peacetrue.result.exception.ResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Max;
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
    private List<User> users = new ArrayList<>(Collections.singletonList(
            new User(1L, "admin", "admin")
    ));
    @Autowired
    private ResultBuilder resultBuilder;

    @GetMapping("/user/view")
    public String view(Model model, @Max(10) Long id) {
        Optional<User> first = users.stream().filter(user -> user.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            //设置用户数据
            ResultUtils.bind(model, first.get());
        } else {
            //抛出自定义异常
            throw new ResultException(resultBuilder.build("EntityNotFoundException", new Object[]{id, User.class}));
        }
        return "user/detail";
    }
//end::class[]

    private long index = 1L;

    @PostMapping("/user")
    public String add(@Valid User user) {
        logger.info("user:{}", user);
        user.setId(index++);
        users.add(user);
        return "redirect:/user/view?id=" + user.getId();
    }

    @PostMapping("/user/body")
    public String addRequestBody(@RequestBody @Valid User user) {
        logger.info("user:{}", user);
        user.setId(index++);
        users.add(user);
        return "redirect:/user/view?id=" + user.getId();
    }


    public static void main(String[] args) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(new UserController().users.get(0)));
    }

}
