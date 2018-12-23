package com.github.peacetrue.sign.validator;

import com.github.peacetrue.sign.SignValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author xiayx
 */
@RestController
@RequestMapping("/validator")
public class ValidatorController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @SignValid
    @RequestMapping("/form")
    public Map<String, String> form(@RequestParam Map<String, String> parameters) {
        logger.info("params: {}", parameters);
        return parameters;
    }

    @SignValid
    @PostMapping("/body")
    public Map<String, String> body(@RequestBody Map<String, String> parameters) {
        logger.info("params: {}", parameters);
        return parameters;
    }

}
