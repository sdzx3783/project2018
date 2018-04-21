package com.hotent.makshi.controller.hr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysAuditModelType;
import com.hotent.platform.service.system.SysOrgService;

/**
 * 对象功能:用户表 控制器类 开发公司:广州宏天软件有限公司 开发人员:csx 创建时间:2011-11-28 10:17:09
 */
@Controller
@RequestMapping("/makshi/hr/allStatistics/")
@Action(ownermodel = SysAuditModelType.USER_MANAGEMENT)
public class AllInfoListController extends BaseController {
	
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 String selectedDate = request.getParameter("selectedDate");
		 String entryDate = DateUtils.formatDateS(new Date());
		 if(!StringUtil.isEmpty(selectedDate)){
			 entryDate = selectedDate;
		 }
//		 List<SysOrg> orgList = sysOrgService.getByOrgType(3L);
		 List<Map<String, Object>> orgList = OrgStatistics(entryDate);
		 List<Map<String, Object>> userList = DepartmentStatistics(entryDate);
		 List<Map<String, Object>> sexList = SexStatistics(entryDate);
		 List<Map<String, Object>> ageList = AgeStatistics(entryDate);
		 List<Map<String, Object>> retireList = RetireStatistics(entryDate);
		Map<String, String> certificatetypes = new HashMap<String, String>() {
			{
				put("建设部监理工程师", "qualification1");
				put("建设部造价工程师", "qualification2");
				put("水利部监理工程师", "qualification3");
				put("水利部造价工程师", "qualification4");
				put("水利部总监", "qualification5");
				put("水利部监理员", "qualification6");
				put("一级建造师", "qualification7");
				put("二级建造师", "qualification8");
				put("咨询工程师(投资)", "qualification9");
				put("信息监理工程师", "qualification10");
				put("注册设备监理工程师", "qualification11");
				put("注册安全工程师", "qualification12");
				put("注册公用设备工程师", "qualification13");
				put("招标师", "qualification14");
				put("系统集成项目管理工程师", "qualification15");
				put("测绘师", "qualification16");
				put("投资项目管理师", "qualification17");
				put("一级结构工程师", "qualification18");
				put("二级结构工程师", "qualification19");
			}
		};
		 List<Map<String, Object>> qualificationList = QualificationStatistics(entryDate,certificatetypes);
		 Long orgId = 0L;
		 List<Map<String,Object>> list = new ArrayList<>();
		 int sumCount=0;
		 int sumMan = 0;
		 int sumWomen = 0;
		 int lessThirty = 0;
		 int thirtyToForty = 0;
		 int fortyToFifty = 0;
		 int fiftyToSixty = 0;
		 int greaterSixty = 0;
		 int university = 0;
		 int college  = 0;
		 int master = 0;
		 int middlemaster = 0;
		 int otherDegree = 0;
		 int womanretire = 0;
		 int manretire = 0;
         
		 int SN_ENGRpositional =0;
		 int hightpositional = 0; 
		 int middlepositional = 0; 
		 int lestpositional = 0; 
		 int otherpositional = 0;
		 
		 int entrycount = 0;
		 int formalcount = 0;
		 int resignationcount = 0;
		 
		 int qualification1 = 0;
		 int qualification2 = 0;
		 int qualification3 = 0;
		 int qualification4 = 0;
		 int qualification5 = 0;
		 int qualification6 = 0;
		 int qualification7 = 0;
		 int qualification8 = 0;
		 int qualification9 = 0;
		 int qualification10 = 0;
		 int qualification11 = 0;
		 int qualification12 = 0;
		 int qualification13 = 0;
		 int qualification14 = 0;
		 int qualification15 = 0;
		 int qualification16 = 0;
		 int qualification17 = 0;
		 int qualification18 = 0;
		 int qualification19 = 0;
		 
		 int registNum = 0;
		 int inNum = 0;
		 int outNum = 0;
		 
