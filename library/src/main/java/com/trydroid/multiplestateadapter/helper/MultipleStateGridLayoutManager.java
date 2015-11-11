package com.trydroid.multiplestateadapter.helper;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MultipleStateGridLayoutManager extends GridLayoutManager {

    private static final int DEFAULT_SPAN_SIZE = 1;
    private int mSpanCount;

    public MultipleStateGridLayoutManager(Context context, int spanCount) {
        super(context, DEFAULT_SPAN_SIZE);
        mSpanCount = spanCount;
    }

    public MultipleStateGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
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