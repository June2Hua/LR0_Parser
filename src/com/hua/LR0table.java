package com.hua;

import com.together.Grammar;

public class LR0table {

    //第一行，包括了ACTION动作，GOTO状态转换
    private char[] row;

    //状态
    private int[] status;

    //LR0分析表
    private String[][] tables;

    //文法
    private Grammar grammar;

    /**
     * 判断input的在第一行的下标，如果不存在则返回-1
     * @param input
     * @return
     */
    public int indexOfFirstRow(char input){
        for (int i=0;i<row.length;i++)
            if (input==row[i])
                return i;
        //如果不存在，返回-1
        return -1;
    }

    /**
     * 获得第index+1个产生式的左部非终结符
     * @param index
     * @return
     */
    public char vnOfProductionOfLeft(int index){
        return grammar.getProduction()[index].charAt(0);
    }

    /**
     * 获得第index+1个产生式的右部
     * @param index
     * @return
     */
    public char[] vnOfProductionOfRight(int index){
        String production=grammar.getProduction()[index];//第index+1个产生式
        int indexOfRight=production.indexOf('>')+1;//"产生式右部的开始下标"
        return production.substring(indexOfRight).toCharArray();
    }

    public LR0table(char[] row, int[] status, String[][] tables, Grammar grammar) {
        this.row = row;
        this.status = status;
        this.tables = tables;
        this.grammar = grammar;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public char[] getRow() {
        return row;
    }

    public void setRow(char[] row) {
        this.row = row;
    }

    public int[] getStatus() {
        return status;
    }

    public String[][] getTables() {
        return tables;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }

    public void setTables(String[][] tables) {
        this.tables = tables;
    }
}