		 for(Map sysOrg:orgList){
			 Map<String,Object> result = new HashMap<String,Object>();
			   orgId = Long.valueOf(sysOrg.get("ORGID").toString());
			   result.put("orgId", orgId);
			   result.put("orgName", sysOrg.get("ORGNAME"));
			   for(Map obj:userList){
				  Long userOrgId = Long.valueOf(obj.get("orgId").toString());
				  if(orgId.equals(userOrgId)){
					  result.put("count", obj.get("count"));
					  sumCount += Integer.valueOf(obj.get("count").toString());
					  break;
				  }
			  }
		      for(Map obj:sexList){
				  Long userOrgId = Long.valueOf(obj.get("orgId").toString());
				  if(orgId.equals(userOrgId)){
					  result.put("man", obj.get("man"));
					  result.put("woman", obj.get("woman"));
					  sumMan += Integer.valueOf(obj.get("man").toString());
					  sumWomen += Integer.valueOf(obj.get("woman").toString());
					  break;
				  }
			   }
			   for(Map obj:ageList){
				  Long userOrgId = Long.valueOf(obj.get("orgId").toString());
				  if(orgId.equals(userOrgId)){
					  result.put("lessThirty", obj.get("lessThirty"));
					  result.put("thirtyToForty", obj.get("thirtyToForty"));
					  result.put("fortyToFifty", obj.get("fortyToFifty"));
					  result.put("fiftyToSixty", obj.get("fiftyToSixty"));
					  result.put("greaterSixty", obj.get("greaterSixty"));
					  
					  result.put("university", obj.get("university"));
					  result.put("college", obj.get("college"));
					  result.put("master", obj.get("master"));
					  result.put("otherDegree", obj.get("otherDegree"));
					  result.put("middlemaster", obj.get("middlemaster"));

					  result.put("SN_ENGRpositional", obj.get("SN_ENGRpositional"));
					  result.put("hightpositional", obj.get("hightpositional"));
					  result.put("middlepositional", obj.get("middlepositional"));
					  result.put("lestpositional", obj.get("lestpositional"));
					  result.put("otherpositional", obj.get("otherpositional"));
					  
					  lessThirty += Integer.valueOf(obj.get("lessThirty").toString());
					  thirtyToForty += Integer.valueOf(obj.get("thirtyToForty").toString());
					  fortyToFifty += Integer.valueOf(obj.get("fortyToFifty").toString());
					  fiftyToSixty += Integer.valueOf(obj.get("fiftyToSixty").toString());
					  greaterSixty += Integer.valueOf(obj.get("greaterSixty").toString());
					  
					  university += Integer.valueOf(obj.get("university").toString());
					  college += Integer.valueOf(obj.get("college").toString());
					  master += Integer.valueOf(obj.get("master").toString());
					  middlemaster+=Integer.valueOf(obj.get("middlemaster").toString());
					  otherDegree += Integer.valueOf(obj.get("otherDegree").toString());
					  
					  SN_ENGRpositional += Integer.valueOf(obj.get("SN_ENGRpositional").toString());
					  hightpositional += Integer.valueOf(obj.get("hightpositional").toString());
					  middlepositional += Integer.valueOf(obj.get("middlepositional").toString());
					  lestpositional += Integer.valueOf(obj.get("lestpositional").toString());
					  otherpositional += Integer.valueOf(obj.get("otherpositional").toString());
					  break;
				  }
			  }
			  for(Map obj:retireList){
				  Long userOrgId = Long.valueOf(obj.get("orgId").toString());
				  if(orgId.equals(userOrgId)){
					  result.put("womanretire", obj.get("womanretire"));
					  result.put("manretire", obj.get("manretire"));
					  
					  result.put("entrycount", obj.get("entrycount"));
					  result.put("formalcount", obj.get("formalcount"));
					  result.put("resignationcount", obj.get("resignationcount"));
					  
					  womanretire+=Integer.valueOf(obj.get("womanretire").toString());
					  manretire += Integer.valueOf(obj.get("manretire").toString());
					  
					  womanretire+=Integer.valueOf(obj.get("entrycount").toString());
					  womanretire+=Integer.valueOf(obj.get("formalcount").toString());
					  womanretire+=Integer.valueOf(obj.get("resignationcount").toString());
					  break;
				  }
			  }
			  for(Map object:qualificationList){
				  Long userOrgId = Long.valueOf(object.get("orgId").toString());
				  if(orgId.equals(userOrgId)){
					  result.put("qualification1",object.get("qualification1"));
					  result.put("qualification2",object.get("qualification2"));
					  result.put("qualification3",object.get("qualification3"));
					  result.put("qualification4",object.get("qualification4"));
					  result.put("qualification5",object.get("qualification5"));
					  result.put("qualification6",object.get("qualification6"));
					  result.put("qualification7",object.get("qualification7"));
					  result.put("qualification8",object.get("qualification8"));
					  result.put("qualification9",object.get("qualification9"));
					  result.put("qualification10",object.get("qualification10"));
					  result.put("qualification11",object.get("qualification11"));
					  result.put("qualification12",object.get("qualification12"));
					  result.put("qualification13",object.get("qualification13"));
					  result.put("qualification14",object.get("qualification14"));
					  result.put("qualification15",object.get("qualification15"));
					  result.put("qualification16",object.get("qualification16"));
					  result.put("qualification17",object.get("qualification17"));
					  result.put("qualification18",object.get("qualification18"));
					  result.put("qualification19",object.get("qualification19"));
					  result.put("qualification17",object.get("registNum"));
					  result.put("inNum",object.get("inNum"));
					  result.put("outNum",object.get("outNum")); 
					  
					  qualification1 += Integer.valueOf(object.get("qualification1").toString());
					  qualification2 += Integer.valueOf(object.get("qualification2").toString());
					  qualification3 += Integer.valueOf(object.get("qualification3").toString());
					  qualification4 += Integer.valueOf(object.get("qualification4").toString());
					  qualification5 += Integer.valueOf(object.get("qualification5").toString());
					  qualification6 += Integer.valueOf(object.get("qualification6").toString());
					  qualification7 += Integer.valueOf(object.get("qualification7").toString());
					  qualification8 += Integer.valueOf(object.get("qualification8").toString());
					  qualification9 += Integer.valueOf(object.get("qualification9").toString());
					  qualification10 +=Integer.valueOf(object.get("qualification10").toString());
					  qualification11 +=Integer.valueOf(object.get("qualification11").toString());
					  qualification12 +=Integer.valueOf(object.get("qualification12").toString());
					  qualification13 +=Integer.valueOf(object.get("qualification13").toString());
					  qualification14 +=Integer.valueOf(object.get("qualification14").toString());
					  qualification15 +=Integer.valueOf(object.get("qualification15").toString());
					  qualification16 +=Integer.valueOf(object.get("qualification16").toString());
					  qualification17 +=Integer.valueOf(object.get("qualification17").toString());
					  qualification18 +=Integer.valueOf(object.get("qualification18").toString());
					  qualification19 +=Integer.valueOf(object.get("qualification19").toString());
					  registNum +=Integer.valueOf(object.get("registNum").toString());
					  inNum +=Integer.valueOf(object.get("inNum").toString());
					  outNum +=Integer.valueOf(object.get("outNum").toString()); 
						 
					  break;
				  }
			  }
			  
			  list.add(result);
		 }
		 Map<String,Object> sumMap = new HashMap<String,Object>();
		 sumMap.put("orgName", "总计");
		 sumMap.put("count", sumCount);
		 sumMap.put("man", sumMan);
		 sumMap.put("woman", sumWomen);
		 
