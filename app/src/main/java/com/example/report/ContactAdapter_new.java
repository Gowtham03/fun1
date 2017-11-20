package com.example.report;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter_new extends RecyclerView.Adapter<ContactAdapter_new.ContactViewHolder> {
    ArrayList<Contact_new> adapter_list=new ArrayList<>();
    Search mainActivity;
    Context ctx;
    public ArrayList<Contact_new> getList()
    {
        return adapter_list;
    }
    public ContactAdapter_new(List<Contact_new> adapter_list, Context ctx)
    {
        this.adapter_list = (ArrayList<Contact_new>) adapter_list;
        this.ctx=ctx;
        mainActivity=(Search)ctx;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout_new, parent, false);
        ContactViewHolder contactViewHolder=new ContactViewHolder(view,mainActivity);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {
        holder.Name.setText(adapter_list.get(position).getName());
        holder.Email.setText(adapter_list.get(position).getEmail());

        holder.pre.setText(adapter_list.get(position).getPre());
        holder.abs.setText(adapter_list.get(position).getAbs());
       holder.total.setText(adapter_list.get(position).getHalf());

    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView Name,Email;
        TextView abs,pre,total;
        Search mainActivity;
        public ContactViewHolder(View itemView,Search mainActivity) {
            super(itemView);

            Name = (TextView) itemView.findViewById(R.id.name);
            Email = (TextView) itemView.findViewById(R.id.no);
            abs = (TextView) itemView.findViewById(R.id.abs);
            pre = (TextView) itemView.findViewById(R.id.pre);
            total = (TextView) itemView.findViewById(R.id.total);

            this.mainActivity = mainActivity;
        }

        @Override
        public void onClick(View v) {

        }

    }


}
