package com.example.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {
@PrimaryKey(autoGenerate = true)

    private int courseID;
    private String courseName;
    private String courseStart;
    private String courseEnd;
    private String courseStatus;
    private String courseInstructorName;
    private String courseInstructorPhone;
    private String courseInstructorEmail;
    private String courseNotes;
    private int termID;

    public Course(int courseID, String courseName, String courseStart, String courseEnd, String courseStatus, String courseInstructorName, String courseInstructorPhone, String courseInstructorEmail, int termID, String courseNotes) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.courseStatus = courseStatus;
        this.courseInstructorName = courseInstructorName;
        this.courseInstructorPhone = courseInstructorPhone;
        this.courseInstructorEmail = courseInstructorEmail;
        this.termID = termID;
        this.courseNotes = courseNotes;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public String getCourseInstructorPhone() {
        return courseInstructorPhone;
    }

    public void setCourseInstructorPhone(String courseInstructorPhone) {
        this.courseInstructorPhone = courseInstructorPhone;
    }

    public String getCourseInstructorEmail() {
        return courseInstructorEmail;
    }

    public void setCourseInstructorEmail(String courseInstructorEmail) {
        this.courseInstructorEmail = courseInstructorEmail;
    }

    public int getTermID() {
        return termID;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    @Override
    public String toString() {
//        return "Course{" +
//                "courseID=" + courseID +
//                ", courseName='" + courseName + '\'' +
//                ", courseStart='" + courseStart + '\'' +
//                ", courseEnd='" + courseEnd + '\'' +
//                ", courseStatus='" + courseStatus + '\'' +
//                ", courseInstructorName='" + courseInstructorName + '\'' +
//                ", courseInstructorPhone='" + courseInstructorPhone + '\'' +
//                ", courseInstructorEmail='" + courseInstructorEmail + '\'' +
//                ", courseNotes='" + courseNotes + '\'' +
//                ", termID=" + termID +
//                '}';
        return courseName;
    }
}


