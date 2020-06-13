package com.together;

/**
 * 定义文法
 */
public class Grammar {

    //文法终结符，包括了“#”，并且“#”放在vt数组的最后
    private char[] vt;

    //文法非终结符，并且第一个非终结符为开始符
    private char[] vn;

    //文法产生式,如“E->E+T”、“F->(E)”等等   产生式中不存在如“E->E+T| i”的形式，即不存在“ | ”
    private String[] production;//产生式

    /**
     * 构造函数
     * @param vt
     * @param vn
     * @param production
     */
    public Grammar(char[] vt, char[] vn, String[] production) {
        this.vt = vt;
        this.vn = vn;
        this.production = production;
    }

    /*以下为get和set方法*/

    public char[] getVt() {
        return vt;
    }

    public void setVt(char[] vt) {
        this.vt = vt;
    }

    public char[] getVn() {
        return vn;
    }

    public void setVn(char[] vn) {
        this.vn = vn;
    }

    public String[] getProduction() {
        return production;
    }

    public void setProduction(String[] production) {
        this.production = production;
    }
}
