package com.test;

import com.bin.LR0Pretreat;
import com.hua.LR0table;
import com.together.Grammar;

public class PretreatTest {

    public static void main(String[] args) {
        String[] prodcutions=new String[]{"E->E+T","E->T","T->T*F","T->F","F->(E)","F->i"};
        Grammar grammar=new Grammar(new char[]{'i', '+', '*', '（', '）'}, new char[]{'E', 'T', 'F'}, prodcutions);//输入的文法
        LR0Pretreat pretreat=new LR0Pretreat(grammar.getVt(), grammar.getVn(), grammar.getProduction());
    }
}
