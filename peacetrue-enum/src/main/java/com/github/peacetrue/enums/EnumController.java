package com.github.peacetrue.enums;

import com.github.peacetrue.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 枚举控制器
 *
 * @author xiayx
 */
@RequestMapping("${peacetrue.enum.mapping:/enums}")
public class EnumController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /** 枚举类 */
    private Map<String, Enum[]> enumClasses = new HashMap<>();

    public EnumController(Map<String, Enum[]> enumClasses) {
        this.enumClasses = Objects.requireNonNull(enumClasses);
    }

    private Enum[] findOneInner(String name) {
        return enumClasses.getOrDefault(name, CollectionUtils.emptyArray(Enum.class));
    }

    @ResponseBody
    @GetMapping(params = "name")
    public Enum[] findOne(String name) {
        logger.info("获取名称为'{}'的枚举信息", name);
        return findOneInner(name);
    }

    @ResponseBody
    @GetMapping(params = "names")
    public Map<String, Enum[]> findSome(String... names) {
        logger.info("获取名称为'{}'的枚举信息", Arrays.toString(names));
        return Arrays.stream(names).collect(Collectors.toMap(Function.identity(), this::findOneInner));
    }


}
