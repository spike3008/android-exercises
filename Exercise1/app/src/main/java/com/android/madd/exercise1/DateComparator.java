package com.android.madd.exercise1;

import java.util.Comparator;

/**
 * Created by madd on 2015-02-09.
 */
public class DateComparator implements Comparator<Site> {

    @Override
    public int compare(Site left, Site right) {
        if (left.isAfter(right)) {
            return 1;
        } else if (right.isAfter(left)) {
            return -1;
        }
        return 0;
    }
}
