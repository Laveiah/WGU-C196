package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Adapters.TermAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.R;
import com.example.c196.Entity.Term;

import java.util.List;

public class ListOfTerms extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_terms);
        RecyclerView recyclerView=findViewById(R.id.listOfTermsRecyclerView);
        final TermAdapter termAdapter= new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository=new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);

    }
    public void detailTermViewScreen(View view) {
        Intent intent = new Intent(ListOfTerms.this, DetailedTermView.class);
        startActivity(intent);
    }
}