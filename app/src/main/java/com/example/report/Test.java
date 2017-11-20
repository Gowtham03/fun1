package com.example.report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test extends AppCompatActivity {

    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        calendarView=(CalendarView)findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        long start=calendar.getTimeInMillis();
        selected30Dates(start);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                String date=(year)+"-"+(month+1)+"-"+(dayOfMonth);
                Toast.makeText(Test.this, date, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Test.this,Attendance.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }

    private void selected30Dates(long start) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start);
        calendar.add(Calendar.DATE, -30);
        long end = calendar.getTimeInMillis();
        calendarView=(CalendarView)findViewById(R.id.calendarView);
        calendarView.setMaxDate(start);
        calendarView.setMinDate(end);
    }
}
