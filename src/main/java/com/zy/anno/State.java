package com.zy.anno;

import com.zy.validation.StateValidation;
import jakarta.validation.Payload;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//元注解，可以标记到帮助文档
@Documented
//元注解，标识我们的注解可以用在哪
@Target(ElementType.FIELD)
//元注解，标识我们的注解可以保留到哪个阶段
@Retention(RetentionPolicy.RUNTIME)
//指定校验规则类
@Constraint(
        validatedBy = {StateValidation.class}
)
public @interface State {
    //提供校验失败提示
    String message() default "state的值只能是"+"已发布"+"或"+"稿";

    //指定分组
    Class<?>[] groups() default {};

    //负载，获取state注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
