package com.huawei.java.main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		//最简单的执行，一个一个的分配，直接按照延时最小的拿，不够的再去第二小的补

		File demand = new File("data/demand.csv");
		File site_bandwidth = new File("/data/site_bandwidth.csv");
		File qos = new File("data/qos.csv");

		//String[] clientName = new String[35];
		try{
			BufferedReader textFile = new BufferedReader(new FileReader(demand));
			String lineDta = "";
			lineDta = textFile.readLine(); //忽略第一行


			List<EdgeNode> edgeNodes = new ArrayList<>();  //保存所有的边缘节点
			textFile = new BufferedReader(new FileReader(site_bandwidth));
			lineDta = textFile.readLine();  //忽略第一行
			while ((lineDta = textFile.readLine()) != null){
				String[] str = lineDta.split(",");
				EdgeNode node = new EdgeNode(str[0], Integer.parseInt(str[1]));
			}
//			System.out.println(Arrays.deepToString(demand));

		}catch (FileNotFoundException e){
			System.out.println("Not found the file");
		} catch (IOException e) {
			System.out.println("文件读写出错");
		}
	}
}
