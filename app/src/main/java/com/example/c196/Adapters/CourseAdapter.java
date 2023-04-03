package com.example.c196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Course;
import com.example.c196.R;
import com.example.c196.UI.DetailedCourseView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private CourseViewHolder(View view) {
            super(view);
            courseItemView = view.findViewById(R.id.coursesRow);
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, DetailedCourseView.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseInstructorName", current.getCourseInstructorName());
                    intent.putExtra("courseInstructorEmail", current.getCourseInstructorEmail());
                    intent.putExtra("courseInstructorPhone", current.getCourseInstructorPhone());
                    intent.putExtra("courseNotes", current.getCourseNotes());
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }

    }
    private List <Course>mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_course_list_row, parent, false);
        return new CourseViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null){
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            holder.courseItemView.setText(name);
        } else {
            holder.courseItemView.setText("No Course Name Info");
        }
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourse(List<Course> courses){
        mCourses = courses;
        notifyDataSetChanged();
    }
}
