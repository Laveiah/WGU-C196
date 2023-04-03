package com.example.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "Assessments")
    public class Assessment {
        @PrimaryKey(autoGenerate = true)
        private int assessmentID;
        private String assessmentTitle;
        private String assessmentStartDate;
        private String assessmentEndDate;
        private String assessmentType;
        private String assessmentCourses;
        private int courseID;

        public Assessment(){
        }
        public Assessment(int assessmentID, String assessmentTitle, String assessmentStartDate, String assessmentEndDate, String assessmentType, String assessmentCourses, int courseID) {
            this.assessmentID = assessmentID;
            this.assessmentTitle = assessmentTitle;
            this.assessmentStartDate = assessmentStartDate;
            this.assessmentEndDate = assessmentEndDate;
            this.assessmentType = assessmentType;
            this.assessmentCourses = assessmentCourses;
            this.courseID = courseID;
        }

        public int getAssessmentID() {
            return assessmentID;
        }

        public void setAssessmentID(int assessmentID) {
            this.assessmentID = assessmentID;
        }

        public String getAssessmentTitle() {
            return assessmentTitle;
        }

        public void setAssessmentTitle(String assessmentTitle) {
            this.assessmentTitle = assessmentTitle;
        }

        public String getAssessmentStartDate() {
            return assessmentStartDate;
        }

        public void setAssessmentStartDate(String assessmentStartDate) {
            this.assessmentStartDate = assessmentStartDate;
        }

        public String getAssessmentEndDate() {
            return assessmentEndDate;
        }

        public void setAssessmentEndDate(String assessmentEndDate) {
            this.assessmentEndDate = assessmentEndDate;
        }

        public String getAssessmentType() {
            return assessmentType;
        }

        public void setAssessmentType(String assessmentType) {
            this.assessmentType = assessmentType;
        }

        public String getAssessmentCourses() {
            return assessmentCourses;
        }

        public void setAssessmentCourses(String assessmentCourses) {
            this.assessmentCourses = assessmentCourses;
        }

        public int getCourseID() {
            return courseID;
        }

        public void setCourseID(int courseID) {
            this.courseID = courseID;
        }
    }
