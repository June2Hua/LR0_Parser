package com.bin;

import java.util.*;

public class LR0Pretreat {

    /**
     * 终结符，不包含#
     */
    private char[] Vt;

    /**
     * 非终结符，要求不要用S，要求文法开始符为E，下面构造拓广产生式要用
     */
    private char[] Vn;

    /**
     * 产生式文法
     */
    private String[] formula;

    /**
     * 拓广产生式
     */
    private List<String> eFormula;

    /**
     * 拓广文法的map,可通过产生式左部直接获得候选式
     */
    private Map<Character, List<String>> eGrammar;

    /**
     * DFA状态集编号，涉及顺序
     */
    private List<Integer> status = new ArrayList<>();   //状态

    /**
     * DFA状态集map，通过编号直接获得相应的项目集
     */
    private Map<Integer, Set<String>> statusSet = new HashMap<>();    //状态集


    @Override
    public String toString() {
        return "LR0Pretreat{" +
                "Vt=" + Arrays.toString(Vt) +
                ", Vn=" + Arrays.toString(Vn) +
                ", formula=" + Arrays.toString(formula) +
                ", eFormula=" + eFormula +
                ", eGrammar=" + eGrammar +
                '}';
    }

    /**
     * 构造函数
     * @param Vt    终结符集
     * @param Vn    非终结符集
     * @param formula   文法
     */
    public LR0Pretreat(char[] Vt, char[] Vn, String[] formula){

        //传入终结符和非终结符，文法
        this.Vt = Vt;
        this.Vn = Vn;
        this.formula = formula;

        //初始化，完成拓广文法和状态集生成
        init();
    }

    /**
     * 初始化，完成拓广文法的实现和状态集的构造
     */
    private void init(){
        extensionGrammar();
        go();
    }

    /**
     * 获得拓广文法
     */
    private void extensionGrammar(){
        /*
           1. 替换文法开始符，增加一项
           2. 将所有的多个候选式分割为多项
           使用map来存，开始的候选式先用E代替
         */

        Map<Character, List<String>> eGrammar = new HashMap<>();
        List<String> list;

        eFormula = new ArrayList<>();

        //获得拓广文法第一项
        list = new ArrayList<>();
        list.add(Vn[0] + "");
        eGrammar.put('S', list);
        eFormula.add("S->E");


        //获得其他项
        for (int i = 0; i < Vn.length; i++){
            list = new ArrayList<>();
            for (int j = 0; j < formula.length; j++){
                if (formula[j].charAt(0) == Vn[i]){
                    //分割字符
                    List<String> split = Utils.splitByChar(formula[j].substring(3), '|');
                    list.addAll(split);
                    for (String s : split){
                        eFormula.add(Vn[i] + "->" + s);
                    }
                }
            }
           // System.out.println(Vn[i] + "-->" + list);
            eGrammar.put(Vn[i], list);

        }
        this.eGrammar = eGrammar;
    }

