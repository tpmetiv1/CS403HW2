package com.example.tyler.hw2;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

/**
 * The MainActivity class handles all of this application's central functionality. JSON queries are
 * made from the SVSU API and fetched data is used to populate a ListView with relevant course
 * options.
 */
public class MainActivity extends AppCompatActivity {

    /** Course ListView */
    private ListView lvCourses;
    /** Course Adapter */
    private CourseAdapter courseAdapter;
    /** Course Client object */
    private CourseClient client;
    /** Progress bar */
    private ProgressBar progress;
    /** Course key */
    public static final String COURSE_DETAIL_KEY = "course";

    /**
     * The onCreate function renders the main state that is seen upon application boot.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Call parent function and set content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        // Assign ListView from xml
        lvCourses = (ListView) findViewById(R.id.lvCourses);

        // Create ArrayList to hold fetched course data
        ArrayList<Course> aCourses = new ArrayList<>();

        // Assign course adapter
        courseAdapter = new CourseAdapter(this, aCourses);
        lvCourses.setAdapter(courseAdapter);

        // Assign progress bar
        progress = (ProgressBar) findViewById(R.id.progress);

        // Call listener
        setupCourseSelectedListener();
    }

    /**
     * The onOptionsItemSelected method handles click events on the action bar.
     *
     * @param item
     * @return
     */
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

    /**
     * The fetchCourses method fetches course data from the SVSU API.
     *
     * @param query
     */
    private void fetchCourses(String query) {
        // Show progress bar
        progress.setVisibility(ProgressBar.VISIBLE);

        // Create CourseClient instance
        client = new CourseClient();

        // Fetch course data from SVSU API
        client.getCourses(query, new JsonHttpResponseHandler() {
            /*
            The onSuccess method is overridden from the BaseHttpResponseHandler abstract class.
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    // Hide progress bar
                    progress.setVisibility(ProgressBar.GONE);

                    // Create reference pointer to JSONArray
                    JSONArray data;

                    if (response != null) {
                        // Get the docs json array
                        data = response.getJSONArray("courses");
                        // Parse json array into array of model objects
                        final ArrayList<Course> courses = Course.fromJson(data);
                        // Remove all courses from the adapter
                        courseAdapter.clear();
                        // Load model objects into the adapter
                        for (Course course : courses) {
                            courseAdapter.add(course); // add course through the adapter
                        }
                        courseAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }

            /*
            The onFailure method is overridden from the BaseHttpResponseHandler abstract class.
             */
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
            }
        });
    }

    /**
     * This method is responsible for creating the options menu visible to the user.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /*
            Alters appearance of text in the search menu bar.
             */
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

    /**
     * This function sets the event handler for item clicks in the course ListView.
     */
    public void setupCourseSelectedListener() {
        lvCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
            Set click event functionality.
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing course as an extra
                Intent intent = new Intent(MainActivity.this, CourseDetailActivity.class);
                intent.putExtra(COURSE_DETAIL_KEY, courseAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
