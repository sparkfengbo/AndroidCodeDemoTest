package com.sparkfengbo.app.android.annotations;


import com.sparkfengbo.app.R;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

/**
 * Created by fengbo on 2017/12/18.
 */

public class FbAnnotInject {
    private static final String TAG = "FbAnnotInject";
    private static Class<?> cls;

    public static void inject(Object obj) {
        if (obj instanceof Activity == false) {
            Log.d(TAG, "Object is not an activity");
            return;
        }
        cls = obj.getClass();
        Log.d(TAG, "Get class : " + cls.getCanonicalName());
        parseBindContentView(obj);
        parseBindView(obj);
        parseBindStringResource(obj);
        parseBindColorResource(obj);
        parseBindOnClick(obj);
    }

    private static void parseBindContentView(Object obj) {
        Log.d(TAG, "parseBindContentView-------------->");
        FBBindContentView bindContentView = cls.getAnnotation(FBBindContentView.class);
        if (bindContentView != null) {
            int layoutRes = bindContentView.value();
            Log.d(TAG, "FBBindContentView annotation value is : " + layoutRes + " and R.layout.activity_main is : " +
                    R.layout.activity_main);
            try {
                //TODO fengbo 确认这里getMethod
                Method method = cls.getMethod("setContentView", int.class);
                method.invoke(obj, layoutRes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "No FBBindContentView Annotation");
        }
    }

    private static void parseBindView(Object obj) {
        Log.d(TAG, "parseBindView---------------------->");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Log.d(TAG, "Field name : " + field.getName());
            FBBindView bindView = field.getAnnotation(FBBindView.class);
            if (bindView == null) {
                Log.d(TAG, field.getName() + " has no FBBindView Annotation");
                continue;
            }

            int viewId = bindView.value();
            try {
                Method method = cls.getMethod("findViewById", int.class);
                Object tmpObj = method.invoke(obj, viewId);
                if (field.getModifiers() != Modifier.PUBLIC) {
                    field.setAccessible(true);
                }
                field.set(obj, tmpObj);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param obj ps: 仅做测试使用，否则重复调用getDeclaredFields，效率很低
     */
    private static void parseBindStringResource(Object obj) {
        Log.d(TAG, "parseBindStringResource-------------------->");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            FBBindString bindString = field.getAnnotation(FBBindString.class);
            if (bindString == null) {
                continue;
            }

            int strValue = bindString.value();
            try {
                Method method = field.getType().getMethod("setText", int.class);
                if (field.getModifiers() != Modifier.PUBLIC) {
                    field.setAccessible(true);
                }
                method.invoke(field.get(obj), strValue);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseBindColorResource(Object obj) {
        Log.d(TAG, "parseBindColorResource-------------------->");
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            FBBindColor bindColor = field.getAnnotation(FBBindColor.class);
            if (bindColor == null) {
                continue;
            }

            int colorValue = bindColor.value();
            try {
                Method method = field.getType().getMethod("setTextColor", int.class);
                if (field.getModifiers() != Modifier.PUBLIC) {
                    field.setAccessible(true);
                }
                method.invoke(field.get(obj), colorValue);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseBindOnClick(Object obj) {
        Log.d(TAG, "parseBindOnClick-------------------->");

        //使用getDeclaredMethods 减少判断量
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            Log.d(TAG, "Method name : " + method.getName());
            FbBindListener bindListener = method.getAnnotation(FbBindListener.class);
            if (bindListener != null) {
                int[] values = bindListener.values();
                if(method.getModifiers() != Modifier.PUBLIC) {
                    method.setAccessible(true);
                }
                FbInvocationHandler handler = new FbInvocationHandler(obj, method);

                Object proxy = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(), new
                        Class<?>[]{View.OnClickListener.class}, handler);
                for(Integer id : values) {
                    try {
                        Object view  = cls.getMethod("findViewById", int.class).invoke(obj, id);
                        if(view != null) {
                            view.getClass().getMethod("setOnClickListener", View.OnClickListener.class).invoke(view, proxy);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
