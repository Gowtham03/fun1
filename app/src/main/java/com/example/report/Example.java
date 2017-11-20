package com.example.report;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class Example extends RecyclerView.Adapter<Example.ContactViewHolder>{
    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ContactViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
