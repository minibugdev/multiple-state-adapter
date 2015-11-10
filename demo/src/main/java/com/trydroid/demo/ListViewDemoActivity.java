package com.trydroid.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ListViewDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Button loadButton = (Button) findViewById(R.id.loading_btn);
        Button addButton = (Button) findViewById(R.id.add_btn);
        Button clearButton = (Button) findViewById(R.id.clear_btn);
        Button errorButton = (Button) findViewById(R.id.error_btn);

        loadButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        errorButton.setOnClickListener(this);

        setupListView();
    }

    private void setupListView() {
        ListView listView = (ListView) findViewById(R.id.my_list_view);
        mAdapter = new ListViewAdapter(this);
        listView.setAdapter(mAdapter);
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
}
