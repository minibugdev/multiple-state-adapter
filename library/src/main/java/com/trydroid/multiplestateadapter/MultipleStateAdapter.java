package com.trydroid.multiplestateadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleStateAdapter<D> extends BaseAdapter implements AdapterAction<D>, ViewAction {

    private List<D> mItems;
    private int     mState;
    private Context mContext;

    private View mEmptyView;
    private View mLoadingView;
    private View mErrorView;

    public MultipleStateAdapter(Context context) {
        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        switch (mState) {
            case State.DONE:
                return getItemSize();

            case State.ERROR:
            case State.EMPTY:
            case State.LOADING:
            default:
                return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        return mState;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case State.DONE:
                return getItemView(position, convertView, parent);

            case State.ERROR:
                return getErrorView(parent);

            case State.EMPTY:
                return getEmptyView(parent);

            case State.LOADING:
            default:
                return getLoadingView(parent);
        }
    }

    /***
     * Abstract
     */
    protected abstract View getItemView(int position, View convertView, ViewGroup parent);

    /***
     * Action
     */
    @Override
    public void addAll(List<D> items) {
        mItems.clear();
        appendAll(items);
    }

    @Override
    public void appendAll(List<D> items) {
        mItems.addAll(items);
        invalidateState();
    }

    @Override
    public void add(D item) {
        mItems.add(item);
        invalidateState();
    }

    @Override
    public void add(D item, int position) {
        mItems.add(item);
        invalidateState();
    }

    @Override
    public boolean update(D data, int position) {
        Object oldData = mItems.set(position, data);
        if (oldData != null) {
            invalidateState();
        }
        return oldData != null;
    }

    @Override
    public boolean remove(int position) {
        boolean validIndex = Utils.isValidIndex(position, getItemSize());
        if (validIndex) {
            mItems.remove(position);
            invalidateState();
        }
        return validIndex;
    }

    @Override
    public boolean remove(D data) {
        if (mItems.contains(data)) {
            return remove(getIndex(data));
        }
        return false;
    }

    @Override
    public void clear() {
        mItems.clear();
        invalidateState();
    }

    @Override
    public void load() {
        setState(State.LOADING);
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public void error() {
        setState(State.ERROR);
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getIndex(D item) {
        return mItems.indexOf(item);
    }

    @Override
    public D getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public boolean isEmpty() {
        return getItemSize() == 0;
    }

    @Override
    public int getItemSize() {
        return mItems.size();
    }

    /***
     * View
     */
    @Override
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    @Override
    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    @Override
    public void setErrorView(View errorView) {
        this.mErrorView = errorView;
    }

    @Override
    public View getEmptyView(ViewGroup parent) {
        if (mEmptyView == null) {
            return Utils.inflateLayout(R.layout.view_state_empty, parent);
        }
        else {
            return mEmptyView;
        }
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        if (mErrorView == null) {
            return Utils.inflateLayout(R.layout.view_state_error, parent);
        }
        else {
            return mErrorView;
        }
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        if (mLoadingView == null) {
            return Utils.inflateLayout(R.layout.view_state_loading, parent);
        }
        else {
            return mLoadingView;
        }
    }

    /***
     * Private
     */
    private void invalidateState() {
        if (getItemSize() > 0) {
            setState(State.DONE);
        }
        else {
            setState(State.EMPTY);
        }
        notifyDataSetChanged();
    }

    private void setState(int state) {
        mState = state;
    }

    private int getState() {
        return mState;
    }
}
