package com.hua;

import java.util.LinkedList;

public class Parser {

    //状态栈
    private LinkedList<Integer> statusStack=null;

    //符号栈
    private LinkedList<String> symbolStack=null;

    /**
     * 分析程序
     * @param table LR0分析表
     * @param input 输入串
     */
    public void parse(String[][] table,String input){
        initStack();//初始化栈
        int index=0;//输入串下标
        String c=null;
        int indexOfC=0;//下标
        int status;//状态栈的栈顶元素
        String info;//LR分析表中的信息
        while(true){
            c=input.substring(index,index+1);

            status=statusStack.getLast();
//            info=status[status][indexOfC]
        }
    }

    private int indexOfRow(String ele){
//        for(int )
        return 0;
    }

    /**
     * 初始化栈
     */
    private void initStack(){
        //初始化状态栈
        statusStack=new LinkedList<>();
        statusStack.add(0);
        //初始化符号栈
        symbolStack=new LinkedList<>();
        symbolStack.add("#");
    }

}
