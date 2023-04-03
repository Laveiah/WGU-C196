package com.example.c196.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.c196.Adapters.CourseAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Course;
import com.example.c196.R;
import com.example.c196.Entity.Term;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedTermView extends AppCompatActivity {
    EditText editTermName;
    EditText editStartDate;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editEndDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();

    String name;
    String dateStart;
    String dateEnd;
    int termID;
    Term term;
    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term_view);
        editTermName=findViewById(R.id.termDetailTitleField);
        editStartDate=findViewById(R.id.termDetailStartDateField);
        editEndDate=findViewById(R.id.termDetailEndDateField);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat startDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat endDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editStartDate.setText(startDateFormat.format(new Date()));
        editEndDate.setText(endDateFormat.format(new Date()));

        name=getIntent().getStringExtra("termName");
        dateStart=getIntent().getStringExtra("termStartDate");
        dateEnd=getIntent().getStringExtra("termEndDate");
        termID=getIntent().getIntExtra("termID", -1);

        editTermName.setText(name);
        editStartDate.setText(dateStart);
        editEndDate.setText(dateEnd);

        repository=new Repository(getApplication());

//----------------- term course -----------------//
//----------------- term course -----------------//
        RecyclerView recyclerView=findViewById(R.id.termDetailCourseRecyclerView);
        List<Course> termCourses=repository.getCoursesByTerm(termID);
        CourseAdapter adapter=new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourse(termCourses);
//----------------- term course -----------------//
//----------------- term course -----------------//

        Button deleteButton=findViewById(R.id.termDetailDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Term term : repository.getAllTerms()) {
                    if (term.getTermID() == termID) {
                        Term currentTerm = term;
                        repository.delete(currentTerm);
                    } else {
                        Toast.makeText(DetailedTermView.this, "Can't Delete" + termCourses + " with courses assigned", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });

        Button button=findViewById(R.id.termDetailSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(termID==-1){
                    term=new Term(0, editTermName.getText().toString(), editStartDate.getText().toString(),
                            editEndDate.getText().toString());
                    repository.insert(term);
                } else {
                    term=new Term(termID, editTermName.getText().toString(), editStartDate.getText().toString(),
                            editEndDate.getText().toString());
                    repository.update(term);
                }
                Intent intent=new Intent(DetailedTermView.this, ListOfTerms.class);
                startActivity(intent);
            }
        });
        //------------ Date Format ------------//
        //------------ Date Format ------------//
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editStartDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarStart.setTime(startDateFormat.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(DetailedTermView.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editEndDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarEnd.setTime(endDateFormat.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(DetailedTermView.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH), myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfTheYear,
                                  int dayOfTheMonth) {

                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfTheYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);

                updateLabelStart();
            }

            private void updateLabelStart() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editStartDate.setText(sdf.format(myCalendarStart.getTime()));
            }
        };


        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfTheYear,
                                  int dayOfTheMonth) {

                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfTheYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfTheMonth);

                updateLabelEnd();
            }

            private void updateLabelEnd() {
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
            }
        };
    }
    //------------ Date Format ------------//


    public void detailedCourseViewScreen(View view) {
        Intent intent = new Intent(DetailedTermView.this, DetailedCourseView.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }
}