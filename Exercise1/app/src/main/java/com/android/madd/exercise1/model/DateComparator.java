package com.android.madd.exercise1.model;

import java.util.Comparator;

public class DateComparator implements Comparator<UrlHistoryItem> {

    @Override
    public int compare(UrlHistoryItem left, UrlHistoryItem right) {
        if (left.isAfter(right)) {
            return 1;
        } else if (right.isAfter(left)) {
            return -1;
        }
        return 0;
    }
}
