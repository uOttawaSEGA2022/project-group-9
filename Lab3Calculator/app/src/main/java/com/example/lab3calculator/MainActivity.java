package com.example.lab3calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button btn00 = findViewById(R.id.btn00);
        Button btn01 = findViewById(R.id.btn01);
        Button btn02 = findViewById(R.id.btn02);
        Button btn03 = findViewById(R.id.btn03);
        Button btn04 = findViewById(R.id.btn04);
        Button btn05 = findViewById(R.id.btn05);
        Button btn06 = findViewById(R.id.btn06);
        Button btn07 = findViewById(R.id.btn07);
        Button btn08 = findViewById(R.id.btn08);
        Button btn09 = findViewById(R.id.btn09);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnDivide = findViewById(R.id.btnDivide);
        Button btnMultiply = findViewById(R.id.btnMultiply);

        Button btnClear = findViewById(R.id.btnClear);
        Button btnDot = findViewById(R.id.btnDot);

        Button btnEqual = findViewById(R.id.btnEqual);

    }





    public void btn00click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s0", currentExpression));
    }



    public void btn01click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s1", currentExpression));
    }

    public void btn02click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s2", currentExpression));
    }

    public void btn03click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s3", currentExpression));
    }

    public void btn04click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s4", currentExpression));
    }

    public void btn05click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s5", currentExpression));
    }

    public void btn06click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s6", currentExpression));
    }

    public void btn07click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s7", currentExpression));
    }

    public void btn08click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s8", currentExpression));
    }

    public void btn09click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s9", currentExpression));
    }

    public void btn_minus_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s-", currentExpression));
    }

    public void btn_add_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s+", currentExpression));
    }

    public void btn_divide_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s÷", currentExpression));
    }

    public void btn_multiply_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s×", currentExpression));
    }

    public void btn_clear_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        displayExpression.setText("");
    }

    public void btn_dot_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());
        displayExpression.setText(String.format("%s.", currentExpression));
    }

    //Added for the Logic
    public void invalid_expression() {
        TextView displayExpression = findViewById(R.id.displayExpression);
        displayExpression.setText("Invalid Expression");

    }

    public void btn_equal_click(View view){
        TextView displayExpression = findViewById(R.id.displayExpression);
        String currentExpression = String.valueOf(displayExpression.getText());

        String[] splitExpression = currentExpression.split(" ");
        String[][] operators_priority = {{"*","/"},{"+","-"}};
        Integer result = null;

        for (int i = 0;i < 2; i++) {
            int count = 0;
            while (count< splitExpression.length) {
                for (int j = 0; j < 2; j++) {
                    if (splitExpression[count] == operators_priority[i][j]) {
                        try {
                        String[] operands = {splitExpression[count -1],splitExpression[count + 1]};
                        if (operators_priority[i][j] == "*") {
                            result = Integer.valueOf(operands[0]) * Integer.valueOf(operands[1]);
                        }

                        if (operators_priority[i][j] == "/") {
                            result = Integer.valueOf(operands[0]) / Integer.valueOf(operands[1]);
                        }

                        if (operators_priority[i][j] == "+") {
                            result = Integer.valueOf(operands[0]) + Integer.valueOf(operands[1]);

                        }

                        if (operators_priority[i][j] == "-") {
                            result = Integer.valueOf(operands[0]) - Integer.valueOf(operands[1]);
                        }

                        //Bro you got to make a new array without the element u want to remove
                        splitExpression.remove(count-1);

                        count -= 1;
                        splitExpression[count] = String.valueOf(result);
                        splitExpression.remove(count+1);
                        count -= 1;
                        }

                        catch (Exception e){
                            invalid_expression();
                        return;
                        }
                    }
                }


            }


        }



    }
}