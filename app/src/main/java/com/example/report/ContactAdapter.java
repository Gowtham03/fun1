package com.example.report;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    ArrayList<Contact> adapter_list=new ArrayList<>();
    Attendance mainActivity;
    Context ctx;
    public ContactAdapter(ArrayList<Contact> adapter_list,Context ctx)
    {
        this.adapter_list =adapter_list;
        this.ctx=ctx;
        mainActivity=(Attendance)ctx;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        ContactViewHolder contactViewHolder=new ContactViewHolder(view,mainActivity);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        holder.Name.setText(adapter_list.get(position).getName());
        holder.Email.setText(adapter_list.get(position).getEmail());
        holder.spinner.setSelection(adapter_list.get(position).getposition());
        holder.spinner.setTag(adapter_list.get(position));
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().equals("Present"))
                {
                    adapterView.setBackgroundColor(Color.parseColor("#2ecc8d"));
                    Spinner spinner=(Spinner)adapterView;
                    Contact contact=(Contact)spinner.getTag();
                    contact.setPosition(0);
                    adapter_list.get(position).setPosition(0);
                }else if(adapterView.getSelectedItem().toString().equals("Absent"))
                {
                    adapterView.setBackgroundColor(Color.parseColor("#ee4f4f"));
                    Spinner spinner=(Spinner)adapterView;
                    Contact contact=(Contact)spinner.getTag();
                    contact.setPosition(1);
                    adapter_list.get(position).setPosition(1);
                }else
                {
                    adapterView.setBackgroundColor(Color.parseColor("#FF6EECF5"));
                    Spinner spinner=(Spinner)adapterView;
                    Contact contact=(Contact)spinner.getTag();
                    contact.setPosition(2);
                    adapter_list.get(position).setPosition(2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView Name,Email;
        public  Spinner spinner;
        Attendance mainActivity;
        CardView cardView;


        public ContactViewHolder(View itemView,Attendance mainActivity) {
            super(itemView);

            Name=(TextView)itemView.findViewById(R.id.name);
            Email=(TextView)itemView.findViewById(R.id.no);
            spinner=(Spinner)itemView.findViewById(R.id.spinner);
            this.mainActivity=mainActivity;


        }

        @Override
        public void onClick(View v) {
            mainActivity.prepareSelection(v,getAdapterPosition());
        }
    }

    public List<Contact> getList()
    {
        return adapter_list;
    }
}
