package com.deuteriun.system.security.entity;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RootPreAuthorize  {
    String value();
}
