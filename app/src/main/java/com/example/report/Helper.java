package com.example.report;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

    public static final String database="attendance";
    public static final String table="attend";
    public static final String col_1="roll";
    public static final String col_2="name";
    public static final String col_3="status";
    public static final String col_4="date";




    public Helper(Context context) {
        super(context, database, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table " + table + "( roll TEXT,name TEXT,status TEXT,date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table);
        onCreate(db);
    }
    public boolean insert(String roll,String name,String status,String date)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(col_1,roll);
        contentValues.put(col_2,name);
        contentValues.put(col_3,status);
        contentValues.put(col_4,date);
        Cursor res1=db.rawQuery("select * from " + table + " where date='" + date + "'", null);
        long res = 0;

        if(res1.getCount()==0)
        {
             res=  db.insert(table,null,contentValues);
        }else
        {
           db.delete(table,"date='"+date+"' and roll='"+roll+"'",null);
            res=  db.insert(table,null,contentValues);
        }

        if (res==-1)
        {
            return  false;
        }else
        {
            return true;
        }

    }
   public Cursor getdata(String date,String status)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res=db.rawQuery("select distinct * from " + table +" where "+col_3+" ='"+status+"' and date='"+date+"'", null);
        return  res;
    }
    public Cursor getdata_count(String date,String date1,String status,String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res=db.rawQuery("select distinct * from " + table + "" +
                " where " + col_3 + " ='" + status + "' and date between '" + date + "' and '" + date1 + "' and name='"+name+"'", null);
        return  res;
    }
    public Cursor date()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select distinct date from " + table +"", null);
        return  res;
    }
    public Cursor date1(String status)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select distinct * from " + table +" where "+col_3+" ='"+status+"'", null);
        return  res;
    }
}
