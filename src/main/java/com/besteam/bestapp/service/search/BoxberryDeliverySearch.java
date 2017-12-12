package com.besteam.bestapp.service.search;

import com.besteam.bestapp.entity.Delivery;
import com.besteam.bestapp.form.SearchDeliveryForm;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BoxberryDeliverySearch implements DeliverySearch {

    @Override
    public SearchDeliveryResult doRequest(SearchDeliveryForm form) {
        return new BoxberryRequest().doRequest(form.getFromCity(), form.getToCity(), Integer.parseInt(form.getHeight()), Integer.parseInt(form.getWidth()), Integer.parseInt(form.getLength()));
    }

    public static class BoxberryRequest {
        private static final CloseableHttpClient httpClient = HttpClients.createDefault();
        private static Map<String, String> citiesFrom = null;
        private static Map<String, String> citiesTo = null;

        public SearchDeliveryResult doRequest(String form, String to, int sizeX, int sizeY, int sizeZ) {
            if (citiesFrom == null) {
                getCities();
            }
            String codeFrom = findCity(form, citiesFrom);
            String codeTo = findCity(to, citiesTo);
            if (codeFrom.isEmpty() || codeTo.isEmpty()) {
                return null;
            }

            HttpPost postRequest = new HttpPost("http://boxberry.ru/bitrix/components/bberry/calculator.express/component.php");
            StringEntity input = null;
            try {
                input = new StringEntity(buildRequest(codeFrom, codeTo, sizeX, sizeY, sizeZ));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            input.setContentType(ContentType.APPLICATION_FORM_URLENCODED.toString());
            postRequest.setEntity(input);
            try (CloseableHttpResponse response = httpClient.execute(postRequest)) {
                String res =  EntityUtils.toString(response.getEntity());
                return parseResponse(res);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private SearchDeliveryResult parseResponse(String res) {
            JSONObject response = new JSONArray(res).getJSONObject(0);
            SearchDeliveryResult r = new SearchDeliveryResult(Delivery.Boxberry);
            r.setCost(response.getInt("priceBase"));
            r.setDeliveryTime(Duration.ofDays(response.getInt("DeliveryPeriod")));
            return r;
        }

        private String buildRequest(String codeFrom, String codeTo, int sizeX, int sizeY, int sizeZ) {
            return "data={\"ArrayOfPlaces\":" +
                    "{\"Place\":[{\"AttachmentTypeID\":6,\"weight\":0,\"PackageID\":0,\"PackageType\":0," +
                    "\"height\":" + sizeX + ",\"width\":" + sizeY + ",\"depth\":" + sizeZ + ",\"packagePaid\":1}]},\"ArrayOfServices\":{\"ServiceID\":[]},\"typePerson\":1,\"promoCode\":\"\",\"direction\":1,\"sender_city_id\":\"" + codeFrom + "\",\"recipient_city_id\":\"" + codeTo + "\",\"publicPrice\":1000}&ajax=LapCalc";
        }

        private static void getCities() {
            HttpGet httpGet = new HttpGet("http://boxberry.ru/calculate_the_cost_of_sending/index_inc.php?calc_id=1");
            try (CloseableHttpResponse response = httpClient.execute(httpGet)){
                HttpEntity entity = response.getEntity();

                String result = EntityUtils.toString(entity, Charset.forName("utf-8"));
                parseGetCities(result);
            } catch (Exception e) {
                return;
            }
        }

        private static void parseGetCities(String xml) throws Exception{
            citiesFrom = new HashMap<>();
            citiesTo = new HashMap<>();
            String[] res = xml.split("Город отправителя|Город получателя");
            fillCity(res[1], citiesFrom);
            fillCity(res[2], citiesTo);
        }

        private static void fillCity(String re, Map<String, String> m) {
            for (String s : re.split("<option")) {
                try {
                    String ar[] = s.split("value=\"|\">|</option>");
                    String city = ar[2];
                    String code = ar[1];
                    if (!city.isEmpty() && !code.isEmpty()){
                        m.put(city, code);
                    }
                }
                catch (Exception e) {}
            }
        }

        private static String findCity(String city, Map<String, String> m){
            if (m.containsKey(city)) {
                return m.get(city);
            }
            for (Map.Entry<String, String> entry : m.entrySet()) {
                if (entry.getKey().contains(city)) {
                    return entry.getValue();
                }
            }
            return null;
        }

    }

}
