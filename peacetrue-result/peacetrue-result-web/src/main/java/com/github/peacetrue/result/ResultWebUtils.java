package com.github.peacetrue.result;

import com.github.peacetrue.spring.util.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * the util class for {@link Result}
 *
 * @author xiayx
 */
public abstract class ResultWebUtils {

    private static ResultBuilder resultBuilder;

    static void setResultBuilder(ResultBuilder resultBuilder) {
        ResultWebUtils.resultBuilder = Objects.requireNonNull(resultBuilder);
    }

    /**
     * bind data to model
     *
     * @param model the model
     * @param data  the data
     */
    public static void bind(Model model, Object data) {
        model.addAllAttributes(BeanUtils.map(resultBuilder.success(data)));
    }

    /**
     * bind data to modelMap
     *
     * @param modelMap the modelMap
     * @param data     the data
     */
    public static void bind(ModelMap modelMap, Object data) {
        modelMap.addAllAttributes(BeanUtils.map(resultBuilder.success(data)));
    }


    /**
     * bind data to request
     *
     * @param request the request
     * @param data    the data
     */
    public static void bind(HttpServletRequest request, Object data) {
        BeanUtils.map(resultBuilder.success(data)).forEach(request::setAttribute);
    }


}
