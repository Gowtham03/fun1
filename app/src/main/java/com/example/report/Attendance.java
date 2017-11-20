package com.example.report;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Attendance extends AppCompatActivity  {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList Contacts=new ArrayList<>();
    Button save;
    Spinner spinner;
    int n,count=0;
    String date;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String[] Name,Roll;
    View view;
    Helper mydb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        mydb= new Helper(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        json_parsing();
        List<String> spinnerArray =  new ArrayList<String>();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-M-d-EEEE");
        SimpleDateFormat df1=new SimpleDateFormat("yyyy-M-d");
        int count=0;

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE, 0);

        String select_date = getIntent().getExtras().getString("date");

        spinnerArray.add(df1.format(cal.getTime()));
        if(df1.format(cal.getTime()).equals(select_date))
        {
        }
        for(int c=0;c<30;c++)
        {
            cal.add(Calendar.DATE, -1);
            spinnerArray.add(df1.format(cal.getTime()));
        }
        for(int c=0;c<30;c++)
        {
            if(((spinnerArray.get(c))).equals(select_date))
            {
                count=c;
                break;
            }
        }
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner2);

        spinner.setAdapter(adapter1);
        spinner.setSelection(count);
        date=spinner.getSelectedItem().toString();

        save=(Button)findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                asksave(view);
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        int j=0;

        for(String NAME:Name)
        {
            Contact contact=new Contact(NAME,Roll[j],0);
            Contacts.add(contact);
            j++;
        }

        adapter = new ContactAdapter(Contacts,Attendance.this);
        recyclerView.setAdapter(adapter);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent=new Intent(Attendance.this,Test.class);
                startActivity(intent);
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Spinner sItems = (Spinner) findViewById(R.id.spinner2);
                String d=sItems.getSelectedItem().toString();

                List<Contact> stList = ((ContactAdapter) adapter).getList();
                date = spinner.getSelectedItem().toString();

                List<String> l = new ArrayList<String>();
                List<String> l1 = new ArrayList<String>();
                List<String> l2 = new ArrayList<String>();

                Cursor res1 = mydb.getdata(d.toString(),"Absent");
                while (res1.moveToNext()) {
                    l.add(res1.getString(1));
                }

                Cursor res2 = mydb.getdata(d.toString(),"Present");
                while (res2.moveToNext()) {
                    l1.add(res2.getString(1));
                }

                Cursor res3 = mydb.getdata(d.toString(),"Half_day");
                while (res3.moveToNext()) {
                    l2.add(res3.getString(1));
                }

//                SharedPreferences s = getSharedPreferences(date, 0);
//                String Nma = s.getString(date, "");
//                String arr[] = Nma.split(",");

                List<String> fin = new ArrayList<String>();

                for (int g = 0; g < stList.size(); g++) {
                    Contact singleStudent = stList.get(g);
                    fin.add(singleStudent.getName());
                }

                stList.clear();

                for (int j = 0; j < 20; j++) {
                    if (l.contains(fin.get(j))) {
                        Contact dp = new Contact(fin.get(j), "" + (j + 1), 1);
                        stList.add(dp);
                    } else if(l2.contains(fin.get(j))){
                        Contact dp = new Contact(fin.get(j), "" + (j + 1), 2);
                        stList.add(dp);
                    }else
                    {
                        Contact dp = new Contact(fin.get(j), "" + (j + 1), 0);
                        stList.add(dp);
                    }
                }
                recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
                layoutManager=new LinearLayoutManager(Attendance.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void asksave(final View view)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Attendance.this);
        builder.setTitle("Do you want to continue");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                onButtonClick(view);
                Toast.makeText(Attendance.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Attendance.this, Attendance.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                onButtonClick(view);
                Toast.makeText(Attendance.this,"Data inserted successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Attendance.this, MainActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Attendance.this,"Data not inserted",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }



    public  void shared_pref_load(String tittle,String text)
    {
        SharedPreferences sp=getSharedPreferences(tittle,0);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(tittle, text);
        editor.commit();
    }

    public  void onButtonClick(View view)
    {
//        String absent="";
//        String present="";
//        String half="";
        Spinner sItems = (Spinner) findViewById(R.id.spinner2);
        String d=sItems.getSelectedItem().toString();
        List<Contact> list=((ContactAdapter)adapter).getList();
        int i;
        for(i=0;i<20;i++)
        {
            Contact contact=list.get(i);
            if(contact.getposition()==1)
            {
           //     absent=absent+""+contact.getName().toString()+",";
                mydb.insert(contact.getEmail().toString(),contact.getName().toString(),"Absent",d.toString());
            }else if(contact.getposition()==2)
            {
           //     half=half+""+contact.getName().toString()+",";
                mydb.insert(contact.getEmail().toString(),contact.getName().toString(),"Half_day",d.toString());
            }else
            {
          //      present=present+""+contact.getName().toString()+",";
                mydb.insert(contact.getEmail().toString(),contact.getName().toString(),"Present",d.toString());
            }
        }
//        SharedPreferences sp=getSharedPreferences(d+"Absent",0);
//        SharedPreferences.Editor editor=sp.edit();
//        editor.putString(d+"Absent", absent);
//        editor.commit();
//
//        SharedPreferences sp1=getSharedPreferences(d+"Present",0);
//        SharedPreferences.Editor editor1=sp1.edit();
//        editor1.putString(d+"Present", present);
//        editor1.commit();
//
//        SharedPreferences sp2=getSharedPreferences(d+"Half_day",0);
//        SharedPreferences.Editor editor2=sp2.edit();
//        editor2.putString(d+"Half_day", half);
//        editor2.commit();


//        Cursor res1 = mydb.getdata(d.toString(),"Absent");
//        final StringBuffer sb = new StringBuffer();
//        while (res1.moveToNext()) {
//            sb.append(res1.getString(1));
//        }
//        SharedPreferences s = getSharedPreferences("Attendance", 0);
//        String Nma = s.getString("Attendance", "");
//        Nma = Nma + "," + d;
//        shared_pref_load("Attendance",Nma);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_mode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout1) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Attendance.this);
            builder.setTitle("Do you want to save or discard data");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    onButtonClick(view);
                    Toast.makeText(Attendance.this, "Data save successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Attendance.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onButtonClick(view);
                    Toast.makeText(Attendance.this, "Data Discarded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Attendance.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setCancelable(false);
            builder.show();

        }


        if (id == R.id.calender) {

            Intent  intent=new Intent(Attendance.this,Search.class);
            startActivity(intent);
        }
        if (id == R.id.scl_calender) {

            Intent  intent=new Intent(Attendance.this,Calender.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        asksave(view);
    }

    public void prepareSelection(View v, int adapterPosition) {
    }
    public  void json_parsing()
    {
        AssetManager am=getAssets();
        InputStream is = null;
        try {
            is = am.open("students.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String st="";
        StringBuilder sb=new StringBuilder();
        try {
            while((st=br.readLine())!=null)
            {
                sb.append(st);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Toast.makeText(Attendance.this, sb.toString(), Toast.LENGTH_SHORT).show();
        try {
            jsonObject = new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonArray = jsonObject.getJSONArray("student_details");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        n=jsonArray.length();
        Name=new String[n];
        Roll=new String[n];

        int i;
        while (count < jsonArray.length()) {
            JSONObject JO = null;
            try {
                JO = jsonArray.getJSONObject(count);
                if(JO.getString("id")!=null)
                {
                    Roll[count]=JO.getString("id");
                    Name[count] = JO.getString("name");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            count++;
        }
    }




}
