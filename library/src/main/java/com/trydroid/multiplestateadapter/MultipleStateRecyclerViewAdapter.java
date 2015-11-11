package com.trydroid.multiplestateadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.trydroid.multiplestateadapter.action.AdapterAction;
import com.trydroid.multiplestateadapter.action.ViewAction;
import com.trydroid.multiplestateadapter.helper.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleStateRecyclerViewAdapter<D, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements AdapterAction<D>, ViewAction {

    private Context mContext;
    private List<D> mItems;
    private int     mState;

    public MultipleStateRecyclerViewAdapter(Context context) {
        this.mContext = context;
        this.mItems = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
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
    public int getItemViewType(int position) {
        return mState;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case State.DONE:
                View inflatedView = Utils.inflateLayout(getViewResourceId(), parent);
                return onCreateViewHolder(inflatedView);

            case State.ERROR:
                return new StateViewHolder(getErrorView(parent));

            case State.EMPTY:
                return new StateViewHolder(getEmptyView(parent));

            case State.LOADING:
            default:
                return new StateViewHolder(getLoadingView(parent));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (State.DONE == getState()) {
            onBindViewHolder((VH) holder, getItem(position), position);
        }
    }

    /***
     * Abstract
     */
    protected abstract int getViewResourceId();

    protected abstract VH onCreateViewHolder(View inflatedView);

    protected abstract void onBindViewHolder(VH holder, D item, int position);

    /***
     * Action
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void addAll(List<D> items) {
        mItems.clear();
        appendAll(items);
    }

    @Override
    public void appendAll(List<D> items) {
        mItems.addAll(items);
        notifyState();
    }

    @Override
    public void add(D item) {
        mItems.add(item);
        notifyStateInserted(getIndex(item));
    }

    @Override
    public void add(D item, int position) {
        mItems.add(item);
        notifyStateInserted(position);
    }

    @Override
    public boolean update(D data, int position) {
        Object oldData = mItems.set(position, data);
        if (oldData != null) {
            notifyStateUpdated(position);
        }
        return oldData != null;
    }

    @Override
    public boolean remove(int position) {
        boolean validIndex = Utils.isValidIndex(position, getItemSize());
        if (validIndex) {
            mItems.remove(position);
            notifyStateRemoved(position);
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
        notifyState();
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
    public View getEmptyView(ViewGroup parent) {
        return Utils.inflateLayout(R.layout.view_state_empty, parent);
    }

    @Override
    public View getErrorView(ViewGroup parent) {
        return Utils.inflateLayout(R.layout.view_state_error, parent);
    }

    @Override
    public View getLoadingView(ViewGroup parent) {
        return Utils.inflateLayout(R.layout.view_state_loading, parent);
    }

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
        if (getItemSize() > 0) {
            setState(State.DONE);
        }
        else {
            setState(State.EMPTY);
        }
    }

    private void setState(int state) {
        mState = state;
    }

    private int getState() {
        return mState;
    }

    private static class StateViewHolder extends RecyclerView.ViewHolder {
        public StateViewHolder(View parent) {
            super(parent);
        }
    }
}
