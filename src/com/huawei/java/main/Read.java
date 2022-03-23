package com.huawei.java.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Read {
    public static void main(String[] args) {
        File demandFile = new File("data/demand.csv");
        File site_bandwidth = new File("data/site_bandwidth.csv");
        File qosFile = new File("data/qos.csv");
        File solutionFile = new File("output/solution.txt");

        try{
            BufferedReader site = new BufferedReader(new FileReader(site_bandwidth));
            BufferedReader demand = new BufferedReader(new FileReader(demandFile));
            BufferedReader qos = new BufferedReader(new FileReader(qosFile));
            BufferedReader solution = new BufferedReader(new FileReader(solutionFile));

            String siteData = "";
            String solutionData = "";
            String qosData = "";
            String demandData = "";

            //一个时刻验证一次
            demand.readLine();  //忽略第一行
            while((demandData = demand.readLine()) != null){
                String[] need = demandData.split(",");
                int index = 1;
                int sum = 0;
                while(index < need.length && (solutionData = solution.readLine()) != null) {
                    sum = 0;
                    String[] alloc = solutionData.split(",");  //当前时刻对一个client的分配方案
                    for (int i = 1; i < alloc.length; i += 2) {
                        String str = alloc[i];
                        int temp = 0;
                        for (char c : str.toCharArray()) {
                            if (c >= '0' && c <= '9') {
                                temp = temp * 10 + (c - '0');
                            }
                        }
                        sum += temp;
                    }
                    if(sum == Integer.parseInt(need[index])) System.out.println("yes");
                    else System.out.println(sum + " " +  need[index]);
                    index++;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
