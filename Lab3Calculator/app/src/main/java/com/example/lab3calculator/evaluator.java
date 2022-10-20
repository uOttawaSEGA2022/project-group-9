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

            // For each operator, we perform the same operation using if statements
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
        // In the case operator 1 or operator 2 is not an integer, throw an exception, which is caught
        // by the evaluate method
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


        // For loop going through the expression twice, to evaluate multiplication and division first
        // Then addition and subtraction
        for (int i = 0; i < 2; i++){
            // Count variable to keep track of the current index in the expression
            int count = 0;
            // While loop going through the expression
            while (count < expression.size()){
                // Contains element checks if the current index in the expression is in the operators list
                // Checks for multiplication and division only first, then addition and subtraction only
                if (containsElement(expression.get(count), operatorsPriority[i])){
                    // Tries to evaluate the expression
                    try{
                        // Defines the operands of the operator found at index count, in a valid expression
                        // the operands would be at indices count-1 and count+1
                        String[] operands = {expression.get(count - 1), expression.get(count + 1)};
                        // Uses evaluate expression method to evaluate the expression:
                        // (operand1, operator, operand2)
                        result = evaluateOperation(operands, expression.get(count));
                        // We remove both operands while making sure count is still accurate
                        expression.remove(count - 1);
                        count --;
                        // We add the result of the mini-operation to the expression list,
                        // to continue evaluating more expressions with the result we got
                        expression.set(count, result);
                        expression.remove(count + 1);
                        count --;

                    }
                    // Catch any exception in the case that we have two operators right after each other
                    catch(Exception e){
                        return "Invalid Expression";
                    }
                }
                // Increase the count because we're done with the current index
                count ++;
            }
        }

        // After the algorithm runs, one double should be left, we transform that into a string and return
        String returnElement = expression.toString();
        returnElement = returnElement.substring(1, returnElement.length() - 1);

        return returnElement;
    }
}
