package com.trydroid.multiplestateadapter;

import android.view.View;
import android.view.ViewGroup;

public interface ViewAction {

    public void setEmptyView(View emptyView);

    public void setLoadingView(View loadingView);

    public void setErrorView(View errorView);

    public View getEmptyView(ViewGroup parent);

    public View getErrorView(ViewGroup parent);

    public View getLoadingView(ViewGroup parent);
}
