package com.trydroid.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.trydroid.multiplestateadapter.MultipleStateRecyclerViewAdapter;

public class RecyclerViewAdapter extends MultipleStateRecyclerViewAdapter<String, RecyclerViewAdapter.ViewHolder> {

    public RecyclerViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getViewResourceId() {
        return R.layout.my_text_view;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View inflatedView) {
        return new ViewHolder(inflatedView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder inflatedView, String item, int position) {
        inflatedView.mTextView.setText(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.my_text_view);
        }
    }
}