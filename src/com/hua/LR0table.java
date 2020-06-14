package com.hua;

import com.bin.LR0Pretreat;
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

    /**
     * 显示LR分析表
     */
    public void showTable(){
        System.out.println("------------------LR0分析表------------------");
        System.out.print("\t");
        for (char c:row)
            System.out.print(c+"\t");
        System.out.println();
        for (int i=0;i<status.length;i++){
            System.out.print(i+"\t");
            for(int j=0;j<row.length;j++){
                System.out.print(tables[i][j]+"\t");
            }
            System.out.println();
        }
    }



    public LR0table(LR0Pretreat pretreat, Grammar grammar) {
        //获得分析表的第一行,包括了#
        char[] rows= GrammarToTableUtils.changeSymbol(grammar);
        //获得分析表的第一列
        int[] cols=GrammarToTableUtils.listToArray(pretreat.getStatus());
        //获得分析表的内容
        String[][] t = GrammarToTableUtils.preTreatToTable(pretreat,grammar,rows);
        //完整的分析表
        grammar.setProduction(pretreat.geteFormula().toArray(new String[pretreat.geteFormula().size()]));//将文法的产生式改成拓广文法

        this.row = rows;
        this.status = cols;
        this.tables = t;
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
