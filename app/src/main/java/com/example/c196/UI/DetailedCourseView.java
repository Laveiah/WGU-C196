package com.example.c196.UI;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.Entity.Term;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailedCourseView extends AppCompatActivity {
    EditText editCourseName;
    EditText editCourseStart;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    EditText editCourseEnd;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();

    Spinner editCourseStatus; //SPINNER
    EditText editCourseInstructorName;
    EditText editCourseInstructorPhone;
    EditText editCourseInstructorEmail;
    EditText editCourseDetailNote;



    String name;
    String dateStart;
    String dateEnd;
    String status;
    String instructorsName;
    String InstructorPhone;
    String InstructorEmail;
    String notes;
    int courseID;
    int termID;
    Course course;
    Repository repository;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);
        editCourseName = findViewById(R.id.editCourseDetailTitleViewText);
        editCourseStart = findViewById(R.id.editCourseDetailStartDateField);
        editCourseEnd = findViewById(R.id.editCourseDetailEndDateField);
        editCourseStatus = findViewById(R.id.courseDetailStatusSpinnerField);//************** Correct?
        editCourseInstructorName = findViewById(R.id.editCourseDetailInstructorPersonNameField);
        editCourseInstructorPhone = findViewById(R.id.editCourseDetailInstructorPhoneField);
        editCourseInstructorEmail = findViewById(R.id.editCourseDetailInstructorEmailAddress);
        editCourseDetailNote = findViewById(R.id.editCourseDetailNoteField);
        String myFormat = "MM/dd/yy";
        SimpleDateFormat startDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat endDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        editCourseStart.setText(startDateFormat.format(new Date()));
        editCourseEnd.setText(endDateFormat.format(new Date()));

        name = getIntent().getStringExtra("courseName");
        dateStart = getIntent().getStringExtra("courseStart");
        dateEnd = getIntent().getStringExtra("courseEnd");
        status = getIntent().getStringExtra("courseStatus");
        instructorsName = getIntent().getStringExtra("courseInstructorName");
        InstructorPhone = getIntent().getStringExtra("courseInstructorPhone");
        InstructorEmail = getIntent().getStringExtra("courseInstructorEmail");
        notes = getIntent().getStringExtra("courseNotes");
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        editCourseName.setText(name);
        editCourseStart.setText(dateStart);
        editCourseEnd.setText(dateEnd);
        editCourseInstructorName.setText(instructorsName);
        editCourseInstructorPhone.setText(InstructorPhone);
        editCourseInstructorEmail.setText(InstructorEmail);
        editCourseDetailNote.setText(notes);

        //------------ Spinner info for Drop Down Course ------------//
        //------------ Spinner info for Drop Down Course ------------//
        ArrayAdapter<CharSequence> courseStatusAdapter = ArrayAdapter.createFromResource(this, R.array.courseStatus, android.R.layout.simple_spinner_item);
        courseStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCourseStatus.setAdapter(courseStatusAdapter);
        repository = new Repository(getApplication());

        if (status != null) {
            if (status.equals("In progress")) {
                editCourseStatus.setSelection(0);
            } else {
                if (status.equals("Completed")) {
                    editCourseStatus.setSelection(1);
                } else {
                    if (status.equals("Dropped")) {
                        editCourseStatus.setSelection(2);
                    } else {
                        editCourseStatus.setSelection(3);
                    }
                }
            }
        }
        //------------ Spinner info for Drop Down Course ------------//
        //------------ Spinner info for Drop Down Course ------------//



        Button deleteButton = findViewById(R.id.courseDetailDeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Course course : repository.getAllCourses()) {
                    if (course.getCourseID() == courseID) {
                        Course currentCourse = course;
                        repository.delete(currentCourse);
                    }
                }
                finish();
            }
        });

        Switch startAlert = findViewById(R.id.editCourseDetailStartReminderSwitch);
        startAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startAlert.isChecked()){
                    String myStartFormat = "MM/dd/yy";
                    SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);

                    if (editCourseStart.getText().toString().compareTo(sdfStart.format(new Date()))>=0){
                        Toast.makeText(DetailedCourseView.this, "Start Alert Set", Toast.LENGTH_SHORT).show();
                        String startDateFromScreen = editCourseStart.getText().toString();
//                        String myStartFormat = "MM/dd/yy";
//                        SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);
                        Date myStartDate = null;
                        try {
                            myStartDate = sdfStart.parse(startDateFromScreen);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long startTrigger = myStartDate.getTime();

                        Intent startIntent = new Intent(DetailedCourseView.this, MyReceiver.class);
                        startIntent.putExtra("key", startDateFromScreen + " should trigger ");
                        PendingIntent startSender = PendingIntent.getBroadcast(DetailedCourseView.this, ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                    } else {
                        Toast.makeText(DetailedCourseView.this, "Please Select a Date Either Current or Future.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailedCourseView.this, "Start Alert Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Switch endAlert = findViewById(R.id.editCourseDetailEndReminderSwitch);
        endAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (endAlert.isChecked()){
                    String myEndFormat = "MM/dd/yy";
                    SimpleDateFormat sdfEnd = new SimpleDateFormat(myEndFormat, Locale.US);

                    if (editCourseEnd.getText().toString().compareTo(sdfEnd.format(new Date()))>=0){
                        Toast.makeText(DetailedCourseView.this, "End Alert Set", Toast.LENGTH_SHORT).show();
                        String endDateFromScreen = editCourseEnd.getText().toString();
//                        String myStartFormat = "MM/dd/yy";
//                        SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);
                        Date myEndDate = null;
                        try {
                            myEndDate = sdfEnd.parse(endDateFromScreen);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long startTrigger = myEndDate.getTime();

                        Intent endIntent = new Intent(DetailedCourseView.this, MyReceiver.class);
                        endIntent.putExtra("key", endDateFromScreen + " should trigger ");
                        PendingIntent startSender = PendingIntent.getBroadcast(DetailedCourseView.this, ++MainActivity.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                        startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
                    } else {
                        Toast.makeText(DetailedCourseView.this, "Date Can't Be Before the Start Date", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailedCourseView.this, "End Alert Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
        });




        Button button = findViewById(R.id.courseDetailsSaveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (courseID == -1) {
                    course = new Course(0, editCourseName.getText().toString(), editCourseStart.getText().toString(),
                            editCourseEnd.getText().toString(), editCourseStatus.getSelectedItem().toString(),
                            editCourseInstructorName.getText().toString(), editCourseInstructorPhone.getText().toString(), editCourseInstructorEmail.getText().toString(), termID, editCourseDetailNote.getText().toString());
                    repository.insert(course);
                } else {
                    course = new Course(courseID, editCourseName.getText().toString(), editCourseStart.getText().toString(),
                            editCourseEnd.getText().toString(), editCourseStatus.getSelectedItem().toString(),
                            editCourseInstructorName.getText().toString(), editCourseInstructorPhone.getText().toString(), editCourseInstructorEmail.getText().toString(), termID, editCourseDetailNote.getText().toString());
                    repository.update(course);
                }
                Intent intent = new Intent(DetailedCourseView.this, ListOfCourses.class);
                startActivity(intent);
            }
        });

        //------------ Date Format ------------//
        //------------ Date Format ------------//
        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editCourseStart.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarStart.setTime(startDateFormat.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(DetailedCourseView.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = editCourseEnd.getText().toString();
                if (info.equals("")) info = "02/10/23";
                try {
                    myCalendarEnd.setTime(endDateFormat.parse(info));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                new DatePickerDialog(DetailedCourseView.this, endDate, myCalendarEnd.get(Calendar.YEAR),
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
                editCourseStart.setText(sdf.format(myCalendarStart.getTime()));
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
                editCourseEnd.setText(sdf.format(myCalendarEnd.getTime()));
            }
        };
    }
    //------------ Date Format ------------//


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courses, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseDetailNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

//            case R.id.notifyStart:
//                String startDateFromScreen = editCourseStart.getText().toString();
//                String myStartFormat = "MM/dd/yy";
//                SimpleDateFormat sdfStart = new SimpleDateFormat(myStartFormat, Locale.US);
//                Date myStartDate = null;
//                try {
//                    myStartDate = sdfStart.parse(startDateFromScreen);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                Long startTrigger = myStartDate.getTime();
//
//                Intent startIntent = new Intent(DetailedCourseView.this, MyReceiver.class);
//                startIntent.putExtra("key", startDateFromScreen + " should trigger ");
//                PendingIntent startSender = PendingIntent.getBroadcast(DetailedCourseView.this, ++MainActivity.numAlert, startIntent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager startAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                startAlarmManager.set(AlarmManager.RTC_WAKEUP, startTrigger, startSender);
//                return true;
//            case R.id.notifyEnd:
//                String endDateFromScreen = editCourseEnd.getText().toString();
//                String myEndFormat = "MM/dd/yy";
//                SimpleDateFormat sdfEnd = new SimpleDateFormat(myEndFormat, Locale.US);
//                Date myEndDate = null;
//                try {
//                    myEndDate = sdfEnd.parse(endDateFromScreen);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                Long endTrigger = myEndDate.getTime();
//                Intent endIntent = new Intent(DetailedCourseView.this, MyReceiver.class);
//                endIntent.putExtra("key", endDateFromScreen + " should trigger ");
//                PendingIntent endSender = PendingIntent.getBroadcast(DetailedCourseView.this, ++MainActivity.numAlert, endIntent, PendingIntent.FLAG_IMMUTABLE);
//                AlarmManager endAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                endAlarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, endSender);
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}