package com.sparkfengbo.app.android.annotations;

import android.support.annotation.ColorInt;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by fengbo on 2017/12/18.
 *
 * 将颜色与View绑定
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FBBindColor {
    @ColorInt int value();
}
