package com.hotent.makshi.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Arrays;

public class OrgResUtils {

	public static Map<String, List> map = new HashMap<String, List>();
	// 二级菜单变为1级菜单
	public static String secondToFirstMenu[] = new String[] { "事务", "资料", "水保园项目", "资产", "部门资产", "合同", "工作", "人事", "财务" };
	// 所有部门菜单
	public static String all[] = new String[] { "招标代理部", "运维部", "咨询部", "环境事业部", "水保部", "河道管养部", "综合部", "监理部" };

	private static String zbdlb[] = new String[] { "运维部", "咨询部", "环境事业部", "水保部", "河道管养部", "综合部", "监理部","办公室" };
	private static String ywb[] = new String[] { "招标代理部", "咨询部", "环境事业部", "水保部", "河道管养部", "综合部", "监理部","办公室"};
	private static String zxb[] = new String[] { "运维部", "招标代理部", "环境事业部", "水保部", "河道管养部", "综合部", "监理部","办公室"};
	private static String hjsyb[] = new String[] { "运维部", "咨询部", "招标代理部", "水保部", "河道管养部", "综合部", "监理部","办公室"};
	private static String sbb[] = new String[] { "运维部", "咨询部", "环境事业部", "招标代理部", "河道管养部", "综合部", "监理部","办公室"};
	private static String hdgyb[] = new String[] { "运维部", "咨询部", "环境事业部", "水保部", "招标代理部", "综合部", "监理部","办公室"};
	private static String zhb[] = new String[] { "运维部", "咨询部", "环境事业部", "水保部", "招标代理部", "河道管养部", "监理部","办公室"};
	private static String jlb[] = new String[] { "运维部", "咨询部", "环境事业部", "水保部", "招标代理部", "河道管养部", "综合部","办公室"};
	private static String bgs[] = new String[] { "运维部", "咨询部", "环境事业部", "水保部", "招标代理部", "河道管养部", "监理部","综合部"};

	public static Map getOrgRes() {
		map.put("招标代理部", Arrays.asList(zbdlb));
		map.put("运维部", Arrays.asList(ywb));
		map.put("咨询部", Arrays.asList(zxb));
		map.put("环境事业部", Arrays.asList(hjsyb));
		map.put("水保部", Arrays.asList(sbb));
		map.put("河道管养部", Arrays.asList(hdgyb));
		map.put("综合部", Arrays.asList(zhb));
		map.put("监理部", Arrays.asList(jlb));
		map.put("办公室", Arrays.asList(bgs));
		return map;
	}
}
