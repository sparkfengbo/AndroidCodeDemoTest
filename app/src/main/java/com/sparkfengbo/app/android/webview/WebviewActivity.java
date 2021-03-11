package com.sparkfengbo.app.android.webview;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.annotations.FBBindColor;
import com.sparkfengbo.app.android.annotations.FBBindContentView;
import com.sparkfengbo.app.android.annotations.FBBindString;
import com.sparkfengbo.app.android.annotations.FBBindView;
import com.sparkfengbo.app.android.annotations.FbAnnotInject;
import com.sparkfengbo.app.android.annotations.FbBindListener;
import com.sparkfengbo.app.android.base.TLog;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBFloatRange;

public class WebviewActivity extends Activity {
    private WebView webview;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webview = (WebView) findViewById(R.id.webview);
        tv = (TextView) findViewById(R.id.tv_info);

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true); // 启用JavaScript
        //允许弹出框
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webview.loadUrl("file:///android_asset/js.html");

        // 原生方式，注册交互的类
        webview.addJavascriptInterface(new JsToJava(), "stub");

        // Js的Prompt实际上就是一个确定弹出框，Android上一般用不上这个功能
        // 我们直接把弹出框这个功能拿来用做交互，当需要交互的时候，就把交互参数作为弹出框内容，然后在Android中拦截了就行了
        // 如果你对HTML不熟悉，就告诉你们的前段：就是js的prompt方法，然后把上面的html给他看看他肯定就知道怎么写了
        // 这种方式我个人不太喜欢，毕竟把JsPromot给占用了，心里不爽...
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                // 这里拿到参数就可以根据约定好的格式解析了
                tv.setText("prompt方式，参数：" + message);
                // 调用一下cancel或者confirm都行，就是模拟手动点击了确定或者取消按钮
//                result.cancel();
                return false;
            }
        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 超链接的方式
                // WebView中任何跳转都会走这个方法，我们在这里进行判断，如果是我们约定好的连接，就进行自己的操作，否则就放行
                tv.setText("url方式交互，url是：" + url);
                return true; // 拦截了，如果不拦截就是 view.loadUrl(url)
            }
        });
    }

    // 原生的方式
    private class JsToJava {
        // 高版本需要加这个注解才能生效
        @JavascriptInterface
        public void jsMethod(final String paramFromJS) {
            Log.e("fengbo", Thread.currentThread().getName());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText("传统方式js调用java，参数：" + paramFromJS);
                }
            });
        }
    }
}
