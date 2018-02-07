package com.sparkfengbo.app.android.annotations;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by fengbo on 2017/12/19.
 */

public class FbInvocationHandler implements InvocationHandler {

    private Object recever;

    private Method method;

    public FbInvocationHandler(Object recever, Method m ) {
        this.recever = recever;
        this.method = m;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return this.method.invoke(recever, args);
    }
}
