package com.trydroid.multiplestateadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleStateAdapter<D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int STATE_LOADING = 0;
    private final static int STATE_DONE    = 1;
    private final static int STATE_ERROR   = 2;
    private final static int STATE_EMPTY   = 3;

    protected List<D> mItems;

    private int  mState;
    private View mEmptyView;
    private View mLoadingView;
    private View mErrorView;

    public MultipleStateAdapter(Context context) {
        this.mItems = new ArrayList<>();
    }

    public int getCount() {
        return mItems.size();
    }

    @Override
    public int getItemCount() {
        switch (mState) {
            case STATE_DONE:
                return getCount();

            case STATE_ERROR:
            case STATE_EMPTY:
            case STATE_LOADING:
            default:
                return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case STATE_DONE:
                View inflatedView = inflateLayout(getViewResourceId(), parent);
                return onCreateViewHolder(inflatedView);

            case STATE_ERROR:
                return new StateViewHolder(getErrorView(parent));

            case STATE_EMPTY:
                return new StateViewHolder(getEmptyView(parent));

            case STATE_LOADING:
            default:
                return new StateViewHolder(getLoadingView(parent));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (STATE_DONE == getState()) {
            onBindViewHolder((VH) holder, getItem(position), position);
        }
    }

    /***
     * Action
     */
    public void addAll(List<D> items) {
        mItems.clear();
        appendAll(items);
    }

    public void appendAll(List<D> items) {
        mItems.addAll(items);
        notifyState();
    }

    public void add(D item) {
        mItems.add(item);
        notifyStateInserted(getIndex(item));
    }

    public void add(D item, int position) {
        mItems.add(item);
        notifyStateInserted(position);
    }

    public boolean update(D data, int position) {
        Object oldData = mItems.set(position, data);
        if (oldData != null) {
            notifyStateUpdated(position);
        }
        return oldData != null;
    }

    public boolean remove(int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            mItems.remove(position);
            notifyStateRemoved(position);
        }
        return validIndex;
    }

    public boolean remove(D data) {
        if (mItems.contains(data)) {
            return remove(getIndex(data));
        }
        return false;
    }

    public void clear() {
        mItems.clear();
        notifyState();
    }

    public void load() {
        setState(STATE_LOADING);
        mItems.clear();
        notifyDataSetChanged();
    }

    public void error() {
        setState(STATE_ERROR);
        mItems.clear();
        notifyDataSetChanged();
    }

    public int getIndex(D item) {
        return mItems.indexOf(item);
    }

    public D getItem(int position) {
        return mItems.get(position);
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    public void setErrorView(View errorView) {
        this.mErrorView = errorView;
    }

    protected abstract int getViewResourceId();

    protected abstract VH onCreateViewHolder(View inflatedView);

    protected abstract void onBindViewHolder(VH holder, D item, int position);

    /***
     * Private
     */
    private void notifyStateRemoved(int position) {
        invalidateState();
        notifyItemRemoved(position);
    }

    private void notifyStateUpdated(int position) {
        invalidateState();
        notifyItemChanged(position);
    }

    private void notifyStateInserted(int position) {
        invalidateState();
        notifyItemInserted(position);
    }

    private void notifyState() {
        invalidateState();
        notifyDataSetChanged();
    }

    private void invalidateState() {
        if (getCount() > 0) {
            setState(STATE_DONE);
        }
        else {
            setState(STATE_EMPTY);
        }
    }

    private void setState(int state) {
        mState = state;
    }

    private int getState() {
        return mState;
    }

    private boolean isValidIndex(int position) {
        return position >= 0 && position < getCount();
    }

    private View getEmptyView(ViewGroup parent) {
        if (mEmptyView == null) {
            return inflateLayout(R.layout.view_state_empty, parent);
        }
        else {
            return mEmptyView;
        }
    }

    private View getErrorView(ViewGroup parent) {
        if (mErrorView == null) {
            return inflateLayout(R.layout.view_state_error, parent);
        }
        else {
            return mErrorView;
        }
    }

    private View getLoadingView(ViewGroup parent) {
        if (mLoadingView == null) {
            return inflateLayout(R.layout.view_state_loading, parent);
        }
        else {
            return mLoadingView;
        }
    }


    private View inflateLayout(int resId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    /***
     * Static
     */
    public static class StateViewHolder extends RecyclerView.ViewHolder {
        public StateViewHolder(View parent) {
            super(parent);
        }
    }
}
