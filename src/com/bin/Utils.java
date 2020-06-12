package com.bin;

import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) {
        List<String> list = Utils.splitByChar("E->ab|k|c", '|');
        System.out.println(list.size());
        for (String s:list
             ) {
            System.out.println(s);
        }

    }
}
