package com.example.c196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.Entity.Term;
import com.example.c196.UI.DetailedTermView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termsItemView;
        private TermViewHolder(View view){
            super(view);
            termsItemView = view.findViewById(R.id.termsRow);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, DetailedTermView.class);
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termStartDate", current.getTermStartDate());
                    intent.putExtra("termEndDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List <Term>mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.activity_terms_list_row, parent, false);
        return new TermViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null){
            Term current = mTerms.get(position);
            String name = current.getTermName();
            holder.termsItemView.setText(name);
        } else {
            holder.termsItemView.setText("No Term Name Info");
        }
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

}
