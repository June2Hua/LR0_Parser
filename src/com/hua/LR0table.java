package com.hua;

public class LR0table {

    //终结符
    private String[] vt;

    //非终结符
    private String[] vn;

    //状态
    private int[] status;

    //LR0分析表
    private String[][] tables;

    public void setVt(String[] vt) {
        this.vt = vt;
    }

    public void setVn(String[] vn) {
        this.vn = vn;
    }

    public void setStatus(int[] status) {
        this.status = status;
    }

    public void setTables(String[][] tables) {
        this.tables = tables;
    }
}
