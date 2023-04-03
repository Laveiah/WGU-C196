package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Adapters.AssessmentAdapter;
import com.example.c196.Adapters.CourseAdapter;
import com.example.c196.Adapters.TermAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Term;
import com.example.c196.R;

import java.util.List;

public class ListOfAssessments extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_assessments);
        RecyclerView recyclerView=findViewById(R.id.listOfAssessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository=new Repository(getApplication());
        List<Assessment> allAssessments = repository.getAllAssessments();
        assessmentAdapter.setAssessment(allAssessments);
    }

    public void detailedAssessmentViewScreen(View view) {
        Intent intent = new Intent(ListOfAssessments.this, DetailedAssessmentView.class);
        System.out.println("test");
        startActivity(intent);
    }

}