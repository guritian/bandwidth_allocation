package com.huawei.java.main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

	public static void main(String[] args) {

		//最简单的执行，一个一个的分配，直接按照延时最小的拿，不够的再去第二小的补

		File demand = new File("data/demand.csv");
		File site_bandwidth = new File("data/site_bandwidth.csv");
		File qos = new File("data/qos.csv");

		//String[] clientName = new String[35];
		try{
			BufferedReader siteFile = new BufferedReader(new FileReader(site_bandwidth));
			BufferedReader qosFile = new BufferedReader(new FileReader(qos));
			String siteData = "";
			String qosData = "";

			List<EdgeNode> edgeNodes = new ArrayList<>();  //保存所有的边缘节点
			siteData = siteFile.readLine();  //忽略第一行
			qosData = qosFile.readLine();
			String[] clientName = qosData.split(","); //clientName
			//先初始化边缘节点
			while ((siteData = siteFile.readLine()) != null && (qosData = qosFile.readLine()) != null){
				String[] str1 = siteData.split(",");
				String[] str2 = qosData.split(",");
				EdgeNode node = new EdgeNode(str1[0], Integer.parseInt(str1[1]));
				PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.getDelay() - o2.getDelay());
				int index = 1;
				while(index < clientName.length){
					queue.offer(new Node(clientName[index], Integer.parseInt(str2[index])));
					index++;
				}
				node.setQueue(queue);
				edgeNodes.add(node);
			}

			//读入文件处理
			BufferedReader demandFile = new BufferedReader(new FileReader(demand));
			String demandData = "";
			demandData = demandFile.readLine();
			clientName = demandData.split(",");
			while ((demandData = demandFile.readLine()) != null){

			}
		}catch (FileNotFoundException e){
			System.out.println("Not found the file");
		} catch (IOException e) {
			System.out.println("文件读写出错");
		}
	}
}
