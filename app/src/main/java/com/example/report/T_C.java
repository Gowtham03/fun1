package com.example.report;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

public class T_C extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t__c);

        WebView webView=(WebView)findViewById(R.id.webView2);
        webView.loadUrl("https://www.reportbee.com/mobile_terms_and_conditions");
        Button agree=(Button)findViewById(R.id.button3);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        agree.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(T_C.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
