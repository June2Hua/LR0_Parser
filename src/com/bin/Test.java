package com.bin;


import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        //综合测试1
//        char[] Vt = new char[]{'a', 'b'};
//        char[] Vn = new char[]{'E', 'B'};
//        String[] strs = new String[]{"E->BB","B->aB","B->b"};
//        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);

        //综合测试2
        char[] Vt = new char[]{'a', 'b', 'c', 'd'};
        char[] Vn = new char[]{'E', 'A', 'B'};
        String[] strs = new String[]{"E->aA|bB", "A->cA|d", "B->cB|d"};
        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);

        //综合测试3
//        char[] Vt = new char[]{'+', '*', '(', ')', 'i'};
//        char[] Vn = new char[]{'E', 'T', 'F'};
//        String[] strs = new String[]{"E->E+T", "E->T", "T->T*F", "T->F", "F->(E)", "F->i"};
//        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);

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

        //测试下一状态转换
        System.out.println(pretreat.nextStatus(0, 'a'));
        System.out.println(pretreat.nextStatus(0, 'b'));
        System.out.println(pretreat.nextStatus(0, 'E'));
        System.out.println(pretreat.nextStatus(0, 'B'));
        System.out.println(pretreat.nextStatus(0, 'c'));

        System.out.println(pretreat.nextStatus(1, 'b'));
    }
}
