package com.huawei.java.main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		//平均分配策略
		File demand = new File("/data/demand.csv");
		File site_bandwidth = new File("/data/site_bandwidth.csv");
		File qos = new File("/data/qos.csv");
		File config = new File("/data/config.ini");
		List<ClientNode> clientNodeList = new ArrayList<>();
		List<List<Integer>> allocationList = new ArrayList<>();

		//String[] clientName = new String[35];
		try{
			BufferedReader siteFile = new BufferedReader(new FileReader(site_bandwidth));
			BufferedReader configFile = new BufferedReader(new FileReader(config));
			BufferedReader qosFile = new BufferedReader(new FileReader(qos));
			String siteData = "";
			String qosData = "";

			//TODO 从配置文件中获取
			configFile.readLine();
			String configString = configFile.readLine();
			configString =  configString.substring(15,configString.length());
			int qos_constraint  = Integer.parseInt(configString);

			//读取时刻总数
			int T = 0;
			BufferedReader demandFile = new BufferedReader(new FileReader(demand));
			demandFile.readLine();
			while(demandFile.readLine() != null) T++;
			demandFile.close();
			List<Integer> times = new ArrayList<>();  //保存各个边缘节点可以放最大值的数量
			List<Boolean> used = new ArrayList<>(); //保存本次节点是否被使用

			List<EdgeNode> edgeNodes = new ArrayList<>();  //保存所有的边缘节点
			siteData = siteFile.readLine();  //忽略第一行
			qosData = qosFile.readLine();
			String[] clientName = qosData.split(","); //clientName
			for(int i = 1;i<clientName.length;i++){
				ClientNode clientNode = new ClientNode(clientName[i],new ArrayList<>());
				clientNodeList.add(clientNode);
			}

			int edgeIndex = 0;
			//先初始化边缘节点
			while ((siteData = siteFile.readLine()) != null && (qosData = qosFile.readLine()) != null){
				String[] str1 = siteData.split(",");
				String[] str2 = qosData.split(",");
				EdgeNode node = new EdgeNode(str1[0], Integer.parseInt(str1[1]));

				for(int i = 1;i<str2.length;i++){
					int qos_num = Integer.parseInt(str2[i]);
					if(qos_num <= qos_constraint){
						clientNodeList.get(i-1).edgeList.add(edgeIndex);
					}
				}
				edgeIndex++;
				edgeNodes.add(node);
				times.add(Math.max((int) (T * 0.05 - 1), 0));
				used.add(false);
				List<Integer>  list = new ArrayList<>();
				allocationList.add(list);
			}

			//读入文件处理
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/output/solution.txt")); //输出流
			demandFile = new BufferedReader(new FileReader(demand));
			String demandData = "";
			demandData = demandFile.readLine();
			//clientName = demandData.split(",");
			int colIndex = 0;
			while ((demandData = demandFile.readLine()) != null){
				String[] demandNum = demandData.split(",");
				//挨个处理每个client的请求量
				for(int i=0;i<clientNodeList.size();i++){
					//复制一份edgeNode 作为 分配前的参照
					List<EdgeNode> edgeNodes_copy = new ArrayList<>();
					List<Integer> edgeList = clientNodeList.get(i).edgeList;
					for(EdgeNode node : edgeNodes){
						edgeNodes_copy.add(new EdgeNode(node.getName(), node.getMax_bandwidth(), node.getRemain_bandwidth()));
					}
					//获取这个client能够达到的边缘节点
					int edgeNum = edgeList.size();
					int needNum = Integer.parseInt(demandNum[i+1]);
					//先尽量往一个里边放
					for(int j = 0;j < edgeList.size(); j++){
						EdgeNode temp = edgeNodes.get(edgeList.get(j));
						while(needNum > 0 && times.get(edgeList.get(j)) > 0 && temp.getRemain_bandwidth() > 0){
							if(temp.getRemain_bandwidth() > needNum){
								temp.setRemain_bandwidth(temp.getRemain_bandwidth() - needNum);
								needNum = 0;
								used.set(edgeList.get(j), true);
//								if(needNum > temp.getMax_bandwidth() * 0.85){
//
//								}
							}
							else {
								needNum -= temp.getRemain_bandwidth();
								temp.setRemain_bandwidth(0);
								used.set(edgeList.get(j), true);
							}
						}
						if(needNum == 0) break;
					}
					while(needNum>0){
						boolean flag = true;
						//重新计算可以分配请求的节点数量
						edgeNum = 0;
						for(Integer nodeIndex:edgeList){
							if(edgeNodes.get(nodeIndex).getRemain_bandwidth()>0){
								edgeNum++;
							}
						}
						//执行平均分配策略
						int currentAllocation = needNum/edgeNum;
						int yu = needNum%edgeNum;
						//为能装下 平摊量的边缘节点
						for(int j =0;j<edgeList.size();j++){
							EdgeNode temp = edgeNodes.get(edgeList.get(j));
 							if(temp.getRemain_bandwidth()>currentAllocation){
								temp.setRemain_bandwidth(temp.getRemain_bandwidth()-currentAllocation);
								needNum -= currentAllocation;
								if(flag){
									if(temp.getRemain_bandwidth()>yu){
										temp.setRemain_bandwidth(temp.getRemain_bandwidth()-yu);
										needNum -= yu;
										flag = false;
									}
								}
							}
						}
					}
					//TODO 记录当前cliallocation = {ArrayList@541}  size = 100ent的分配    把所有的分配情况拼接成答案
					List<Integer> allocation =  new ArrayList<>();
					StringBuilder sb = new StringBuilder();
					sb.append(clientName[i + 1]).append(":");
					for(int j = 0;j < edgeNodes.size(); j++){
						EdgeNode temp = edgeNodes.get(j);
						EdgeNode copy = edgeNodes_copy.get(j);
						int num = copy.getRemain_bandwidth() - temp.getRemain_bandwidth();
						allocation.add(num);

						if(num != 0){
							sb.append("<").append(edgeNodes.get(j).getName()).append(",").append(num).append(">").append(",");
						}
					}
					if(sb.charAt(sb.length() - 1) == ','){
						sb.deleteCharAt(sb.length() - 1); //删除多余的,
					}
					if(colIndex != 0) {
						bufferedWriter.write("\r\n");
					}
					bufferedWriter.write(String.valueOf(sb));
					colIndex++;
				}
				//处理完这一时刻的分配 将所有edges 的状态回复为起始状态
				for(int i=0;i<edgeNodes.size();i++){
					List<Integer> list = allocationList.get(i);
					int num = edgeNodes.get(i).getMax_bandwidth() - edgeNodes.get(i).getRemain_bandwidth();
					list.add(num);

					edgeNodes.get(i).setRemain_bandwidth(edgeNodes.get(i).getMax_bandwidth());
				}
				//一个时刻处理完成之后，修改可用最大次数
				for(int i = 0; i < used.size(); i++){
					if(used.get(i)){
						used.set(i, false);
						times.set(i, times.get(i) - 1);
					}
				}
			}
			bufferedWriter.close();
		}catch (FileNotFoundException e){
			System.out.println("Not found the file");
		} catch (IOException e) {
			System.out.println("文件读写出错");
		}
		Util util = new Util();
		System.out.println(util.getScore(allocationList));
	}
}
