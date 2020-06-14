package com.bin;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

public class Utils {

    /**
     * 分割候选式
     * @param str   需要分割的字符串
     * @param c     分割的字符
     * @return
     */
    public static List<String> splitByChar(String str, char c){
        if(str == null){
            return null;
        }
        List<String> list = new ArrayList<String>();
        int beginIndex = 0;
        for(int i = 0; i < str.length(); i++){
            if (str.charAt(i) == c ){
                list.add(str.substring(beginIndex, i));
                beginIndex = i+1;
            }
            if (i == str.length()-1){
                list.add(str.substring(beginIndex));
            }
        }
        return list;
    }

    /**
     * 显示数据
     * @param pretreat
     */
    public static void show(LR0Pretreat pretreat){
        /**         输出      */
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
    }

    /**
     * 文法是否具有二义性
     * @param pretreat
     * @return
     */
    public static boolean isAmbiguous(LR0Pretreat pretreat){
        Map<Integer, Set<String>> statusSet = pretreat.getStatusSet();
        Collection<Set<String>> values = statusSet.values();
        HashMap<Character, Boolean> cache=null;
        for (Set<String> set:values){
            cache=new HashMap<>();
            for (String str:set){
                int indexOfDot=str.indexOf('·');
                //如果·在最末尾
                if(indexOfDot==str.length()-1)
                    continue;
                char c=str.charAt(indexOfDot+1);//获得·后面的字符
                Boolean isExist = cache.get(c);//是否存在
                if(isExist!=null&&isExist)//存在，说明有二义性
                    return true;
                cache.put(c,true );
            }
            //清空cache
            cache=null;
        }
        return false;
    }

    public static void main(String[] args) {
        List<String> list = Utils.splitByChar("E->ab", '|');
        System.out.println(list.size());
        for (String s:list
             ) {
            System.out.println(s);
        }

    }
}
