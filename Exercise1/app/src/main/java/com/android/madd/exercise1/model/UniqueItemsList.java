package com.android.madd.exercise1.model;

import java.util.ArrayList;
import java.util.Collections;

public class UniqueItemsList extends ArrayList<UrlHistoryItem> {

    public void replaceOlderRequests(UrlHistoryItem urlHistoryItem) {
        for (int i = 0; i < this.size(); i++) {
            UrlHistoryItem item = get(i);
            if (item.hasEqualUrl(urlHistoryItem)) {
                remove(i);
                break;
            }
        }
        add(urlHistoryItem);
        Collections.sort(this, Collections.reverseOrder(new DateComparator()));
    }


}
