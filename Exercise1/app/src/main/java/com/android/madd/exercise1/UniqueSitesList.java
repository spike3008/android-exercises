package com.android.madd.exercise1;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by madd on 2015-02-06.
 */
public class UniqueSitesList extends ArrayList<Site> {

    public void replaceOlderRequests(Site site) {
        boolean sameFound = false;
        for (int i = 0; i < this.size(); i++) {
            Site item = this.get(i);
            if (item.hasEqualUrl(site)) {
                sameFound = true;
                this.remove(i);
                this.add(i, site);
                break;
            }
        }
        if (!sameFound) {
            this.add(site);
        }
        Collections.sort(this, new DateComparator());
        Collections.reverse(this);
    }


}
