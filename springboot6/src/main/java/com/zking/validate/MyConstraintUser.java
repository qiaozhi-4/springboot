package com.zking.validate;

import com.zking.entity.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class MyConstraintUser implements ConstraintValidator<MyValidate, User> {
    private String key;

    @Override
    public void initialize(MyValidate constraintAnnotation) {
        // 这里可以进行一些初始化，比如加载文件、数据库……
//        System.out.println("初始化中，必须添加关键字：" + constraintAnnotation.keyWord());
//        key = constraintAnnotation.keyWord();
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {

//        User对象不能为空，id必须为正数，age必须为18~100之间
//        其他属性不能为空（比如email/phone/name等）
//        添加Controller配合ApiPost测试
        // 这里判断是否符合验证的要求，返回false说明验证失败
        if (user == null) {

            return false;
        }
        if (user.getPassword() == null || user.getUsername() == null ||
                user.getId() == null || user.getMoney() == null) {
            return false;
        }
        if (user.getId() < 1) {
            return false;
        }
        if (user.getMoney() < 0 || user.getMoney() > 10000) {
            return false;
        }
        return true;
    }
}