    /**
     * 获得单一项目的闭包集
     * @param project   项目
     * @return
     */
    private Set<String> closure(String project){

        Set<String> e_closure = new HashSet<>();

        //将该项目加入闭包
        e_closure.add(project);
        int index = project.indexOf("·");
        if(index != project.length() - 1){
            char c = project.charAt(index + 1);     //获得·后的字符
            //该字符为非终结符时，递归获取产生式闭包集
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
     * 获得项目集的闭包集
     * @param projects
     * @return
     */
    private Set<String> e_closure(List<String> projects){
        Set<String> e_closure = new HashSet<>();

        //将该项目集加入闭包集
        e_closure.addAll(projects);

        //遍历每一个项目集
        for(int i = 0; i < projects.size(); i++){
            int index = projects.get(i).indexOf("·");
            if(index != projects.get(i).length() - 1){
                //获取·后的字符
                char c = projects.get(i).charAt(index + 1);
                if(!isVt(c)){
                    //字符为非终结符时，将该非终结符的所有第一个项目的闭包集加入其中
                    List<String> firstP = firstProject(c);
                    e_closure.addAll(e_closure(firstP));

                }
            }
        }

        return e_closure;
    }

    /**
     * 返回第一个项目
     * @param c 非终结符
     * @return
     */
    private List<String> firstProject(char c) {
        //获得非终结符的候选式
        List<String> list = eGrammar.get(c);
        List<String> firstP = new ArrayList<>();

        //获得所有候选式的第一个项目
        for (int i = 0; i < list.size(); i++){
            String s = c + "->·" + list.get(i);
            firstP.add(s);
        }
        return firstP;
    }

    /**
     * 判断是否为终结符
     * @param c 字符
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
     * 获得项目集的移进字符
     * @param projects 项目集
     * @return
     */
    private Set<Character> getNextChars(Set<String> projects){
        Set<Character> c = new HashSet<>();

        //获得所有·后面的字符，·在最后则返回null
        for (String str: projects) {
            int index = str.indexOf("·");
            if (index != str.length() - 1){
                c.add(str.charAt(index + 1));
            }
        }

        return c;
    }

    /**
     * 获得项目集关于某个移进字符的下一个项目的集合（即Go(x,a)先转换，但不求闭包）
     * @param projects  项目集
     * @param c 移进字符
     * @return
     */
    private Set<String> next(Set<String> projects, char c){
        Set<String> nextP = new HashSet<>();

        //遍历项目集
        for (String str: projects) {
            int index = str.indexOf("·");
            if (index != str.length() - 1){
                if (str.charAt(index + 1) == c){
                    //在符合移进字符的项目中将·后移，获得下一个项目
                    String temp = str.substring(0, index) + str.substring(index + 1, index + 2) + "·" + str.substring(index +2);
                    nextP.add(temp);
                }
            }
        }
        return nextP;
    }


    /**
     * Go状态转换,获得所有的状态集
     */
    private void go(){
        //初始化I0的项目集
        initStatus();

        //定义获得项目集能移进的字符
        Set<Character> nextChars;
        int num = 1;

        //遍历状态集
        for (int i = 0; i < statusSet.size(); i++){
            //获得所有移进字符
            nextChars = getNextChars(statusSet.get(i));

            //System.out.println(nextChars);

            //遍历移进字符
            for (char c : nextChars){
                //获得该状态集关于该字符的移进后的下一个项目组成的项目集
                Set<String> next = next(statusSet.get(i), c);
                List<String> list = new ArrayList<>();
                for (String str : next){
                    list.add(str);
                }
                //获得项目集的闭包集，组成下一个状态集
                Set<String> set = e_closure(list);
                if(!statusSet.containsValue(set)){
                    status.add(num);
                    statusSet.put(num++, set);
                }

                //System.out.println(c + "------>" + set);
            }
        }

        //System.out.println("status = " + status);
        //System.out.println("statusSet = " + statusSet);
    }

    /**
     * 初始化状态集，即构造I0
     */
    private void initStatus() {
        Set<String> I0 = closure("S->·E");
        status.add(0);
        statusSet.put(0, I0);
        //System.out.println("I0 = " + I0);
    }

    /**
     * 根据输入符号获得状态集的下一个状态
     * @param num 状态集编号
     * @param c 输入符号（终结符或非终结符）
     * @return
     */
    public int nextStatus(int num, char c){

        Set<String> status = statusSet.get(num);

        //获得该状态集的移进字符集
        Set<Character> nextChars = getNextChars(status);

        //判断移进字符集是否包含该字符
        if(nextChars.contains(c)){
            //获得该状态集关于输入字符的下一个状态集
            Set<String> next = next(status, c);
            List<String> list = new ArrayList<>();
            for (String str : next){
                list.add(str);
            }
            Set<String> set = e_closure(list);

            //遍历状态集，返回符合的状态集编号
            Set<Integer> keySet = statusSet.keySet();
            for (int key: keySet) {
                if(statusSet.get(key).equals(set)){
                    return key;
                }
            }
        }
        return -1;  //不存在的返回-1
    }

    /**
     * 获得拓广文法
     * @return
     */
    public List<String> geteFormula() {
        return eFormula;
    }

    /**
     * 获得状态集编号
     * @return
     */
    public List<Integer> getStatus() {
        return status;
    }

    /**
     * 获得状态集
     * @return
     */
    public Map<Integer, Set<String>> getStatusSet() {
        return statusSet;
    }

//    /**
//     * 输出一下关系
//     */
//    public void showDFA(){
//        for (int statu : status){
//            for (char c : Vt){
//                System.out.print(nextStatus(statusSet.get(statu), c) + "   ");
//            }
//
//            for (char c : Vn){
//                System.out.print(nextStatus(statusSet.get(statu), c) + "   ");
//            }
//
//            System.out.println("\n");
//        }
//    }
}
