package com.hua;

import java.util.LinkedList;

public class Parser {

    //状态栈
    private LinkedList<Integer> statusStack=null;

    //符号栈
    private LinkedList<Character> symbolStack=null;

    /**
     * 分析程序
     * @param table LR0分析表
     * @param input 输入串
     */
    public void parse(LR0table table,String input){
        initStack();//初始化栈
        int index=0;//输入串下标
        char c;
        int indexOfC=0;//下标
        int status;//状态栈的栈顶元素
        String info;//LR分析表中的信息
        show(index,input);//显示
        c=input.charAt(index++);//当前index指向的字符c
        indexOfC=table.indexOfFirstRow(c);//c对应table中的下标
        while(true){
            /*如果没有找到,输入字符串有错误*/
            if(indexOfC==-1){
                System.err.println("错误，输入字符串有误。找不到("+c+")字符");
                return ;
            }
            status=statusStack.getLast();//获得状态码
            info=table.getTables()[status][indexOfC];//获得对应的ACTION或者GOTO状态转化
            //报错，为null或者空
            if(info==null||info.length()==0){
                System.err.println("错误，分析表中查询不到");
                return ;
            }
            //接受,acc
            if("acc".equals(info)){
                System.out.println("成功!");
                return ;
            }
            //移进,S开头
            if('S'==info.charAt(0)){
                int nextStatus=Integer.valueOf(info.substring(1));//获得下一个状态
                statusStack.add(nextStatus);//状态入栈
                symbolStack.add(c);//符号进栈
                System.out.println("ACTION["+status+","+c+"]="+info+",即状态"+nextStatus+"进栈");
                show(index,input);//显示
                c=input.charAt(index++);//当前index指向的字符c
                indexOfC=table.indexOfFirstRow(c);//c对应table中的下标
                continue;
            }
            //归约
            if('R'==info.charAt(0)){
                int indexOfProduction=Integer.valueOf(info.substring(1))-1;//获得产生式下标
                char leftProduction=table.vnOfProductionOfLeft(indexOfProduction);//获得产生式左边非终结符
                char[] rightProduction=table.vnOfProductionOfRight(indexOfProduction);//产生式右部
                /*判断归约是否正确*/
                for(int i=rightProduction.length-1;i>=0;i--){
                    if (symbolStack.removeLast()!=rightProduction[i]){
                        System.err.println("错误，归约出现错误------");
                        return ;
                    }
                    statusStack.removeLast();//同时状态出栈******
                }

                int curStatus=statusStack.getLast();//当前状态
                int rowIndex=table.indexOfFirstRow(leftProduction);//获得产生式左部下标
                String nextStatusString=table.getTables()[curStatus][rowIndex];
                int nextStatus=Integer.valueOf(nextStatusString);//状态
                System.out.println(info+":用"+leftProduction+"->"+rightProduction+"归约且GOTO（"+curStatus+","+leftProduction+")="+nextStatus+"入栈");
                show(index,input);//显示
                symbolStack.add(leftProduction);//符号栈进栈
                statusStack.add(nextStatus);//状态进栈
                continue;
            }
            System.err.println("错误--------");
            break;
        }
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
        symbolStack.add('#');
    }

    /**
     * 显示栈
     */
    private void show(int index,String input){
        int numOfTable=5;
        System.out.print(statusStack);
        for (int i=statusStack.toString().length()/4;i<numOfTable;i++)
            System.out.print("\t");
        System.out.print(symbolStack);
        for (int i=symbolStack.toString().length()/4;i<numOfTable;i++)
            System.out.print("\t");
        System.out.print(input.substring(index));
        for (int i=input.substring(index).length()/4;i<numOfTable;i++)
            System.out.print("\t");
    }

}
