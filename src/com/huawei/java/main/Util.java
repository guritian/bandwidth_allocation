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
}