		 sumMap.put("lessThirty", lessThirty);
		 sumMap.put("thirtyToForty", thirtyToForty);
		 sumMap.put("fortyToFifty", fortyToFifty);
		 sumMap.put("fiftyToSixty", fiftyToSixty);
		 sumMap.put("greaterSixty", greaterSixty);
		 
		 sumMap.put("university", university);
		 sumMap.put("college", college);
		 sumMap.put("master", master);
		 sumMap.put("middlemaster", middlemaster);
		 sumMap.put("otherDegree", otherDegree);
		 
		 sumMap.put("SN_ENGRpositional",SN_ENGRpositional);
		 sumMap.put("hightpositional",hightpositional);
		 sumMap.put("middlepositional",middlepositional);
		 sumMap.put("lestpositional",lestpositional);
		 sumMap.put("otherpositional",otherpositional);
		 
		 sumMap.put("womanretire", womanretire);
		 sumMap.put("manretire", manretire);

		 sumMap.put("entrycount",entrycount);
		 sumMap.put("formalcount",formalcount);
		 sumMap.put("resignationcount",resignationcount);
		 
		 sumMap.put("qualification1",qualification1);
		 sumMap.put("qualification2",qualification2);
		 sumMap.put("qualification3",qualification3);
		 sumMap.put("qualification4",qualification4);
		 sumMap.put("qualification5",qualification5);
		 sumMap.put("qualification6",qualification6);
		 sumMap.put("qualification7",qualification7);
		 sumMap.put("qualification8",qualification8);
		 sumMap.put("qualification9",qualification9);
		 sumMap.put("qualification10",qualification10);
		 sumMap.put("qualification11",qualification11);
		 sumMap.put("qualification12",qualification12);
		 sumMap.put("qualification13",qualification13);
		 sumMap.put("qualification14",qualification14);
		 sumMap.put("qualification15",qualification15);
		 sumMap.put("qualification16",qualification16);
		 sumMap.put("qualification17",qualification17);
		 sumMap.put("qualification18",qualification18);
		 sumMap.put("qualification19",qualification19);
		 sumMap.put("registNum",registNum);
		 sumMap.put("inNum",inNum);
		 sumMap.put("outNum",outNum);
		 
