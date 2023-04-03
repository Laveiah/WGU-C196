package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.Toast;

import com.example.c196.Adapters.AssessmentAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Term;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAssessmentView extends AppCompatActivity {
    EditText editAssessmentName;
    Spinner editAssessmentType;
    Spinner editAssessmentCourse;
    EditText editAssessmentStartDate;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editAssessmentEndDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();



    String name;
    String dateStart;
    String dateEnd;
    String type;
    String course;
    int assessmentID;
    int courseID;
    Repository repository;
    Assessment assessment;

    ArrayAdapter<Course> assessmentCourses = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment_view);
        editAssessmentName=findViewById(R.id.assessmentDetailedNameField);
        editAssessmentStartDate=findViewById(R.id.assessmentDetailedStartDateField);
        editAssessmentEndDate=findViewById(R.id.assessmentDetailedEndDateField);
        editAssessmentType=findViewById(R.id.assessmentDetailedTypeSpinner);
        editAssessmentCourse=findViewById(R.id.assessmentDetailedCoursesSpinner);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat startDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat endDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editAssessmentStartDate.setText(startDateFormat.format(new Date()));
        editAssessmentEndDate.setText(endDateFormat.format(new Date()));

        name= getIntent().getStringExtra("name");
        dateStart=getIntent().getStringExtra("startDate");
        dateEnd=getIntent().getStringExtra("endDate");
        type=getIntent().getStringExtra("objective");
        course=getIntent().getStringExtra("course");
        assessmentID=getIntent().getIntExtra("assessmentID", -1);
        courseID=getIntent().getIntExtra("courseID", -1);
//        courseID=1;

        editAssessmentName.setText(name);
        editAssessmentStartDate.setText(dateStart);
        editAssessmentEndDate.setText(dateEnd);

        //------------ Spinner info for Drop Down Assessment Type ------------//
        //------------ Spinner info for Drop Down Assessment Type ------------//
        ArrayAdapter<CharSequence> assessmentTypeAdapter = ArrayAdapter.createFromResource(this, R.array.assessmentType, android.R.layout.simple_spinner_item);
        assessmentTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editAssessmentType.setAdapter(assessmentTypeAdapter);
        repository=new Repository(getApplication());

        if (type != null) {
            if (type.equals("Objective")) {
                editAssessmentType.setSelection(0);
            } else {
                if (type.equals("Performance")) {
                    editAssessmentType.setSelection(1);
                }
            }
        }

        //------------ Spinner info for Drop Down Assessment Course ------------//
        //------------ Spinner info for Drop Down Assessment Course ------------//

        Spinner spinner=findViewById(R.id.assessmentDetailedCoursesSpinner);
        assessmentCourses=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, repository.getAllCourses());
        spinner.setAdapter(assessmentCourses);
        //for loop, find course by matching the courseid with the assessment courseid. courseobject setselction
        for(int i =0; i < assessmentCourses.getCount(); i++){
            if(    assessmentCourses.getItem(i).getCourseID()==courseID){
                editAssessmentCourse.setSelection(i);
            }
        }
