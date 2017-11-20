package com.example.report;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Search extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String name[];    String[] id;
    int i=0;
    Helper mydb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button search=(Button)findViewById(R.id.search);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerview_new);
        layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        try {
            json_paring();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final EditText start=(EditText)findViewById(R.id.start_date);
        final EditText end=(EditText)findViewById(R.id.end_date);
        final SimpleDateFormat df=new SimpleDateFormat("yyyy-M-d");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Contact_new> contacts=new ArrayList<>();
                mydb= new Helper(Search.this);
                String start_date=start.getText().toString();
                String end_date=end.getText().toString();

                for(int m=0;m<20;m++)
                {
                        Cursor res= mydb.getdata_count(start_date,end_date, "Absent",name[m]);
                        Cursor res1= mydb.getdata_count(start_date,end_date,"Present",name[m]);
                        Cursor res2=  mydb.getdata_count(start_date,end_date,"Half_day",name[m]);
                        Contact_new contact=new Contact_new(name[m],id[m],
                                ""+res1.getCount(),""+res.getCount(),""+res2.getCount());
                    contacts.add(contact);
                }

                final ContactAdapter_new adapter=new ContactAdapter_new(contacts,Search.this);
                recyclerView.setAdapter(adapter);
                recyclerView=(RecyclerView)findViewById(R.id.recyclerview_new);
                layoutManager =new LinearLayoutManager(Search.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);

            }
        });
    }
    public  void json_paring() throws IOException, JSONException {
        JSONArray jsonArray;
        JSONObject jsonObject;
        int n,count=0;
        InputStream inputStream = null;
        AssetManager am=getAssets();
        inputStream=am.open("students.json");
        BufferedReader bf=new BufferedReader(new InputStreamReader(inputStream));
        String str=null;
        StringBuilder sb=new StringBuilder();
        while((str=bf.readLine())!=null)
        {
            sb.append(str);
        }


        jsonObject=new JSONObject(sb.toString());
        jsonArray =jsonObject.getJSONArray("student_details");
        n=jsonArray.length();
        name=new String[n];
        id=new String[n];

        while (count<n)
        {
            JSONObject Jo;
            Jo=jsonArray.getJSONObject(count);
            if(Jo!=null)
            {
                name[count]=Jo.getString("name");
                id[count]=Jo.getString("id");
            }
            count++;
        }

    }
}
