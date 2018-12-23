package com.github.peacetrue.sign.appender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xiayx
 */
@RestController
@RequestMapping("/appender")
public class AppenderController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/form")
    public Map<String, String> form(@RequestParam Map<String, String> parameters) {
        logger.info("params: {}", parameters);
        return parameters;
    }

    @PostMapping("/body")
    public Map<String, String> body(@RequestBody Map<String, String> parameters) {
        logger.info("params: {}", parameters);
        return parameters;
    }

}
