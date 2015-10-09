package com.example.tyler.hw2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * The CourseDetailActivity class handles all functionality relevant to the detail display view
 * of this application.
 */
public class CourseDetailActivity extends AppCompatActivity {

    /** Title TextView */
    private TextView tvTitle;
    /** Description TextView */
    private TextView tvDescription;
    /** Term TextView */
    private TextView tvTerm;
    /** Instructor Name TextView */
    private TextView tvInstructor;
    /** Course Abbreviation TextView */
    private TextView tvAbbrev;

    /**
     * Overridden onCreate method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Call parent method and set content view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // Fetch views
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvTerm = (TextView) findViewById(R.id.tvTerm);
        tvInstructor = (TextView) findViewById(R.id.tvInstructor);
        tvAbbrev = (TextView) findViewById(R.id.tvAbbrev);

        // Use the course to populate the data into our views
        Course course = (Course) getIntent().getSerializableExtra(MainActivity.COURSE_DETAIL_KEY);
        loadCourse(course);
    }

    /**
     * Populate data for the course
     *
     * @param course
     */
    private void loadCourse(Course course) {
        // Change activity title
        this.setTitle(course.getTitle());

        // Populate data
        tvTitle.setText(course.getTitle());
        tvDescription.setText(course.getDescription());
        tvTerm.setText(course.getTerm());
        tvInstructor.setText(course.getInstructor());
        tvAbbrev.setText(course.getPrefix() + " " + course.getCourseNumber());
    }

    /**
     * Creates menu options.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles clicks on action bar.
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
}
