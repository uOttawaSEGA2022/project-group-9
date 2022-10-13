package com.example.lab3calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class evaluator{

    String expression;

    public evaluator(String expression){
        this.expression = expression;
    }

    public static String evaluateOperation(String[] stringOperands, String operator){
        try{
            double result = 0;
            double operand1 = Double.parseDouble(stringOperands[0]);
            double operand2 = Double.parseDouble(stringOperands[1]);
            if (operator.equals("+")){
                result = operand1 + operand2;
            }

            else if (operator.equals("-")){
                result = operand1 - operand2;
            }

            else if (operator.equals("×")){
                result = operand1 * operand2;
            }

            else if (operator.equals("÷")){
                result = operand1 / operand2;
            }
            return String.valueOf(result);
        }
        catch(Exception e){
            throw new IllegalArgumentException();
        }
    }

    public static boolean containsElement(String element, String[] listStrings){
        for (String elementInList: listStrings){
            if (elementInList.equals(element)){
                return true;
            }
        }
        return false;
    }

    public String evaluate(){
        String result = "";

        String[][] operatorsPriority = {{"×", "÷"}, {"+", "-"}};

        String[] listExpression = this.expression.split(" ");

        List<String> expression = new ArrayList<String>(Arrays.asList(listExpression));



        for (int i = 0; i < 2; i++){
            int count = 0;
            while (count < expression.size()){
                if (containsElement(expression.get(count), operatorsPriority[i])){
                    try{
                        String[] operands = {expression.get(count - 1), expression.get(count + 1)};
                        result = evaluateOperation(operands, expression.get(count));
                        expression.remove(count - 1);
                        count --;
                        expression.set(count, result);
                        expression.remove(count + 1);
                        count --;

                        System.out.println(Arrays.toString(operands));

                    }
                    catch(Exception e){
                        return "Invalid Expression";
                    }
                }
                count ++;
            }
        }

        String returnElement = expression.toString();
        returnElement = returnElement.substring(1, returnElement.length() - 1);

        return returnElement;
    }
}
