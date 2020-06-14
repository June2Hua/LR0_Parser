package com.hua;

import com.bin.LR0Pretreat;
import com.together.Grammar;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrammarToTableUtils {

    /**
     * List转数组
     * @param list
     * @return
     */
    public static int[] listToArray(List<Integer> list){
        int[] result=new int[list.size()];
        int i=0;
        for(Integer num:list){
            result[i]=num;
            i++;
        }
        return result;
    }

    /**
     * 根据预处理得到LR分析表
     * @param pretreat
     * @return
     */
    public static String[][] preTreatToTable(LR0Pretreat pretreat, Grammar grammar,char[] firstRow){
        //项目族
        Map<Integer, Set<String>> statusSet = pretreat.getStatusSet();
        //拓广文法
        List<String> extendGrammar = pretreat.geteFormula();
        //分析表内容，即返回值
        String[][] result=new String[pretreat.getStatus().size()][];
        //创建分析表，并填充内容
        for (int row:pretreat.getStatus()){
            result[row]=new String[firstRow.length];//创建
            for(int i=0;i<firstRow.length;i++){
                result[row][i]="";
                char c=firstRow[i];
                int status = pretreat.nextStatus(row, c);
                //可以跳转
                if (status!=-1){
                    //非终结符
                    if(c<='Z'&&c>='A'){
                        //直接填充内容
                        result[row][i]=String.valueOf(status);//表示归约        GOTO
                    }
                    //终结符
                    else{
                        result[row][i]="S"+String.valueOf(status);//Sj，表示移进     Action
                    }
                }
                //如果项目族中仅有一个项目，则可能为Rj或者acc
                if (statusSet.get(row).size()==1){
                    String production = statusSet.get(row).iterator().next();//项目表达式
                    int length=production.length();//长度
                    //如果项目最后侧为·，则说明为可以acc或者Rj
                    if ((c>'Z'||c<'A')&&production.charAt(length-1)=='·'){
                        //添加acc情况
                        if (production.charAt(0)=='S'){//
                            result[row][grammar.getVt().length]="acc";
                            continue;
                        }
                        //添加Rj情况
                        int index=0;
                        for(String prod:extendGrammar){
                            if (prod.equals(production.substring(0,length-1))){
                                result[row][i]="R"+(index+1);
                                break;
                            }
                            index++;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 将终结符和非终结拼接#
     * @param grammar
     * @return
     */
    public static char[] changeSymbol(Grammar grammar){
        char[] row=new char[grammar.getVn().length+grammar.getVt().length+1];
        //通过文法，拼接处LR分析表的第一行，终结符+#+非终结符
        for(int i=0;i<grammar.getVt().length;i++)
            row[i]=grammar.getVt()[i];
        row[grammar.getVt().length]='#';
        for (int i=0;i<grammar.getVn().length;i++)
            row[grammar.getVt().length+1+i]=grammar.getVn()[i];
        return row;
    }
}
