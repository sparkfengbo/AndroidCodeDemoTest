package com.sparkfengbo.app.android;

import com.sparkfengbo.app.R;
import com.sparkfengbo.app.firstpage.TLog;
import com.sparkfengbo.app.android.annotations.FbAnnotInject;
import com.sparkfengbo.app.android.annotations.FBBindColor;
import com.sparkfengbo.app.android.annotations.FBBindContentView;
import com.sparkfengbo.app.android.annotations.FBBindString;
import com.sparkfengbo.app.android.annotations.FBBindView;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBEnumTest;
import com.sparkfengbo.app.libfbannotation.c21annotation.mydemo.FBFloatRange;
import com.sparkfengbo.app.android.annotations.FbBindListener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

@FBBindContentView(value = R.layout.activity_main)
public class AnotationActivity extends Activity {
    @FBBindView(R.id.tv_1)
    @FBBindColor(Color.GREEN)
    @FBBindString(R.string.test_1)
    private TextView mTextView1;

    /**
     * 注意，这里FBBindView，FBBindColor这两个注解都是添加了@Documented元注解，但FBBindString没有添加
     * 所以AS在mTextView2按F1查看注释的时候是看不到FBBindString信息的，但是能看到FBBindView，FBBindColor的信息
     */
    @FBBindView(R.id.tv_2)
    @FBBindColor(Color.BLACK)
    @FBBindString(R.string.test_2)
    private TextView mTextView2;

    @FBBindView(R.id.tv_3)
    @FBBindColor(Color.BLUE)
    @FBBindString(R.string.test_3)
    private TextView mTextView3;

    @FBFloatRange(from = 0, to = 10)
    private Float mFloatTest = 100f;

    @FBEnumTest
    private int color = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TLog.d("onCreate");
        FbAnnotInject.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences editor = this.getSharedPreferences("setting", MODE_PRIVATE);
        String testStr = editor.getString("fengbo", "null");
        TLog.e(testStr);
    }

    @FbBindListener(values = {R.id.tv_1, R.id.tv_2, R.id.tv_3})
    private void onClick(View view) {
        TLog.d("onClick view.id : " + view.getId());
        if (view.getId() == R.id.tv_1) {
            Toast.makeText(this, "tv_1_click", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.tv_2) {
            Toast.makeText(this, "tv_2_click", Toast.LENGTH_SHORT).show();

        } else if (view.getId() == R.id.tv_3) {
            Toast.makeText(this, "tv_3_click", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
