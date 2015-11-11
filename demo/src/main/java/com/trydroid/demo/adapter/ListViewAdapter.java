package com.trydroid.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trydroid.demo.R;
import com.trydroid.multiplestateadapter.MultipleStateAdapter;
import com.trydroid.multiplestateadapter.helper.Utils;

public class ListViewAdapter extends MultipleStateAdapter<String, ListViewAdapter.ViewHolder> {

    public ListViewAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getViewResourceId() {
        return R.layout.my_text_view;
    }

    @Override
    protected ViewHolder onCreateViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, String item, int position) {
        holder.textView.setText(getItem(position));
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        return Utils.inflateLayout(R.layout.custom_empty, parent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.my_text_view);
        }
    }
}