package com.besteam.bestapp.service.search;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.awt.geom.Point2D;
import java.io.IOException;

public class LocationInfoHelpersRequests {
    private static final String URL_POSTAL_CODE = "https://www.pochta.ru/portal-portlet/delegate/postoffice-api/method/offices.find.nearby.details?latitude=:lat&longitude=:lon";
    private static final String URL_COORDINATES = "https://geocode-maps.yandex.ru/1.x/?geocode=";

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public Point2D.Double getCoordinatesByCityName(String cityName) {
        try {
        HttpGet get = new HttpGet(URL_COORDINATES + cityName);
            CloseableHttpResponse response = httpClient.execute(get);
            return parseCoordinatesResponse(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Point2D.Double parseCoordinatesResponse(String response) {
        String[] coords = response.split("<pos>|</pos>")[1].split(" ");
        return new Point2D.Double(Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()));
    }

    public String getPostalCodeByCoordinates(Point2D.Double point) {
        try {
        HttpGet get = new HttpGet(URL_POSTAL_CODE
                .replace(":lon", Double.toString(point.getX()))
                .replace(":lat", Double.toString(point.getY())));
            CloseableHttpResponse response = httpClient.execute(get);
            return parsePostalCodeResponse(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parsePostalCodeResponse(String response) {
        return new JSONArray(response).getJSONObject(0).getString("postalCode");
    }

    public String getPostalCodeByCityName(String cityName) {
        return getPostalCodeByCoordinates(getCoordinatesByCityName(cityName));
    }
}
