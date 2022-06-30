package com.zking.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MyConstraint.class, MyConstraintUser.class})
public @interface MyValidate
{
    // 可以自定义参数
//    String keyWord() default "SpringBoot";
    
    // 这些都是必须的属性
    // 表示验证失败后的错误信息
    String message() default "非法用户名！";
    
    // 相当于message
    String value() default "非法用户名！";
    
    // 分组
    Class<?>[] groups() default {};
    
    // 验证相关的信息
    Class<? extends Payload>[] payload() default {};
}
