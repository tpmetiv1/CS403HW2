package com.example.tyler.hw2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Course class models a course offered at SVSU. Data members that model the attributes of a
 * particular course are included.
 */
public class Course implements Serializable {

    /** Course number */
    private String courseNumber;
    /** Course description */
    private String description;
    /** Course prefix */
    private String prefix;
    /** Course section number */
    private String section;
    /** Course term */
    private String term;
    /** Course title */
    private String title;
    /** Course instructor */
    private String instructor;

    /**
     * Default constructor.
     */
    Course() {}

    /**
     * Sets course number.
     *
     * @param courseNumber
     */
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    /**
     * Sets course description.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets course prefix.
     *
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Sets course section.
     *
     * @param section
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Sets course term.
     *
     * @param term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Sets course title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets course instructor.
     *
     * @param instructor
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Returns course number.
     *
     * @return courseNumber
     */
    public String getCourseNumber() {
        return courseNumber;
    }

    /**
     * Return course description.
     *
     * @return courseDescription
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return course prefix.
     *
     * @return prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Return course section.
     *
     * @return section
     */
    public String getSection() {
        return section;
    }

    /**
     * Return course term.
     *
     * @return term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Return course title.
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return course instructor.
     *
     * @return instructor
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Returns a Book given the expected JSON.
     *
     * @param jsonObject
     * @return course
     */
    public static Course fromJson(JSONObject jsonObject) {

        // Instantiate Course object
        Course course = new Course();

        try {
            // Set course attributes
            course.setCourseNumber(jsonObject.getString("courseNumber"));
            course.setDescription(jsonObject.getString("description"));
            course.setPrefix(jsonObject.getString("prefix"));
            course.setSection(jsonObject.getString("section"));
            course.setTerm(jsonObject.getString("term"));
            course.setTitle(jsonObject.getString("title"));

            // If instructor's array is populated
            if (jsonObject.has("instructors")) {
                // Set instructor name
                final JSONArray instructors = jsonObject.getJSONArray("instructors");
                JSONObject innerElement = instructors.getJSONObject(0);
                course.setInstructor(innerElement.getString("name"));
            }
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        // Return new object
        return course;
    }

    /**
     * This function gathers courses from a JSON query.
     *
     * @param jsonArray
     * @return courses
     */
    public static ArrayList<Course> fromJson(JSONArray jsonArray) {

        // Instantiate ArraList to hold queried course data
        ArrayList<Course> courses = new ArrayList<>(jsonArray.length());

        // Retrieve course data from JSONArray
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject courseJson;
            try {
                courseJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Course course = Course.fromJson(courseJson);
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }
}
