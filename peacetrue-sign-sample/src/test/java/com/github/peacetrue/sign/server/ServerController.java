package com.github.peacetrue.sign.server;

import com.github.peacetrue.sign.SignValid;
import com.github.peacetrue.sign.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiayx
 */
@RestController
public class ServerController {

    /** 供客户端获取数据 */
    @SignValid
    @GetMapping
    public User getByParam(User user) {
        return user;
    }

    /** 供客户端获取数据 */
    @SignValid
    @PostMapping
    public User postByParam(User user) {
        return user;
    }


    /** 供客户端获取数据 */
    @SignValid
    @PostMapping("/body")
    public User postByBody(@RequestBody User user) {
        return user;
    }


}
