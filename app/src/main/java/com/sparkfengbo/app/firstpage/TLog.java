package com.sparkfengbo.app.firstpage;

import java.util.ArrayList;

import android.text.TextUtils;
import android.util.Log;


public class TLog {
    static private final String LOG_TAG = "fengbo";

    public static boolean isDebugMode() {
        return true;
    }


    static private void i(String className, String method, String msg) {
        String log = createMsg(true, className, method, msg);
        if (log != null) {
            Log.i(LOG_TAG, log);
        }
    }


    static private void e(String className, String method, String msg) {
        String log = createMsg(false, className, method, msg);
        if (log != null) {
            Log.e(LOG_TAG, log);
        }
    }

    static private void w(String className, String method, String msg) {
        String log = createMsg(false, className, method, msg);
        if (log != null) {
            Log.w(LOG_TAG, log);
        }
    }

    static private void v(String className, String method, String msg) {
        String log = createMsg(true, className, method, msg);
        if (log != null) {
            Log.v(LOG_TAG, log);
        }
    }

    static private void d(String className, String method, String msg) {
        String log = createMsg(true, className, method, msg);
        if (log != null) {
            Log.d(LOG_TAG, log);
        }
    }

    static private String createMsg(boolean underPageackContrl, String className, String method, String msg) {
        if (isDebugMode()) {
            StringBuffer fullMsg = new StringBuffer(100);
            fullMsg.append(className);
            fullMsg.append(":");
            fullMsg.append(method);
            fullMsg.append(":");
            fullMsg.append(msg);
            return fullMsg.toString();
        } else {
            return null;
        }
    }

    static public int printLog(int type, String msg) {
        if (isDebugMode()) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            if (elements.length < 5) {
                return -1;
            }
            StackTraceElement element = elements[4];
            String methodName = element.getMethodName();
            String className = element.getClassName();
            if (type == 0) {
                e(className, methodName, msg);
            } else if (type == 1) {
                w(className, methodName, msg);
            } else if (type == 2) {
                i(className, methodName, msg);
            } else if (type == 3) {
                d(className, methodName, msg);
            } else {
                v(className, methodName, msg);
            }
            return 0;
        } else {
            return -1;
        }
    }

    static public int detailException(String msg, Throwable t) {
        if (isDebugMode() && t != null) {
            Log.e(LOG_TAG, msg, t);
        }

        return printLog(0, msg);
    }

    static public int detailException(Throwable t) {
        if (isDebugMode() && t != null) {
            Log.e(LOG_TAG, t.getMessage(), t);
            return printLog(0, t.getMessage());
        }

        return -1;
    }

    static public int e(Throwable e) {
        return printLog(0, e.getMessage());
    }

    static public int e(String msg) {
        return printLog(0, msg);
    }

    static public int w(String msg) {
        return printLog(1, msg);
    }

    static public int i(String msg) {
        return printLog(2, msg);
    }

    static public int d(String msg) {
        return printLog(3, msg);
    }

    static public int v(String msg) {
        return printLog(4, msg);
    }
}
