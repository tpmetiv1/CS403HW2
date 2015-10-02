package com.example.tyler.hw2;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tyler on 9/30/15.
 */
public class CourseClient {

    public static final String API_BASE_URL = "http://api.svsu.edu/courses?";
    private AsyncHttpClient client;

    /**
     *
     */
    public CourseClient() {
        this.client = new AsyncHttpClient();
    }

    /**
     *
     * @param relativeUrl
     * @return
     */
    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    /**
     * Method for accessing the search API
     * @param query
     * @param handler
     */
    public void getCourses(final String query, JsonHttpResponseHandler handler) {
        try {
            String url = getApiUrl("courseNumber=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
