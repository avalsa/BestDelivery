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
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Arrays;

public class PostRfSearch implements DeliverySearch {
    @Override
    public SearchDeliveryResult doRequest(SearchDeliveryForm form) {
        try {
            return new PostRequest(new Location(form.getFromCountry(),"",form.getFromCity(),""), new Location(form.getToCountry(),"",form.getToCity(),""), (int) (Integer.parseInt(form.getWeight()) * 0.7), (int) (Integer.parseInt(form.getWeight()) * 1.3), Integer.parseInt(form.getIndexFrom()), Integer.parseInt(form.getIndexTo())).doRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public static class PostRequest {
    private static final String POST_RF_URL = "https://www.pochta.ru/portal-portlet/delegate/calculator/v1/api/delivery.time.cost.get";

        private Location from;
        private Location to;
        private int weightMin;
        private int weightMax;
        private final int zipFrom;
        private final int zipTo;

        public PostRequest(Location from, Location to, int weightMin, int weightMax, int zipFrom, int zipTo) {
            this.from = from;
            this.to = to;
            this.weightMin = weightMin;
            this.weightMax = weightMax;
            this.zipFrom = zipFrom;
            this.zipTo = zipTo;
        }

        public String toJson() {

            JSONObject jsonObject = new JSONObject()
                    .put("calculationEntity", new JSONObject()
                            .put("origin", new JsonLocationObject(from))
                            .put("destination", new JsonLocationObject(to))
                            .put("sendingType", "PACKAGE"))
                    .put("costCalculationEntity", new JSONObject()
                            .put("postingType", "VPO")
                            .put("zipCodeFrom", Integer.toString(zipFrom))
                            .put("zipCodeTo", Integer.toString(zipTo))
                            .put("weightRange", Arrays.asList(weightMin, weightMax))
                            .put("wayForward", "EARTH")
                            .put("postingKind", "PARCEL")
                            .put("postingCategory", "ORDINARY")
                            .put("parcelKind", "STANDARD"))
                    .put("productPageState", (Object) null);
            return jsonObject.toString();
        }

        public SearchDeliveryResult parseResponse(String json) {
            JSONObject response = new JSONObject(json).getJSONObject("data");
            SearchDeliveryResult r = new SearchDeliveryResult(Delivery.PostRf);

            JSONObject timeEntity = response.getJSONObject("timeEntity");
            r.setMinTime(Duration.ofDays(timeEntity.getInt("minTime")));
            r.setMaxTime(Duration.ofDays(timeEntity.getInt("maxTime")));

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
                    .put("region", l.getRegion())
                    .put("district", l.getDistinct())
                    .put("city", l.getCity());
        }
    }
}
