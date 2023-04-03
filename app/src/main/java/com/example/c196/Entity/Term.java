package com.example.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int TermID;
    private String TermName;
    private String TermStartDate;
    private String TermEndDate;

    public Term() {
    };
    public Term(int termID, String termName, String termStartDate, String termEndDate) {
        TermID = termID;
        TermName = termName;
        TermStartDate = termStartDate;
        TermEndDate = termEndDate;
    }

    public int getTermID() {
        return TermID;
    }

    public void setTermID(int termID) {
        TermID = termID;
    }

    public String getTermName() {
        return TermName;
    }

    public void setTermName(String termName) {
        TermName = termName;
    }

    public String getTermStartDate() {
        return TermStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        TermStartDate = termStartDate;
    }

    public String getTermEndDate() {
        return TermEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        TermEndDate = termEndDate;
    }
}
