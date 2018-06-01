package com.meitu.qihangni.autosizetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_test=findViewById(R.id.tv_test);

        AutoSizeTextView autoSizeTextView=findViewById(R.id.autosizetv_1);
        autoSizeTextView.setText("1");

    }
}
