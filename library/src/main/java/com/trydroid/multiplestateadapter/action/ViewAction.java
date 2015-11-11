package com.trydroid.multiplestateadapter.action;

import android.view.View;
import android.view.ViewGroup;

public interface ViewAction {

    public View getEmptyView(ViewGroup parent);

    public View getErrorView(ViewGroup parent);

    public View getLoadingView(ViewGroup parent);
}
