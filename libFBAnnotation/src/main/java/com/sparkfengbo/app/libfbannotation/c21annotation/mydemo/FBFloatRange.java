package com.sparkfengbo.app.libfbannotation.c21annotation.mydemo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fengbo on 2017/12/19.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface FBFloatRange {
    double from();
    double to();
}
