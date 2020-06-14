package com;

import com.bin.LR0Pretreat;
import com.bin.Utils;
import com.hua.GrammarToTableUtils;
import com.hua.LR0table;
import com.hua.Parser;
import com.together.Grammar;
import sun.nio.ch.Util;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 主程序
 */
public class Enter {

    public static void main(String[] args) {
        /**
         * 第一步，创建文法
         */
        char[] Vt = new char[]{'a', 'b', 'c', 'd'};//终结符
        char[] Vn = new char[]{'E', 'A', 'B'};//非终结符
        String[] strs = new String[]{"E->aA|bB", "A->cA|d", "B->cB|d"};//产生式


        //文法
        Grammar grammar=new Grammar(Vt, Vn, strs);

        /**
         * 第二步，创建拓广文法、项目集、闭包等
         */
        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);
        //显示数据，包括拓广文法、项目集等
        Utils.show(pretreat);
        //判断是否存在二义性
        if(Utils.isAmbiguous(pretreat)){
            System.err.println("存在二义性，错误");
            return ;
        }

        /**
         * 第三步，创建LR分析表
         */
        LR0table table=new LR0table(pretreat,grammar);
        //LR分析表
        table.showTable();

        /**
         * 第四步，根据LR分析表对输入串进行分析
         */
        //根据LR分析表对输入串进行分析
        Parser parser=new Parser();
        //分析程序开始
        System.out.println("------------------LR0分析程序------------------");
        parser.parse(table, "acd#");//input输入串
    }
}

