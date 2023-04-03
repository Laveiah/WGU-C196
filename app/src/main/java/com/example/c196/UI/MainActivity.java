package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Mentor;
import com.example.c196.R;
import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Term;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessments, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                Assessment assessment = new Assessment(0, "Intro to IT", "01/1/22",
                        "02/1/2022", "Objective", "c196", 0);
                Repository repository = new Repository(getApplication());
                repository.insert(assessment);
                Course course = new Course(0, "c196", "03/01/2022", "03/20/2022",
                        "Completed", "Phillip Webber", "555-555-5555",
                        "pwebber@wgu.edu", 1, "test");
                repository.insert(course);
                Term term = new Term(0, "Term1", "01/01/2022", "03/01/2022");
                repository.insert(term);
                Mentor mentor = new Mentor(0, "Phillip Webber", "555-555-5555", "pwebber@wgu.edu");
                repository.insert(mentor);
        }
        return super.onOptionsItemSelected(item);
    }

    public void termScreen(View view) {
        Intent intent = new Intent(MainActivity.this, ListOfTerms.class);
        startActivity(intent);
    }

    public void coursesScreen(View view) {
        Intent intent = new Intent(MainActivity.this, ListOfCourses.class);
        startActivity(intent);
    }

    public void assessmentScreen(View view) {
        Intent intent = new Intent(MainActivity.this, ListOfAssessments.class);
        startActivity(intent);
    }
}