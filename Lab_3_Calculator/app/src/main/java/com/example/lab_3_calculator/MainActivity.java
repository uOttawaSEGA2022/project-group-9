package com.example.lab_3_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn00 = (Button) findViewById(R.id.);
        Button btn01 = (Button) findViewById(R.id.);
        Button btn02 = (Button) findViewById(R.id.);
        Button btn03 = (Button) findViewById(R.id.);
        Button btn04 = (Button) findViewById(R.id.);
        Button btn05 = (Button) findViewById(R.id.);
        Button btn06 = (Button) findViewById(R.id.);
        Button btn07 = (Button) findViewById(R.id.);
        Button btn08 = (Button) findViewById(R.id.);
        Button btn09 = (Button) findViewById(R.id.);
        Button btnEQ = (Button) findViewById(R.id.);
        Button btnClear = (Button) findViewById(R.id.);
        Button btnAdd = (Button) findViewById(R.id.);
        Button btnMinus = (Button) findViewById(R.id.);
        Button btnMultiply = (Button) findViewById(R.id.);
        Button btnDivide = (Button) findViewById(R.id.);
        Button btnResult = (Button) findViewById(R.id.);
        Button btnDot = (Button) findViewById(R.id.);
    }
}