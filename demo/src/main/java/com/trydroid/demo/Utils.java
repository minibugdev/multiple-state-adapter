package com.trydroid.demo;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> buildDataSet() {
        int size = 25;
        List<String> dataset = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            dataset.add("Item #" + (i + 1));
        }

        return dataset;
    }
}
