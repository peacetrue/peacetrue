package com.github.peacetrue.spring.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author : xiayx
 * @since : 2020-06-26 17:04
 **/
@Data
@ConfigurationProperties(prefix = "peacetrue.spring.security")
public class SecurityJsonProperties {

    /** 未登陆链接 */
    private String unauthorizedUrl = "/unauthorized";
    /** 登陆链接(执行登陆) */
    private String loginUrl = "/login";
    /** 登陆成功链接 */
    private String loginSuccessUrl = "/login/success";
    /** 登陆失败链接 */
    private String loginFailureUrl = "/login/failure";
    /** 退出成功链接 */
    private String logoutSuccessUrl = "/logout/success";
    /** 需要忽略认证的链接 */
    private List<String> ignoredUrls;

}
