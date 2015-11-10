package com.trydroid.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loading_btn:
                mAdapter.load();
                break;

            case R.id.add_btn:
                mAdapter.addAll(buildDataSet());
                break;

            case R.id.clear_btn:
                mAdapter.clear();
                break;

            case R.id.error_btn:
                mAdapter.error();
                break;
        }
    }

    private void setupRecylcerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this);
        recyclerView.setAdapter(mAdapter);
    }

    private List<String> buildDataSet() {
        int size = 25;
        List<String> dataset = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataset.add("Item #" + (i + 1));
        }

        return dataset;
    }
}
