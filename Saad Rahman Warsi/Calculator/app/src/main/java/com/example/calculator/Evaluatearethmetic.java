package com.example.calculator;

public class Evaluatearethmetic {
    public static double evaluate(String s)
    {
        double[] oprends;
        char[] opretor;
        oprends=new double[s.length()];
        opretor=new char[s.length()];
        boolean intoten;
        intoten=true;
        double opr;
        opr=0.0;
        int oprendint=0;
        int opretorint=0;
        int intotenfalse=1;
        for (int i=0;i<=s.length()-1;i++)
        {
            if (i==s.length()-1)
            {
                if (intoten==true)
                {
                    opr=opr*10.0+(int)(s.charAt(i))-48.0;
                }
                else
                {
                    opr=opr+((int)(s.charAt(i))-48.0)*Math.pow(10.0,(-1.0*intotenfalse));
                    intotenfalse++;
                }
                oprends[oprendint]=opr;
            }
            else if (s.charAt(i)=='+' || s.charAt(i)=='-' || s.charAt(i)=='X' || s.charAt(i)=='/')
            {
                intoten=true;
                intotenfalse=1;
                oprends[oprendint]=opr;
                opretor[opretorint]=s.charAt(i);
                oprendint++;
                opretorint++;
                opr=0.0;
            }
            else if (s.charAt(i)=='.')
            {
                intoten=false;
                intotenfalse=1;
            }
            else
            {
                if (intoten==true)
                {
                    opr=opr*10.0+(int)(s.charAt(i))-48.0;
                }
                else
                {
                    opr=opr+((int)(s.charAt(i))-48.0)*Math.pow(10.0,(-1.0*intotenfalse));
                    intotenfalse++;
                }
            }
        }
        for (int i=0;i<=s.length()-1;i++)
        {
            System.out.println(oprends[i]);
        }
        for (int i=0;i<=s.length()-1;i++)
        {
            System.out.println(opretor[i]);
        }
        for (int i=0;i<=s.length()-2;i++)
        {
            System.out.println(i);
            if (opretor[i]=='X'||opretor[i]=='/')
            {
                if (opretor[i]=='X')
                {
                    //System.out.println("hi");
                    oprends[i]=oprends[i]*oprends[i+1];
                }
                else
                {
                    oprends[i]=oprends[i]/oprends[i+1];
                }
                for (int j=i+2;j<=s.length()-1;j++)
                {
                    oprends[j-1]=oprends[j];

                }
                for (int j=i+1;j<=s.length()-1;j++)
                {

                    opretor[j-1]=opretor[j];

                }
                i=-1;
            }

        }
        for (int i=0;i<=s.length()-1;i++)
        {
            System.out.println(oprends[i]);
        }
        for (int i=0;i<=s.length()-1;i++)
        {
            System.out.println(opretor[i]);
        }
        double ans;
        ans=oprends[0];
        for (int i=0;i<=s.length()-2;i++)
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
