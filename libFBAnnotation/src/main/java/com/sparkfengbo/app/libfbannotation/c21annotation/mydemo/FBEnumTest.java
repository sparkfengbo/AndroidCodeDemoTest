package com.sparkfengbo.app.libfbannotation.c21annotation.mydemo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest.GREEN;
import static com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest.RED;
import static com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest.YELLOW;

/**
 * Created by fengbo on 2017/12/24.
 */
@IntDef({RED, GREEN, YELLOW})
@Retention(RetentionPolicy.SOURCE)
public @interface FBEnumTest {
    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int YELLOW = 2;
}
