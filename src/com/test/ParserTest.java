package com.test;

import com.hua.LR0table;
import com.hua.Parser;
import com.together.Grammar;

public class ParserTest {

    /**
     * 测试LR分析表程序,以ppt112页为例子
     * @param args
     */
    public static void main(String[] args) {
        //LR分析表第一行
        char[] row=new char[]{'i', '+', '*', '（', '）', '#', 'E', 'T', 'F'};
        int[] status=new int[12];//状态
        for(int i=0;i<status.length;i++)
            status[i]=i;
        //LR分析表测试
        String[][] t=new String[12][];
        t[0]=new String[]{"S5",   "",     "",     "S4",     "",     "",     "1",     "2",     "3",};
        t[1]=new String[]{"",   "S6",     "",     "",     "",     "acc",     "",     "",     "",};
        t[2]=new String[]{"",   "R2",     "S7",     "",     "R2",     "R2",     "",     "",     "",};
        t[3]=new String[]{"",   "R4",     "R4",     "",     "R4",     "",     "",     "",     "",};
        t[4]=new String[]{"S5",   "",     "",     "S4",     "",     "R4",     "8",     "2",     "3",};
        t[5]=new String[]{"",   "R6",     "R6",     "",     "R6",     "R6",     "",     "",     "",};
        t[6]=new String[]{"S5",   "S4",     "",     "",     "",     "",     "",     "9",     "3",};
        t[7]=new String[]{"S5",   "",     "",     "S4",     "",     "",     "",     "",     "10",};
        t[8]=new String[]{"",   "S6",     "",     "",     "S11",     "",     "",     "",     "",};
        t[9]=new String[]{"",   "R1",     "S7",     "",     "R1",     "R1",     "",     "",     "",};
        t[10]=new String[]{"",   "R3",     "R3",     "",     "R3",     "R3",     "",     "",     "",};
        t[11]=new String[]{"",   "R5",     "R5",     "",     "R5",     "R5",     "",     "",     "",};
        String[] prodcutions=new String[]{"E->E+T","E->T","T->T*F","T->F","F->(E)","F->i"};
        Grammar grammar=new Grammar(new char[]{'i', '+', '*', '（', '）'}, new char[]{'E', 'T', 'F'}, prodcutions);//输入的文法
        LR0table table=new LR0table(row,status,t,grammar);

        //分析
        new Parser().parse(table, "i+i*i#");
    }
}
