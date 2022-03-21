package com.huawei.java.main;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ClientNode {
    private String name; //客户端名称
    private int need;  //带宽需求值
    private int remain; //剩余带宽需求
    public List<Integer> edgeList;  //保存每个client 能够到达的边缘节点的列表

    public ClientNode(String name, int need,List<Integer> edgeList){
        this.name = name;
        this.need = need;
        this.remain = need;
        this.edgeList = edgeList;
    }

    public ClientNode(String name,List<Integer> edgeList){
        this.name = name;
        this.edgeList = edgeList;
    }

    public List<Integer> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Integer> edgeList) {
        this.edgeList = edgeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNeed() {
        return need;
    }

    public void setNeed(int need) {
        this.need = need;
    }

    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }
}
