package com.github.peacetrue.result;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * validator for {@link UserIdentity}
 *
 * @author xiayx
 */
@Component
public class UserIdentityValidator implements ConstraintValidator<UserIdentity, User> {

    private String name;
    private String password;

    @Override
    public void initialize(UserIdentity userIdentity) {
        name = userIdentity.name();
        password = userIdentity.password();
    }

    @Override
    public boolean isValid(User value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value.getName()) || StringUtils.isEmpty(value.getPassword())) return true;
        return name.equals(value.getName()) && password.equals(value.getPassword());
    }
}