package com.example.tyler.hw2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tyler on 9/30/15.
 */
public class Course implements Serializable {

    private String academicLevel;
    private String capacity;
    private String courseNumber;
    private String credit;
    private String description;
    private String lineNumber;
    private String location;
    private String prefix;
    private String prereq;
    private String seatsAvailable;
    private String section;
    private String status;
    private String term;
    private String title;

    Course() {}

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setPrereq(String prereq) {
        this.prereq = prereq;
    }

    public void setSeatsAvailable(String seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCredit() {
        return credit;
    }

    public String getDescription() {
        return description;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPrereq() {
        return prereq;
    }

    public String getSeatsAvailable() {
        return seatsAvailable;
    }

    public String getSection() {
        return section;
    }

    public String getStatus() {
        return status;
    }

    public String getTerm() {
        return term;
    }

    public String getTitle() {
        return title;
    }

    // Returns a Book given the expected JSON
    public static Course fromJson(JSONObject jsonObject) {
        Course course = new Course();

        try {
            course.setAcademicLevel(jsonObject.getString("academicLevel"));
            course.setCapacity(jsonObject.getString("capacity"));
            course.setCourseNumber(jsonObject.getString("courseNumber"));
            course.setCredit(jsonObject.getString("credit"));
            course.setDescription(jsonObject.getString("description"));
            course.setLineNumber(jsonObject.getString("lineNumber"));
            course.setLocation(jsonObject.getString("location"));
            course.setPrefix(jsonObject.getString("prefix"));
            course.setPrereq(jsonObject.getString("prerequisites"));
            course.setSeatsAvailable(jsonObject.getString("seatsAvailable"));
            course.setSection(jsonObject.getString("section"));
            course.setStatus(jsonObject.getString("status"));
            course.setTerm(jsonObject.getString("term"));
            course.setTitle(jsonObject.getString("title"));
        }
        catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        // Return new object
        return course;
    }

    public static ArrayList<Course> fromJson(JSONArray jsonArray) {
        ArrayList<Course> courses = new ArrayList<>(jsonArray.length());

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
