package com.trydroid.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class RecylcerViewDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        Button loadButton = (Button) findViewById(R.id.loading_btn);
        Button addButton = (Button) findViewById(R.id.add_btn);
        Button clearButton = (Button) findViewById(R.id.clear_btn);
        Button errorButton = (Button) findViewById(R.id.error_btn);

        loadButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        errorButton.setOnClickListener(this);

        setupRecylcerView();
    }

    private void setupRecylcerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        AutoSpanGridLayoutManager layoutManager = new AutoSpanGridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loading_btn:
                mAdapter.load();
                break;

            case R.id.add_btn:
                mAdapter.addAll(Utils.buildDataSet());
                break;

            case R.id.clear_btn:
                mAdapter.clear();
                break;

            case R.id.error_btn:
                mAdapter.error();
                break;
        }
    }

    public static class AutoSpanGridLayoutManager extends GridLayoutManager {

        private static final int DEFAULT_SPAN_SIZE = 1;
        private int mSpanCount;

        public AutoSpanGridLayoutManager(Context context, int spanCount) {
            super(context, DEFAULT_SPAN_SIZE);
            mSpanCount = spanCount;
        }

        public AutoSpanGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, DEFAULT_SPAN_SIZE, orientation, reverseLayout);
            mSpanCount = spanCount;
        }

        @Override
        public void onItemsChanged(RecyclerView recyclerView) {
            int span = getItemCount() == DEFAULT_SPAN_SIZE ? DEFAULT_SPAN_SIZE : mSpanCount;
            setSpanCount(span);
            super.onItemsChanged(recyclerView);
        }
    }
}
