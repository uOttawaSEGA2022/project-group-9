package com.example.lab3calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    Button btn00 = (Button) findViewById(R.id.btn00);
    Button btn01 = (Button) findViewById(R.id.btn01);
    Button btn02 = (Button) findViewById(R.id.btn02);
    Button btn03 = (Button) findViewById(R.id.btn03);
    Button btn04 = (Button) findViewById(R.id.btn04);
    Button btn05 = (Button) findViewById(R.id.btn05);
    Button btn06 = (Button) findViewById(R.id.btn06);
    Button btn07 = (Button) findViewById(R.id.btn07);
    Button btn08 = (Button) findViewById(R.id.btn08);
    Button btn09 = (Button) findViewById(R.id.btn09);

    Button btnAdd = (Button) findViewById(R.id.btnAdd);
    Button btnMinus = (Button) findViewById(R.id.btnMinus);
    Button btnDivide = (Button) findViewById(R.id.btnDivide);
    Button btnMultiply = (Button) findViewById(R.id.btnMultiply);

    Button btnClear = (Button) findViewById(R.id.btnClear);
    Button btnDot = (Button) findViewById(R.id.btnDot);

    Button btnEqual = (Button) findViewById(R.id.btnEqual);


    public void btn00click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s0", currentExpression));
    }

    public void btn01click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s1", currentExpression));
    }

    public void btn02click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s2", currentExpression));
    }

    public void btn03click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s3", currentExpression));
    }

    public void btn04click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s4", currentExpression));
    }

    public void btn05click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s5", currentExpression));
    }

    public void btn06click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s6", currentExpression));
    }

    public void btn07click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s7", currentExpression));
    }

    public void btn08click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s8", currentExpression));
    }

    public void btn09click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s9", currentExpression));
    }

    public void btn_minus_click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s-", currentExpression));
    }

    public void btn_add_click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s+", currentExpression));
    }

    public void btn_divide_click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s÷", currentExpression));
    }

    public void btn_multiply_click(){
        EditText displayExpression = (EditText) findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s×", currentExpression));
    }



    }