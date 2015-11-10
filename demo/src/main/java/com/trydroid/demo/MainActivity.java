package com.trydroid.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.recylcerview);
        Button button2 = (Button) findViewById(R.id.listview);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recylcerview:
                startActivity(new Intent(this, RecylcerViewDemoActivity.class));
                break;

            case R.id.listview:
                startActivity(new Intent(this, ListViewDemoActivity.class));
                break;
        }
    }
}
