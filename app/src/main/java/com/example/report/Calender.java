package com.example.report;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Calender extends AppCompatActivity implements OnChartValueSelectedListener {
    SimpleDateFormat simpleDateFormat;
    CompactCalendarView compactCalendarView;
    Helper mydb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String arr;
        int i=0;

        compactCalendarView=(CompactCalendarView)findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-M-d");
        mydb= new Helper(Calender.this);
        Cursor res= mydb.date();
        while (res.moveToNext()) {
                arr=res.getString(0);
                Date d= null;
                try {
                    d = df.parse(arr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long mili=d.getTime();
            Event e=new Event(Color.RED,mili,"Absent");
            compactCalendarView.addEvent(e);
        }

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        TextView text=(TextView)findViewById(R.id.textView4);
        text.setText(df.format(cal.getTime()));



        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy-EEEE");
                SimpleDateFormat df1=new SimpleDateFormat("yyyy-M-d");
                Cursor res= null,res1= null,res2= null;

                String date="";
                String date1="";

                date1= (dateClicked.getYear())+1900+"-"+(1+dateClicked.getMonth())+"-"+dateClicked.getDate();
                date=date1;
                res = mydb.getdata(date,"Absent");
                res1= mydb.getdata(date, "Present");
                res2=mydb.getdata(date, "Half_day");
                if(res.getCount()==0&&res1.getCount()==0&&res2.getCount()==0)
                {
                    pieChart(0,20,0, date1);
                }else
                {
                    pieChart(res.getCount(),res1.getCount(),res2.getCount(), date1);
                }

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                TextView text=(TextView)findViewById(R.id.textView4);
                SimpleDateFormat df=new SimpleDateFormat("dd-MM-yyyy");
                text.setText(df.format(firstDayOfNewMonth.getTime()));
            }
        });
    }
    public void pieChart(int abs,int pre,int half,String date) {
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        yvalues.add( new PieEntry(abs, 0));
        yvalues.add(new PieEntry(pre, 1));
        yvalues.add(new PieEntry(half, 2));

        PieDataSet dataSet = new PieDataSet(yvalues, "Attendance of "+date);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Present");
        xVals.add("Absent");
        xVals.add("Half_day");



        PieChart pieChart = (PieChart) findViewById(R.id.pieChart1);
        pieChart.setUsePercentValues(true);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(34f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(date);

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(Calender.this);

        pieChart.animateXY(1400, 1400);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
