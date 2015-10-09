package com.example.tyler.hw2;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * The CourseClient class prepares query strings needed to query the SVSU API.
 */
public class CourseClient {

    /** Base URL for SVSU API */
    public static final String API_BASE_URL = "http://api.svsu.edu/courses?";
    /** Asynchronous client object */
    private AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Default constructor.
     */
    public CourseClient() {}

    /**
     * Returns JSON query.
     *
     * @param relativeUrl
     * @return relativeUrl
     */
    private String getApiUrl(String relativeUrl) {
        // Append query to base URL
        return API_BASE_URL + relativeUrl;
    }

    /**
     * Method for accessing the search API.
     * @param query
     * @param handler
     */
    public void getCourses(final String query, JsonHttpResponseHandler handler) {
        try {
            // Query course number
            String url = getApiUrl("courseNumber=");
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
