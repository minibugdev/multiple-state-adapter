package com.trydroid.multiplestateadapter.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Utils {

    public static boolean isValidIndex(int position, int count) {
        return position >= 0 && position < count;
    }

    public static View inflateLayout(int resId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }
}
