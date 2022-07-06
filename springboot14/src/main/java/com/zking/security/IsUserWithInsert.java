package com.zking.security;

import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.security.RolesAllowed;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})//方法和类型可用
@Retention(RetentionPolicy.RUNTIME)//运行是配置
@Inherited
@Documented
@PreAuthorize("hasRole('user') and hasAuthority('insert')")
public @interface IsUserWithInsert {
}
