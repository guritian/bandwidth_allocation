package com.huawei.java.main;


public class EdgeNode {
    private String name; //边缘节点的名字
    private int max_bandwidth; //最大带宽值
    private int remain_bandwidth;  //剩余可用带宽值

    public EdgeNode(String name, int max_bandwidth){
        this.name = name;
        this.max_bandwidth = max_bandwidth;
        this.remain_bandwidth = max_bandwidth;
    }

    public EdgeNode(String name, int max_bandwidth, int remain_bandwidth){
        this.name = name;
        this.max_bandwidth = max_bandwidth;
        this.remain_bandwidth = remain_bandwidth;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax_bandwidth() {
        return max_bandwidth;
    }

    public void setMax_bandwidth(int max_bandwidth) {
        this.max_bandwidth = max_bandwidth;
    }

    public int getRemain_bandwidth() {
        return remain_bandwidth;
    }

    public void setRemain_bandwidth(int remain_bandwidth) {
        this.remain_bandwidth = remain_bandwidth;
    }


}
