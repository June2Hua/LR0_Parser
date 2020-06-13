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
        //早期测试
//        char[] Vt = new char[]{'a', 'b'};
//        char[] Vn = new char[]{'E', 'B'};
//        String[] strs = new String[]{"E->BB","B->aB","B->b"};

//        char[] Vt = new char[]{'a', 'b', 'c', 'd'};
//        char[] Vn = new char[]{'E', 'A', 'B'};
//        String[] strs = new String[]{"E->aA|bB", "A->cA|d", "B->cB|d"};
//        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);

        //测试拓广文法
//        pretreat.extensionGrammar();
//        System.out.println(pretreat);

        //测试单闭包
//        Set<String> closure = pretreat.closure("S->·E");
//        System.out.println(closure);

        //测试闭包集
//        List<String> list = new ArrayList<>();
//        list.add("S->·E");
//        list.add("E->·aA");
//        list.add("E->·bB");
//        Set<String> set = pretreat.e_closure(list);
//        System.out.println(set);

//        pretreat.go();
//        pretreat.showDFA();



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

        //输出拓广文法
        List<String> list = pretreat.geteFormula();
        System.out.println("拓广文法：\n" + list);

        //输出状态集编号
        List<Integer> status = pretreat.getStatus();
        System.out.println("状态集编号：\n" +status);

        //输出状态集
        Map<Integer, Set<String>> statusSet = pretreat.getStatusSet();
        System.out.println("状态集：\n" +statusSet);

        //测试下一状态
        System.out.println(pretreat.nextStatus(0, 'a'));
        System.out.println(pretreat.nextStatus(0, 'b'));
        System.out.println(pretreat.nextStatus(0, 'E'));
        System.out.println(pretreat.nextStatus(0, 'B'));
        System.out.println(pretreat.nextStatus(0, 'c'));

        System.out.println(pretreat.nextStatus(1, 'b'));




    }
}
