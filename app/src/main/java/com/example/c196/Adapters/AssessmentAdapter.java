package com.example.c196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Assessment;
import com.example.c196.Entity.Course;
import com.example.c196.R;
import com.example.c196.UI.DetailedAssessmentView;
import com.example.c196.UI.DetailedCourseView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    public class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View view){
            super(view);
            assessmentItemView=view.findViewById(R.id.assessmentRow);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current=mAssessments.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentView.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("name", current.getAssessmentTitle());
                    intent.putExtra("startDate", current.getAssessmentStartDate());
                    intent.putExtra("endDate", current.getAssessmentEndDate());
                    intent.putExtra("objective", current.getAssessmentType());
                    intent.putExtra("course", current.getAssessmentCourses());
                    intent.putExtra("courseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_assessment_list_row, parent, false);
        return new AssessmentViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null){
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentTitle();
            holder.assessmentItemView.setText(name);
        } else {
            holder.assessmentItemView.setText("No Assessment Info");
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public void setAssessment(List<Assessment> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
}
}
