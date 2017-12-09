package com.besteam.bestapp.service.search;

import com.besteam.bestapp.form.SearchDeliveryForm;

public interface DeliverySearch {
    SearchDeliveryResult doRequest(SearchDeliveryForm form);
}
