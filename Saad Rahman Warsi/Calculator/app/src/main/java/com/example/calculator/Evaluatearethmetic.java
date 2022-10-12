package com.example.calculator;

import android.util.Log;

public class Evaluatearethmetic {
    public static double evaluate(String s)
    {
        double[] oprends;
        char[] opretor;
        oprends=new double[s.length()]; //max length needed for the oprends
        opretor=new char[s.length()]; // max length needed for the opretor
        boolean intoten; //Solve the 45+62 problem (45 = 40 +5)
        intoten=true; //intoten var = true means num 0-9
        double opr;
        opr=0.0;  //current number working with
        int oprendint=0;  //index for the oprend
        int opretorint=0;  //index for the opretor
        int intotenfalse=1;  // provides a num to tell which power of ten is needed
        for (int i=0;i<=s.length()-1;i++) //For every character in the string
        {
            if (i==s.length()-1) //what?, for last character only?
            {
               if (intoten==true) //For single digit numbers
                {
                    Log.i("Unique Tag", String.valueOf(opr));
                    opr=opr*10.0+(int)(s.charAt(i))-48.0; //Converts Char 4 into int 4,
                    Log.i("Unique Tag", String.valueOf(opr));
                }
                else // for numbers 10+
                {
                    Log.i("Unique Tag", String.valueOf(opr));
                    opr=opr+((int)(s.charAt(i))-48.0)*Math.pow(10.0,(-1.0*intotenfalse)); //what?
                    Log.i("Unique Tag", String.valueOf(opr));
                    intotenfalse++; //if 40, isn't enough, will increase the power by 1, 40 -> 400 if needed
                }
                oprends[oprendint]=opr; //adds num to array
            }
            else if (s.charAt(i)=='+' || s.charAt(i)=='-' || s.charAt(i)=='X' || s.charAt(i)=='/') //handles operators
            {
                intoten=true; //resetting variables
                intotenfalse=1;  //what is going on with the var?
                oprends[oprendint]=opr; //adds num to oprends array
                opretor[opretorint]=s.charAt(i); //adds opretor to opreter array
                oprendint++; //moves index in array for next element
                opretorint++;
                opr=0.0;
            } //handles decimals, resets some variables
            else if (s.charAt(i)=='.')
            {
                intoten=false;
                intotenfalse=1;
            }
            else //For the non operator, non decimal, non last character
            {
                if (intoten==true) //for num 0-9
                {
                    opr=opr*10.0+(int)(s.charAt(i))-48.0;
                }
                else
                {   //for num 10+
                    opr=opr+((int)(s.charAt(i))-48.0)*Math.pow(10.0,(-1.0*intotenfalse));
                    intotenfalse++;
                }
            }
        } //start of the print statements
        for (int i=0;i<=s.length()-1;i++)
        {
            Log.i("Unique ID", String.valueOf(oprends[i]));
        }
        for (int i=0;i<=s.length()-1;i++)
        {
            Log.i("Unique ID", String.valueOf(opretor[i]));
        } //end of the print statements
        for (int i=0;i<=s.length()-2;i++) //why s.length()-2
        {
            Log.i("Unique ID", String.valueOf(i));
            if (opretor[i]=='X'||opretor[i]=='/') //BEDMAS / PEDMAS
            {   //multiples 2 nums
                if (opretor[i]=='X')
                {
                    oprends[i]=oprends[i]*oprends[i+1];
                }
                else //divides 2 nums
                {
                    oprends[i]=oprends[i]/oprends[i+1];
                }
                for (int j=i+2;j<=s.length()-1;j++) //not too sure what these 2 for loops do
                {
                    oprends[j-1]=oprends[j];

                }
                for (int j=i+1;j<=s.length()-1;j++)
                {

                    opretor[j-1]=opretor[j];

                }
                i=-1;
            }

        } //start of print statements
        for (int i=0;i<=s.length()-1;i++)
        {
            System.out.println(oprends[i]);
        }
        for (int i=0;i<=s.length()-1;i++)
        {
            System.out.println(opretor[i]);
        } //end of print statements
        double ans;
        ans=oprends[0];
        for (int i=0;i<=s.length()-2;i++) //handles + and -
        {
            if (opretor[i]=='+')
            {
                ans=ans+oprends[i+1];
            }
            else
            {
                ans=ans-oprends[i+1];
            }
        }
        return ans;
    }

}
