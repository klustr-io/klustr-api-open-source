package io.klustr.json;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DynamicJson {
    /**
     * discriminator property name, e.g. "type"
     */
    String property() default "type";
}