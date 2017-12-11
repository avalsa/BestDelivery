package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;
import com.besteam.bestapp.form.SearchResultForm;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<SearchResultForm> toForm() {
        return getResults()
                .parallelStream()
                .map(r -> new SearchResultForm(r.getDelivery(), r.getCost(), r.getDeliveryTime()))
                .collect(Collectors.toList());
    }
}
