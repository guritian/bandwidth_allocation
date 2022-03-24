package com.huawei.java.main;

import java.util.Collections;
import java.util.List;

public class Util {
    public  int getScore(List<List<Integer>> allocationList){
        int res = 0;
        for(List<Integer> list: allocationList){
            Collections.sort(list);
            int index = (int)Math.ceil(list.size()*0.95);
            res += list.get(index-1);
        }
        return res;
    }

    public int getTotalNeed(String[] demandNum){
        int num = 0;
        for(int i=1;i<demandNum.length;i++){
            num += Integer.parseInt(demandNum[i]);
        }
        return num;
    }

    public EdgeNode getEdgeNode(List<EdgeNode> list,int index){
        for(EdgeNode temp : list){
            if(temp.index == index){
                return temp;
            }
        }
        return null;
    }
}
