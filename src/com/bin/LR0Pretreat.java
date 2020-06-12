package com.bin;

import java.util.*;

public class LR0Pretreat {
    private char[] Vt;
    private char[] Vn;
    private String[] formula;
    private Map<Character, List<String>> newVn;

    @Override
    public String toString() {
        return "LR0Pretreat{" +
                "Vt=" + Arrays.toString(Vt) +
                ", Vn=" + Arrays.toString(Vn) +
                ", formula=" + Arrays.toString(formula) +
                ", newVn=" + newVn +
                '}';
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
//        char[] Vt = new char[]{'a', 'b'};
//        char[] Vn = new char[]{'S', 'B'};
//        String[] strs = new String[]{"S->BB","B->aB|b"};

        char[] Vt = new char[]{'a', 'b', 'c', 'd'};
        char[] Vn = new char[]{'B', 'E', 'A'};
        String[] strs = new String[]{"E->aA|bB", "A->cA|d", "B->cB|d"};
        LR0Pretreat pretreat = new LR0Pretreat(Vt, Vn, strs);
        pretreat.extensionGrammar();
        System.out.println(pretreat);
        Set<String> closure = pretreat.closure("S->·E");
        System.out.println(closure);

        List<String> list = new ArrayList<>();
        list.add("S->·E");
        list.add("E->·aA");
        list.add("E->·bB");
        Set<String> set = pretreat.e_closure(list);
        System.out.println(set);
    }

    /**
     * 构造函数
     */
    public LR0Pretreat(char[] Vt, char[] Vn, String[] formula){
        this.Vt = Vt;
        this.Vn = Vn;
        this.formula = formula;
    }

    /**
     * 获得拓广文法
     */
    public void extensionGrammar(){
        /*
           1. 替换文法开始符，增加一项
           2. 将所有的多个候选式分割为多项
           使用map来存，开始的候选式先用E代替
         */

        Map<Character, List<String>> eVn = new HashMap<>();
        List<String> list = null;

        //第一项
        list = new ArrayList<>();
        list.add("E");
        eVn.put('S', list);

        //获得其他项
        for (int i = 0; i < Vn.length; i++){
            list = new ArrayList<>();
            for (int j = 0; j < formula.length; j++){
                if (formula[j].charAt(0) == Vn[i]){
                    List<String> split = Utils.splitByChar(formula[j].substring(3), '|');
                    list.addAll(split);
                }
            }
            System.out.println(Vn[i] + "-->" + list);
            eVn.put(Vn[i], list);
        }
        this.newVn = eVn;

    }

    /**
     * 单一闭包
     */
    public Set<String> closure(String project){

        Set<String> e_closure = new HashSet<>();
        e_closure.add(project);
        int index = project.indexOf("·");
        if(index != project.length() - 1){
            char c = project.charAt(index + 1);
            if(!isVt(c)){
                List<String> firstP = firstProject(c);
                for (String str:firstP) {
                    e_closure.addAll(closure(str));
                }
            }
        }
        return e_closure;
    }

    /**
     * 获得闭包集
     * @param projects
     * @return
     */
    public Set<String> e_closure(List<String> projects){
        Set<String> e_closure = new HashSet<>();
        e_closure.addAll(projects);

        for(int i = 0; i < projects.size(); i++){
            int index = projects.get(i).indexOf("·");
            if(index != projects.get(i).length() - 1){
                char c = projects.get(i).charAt(index + 1);
                if(!isVt(c)){
                    List<String> firstP = firstProject(c);
                    e_closure.addAll(e_closure(firstP));
                }
            }
        }

        return e_closure;
    }

    /**
     * 返回第一个项目
     * @param c
     * @return
     */
    private List<String> firstProject(char c) {
        List<String> list = newVn.get(c);
        List<String> firstP = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            String s = c + "->·" + list.get(i);
            firstP.add(s);
        }
        return firstP;
    }

    /**
     * 判断是否为终结符
     * @param c
     * @return
     */
    private boolean isVt(char c) {
        for(char vt : Vt){
            if (vt == c){
                return true;
            }
        }
        return false;
    }

    /**
     * Go状态转换
     */
    public void go(){

    }

    public void init(){

    }

}
