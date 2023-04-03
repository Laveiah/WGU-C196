package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Adapters.CourseAdapter;
import com.example.c196.Adapters.TermAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Term;
import com.example.c196.R;

import java.util.List;

public class ListOfCourses extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_courses);
        RecyclerView recyclerView=findViewById(R.id.listOfCoursesRecyclerView);
        final CourseAdapter courseAdapter= new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository=new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();
        courseAdapter.setCourse(allCourses);
    }

    public void detailedCourseViewScreen(View view) {
        System.out.println("TTTest");
        Intent intent = new Intent(ListOfCourses.this, DetailedCourseView.class);
        startActivity(intent);
    }
}