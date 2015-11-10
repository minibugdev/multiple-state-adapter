package com.trydroid.multiplestateadapter;

import android.content.Context;

import java.util.List;

public interface AdapterAction<D> {
    public Context getContext();

    public void addAll(List<D> items);

    public void appendAll(List<D> items);

    public void add(D item);

    public void add(D item, int position);

    public boolean update(D data, int position);

    public boolean remove(int position);

    public boolean remove(D data);

    public void clear();

    public void load();

    public void error();

    public int getIndex(D item);

    public int getItemSize();

    public D getItem(int position);

    public boolean isEmpty();
}
