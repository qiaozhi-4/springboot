package com.zking.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraint implements ConstraintValidator<MyValidate, String>
{
    private String key;
    
    @Override
    public void initialize(MyValidate constraintAnnotation)
    {
        // 这里可以进行一些初始化，比如加载文件、数据库……
//        System.out.println("初始化中，必须添加关键字：" + constraintAnnotation.keyWord());
//        key = constraintAnnotation.keyWord();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        // 这里判断是否符合验证的要求，返回false说明验证失败
        if (value == null)
        {
            return false;
        }
        return true;
    }
}
