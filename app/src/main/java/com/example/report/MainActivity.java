package com.example.report;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button verify=(Button)findViewById(R.id.button);
        final TextView mob,otp;
        mob=(TextView)findViewById(R.id.mob);
        otp=(TextView)findViewById(R.id.otp);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            if((mob.getText().toString()).equals("7812012012")&&(otp.getText().toString()).equals("3535")) {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Calendar calendar = Calendar.getInstance();
                                calendar.add(Calendar.DATE, 0);
                                Intent intent = new Intent(MainActivity.this, Attendance.class);
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                                String date=simpleDateFormat.format(calendar.getTime());
                                intent.putExtra("date",date);
                                startActivity(intent);
                            }
                            else if((mob.getText().toString()).length()==0||(otp.getText().toString()).length()==0)
                            {
                                Snackbar.make(view, "Fields should not be empty", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }else
                            {
                                Snackbar.make(view, "Enter valid credentials", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Do you want Exit");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(MainActivity.this, "Thank you", Toast.LENGTH_LONG).show();
                finishAffinity();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public  void t_and_c(View view)
    {
        TextView verify=(TextView)findViewById(R.id.textView3);
        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainActivity.this, T_C.class);
                        startActivity(intent);
                    }
                }
        );
    }

}
