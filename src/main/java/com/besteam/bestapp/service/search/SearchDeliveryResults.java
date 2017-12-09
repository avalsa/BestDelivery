package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchDeliveryResults {
    private List<SearchDeliveryResult> results = new ArrayList<>();

    public void addResult(@NotNull SearchDeliveryResult r) {
        results.add(r);
    }

    public List<SearchDeliveryResult> getResults() {
        return Collections.unmodifiableList(results);
    }

    public SearchDeliveryResult getSearchResultById(int id) {
        return results.get(id);
    }

    public SearchDeliveryResult getSearchReslutByDeliveryName(Delivery delivery) {
        for (SearchDeliveryResult r : results) {
            if (r.getDelivery() == delivery) {
                return r;
            }
        }
        return null;
    }
}
