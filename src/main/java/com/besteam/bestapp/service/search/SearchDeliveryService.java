package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;
import com.besteam.bestapp.form.SearchDeliveryForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class SearchDeliveryService {
    private static final int LOADERS_THREAD_CNT = 20;
    private static final int WAIT_TIME = 10; //sec
    private static final List<DeliverySearch> searchEngines = new ArrayList<>() ;
    private static final ExecutorService executor = Executors.newFixedThreadPool(LOADERS_THREAD_CNT);


    static {
        searchEngines.add(new ImlDeliverySearch());
        searchEngines.add(new PostRfSearch());
        searchEngines.add(new BoxberryDeliverySearch());
    }

    public SearchDeliveryResults doRequest(SearchDeliveryForm form) {
        SearchDeliveryResults res = new SearchDeliveryResults();
        List<TaskSearch> tasks = new ArrayList<>();
        for (DeliverySearch searchEngine : searchEngines) {
            tasks.add(new TaskSearch(searchEngine, form));
        }
        try {
            List<Future<SearchDeliveryResult>> futures = executor.invokeAll(tasks, WAIT_TIME, TimeUnit.SECONDS);
            SearchDeliveryResults results = new SearchDeliveryResults();
            futures
                    .parallelStream()
                    .filter(Future::isDone)
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (Exception e) {
                            return null; //shall not be
                        }
                    })
                    .filter(Objects::nonNull)
                    .forEach(results::addResult);
            return results;
        } catch (Exception e) {
            e.printStackTrace();    //problem to fix if fall here
            return null;
        }
    }


    private static class TaskSearch implements Callable<SearchDeliveryResult> {
        private DeliverySearch deliverySearch;
        private SearchDeliveryForm form;

        public TaskSearch(DeliverySearch deliverySearch, SearchDeliveryForm form) {
            this.deliverySearch = deliverySearch;
            this.form = form;
        }

        @Override
        public SearchDeliveryResult call() throws Exception {
            return deliverySearch.doRequest(form);
        }
    }
}
