package com.example.tyler.hw2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The CourseAdapter class acts as an adapter to display fetched course data in the course ListView.
 */
public class CourseAdapter extends ArrayAdapter<Course> {

    /**
     * Nested ViewHolder class which stores TextViews for course prefix and title.
     */
    public static class ViewHolder {
        /** Course prefix TextView */
        public TextView tvPrefixNumber;
        /** Course title TextView */
        public TextView tvTitle;
    }

    /**
     * Parameterized constructor.
     *
     * @param context
     * @param aCourses
     */
    public CourseAdapter(Context context, ArrayList<Course> aCourses) {
        super(context, 0, aCourses);
    }

    /**
     * Overriden getView method that returns a ViewHolder object containing relevant course prefix
     * and title.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        final Course course = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        // Set ViewHolder data
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_course, parent, false);
            viewHolder.tvPrefixNumber = (TextView)convertView.findViewById(R.id.tvPrefixNumber);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.tvPrefixNumber.setText(course.getPrefix() + " " + course.getCourseNumber() + " - " + course.getSection());
        viewHolder.tvTitle.setText(course.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }
}