//        editAssessmentCourse.setSelection(assessmentCourses.getItem(0).getCourseID()-1);//fix
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editAssessmentCourse.setSelection(assessmentCourses.getItem(i).getCourseID()-1);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //------------ Spinner info for Drop Down Assessment Course ------------//
        //------------ Spinner info for Drop Down Assessment Course ------------//



        Button deleteButton=findViewById(R.id.assessmentDetailDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Assessment assessment : repository.getAllAssessments()) {
                    if (assessment.getAssessmentID() == assessmentID) {
                        Assessment currentAssessment = assessment;
                        repository.delete(currentAssessment);
                        Toast.makeText(DetailedAssessmentView.this, "Assessment Deleted", Toast.LENGTH_SHORT).show();

                    }
                }
                finish();
            }
        });
        Switch startAlert = findViewById(R.id.assessmentDetailStartReminderSwitch);
        startAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startAlert.isChecked()){
                    String myStartFormat = "MM/dd/yy";
                    SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);

                    if (editAssessmentStartDate.getText().toString().compareTo(sdfStart.format(new Date()))>=0){
                        Toast.makeText(DetailedAssessmentView.this, "Start Alert Set", Toast.LENGTH_SHORT).show();
                        String startDateFromScreen = editAssessmentStartDate.getText().toString();
//                        String myStartFormat = "MM/dd/yy";
//                        SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);
                        Date myStartDate = null;
                        try {
                            myStartDate = sdfStart.parse(startDateFromScreen);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long startTrigger = myStartDate.getTime();

                        Intent startIntent = new Intent(DetailedAssessmentView.this, MyReceiver.class);
                        startIntent.putExtra("key", startDateFromScreen + " should trigger ");
                        PendingIntent startSender = PendingIntent.getBroadcast(DetailedAssessmentView.this, ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                    } else {
                        Toast.makeText(DetailedAssessmentView.this, "Please Select a Date Either Current or Future.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailedAssessmentView.this, "Start Alert Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Switch endAlert = findViewById(R.id.assessmentDetailEndReminderSwitch);
        endAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (endAlert.isChecked()){
                    String myEndFormat = "MM/dd/yy";
                    SimpleDateFormat sdfEnd = new SimpleDateFormat(myEndFormat, Locale.US);

                    if (editAssessmentEndDate.getText().toString().compareTo(sdfEnd.format(new Date()))>=0){
                        Toast.makeText(DetailedAssessmentView.this, "End Alert Set", Toast.LENGTH_SHORT).show();
                        String endDateFromScreen = editAssessmentEndDate.getText().toString();
//                        String myStartFormat = "MM/dd/yy";
//                        SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);
                        Date myEndDate = null;
                        try {
                            myEndDate = sdfEnd.parse(endDateFromScreen);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long startTrigger = myEndDate.getTime();

                        Intent endIntent = new Intent(DetailedAssessmentView.this, MyReceiver.class);
                        endIntent.putExtra("key", endDateFromScreen + " should trigger ");
                        PendingIntent startSender = PendingIntent.getBroadcast(DetailedAssessmentView.this, ++MainActivity.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                    } else {
                        Toast.makeText(DetailedAssessmentView.this, "Date Can't Be Before the Start Date", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailedAssessmentView.this, "End Alert Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button button=findViewById(R.id.assessmentDetailedSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName = editAssessmentCourse.getSelectedItem().toString();
                for(int i=0; i<assessmentCourses.getCount(); i++){
                    if(assessmentCourses.getItem(i).getCourseName().equals(courseName)){
                        courseID=assessmentCourses.getItem(i).getCourseID();
                    }
                }
                if(assessmentID==-1){
                    assessment=new Assessment(0, editAssessmentName.getText().toString(),
                            editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(),
                            editAssessmentType.getSelectedItem().toString(), courseName,
                            courseID);
                    repository.insert(assessment);
                } else {
                    assessment = new Assessment(assessmentID, editAssessmentName.getText().toString(),
                            editAssessmentStartDate.getText().toString(), editAssessmentEndDate.getText().toString(),
                            editAssessmentType.getSelectedItem().toString(), courseName,
                            courseID);
                    repository.update(assessment);
                }
                    Intent intent = new Intent(DetailedAssessmentView.this, ListOfAssessments.class);
                    startActivity(intent);

            }
            });
        //------------ Date Format ------------//
        //------------ Date Format ------------//
        editAssessmentStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editAssessmentStartDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarStart.setTime(startDateFormat.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(DetailedAssessmentView.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editAssessmentEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editAssessmentEndDate.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarEnd.setTime(endDateFormat.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(DetailedAssessmentView.this, endDate, myCalendarEnd.get(Calendar.YEAR),
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
                editAssessmentStartDate.setText(sdf.format(myCalendarStart.getTime()));
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
                editAssessmentEndDate.setText(sdf.format(myCalendarEnd.getTime()));
            }
        };
    }
    //------------ Date Format ------------//

}

