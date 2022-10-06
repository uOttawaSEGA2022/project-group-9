package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView exp;
    private TextView result;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b0;
    private Button eval;
    private Button dec;
    private Button add;
    private Button sub;
    private Button mul;
    private Button div;
    private String expression;
    private int evalstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expression="";
        evalstate=0;
        exp=(TextView) findViewById(R.id.expression);
        result=(TextView) findViewById(R.id.result);
        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.b2);
        b3=(Button) findViewById(R.id.b3);
        b4=(Button) findViewById(R.id.b4);
        b5=(Button) findViewById(R.id.b5);
        b6=(Button) findViewById(R.id.b6);
        b7=(Button) findViewById(R.id.b7);
        b8=(Button) findViewById(R.id.b8);
        b9=(Button) findViewById(R.id.b9);
        b0=(Button) findViewById(R.id.b0);
        eval=(Button) findViewById(R.id.eval);
        dec=(Button) findViewById(R.id.dec);
        add=(Button) findViewById(R.id.add);
        sub=(Button) findViewById(R.id.sub);
        mul=(Button) findViewById(R.id.mul);
        div=(Button) findViewById(R.id.div);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"1";
                exp.setText(expression);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"2";
                exp.setText(expression);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"3";
                exp.setText(expression);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"4";
                exp.setText(expression);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"5";
                exp.setText(expression);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"6";
                exp.setText(expression);
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"7";
                exp.setText(expression);
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"8";
                exp.setText(expression);
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"9";
                exp.setText(expression);
            }
        });
        b0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"0";
                exp.setText(expression);
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+".";
                exp.setText(expression);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"+";
                exp.setText(expression);
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"-";
                exp.setText(expression);
            }
        });
        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"X";
                exp.setText(expression);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expression=expression+"/";
                exp.setText(expression);
            }
        });
        eval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (evalstate==0) {
                    double r;
                    r = Evaluatearethmetic.evaluate(expression);
                    String rs;
                    rs = Double.toString(r);
                    result.setText(rs);
                    eval.setText("AC");
                    evalstate=1;
                }
                else
                {
                    expression="";
                    result.setText("RESULT");
                    exp.setText("EXPRESSION");
                    eval.setText("=");
                    evalstate=0;
                }
            }
        });
    }
}