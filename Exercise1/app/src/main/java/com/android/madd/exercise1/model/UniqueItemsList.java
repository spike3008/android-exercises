package com.android.madd.exercise1.model;

import java.util.ArrayList;
import java.util.Collections;

public class UniqueItemsList extends ArrayList<UrlHistoryItem> {

    public void replaceOlderRequests(UrlHistoryItem urlHistoryItem) {
        boolean sameFound = false;
        for (int i = 0; i < this.size(); i++) {
            UrlHistoryItem item = this.get(i);
            if (item.hasEqualUrl(urlHistoryItem)) {
                sameFound = true;
                this.remove(i);
                this.add(i, urlHistoryItem);
                break;
            }
        }
        if (!sameFound) {
            this.add(urlHistoryItem);
        }
        Collections.sort(this, Collections.reverseOrder(new DateComparator()));
    }


}
