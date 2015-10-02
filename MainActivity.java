package com.example.tyler.hw2;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

//import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private ListView lvCourses;
    private CourseAdapter courseAdapter;
    private CourseClient client;
    private ProgressBar progress;
    public static final String COURSE_DETAIL_KEY = "course";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        lvCourses = (ListView) findViewById(R.id.lvCourses);
        ArrayList<Course> aCourses = new ArrayList<Course>();
        courseAdapter = new CourseAdapter(this, aCourses);
        lvCourses.setAdapter(courseAdapter);
        progress = (ProgressBar) findViewById(R.id.progress);
        setupCourseSelectedListener();
        //fetchCourses();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchCourses(String query) {
        progress.setVisibility(ProgressBar.VISIBLE);
        client = new CourseClient();
        client.getCourses(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    progress.setVisibility(ProgressBar.GONE);
                    JSONArray data;
                    if (response != null) {
                        // Get the docs json array
                        data = response.getJSONArray("courses");
                        // Parse json array into array of model objects
                        final ArrayList<Course> courses = Course.fromJson(data);
                        // Remove all books from the adapter
                        courseAdapter.clear();
                        // Load model objects into the adapter
                        for (Course course : courses) {
                            courseAdapter.add(course); // add book through the adapter
                        }
                        courseAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                fetchCourses(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                MainActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    public void setupCourseSelectedListener() {
        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
                intent.putExtra(COURSE_DETAIL_KEY, courseAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
