package com.test;

import com.bin.LR0Pretreat;
import com.hua.GrammarToTableUtils;
import com.hua.LR0table;
import com.hua.Parser;
import com.together.Grammar;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrammarToTable {
    public static void main(String[] args) {
        //综合测试
        char[] Vt = new char[]{'a', 'b', 'c', 'd'};//终结符
        char[] Vn = new char[]{'E', 'A', 'B'};//非终结符
        String[] strs = new String[]{"E->aA|bB", "A->cA|d", "B->cB|d"};//产生式


        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);

        //文法
        Grammar grammar=new Grammar(Vt, Vn, strs);

        LR0table table=new LR0table(pretreat,grammar);
        //根据LR分析表对输入串进行分析
        Parser parser=new Parser();



        /**         输出      */
        //输出拓广文法
        List<String> list = pretreat.geteFormula();
        System.out.println("拓广文法：\n" + list);

        //输出状态集编号
        List<Integer> status = pretreat.getStatus();
        System.out.println("状态集编号：\n" +status);

        //输出状态集
        Map<Integer, Set<String>> statusSet = pretreat.getStatusSet();
        System.out.println("状态集：\n" +statusSet);

        //状态转换表
        System.out.println("\n状态转换表：");
        pretreat.showResult();

        //LR分析表
        table.showTable();

        //分析程序开始
        System.out.println("------------------LR0分析程序------------------");
        parser.parse(table, "acd#");//input输入串
    }

}
