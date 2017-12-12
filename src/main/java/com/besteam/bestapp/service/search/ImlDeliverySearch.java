package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;
import com.besteam.bestapp.form.SearchDeliveryForm;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.StringJoiner;

public class ImlDeliverySearch implements DeliverySearch {
    @Override
    public SearchDeliveryResult doRequest(SearchDeliveryForm form) {
        Integer weight = Integer.parseInt(form.getWeight());
        weight = Double.valueOf(Math.ceil(weight * 1. / 1000)).intValue();
        return new ImlRequest().doRequest(form.getFromCity().toUpperCase(), form.getToCity().toUpperCase(), weight);
    }

    public class ImlRequest {
        private static final String IML_URL = "https://iml.ru/calc";


        public String buildRequest(String fromCity, String toCity, int weight) {
            try {
                return new StringJoiner("&")
                        .add("from_type=list")
                        .add("to_type=list")
                        .add("use_login=0")
                        .add("login=")
                        .add("password=")
                        .add("delivery=1")
                        .add("payment=2")
                        .add("fromPlace=" + URLEncoder.encode(fromCity, "utf8"))
                        .add("toPlace=" + URLEncoder.encode(toCity, "utf8"))
                        .add("pickpoint=1")
                        .add("weight=" + weight)
                        .add("places=1")
                        .add("DeclaredValue=0")
                        .add("ReceiptAmount=0").toString();
            } catch (UnsupportedEncodingException e) {
                assert false;
                return null;
            }
        }

        public SearchDeliveryResult doRequest(String formCity, String toCity, int weight) {
            try {
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost postRequest = new HttpPost(IML_URL);
                StringEntity input = null;

                input = new StringEntity(buildRequest(formCity.toUpperCase(), toCity.toUpperCase(), weight));

                input.setContentType(ContentType.APPLICATION_FORM_URLENCODED.toString());
                postRequest.setEntity(input);
                postRequest.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
                CloseableHttpResponse response = null;

                response = httpClient.execute(postRequest);
                HttpEntity entity = response.getEntity();
                String postResponse = EntityUtils.toString(entity, Charset.forName("utf-8"));
                EntityUtils.consume(entity);
                response.close();
                SearchDeliveryResult r = parseResponse(postResponse);
                return r;
            } catch (Exception e) {
                return null;
            }
        }

        private SearchDeliveryResult parseResponse(String json) {
            JSONObject response = new JSONObject(json);
            SearchDeliveryResult r = new SearchDeliveryResult(Delivery.Iml);
            r.setCost(response.getInt("sum"));
            return r;
        }


    }
}
