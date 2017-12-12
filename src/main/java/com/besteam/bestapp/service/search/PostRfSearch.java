package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;
import com.besteam.bestapp.form.SearchDeliveryForm;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.Arrays;

public class PostRfSearch implements DeliverySearch {
    @Override
    public SearchDeliveryResult doRequest(SearchDeliveryForm form) {
        try {
            return new PostRequest(new Location(form.getFromCountry(),"",form.getFromCity(),""), new Location(form.getToCountry(),"",form.getToCity(),""),Integer.parseInt(form.getWeight())).doRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static class PostRequest {
    private static final String POST_RF_URL = "https://www.pochta.ru/portal-portlet/delegate/calculator/v1/api/delivery.time.cost.get";

        private Location from;
        private Location to;
        private int weight;
        private String zipFrom;
        private String zipTo;

        public PostRequest(Location from, Location to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
            zipFrom = new LocationInfoHelpersRequests().getPostalCodeByCityName(from.getCity());
            zipTo = new LocationInfoHelpersRequests().getPostalCodeByCityName(to.getCity());
        }

        public String toJson() {

            JSONObject jsonObject = new JSONObject()
                    .put("calculationEntity", new JSONObject()
                            .put("origin", new JsonLocationObject(from))
                            .put("destination", new JsonLocationObject(to))
                            .put("sendingType", "PACKAGE"))
                    .put("costCalculationEntity", new JSONObject()
                            .put("postingType", "VPO")
                            .put("zipCodeFrom", zipFrom)
                            .put("zipCodeTo", zipTo)
                            .put("weightRange", new JSONArray(Arrays.asList((int)(weight * 0.7), (int)(weight * 1.3))))
                            .put("wayForward", "EARTH")
                            .put("postingKind", "PARCEL")
                            .put("postingCategory", "ORDINARY")
                            .put("parcelKind", "STANDARD")
                    .put("productPageState", (Object)null));
            return jsonObject.toString();
        }

        public SearchDeliveryResult parseResponse(String json) {
            JSONObject response = new JSONObject(json).getJSONObject("data");
            SearchDeliveryResult r = new SearchDeliveryResult(Delivery.PostRf);

//            JSONObject timeEntity = response.getJSONObject("timeEntity");
//            r.setDeliveryTime(Duration.ofDays(timeEntity.getInt("minTime")));

            JSONObject costEntity = response.getJSONObject("costEntity");
            r.setCost(costEntity.getInt("cost"));

            r.setAviaAvailable(response.getBoolean("aviaAvailable"));

            return r;
        }

        public SearchDeliveryResult doRequest() throws Exception {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost postRequest = new HttpPost(POST_RF_URL);

            StringEntity input = new StringEntity(this.toJson());
            input.setContentType("application/json");
            postRequest.setEntity(input);
            CloseableHttpResponse response = httpClient.execute(postRequest);

            try {
                HttpEntity entity = response.getEntity();
                String postResponse = EntityUtils.toString(entity, Charset.forName("utf8"));
                EntityUtils.consume(entity);
                SearchDeliveryResult r = parseResponse(postResponse);
                return r;
            } catch (Exception e) {
                return null;
            } finally {
                response.close();
            }
        }
    }

    public static class Location {

        private String country;
        private String region;
        private String city;
        private String distinct;

        public Location() { }

        public Location(String country, String region, String city, String distinct) {
            this.country = country;
            this.region = region;
            this.city = city;
            this.distinct = distinct;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistinct() {
            return distinct;
        }

        public void setDistinct(String distinct) {
            this.distinct = distinct;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public static class JsonLocationObject extends JSONObject {
        public JsonLocationObject(Location l) {
            put("country", l.getCountry())
                    .put("city", l.getCity());
        }
    }
}