		 list.add(sumMap);
		 ModelAndView mv = this.getAutoView().addObject("list", list);
		 return mv;
	}
	
	/**
	 * 查询日期之前 的每个部门（日期内创建的）
	 * @return
	 */
	private List<Map<String, Object>> OrgStatistics(String entryDate) {
		String sql=
		 		"select ORGID,ORGNAME from SYS_ORG  where (orgType = 3 or orgType=1) and isdelete=0 ";
		 List<Map<String,Object>> userList =  jdbcTemplate.queryForList(sql);
		return userList;
	}

	/**
	 * 查询日期之前 每个部门的人数(未离职的)
	 * @param entryDate
	 * @return
	 */
	private List<Map<String, Object>> DepartmentStatistics(String entryDate) {
		String sql=
		 		"SELECT count(*) count,z.orgId FROM( SELECT c.ORGTYPE,c.ORGNAME,c.ORGSUPID,CASE WHEN c.ORGTYPE=4 THEN orgsupid ELSE c.orgId  END orgId FROM sys_user a "+
				"LEFT JOIN sys_user_pos b ON a.USERID = b.USERID AND b.ISDELETE = 0 AND b.ISPRIMARY = 1 "+
				"LEFT JOIN sys_org c ON b.ORGID = c.ORGID AND ENTRYDATE <= '"+entryDate+" 23:59:59' AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE >'"+entryDate+" 23:59:59')) t  JOIN "+
				"(SELECT orgId,orgName FROM sys_org WHERE (orgType = 3 OR orgType = 1) AND isdelete = 0) z "+
				 "ON t.orgId = z.orgId "+
				 "GROUP BY z.orgName";
		 
		 List<Map<String,Object>> userList =  jdbcTemplate.queryForList(sql);
		return userList;
	}
	
	/**
	 * 查询日期之前 每个部门的男女人数(未离职的)
	 * @param entryDate
	 * @return
	 */
	private List<Map<String, Object>> SexStatistics(String entryDate) {
		String sql=
		 		"SELECT z.orgId,sum(case WHEN sex=0 then 1 ELSE 0 END) AS woman,sum(case WHEN sex=1 then 1 ELSE 0 END) AS man FROM( SELECT c.ORGTYPE,c.ORGNAME,c.ORGSUPID,a.SEX,CASE WHEN c.ORGTYPE=4 THEN orgsupid ELSE c.orgId  END orgId FROM sys_user a "+
				"LEFT JOIN sys_user_pos b ON a.USERID = b.USERID AND b.ISDELETE = 0 AND b.ISPRIMARY = 1 "+
				"LEFT JOIN sys_org c ON b.ORGID = c.ORGID AND ENTRYDATE <= '"+entryDate+" 23:59:59' AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE >'"+entryDate+" 23:59:59')) t  JOIN "+
				"(SELECT orgId,orgName FROM sys_org WHERE (orgType = 3 OR orgType = 1) AND isdelete = 0) z "+
				 "ON t.orgId = z.orgId "+
				 "GROUP BY z.orgName";
		 
		 List<Map<String,Object>> userList =  jdbcTemplate.queryForList(sql);
		return userList;
	}

	/**
	 *1 查询日期之前 未离职的员工的退休分布情况（日期内未离职的）
	 *2 查询日期之前 学历分布情况（日期内未离职的）
	 *3 查询日期之前  职称分布情况 （日期内未离职的）
	 * @param entryDate
	 * @return
	 */
	private List<Map<String, Object>> AgeStatistics(String entryDate) {
		 String sql =
				 "SELECT z.orgId,z.orgName,count(*),sum(case WHEN age<=30 then 1 ELSE 0 END) AS 'lessThirty',sum(case WHEN age>30 AND age<=40 then 1 ELSE 0 END) AS 'thirtyToForty', "+
				 "sum(case WHEN age>40 AND age<=50 then 1 ELSE 0 END) AS 'fortyToFifty',sum(case WHEN age>50 AND age<=60 then 1 ELSE 0 END) AS 'fiftyToSixty',sum(case WHEN age>60  then 1 ELSE 0 END) AS 'greaterSixty', "+
				 "sum(CASE WHEN F_education like'%本科%' THEN 1 ELSE 0 END) AS university , "+
				 "sum(CASE WHEN F_education like '%大专%' THEN 1 ELSE 0 END) AS college, "+
				 "sum(CASE WHEN F_education like '%硕士%'  OR F_education like '%研究生%' OR F_education like '%博士%' THEN 1 ELSE 0 END) AS master , "+
				 "sum(CASE WHEN F_education like '%中专%' THEN 1 ELSE 0 END) AS middlemaster , "+
				 "sum(CASE WHEN F_education='' OR F_education is NULL or F_education like '%其他%'  THEN 1 ELSE 0 END) AS otherDegree , "+
				 "sum( CASE WHEN F_positional ='教高' THEN 1 ELSE 0 END ) AS SN_ENGRpositional, "
				 + "sum( CASE WHEN F_positional='高级工程师' OR F_positional='高工' OR F_positional='高级' THEN 1 ELSE 0 END ) AS hightpositional, "
				 + "sum( CASE WHEN F_positional ='中级' OR F_positional='工程师'  THEN 1 ELSE 0 END ) AS middlepositional, "
				 + "sum( CASE WHEN F_positional='初级' OR F_positional='助理工程师' THEN 1 ELSE 0 END ) AS lestpositional, "
				 + "sum( CASE WHEN (F_positional !='教高' "
				 + "AND F_positional !='高级工程师' AND F_positional !='高工' AND F_positional !='高级' "
				 + "AND F_positional !='中级' AND F_positional !='工程师' "
				 + "AND F_positional !='初级' AND F_positional !='助理工程师') OR F_positional IS NULL OR F_positional='' THEN 1 ELSE 0 END ) AS otherpositional "+ 
				 "FROM(SELECT c.ORGTYPE,c.ORGNAME,c.ORGSUPID,a.SEX,a.FULLNAME,a.USERID,CASE WHEN c.ORGTYPE = 4 THEN orgsupid ELSE c.orgId END orgId,timestampdiff(YEAR,d.F_birthday,'"+entryDate+"') AS age,F_positional,F_education FROM  sys_user a "+
				 "LEFT JOIN sys_user_pos b ON a.USERID = b.USERID AND b.ISDELETE = 0 AND b.ISPRIMARY = 1 AND ENTRYDATE <= '"+entryDate+" 23:59:59' AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE >'"+entryDate+" 23:59:59') LEFT JOIN sys_org c ON b.ORGID = c.ORGID LEFT JOIN w_user_infomation d ON a.USERID = d.F_userId  ) t "+
				 "JOIN (SELECT orgId, orgName FROM sys_org WHERE (orgType = 3 OR orgType = 1) AND isdelete = 0) z ON t.orgId = z.orgId GROUP BY z.orgName ";
		List<Map<String,Object>> ageList =  jdbcTemplate.queryForList(sql);
		return ageList;
	}
	
	/**
	 * 1 查询日期之前 的员工退休男女分布情况（日期内未离职的）
	 * 2 查询该月1号至日期当日的工作状态情况  入职情况（日期内未离职的），转正情况（日期内未离职的），离职情况
	 * @param entryDate
	 * @return
	 */
	private List<Map<String, Object>> RetireStatistics(String entryDate) {
		String monthDate=entryDate.substring(0,entryDate.lastIndexOf("-")+1)+"1 00:00:00";
		 String sql =
				"SELECT z.orgId, sum(CASE WHEN age > 55 AND sex = 1 AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE >'"+entryDate+" 23:59:59') THEN 1 ELSE 0 END) AS womanretire, sum(CASE WHEN age > 60 AND sex = 0 AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE >'"+entryDate+" 23:59:59') THEN 1 ELSE 0 END ) AS manretire , "+ 
				"SUM(CASE WHEN ENTRYDATE >= '"+monthDate+"' AND ENTRYDATE<= '"+entryDate+" 23:59:59' AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE > ENTRYDATE) THEN 1 ELSE 0 END) AS entrycount,"+
				"SUM(CASE WHEN FORMALDATE >= '"+monthDate+"' AND FORMALDATE<= '"+entryDate+" 23:59:59' AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE > FORMALDATE) THEN 1 ELSE 0 END) AS formalcount,"+
				"SUM(CASE WHEN RESIGNATIONDATE >= '"+monthDate+"' AND RESIGNATIONDATE<= '"+entryDate+" 23:59:59' THEN 1 ELSE 0 END) AS resignationcount FROM "+
				"( SELECT c.ORGTYPE, c.ORGNAME, c.ORGSUPID, a.SEX, CASE WHEN c.ORGTYPE = 4 THEN orgsupid ELSE c.orgId END orgId, timestampdiff( YEAR, d.F_birthday,'"+entryDate+"' ) AS age,ENTRYDATE,FORMALDATE,RESIGNATIONDATE FROM sys_user a "
							+ "LEFT JOIN sys_user_pos b ON a.USERID = b.USERID AND b.ISDELETE = 0 AND b.ISPRIMARY = 1 LEFT JOIN sys_org c ON b.ORGID = c.ORGID LEFT JOIN w_user_infomation d ON a.USERID = d.F_userId ) t "
							+ "JOIN ( SELECT orgId, orgName FROM sys_org WHERE (orgType = 3 OR orgType = 1) AND isdelete = 0 ) z ON t.orgId = z.orgId GROUP BY z.orgName"; 
		List<Map<String,Object>> retirelist =  jdbcTemplate.queryForList(sql);
		return retirelist;
	}

	/**
	 *1  查询该月1号至日期当日的初始注册人数（人员日期内未离职的,证书日期内未转出的）、 转入人数（人员日期内未离职的,证书日期内未转出的）、 转出人数（人员日期内未离职的）
	 *2  查询日期之前  的执业证书情况（日期内未离职的）
	 * @param entryDate
	 * @param certificatetypes
	 * @return
	 */
	private List<Map<String, Object>> QualificationStatistics(String entryDate,Map<String, String> certificatetypes) {
		String monthDate=entryDate.substring(0,entryDate.lastIndexOf("-")+1)+"1 00:00:00";
		String sql = "SELECT sum(CASE WHEN F_regist_date>='"+monthDate+"' AND F_regist_date<='"+entryDate+" 23:59:59' AND deleted = 0 AND ( F_regist_out_date > '"+entryDate+" 23:59:59' OR F_regist_out_date IS NULL OR F_regist_out_date = '' ) THEN 1 ELSE 0 END ) registNum, "
				+ "sum(CASE WHEN F_in_date>='"+monthDate+"' AND F_in_date<='"+entryDate+" 23:59:59' AND deleted = 0 AND ( (F_in_date < F_regist_out_date AND F_regist_out_date > '"+entryDate+" 23:59:59')  OR F_out_date IS NULL OR F_out_date = '' ) THEN 1 ELSE 0 END) inNum, "
				+ "sum(CASE WHEN F_regist_out_date>='"+monthDate+"' AND F_regist_out_date<='"+entryDate+" 23:59:59' AND deleted = 0 AND ( F_regist_out_date > F_in_date OR F_in_date IS NULL OR F_in_date = '' ) THEN 1 ELSE 0 END) outNum,";
		for(Map.Entry<String, String> certificatetype:certificatetypes.entrySet()){
//			sql+="SUM(d.F_certificate_type='"+certificatetype.getKey()+"') AS "+certificatetype.getValue()+",";
			sql+="SUM( CASE WHEN ( d.F_certificate_type = '"+certificatetype.getKey()+"' AND ( d.F_in_date <= '"+entryDate+" 23:59:59' OR d.F_regist_date <= '"+entryDate+" 23:59:59' ) AND ( d.F_in_date > d.F_regist_out_date OR d.F_regist_date > d.F_regist_out_date OR d.F_regist_out_date IS NULL OR d.F_regist_out_date = '' ) ) THEN 1 ELSE 0 END ) AS "+certificatetype.getValue()+",";
		}
		sql += "ORGID FROM w_personal_qualification_regist d"
				+ " LEFT JOIN ( SELECT z.orgId, t.userid, t.FULLNAME, t.ORGTYPE, z.ORGNAME, t.ORGSUPID "
				+ "FROM ( SELECT a.USERID, a.FULLNAME, c.ORGTYPE, c.ORGNAME, c.ORGSUPID, CASE WHEN c.ORGTYPE = 4 THEN orgsupid ELSE c.orgId END orgId FROM sys_user a "
				+ "LEFT JOIN sys_user_pos b ON a.USERID = b.USERID AND b.ISDELETE = 0 AND b.ISPRIMARY = 1 AND (RESIGNATIONDATE IS NULL OR RESIGNATIONDATE IS NULL OR RESIGNATIONDATE >'"+entryDate+" 23:59:59') "
				+ "LEFT JOIN sys_org c ON b.ORGID = c.ORGID ) t "
				+ "JOIN ( SELECT orgId, orgName FROM sys_org WHERE (orgType = 3 OR ORGTYPE = 1) AND isdelete = 0 ) z ON t.orgId = z.orgId ) x "
				+ "ON d.F_userId = x.USERID WHERE ( F_certificate_type IS NOT NULL AND F_certificate_type != '' ) AND userid IS NOT NULL GROUP BY ORGNAME ";
		List<Map<String,Object>> qualificationList =  jdbcTemplate.queryForList(sql);
		return qualificationList;
	}
}
