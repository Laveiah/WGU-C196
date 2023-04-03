package com.example.c196.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.dao.AssessmentDAO;
import com.example.c196.dao.CourseDAO;
import com.example.c196.dao.MentorDAO;
import com.example.c196.dao.TermDAO;
import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Mentor;
import com.example.c196.Entity.Term;

@Database(entities = {Assessment.class, Course.class, Term.class, Mentor.class}, version = 1)
public abstract class c196MobileApplicationDataBase extends RoomDatabase {

    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract TermDAO termDAO();
    public abstract MentorDAO mentorDAO();

    private static volatile c196MobileApplicationDataBase INSTANCE;

    static c196MobileApplicationDataBase getDataBase(final Context context){
        if(INSTANCE==null){
            synchronized (c196MobileApplicationDataBase.class){
                if (INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),c196MobileApplicationDataBase.class, "classSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
