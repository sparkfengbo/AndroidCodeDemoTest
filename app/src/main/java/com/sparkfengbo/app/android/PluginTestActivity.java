package com.sparkfengbo.app.android;

import com.sparkfengbo.app.firstpage.StartActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PluginTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        TextView tv = new TextView(this);
        tv.setText("hhh");
        tv.setTextSize(24);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.gravity = Gravity.CENTER;
        ll.addView(tv, llp);
        setContentView(ll);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PluginTestActivity.this, ExportedActivity.class));
            }
        });
    }

}
