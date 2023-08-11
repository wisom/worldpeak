package com.worldpeak.chnsmilead.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc：需要使用eventbus的activit和Fragment都需要以注解的方式绑定到此
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseEventBus {
}

