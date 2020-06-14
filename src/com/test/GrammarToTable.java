package com.test;

import com.bin.LR0Pretreat;
import com.hua.GrammarToTableUtils;
import com.hua.LR0table;
import com.hua.Parser;
import com.together.Grammar;

import java.util.Arrays;
import java.util.List;

public class GrammarToTable {
    public static void main(String[] args) {
        //综合测试
        char[] Vt = new char[]{'a', 'b', 'c', 'd'};
        char[] Vn = new char[]{'E', 'A', 'B'};
        String[] strs = new String[]{"E->aA|bB", "A->cA|d", "B->cB|d"};
        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);


        //文法
        Grammar grammar=new Grammar(Vt, Vn, strs);
        //获得分析表的第一行,包括了#
        char[] rows=GrammarToTableUtils.changeSymbol(grammar);
        //获得分析表的第一列
        int[] cols=GrammarToTableUtils.listToArray(pretreat.getStatus());
        //获得分析表的内容
        String[][] t = GrammarToTableUtils.preTreatToTable(pretreat,grammar,rows);
        //完整的分析表
        grammar.setProduction(pretreat.geteFormula().toArray(new String[pretreat.geteFormula().size()]));//将文法的产生式改成拓广文法
        LR0table table=new LR0table(rows,cols, t,grammar);
        //根据LR分析表对输入串进行分析
        Parser parser=new Parser();
        parser.parse(table, "");
    }
}
