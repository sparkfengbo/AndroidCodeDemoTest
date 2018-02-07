package com.sparkfengbo.app.firstpage;

import com.sparkfengbo.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class StartActivity extends Activity {

    private RecyclerView mRecyclerView;
    private StartItemAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mAdapter = new StartItemAdapter(this);

        final GridLayoutManager manager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (mAdapter != null && mAdapter.getItemViewType(position) == BaseItemData.TYPE_TITLE) {
                    TLog.e("p = " + position + "  1");
                    return 1;
                }

                TLog.e("p = " + position + "  manager.getSpanCount()");
                return manager.getSpanCount();
            }
        });
//        mRecyclerView.setHasFixedSize(true);
    }
}
