package com.hotent.makshi.controller.project;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.core.util.StringUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.project.ClassifyStage;
import com.hotent.makshi.model.project.ClassifyStageTask;
import com.hotent.makshi.model.project.Project;
import com.hotent.makshi.model.project.ProjectStage;
import com.hotent.makshi.model.project.ProjectStageTask;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.service.project.ClassifyLibraryService;
import com.hotent.makshi.service.project.ClassifyStageService;
import com.hotent.makshi.service.project.ClassifyStageTaskService;
import com.hotent.makshi.service.project.ProjectService;
import com.hotent.makshi.service.project.ProjectStageService;
import com.hotent.makshi.service.project.ProjectStageTaskService;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;
@Controller
@RequestMapping("/makshi/import/")
public class ImportProjectUtil extends BaseController{
	private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectStageService stageService;
	@Autowired
	private ProjectStageTaskService taskService;
	@Autowired
	private ClassifyLibraryService classifyLibraryService;
	@Autowired
	private ClassifyStageService classifyStageService;
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysUserService userService;
	@Autowired
	private ClassifyStageTaskService classifyTaskService;
	@Autowired
	private ContractinfoService contractinfoService;
	@Autowired
	private HttpServletRequest request;
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;
	private Workbook wkb;
	private Sheet sht;
	private Row headRow;
	@RequestMapping("project_zxb_file")
	public @ResponseBody Map<String,Object> importFile(){
		Map<String,Object> map=new LinkedHashMap<>();
		String filename="oa系统项目管理在建项目基本信息采集表-咨询部.xlsx";
		InputStream is = request.getServletContext().getResourceAsStream(File.separator+"files"+File.separator+filename);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy.MM.dd");
		List<String> listInfo=new ArrayList<>();
		List<String> listError=new ArrayList<>();
		try {
			String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
			System.out.println("++++项目信息数据导入开始++++");
			sht = wkb.getSheetAt(0);
			// 得到总行数
			int rowNum = sht.getLastRowNum();
			headRow = sht.getRow(0);
			Date now = new Date();
			// 正文内容应该从第二行开始,第一行为表头的标题
			for (int i = 1; i <= rowNum; i++) {
				try {
					headRow = sht.getRow(i);
					String seq = getStringCellValue(headRow.getCell(0));
					String classifyName = getStringCellValue(headRow.getCell(2));
					String proName = getStringCellValue(headRow.getCell(3));
					String contractno = getStringCellValue(headRow.getCell(4));
					
					//String contractname = getStringCellValue(headRow.getCell(5));
					
					//String contract_money = getStringCellValue(headRow.getCell(5));
					String proCharger = getStringCellValue(headRow.getCell(5));
					String proJoiner = getStringCellValue(headRow.getCell(6));
					String proStartTime = getStringCellValue(headRow.getCell(7));
					if(proStartTime.contains(".")){
						Date parse = sdf1.parse(proStartTime);
						proStartTime=sdf.format(parse);
					}
					String proEndTime = getStringCellValue(headRow.getCell(8));
					if(proEndTime.contains(".")){
						Date parse = sdf1.parse(proEndTime);
						proEndTime=sdf.format(parse);
					}
					
					int proStatus = getStatus(getStringCellValue(headRow.getCell(9)));
					System.out.println(seq+" "+classifyName+" "+proName+" "
					+contractno+" "+proCharger
					+" "+proJoiner+" "+proStartTime+" "+proEndTime+" "+proStatus);
					Project pro=new Project();
					pro.setName(proName);
					
					pro.setCtime(new Date());
					pro.setIsdelete(0);
					if(StringUtils.isEmpty(classifyName)){
						map.put("seq:"+seq, "项目分类名不能为空");
						continue;
					}
					String importOrgId="10000011000055";//咨询部
					//pro.setCuser("10000000010023");//监理部默认孙晖创建的
					//施工图审查类  杨立寒 其他类型的 罗来辉
					ClassifyLibrary classifyLibrary = classifyLibraryService.getByNameAndOrgId(classifyName, importOrgId);//监理部的分类库名称
					if(classifyLibrary==null){
						map.put("seq:"+seq, "项目分类名不能为空");
						continue;
					}
					if("施工图审查类".equals(classifyLibrary.getName())){
						pro.setCuser("10000000010211");
					}else{
						pro.setCuser("10000000010210");
					}
					pro.setClassifylibraryid(classifyLibrary.getId());
					if(StringUtil.isNotEmpty(contractno)){
						Contractinfo contractinfo = contractinfoService.getContractinfoByNum(contractno.toUpperCase());
						if(contractinfo!=null){
							pro.setContractnum(contractno);
							pro.setContractname(contractinfo.getContract_name());
							pro.setContractmoney(contractinfo.getContract_money()==null ?null:contractinfo.getContract_money().toString());
						}
					}
					if(StringUtils.isEmpty(pro.getContractname())){
						map.put("seq:"+seq, "合同编号不存在！或合同名称为空");
					}
					
					if(StringUtils.isNotEmpty(proCharger)){
						List<SysUser> byUserName = userService.getByUserName(proCharger);
						if(byUserName==null || byUserName.size()<=0){
							byUserName=userService.getAllByUserNameWithOutCondition(proCharger);
						}
						if(byUserName==null || byUserName.size()<=0){
							map.put("seq:"+seq, "项目负责人系统未查询到！");
							continue;
						}else{
							boolean isFind=false;
							for (SysUser sysUser : byUserName) {
								if(isZxbUser(sysUser.getUserId())){
									pro.setApplicant(sysUser.getFullname());
									pro.setApplicantID(sysUser.getUserId()+"");
									isFind=true;
									break;
								}
							}
							if(!isFind){
								//没找到对应部门的  默认取查询到的第一个
								pro.setApplicant(byUserName.get(0).getFullname());
								pro.setApplicantID(byUserName.get(0).getUserId()+"");
							}
							if(StringUtils.isEmpty(pro.getApplicantID())){
								map.put("seq:"+seq, "项目负责人系统查询到，但不是咨询部的人！");
								continue;
							}
						}
					}
					if(StringUtils.isNotEmpty(proJoiner)){
						String[] split = StringUtils.split(proJoiner, "、");
						String usernames="";
						String userids="";
						for (String username : split) {
							List<SysUser> byUserName = userService.getByUserName(username);
							if(byUserName==null || byUserName.size()<=0){
								byUserName=userService.getAllByUserNameWithOutCondition(proCharger);
							}
							if(byUserName==null || byUserName.size()<=0){
								map.put("seq:"+seq, "项目组成员："+username+" 系统未查询到！");
							}else{
								boolean isFind=false;
								for (SysUser sysUser : byUserName) {
									if(isZxbUser(sysUser.getUserId())){
										usernames+=","+sysUser.getFullname();
										userids+=","+sysUser.getUserId();
										isFind=true;
										break;
									}
								}
								if(!isFind){
									//没找到对应部门的  默认取查询到的第一个
									usernames+=","+byUserName.get(0).getFullname();
									userids+=","+byUserName.get(0).getUserId();
								}
							}
						}
						if(usernames.length()>0){
							usernames=usernames.substring(1);
							userids=userids.substring(1);
						}
						if(split.length>usernames.split(",").length){
							map.put("seq:"+seq, "项目组部分成员名字 系统未查询到！");
							//continue;
						}else{
							pro.setMember(usernames);
							pro.setMemberID(userids);
						}
					}
					if(StringUtils.isNotEmpty(proStartTime)){
						Date parse = sdf.parse(proStartTime);
						pro.setExpectstarttime(parse);
					}
					if(StringUtils.isNotEmpty(proEndTime)){
						Date parse = sdf.parse(proEndTime);
						pro.setExpectendtime(parse);
					}
					listInfo.add(seq+":"+ReflectionToStringBuilder.toString(pro));
					pro.setStatus(proStatus);
					addStageAndStageTask(pro);
					
				}catch(Exception e){
					listError.add("第"+i+"行：错误:"+e.getMessage());
					log.error("错误信息",e);
				}
			}
			map.put("succData", listInfo);
			map.put("listError", listError);
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		
		return map;
	}
	
	@RequestMapping("project_sbb_file")
	public @ResponseBody Map<String,Object> importSbbFile(){
		Map<String,Object> map=new LinkedHashMap<>();
		String filename="水保部-项目导入.xlsx";
		InputStream is = request.getServletContext().getResourceAsStream(File.separator+"files"+File.separator+filename);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy.MM.dd");
		List<String> listInfo=new ArrayList<>();
		List<String> listError=new ArrayList<>();
		try {
			String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
			System.out.println("++++项目信息数据导入开始++++");
			sht = wkb.getSheetAt(0);
			// 得到总行数
			int rowNum = sht.getLastRowNum();
			headRow = sht.getRow(0);
			Date now = new Date();
			// 正文内容应该从第二行开始,第一行为表头的标题
			for (int i = 1; i <= rowNum; i++) {
				try {
					headRow = sht.getRow(i);
					String seq = getStringCellValue(headRow.getCell(0));
					String classifyName = getStringCellValue(headRow.getCell(2));
					String proName = getStringCellValue(headRow.getCell(3));
					String contractno = getStringCellValue(headRow.getCell(4));
					
					//String contractname = getStringCellValue(headRow.getCell(5));
					
					//String contract_money = getStringCellValue(headRow.getCell(5));
					String proCharger = getStringCellValue(headRow.getCell(5));
					String proJoiner = getStringCellValue(headRow.getCell(6));
					String proStartTime = getStringCellValue(headRow.getCell(7));
					if(proStartTime.contains(".")){
						Date parse = sdf1.parse(proStartTime);
						proStartTime=sdf.format(parse);
					}
					String proEndTime = getStringCellValue(headRow.getCell(8));
					if(proEndTime.contains(".")){
						Date parse = sdf1.parse(proEndTime);
						proEndTime=sdf.format(parse);
					}
					
					int proStatus = getStatus(getStringCellValue(headRow.getCell(9)));
					System.out.println(seq+" "+classifyName+" "+proName+" "
					+contractno+" "+proCharger
					+" "+proJoiner+" "+proStartTime+" "+proEndTime+" "+proStatus);
					Project pro=new Project();
					pro.setName(proName);
					
					pro.setCtime(new Date());
					pro.setIsdelete(0);
					if(StringUtils.isEmpty(classifyName)){
						map.put("seq:"+seq, "项目分类名不能为空");
						continue;
					}
					String importOrgId="10000007780656";//水保部
					ClassifyLibrary classifyLibrary = classifyLibraryService.getByNameAndOrgId(classifyName, importOrgId);//监理部的分类库名称
					if(classifyLibrary==null){
						map.put("seq:"+seq, "项目分类名不能为空");
						continue;
					}
					pro.setCuser("10000000010073");//黄春霞
					pro.setClassifylibraryid(classifyLibrary.getId());
					if(StringUtil.isNotEmpty(contractno)){
						Contractinfo contractinfo = contractinfoService.getContractinfoByNum(contractno.toUpperCase());
						if(contractinfo!=null){
							pro.setContractnum(contractno);
							pro.setContractname(contractinfo.getContract_name());
							pro.setContractmoney(contractinfo.getContract_money()==null ?null:contractinfo.getContract_money().toString());
						}
					}
					if(StringUtils.isEmpty(pro.getContractname())){
						map.put("seq:"+seq, "合同编号不存在！或合同名称为空");
					}
					
					if(StringUtils.isNotEmpty(proCharger)){
						List<SysUser> byUserName = userService.getByUserName(proCharger);
						if(byUserName==null || byUserName.size()<=0){
							byUserName=userService.getAllByUserNameWithOutCondition(proCharger);
						}
						if(byUserName==null || byUserName.size()<=0){
							map.put("seq:"+seq, "项目负责人系统未查询到！");
							continue;
						}else{
							boolean isFind=false;
							for (SysUser sysUser : byUserName) {
								if(isSbbUser(sysUser.getUserId())){
									pro.setApplicant(sysUser.getFullname());
									pro.setApplicantID(sysUser.getUserId()+"");
									isFind=true;
									break;
								}
							}
							if(!isFind){
								//没找到对应部门的  默认取查询到的第一个
								pro.setApplicant(byUserName.get(0).getFullname());
								pro.setApplicantID(byUserName.get(0).getUserId()+"");
							}
							if(StringUtils.isEmpty(pro.getApplicantID())){
								map.put("seq:"+seq, "项目负责人系统查询到，但不是水保部的人！");
								continue;
							}
						}
					}
					if(StringUtils.isNotEmpty(proJoiner)){
						String[] split = StringUtils.split(proJoiner, "、");
						String usernames="";
						String userids="";
						for (String username : split) {
							List<SysUser> byUserName = userService.getByUserName(username);
							if(byUserName==null || byUserName.size()<=0){
								byUserName=userService.getAllByUserNameWithOutCondition(proCharger);
							}
							if(byUserName==null || byUserName.size()<=0){
								map.put("seq:"+seq, "项目组成员："+username+" 系统未查询到！");
							}else{
								boolean isFind=false;
								for (SysUser sysUser : byUserName) {
									if(isSbbUser(sysUser.getUserId())){
										usernames+=","+sysUser.getFullname();
										userids+=","+sysUser.getUserId();
										isFind=true;
										break;
									}
								}
								if(!isFind){
									//没找到对应部门的  默认取查询到的第一个
									usernames+=","+byUserName.get(0).getFullname();
									userids+=","+byUserName.get(0).getUserId();
								}
							}
						}
						if(usernames.length()>0){
							usernames=usernames.substring(1);
							userids=userids.substring(1);
						}
						if(split.length>usernames.split(",").length){
							map.put("seq:"+seq, "项目组部分成员名字 系统未查询到！");
							//continue;
						}else{
							pro.setMember(usernames);
							pro.setMemberID(userids);
						}
					}
					if(StringUtils.isNotEmpty(proStartTime)){
						Date parse = sdf.parse(proStartTime);
						pro.setExpectstarttime(parse);
					}
					if(StringUtils.isNotEmpty(proEndTime)){
						Date parse = sdf.parse(proEndTime);
						pro.setExpectendtime(parse);
					}
					listInfo.add(seq+":"+ReflectionToStringBuilder.toString(pro));
					pro.setStatus(proStatus);
					addStageAndStageTask(pro);
					
				}catch(Exception e){
					listError.add("第"+i+"行：错误:"+e.getMessage());
					log.error("错误信息",e);
				}
			}
			map.put("succData", listInfo);
			map.put("listError", listError);
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		
		return map;
	}
	
	@RequestMapping("project_jlb_file")
	public @ResponseBody Map<String,Object> importJlBFile(){
		Map<String,Object> map=new LinkedHashMap<>();
		String filename="监理部oa系统项目管理在建项目基本信息采集表.xlsx";
		InputStream is = request.getServletContext().getResourceAsStream(File.separator+"files"+File.separator+filename);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy.MM.dd");
		List<String> listInfo=new ArrayList<>();
		List<String> listError=new ArrayList<>();
		try {
			String fileType = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
			if (fileType.equals("xls")) {
				wkb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wkb = new XSSFWorkbook(is);
			} else {
				throw new RuntimeException("上传文件的格式不正确");
			}
			System.out.println("++++项目信息数据导入开始++++");
			sht = wkb.getSheetAt(0);
			// 得到总行数
			int rowNum = sht.getLastRowNum();
			headRow = sht.getRow(0);
			Date now = new Date();
			// 正文内容应该从第二行开始,第一行为表头的标题
			for (int i = 1; i <= rowNum; i++) {
				try {
					headRow = sht.getRow(i);
					String seq = getStringCellValue(headRow.getCell(0));
					String classifyName = getStringCellValue(headRow.getCell(2));
					String proName = getStringCellValue(headRow.getCell(3));
					String contractno = getStringCellValue(headRow.getCell(4));
					
					//String contractname = getStringCellValue(headRow.getCell(5));
					
					//String contract_money = getStringCellValue(headRow.getCell(5));
					String proCharger = getStringCellValue(headRow.getCell(5));
					String proJoiner = getStringCellValue(headRow.getCell(6));
					String proStartTime = getStringCellValue(headRow.getCell(7));
					if(proStartTime.contains(".")){
						Date parse = sdf1.parse(proStartTime);
						proStartTime=sdf.format(parse);
					}
					String proEndTime = getStringCellValue(headRow.getCell(8));
					if(proEndTime.contains(".")){
						Date parse = sdf1.parse(proEndTime);
						proEndTime=sdf.format(parse);
					}
					
					int proStatus = getStatus(getStringCellValue(headRow.getCell(9)));
					System.out.println(seq+" "+classifyName+" "+proName+" "
					+contractno+" "+proCharger
					+" "+proJoiner+" "+proStartTime+" "+proEndTime+" "+proStatus);
					Project pro=new Project();
					pro.setName(proName);
					
					pro.setCtime(new Date());
					pro.setIsdelete(0);
					if(StringUtils.isEmpty(classifyName)){
						map.put("seq:"+seq, "项目分类名不能为空");
						continue;
					}
					String importOrgId="10000007780857";//监理部
					
					ClassifyLibrary classifyLibrary = classifyLibraryService.getByNameAndOrgId(classifyName, importOrgId);//监理部的分类库名称
					if(classifyLibrary==null){
						map.put("seq:"+seq, "项目分类名不能为空");
						continue;
					}
					pro.setCuser("10000000010023");//监理部默认孙晖创建的
					pro.setClassifylibraryid(classifyLibrary.getId());
					if(StringUtil.isNotEmpty(contractno)){
						Contractinfo contractinfo = contractinfoService.getContractinfoByNum(contractno.toUpperCase());
						if(contractinfo!=null){
							pro.setContractnum(contractno);
							pro.setContractname(contractinfo.getContract_name());
							pro.setContractmoney(contractinfo.getContract_money()==null ?null:contractinfo.getContract_money().toString());
						}
					}
					if(StringUtils.isEmpty(pro.getContractname())){
						map.put("seq:"+seq, "合同编号不存在！或合同名称为空");
					}
					
					if(StringUtils.isNotEmpty(proCharger)){
						List<SysUser> byUserName = userService.getByUserName(proCharger);
						if(byUserName==null || byUserName.size()<=0){
							byUserName=userService.getAllByUserNameWithOutCondition(proCharger);
						}
						if(byUserName==null || byUserName.size()<=0){
							map.put("seq:"+seq, "项目负责人系统未查询到！");
							continue;
						}else{
							boolean isFind=false;
							for (SysUser sysUser : byUserName) {
								if(isJlbUser(sysUser.getUserId())){
									pro.setApplicant(sysUser.getFullname());
									pro.setApplicantID(sysUser.getUserId()+"");
									isFind=true;
									break;
								}
							}
							if(!isFind){
								//没找到对应部门的  默认取查询到的第一个
								pro.setApplicant(byUserName.get(0).getFullname());
								pro.setApplicantID(byUserName.get(0).getUserId()+"");
							}
							if(StringUtils.isEmpty(pro.getApplicantID())){
								map.put("seq:"+seq, "项目负责人系统查询到，但不是监理部的人！");
								continue;
							}
						}
					}
					if(StringUtils.isNotEmpty(proJoiner)){
						String[] split = StringUtils.split(proJoiner, "、");
						String usernames="";
						String userids="";
						for (String username : split) {
							List<SysUser> byUserName = userService.getByUserName(username);
							if(byUserName==null || byUserName.size()<=0){
								byUserName=userService.getAllByUserNameWithOutCondition(proCharger);
							}
							if(byUserName==null || byUserName.size()<=0){
								map.put("seq:"+seq, "项目组成员："+username+" 系统未查询到！");
							}else{
								boolean isFind=false;
								for (SysUser sysUser : byUserName) {
									if(isJlbUser(sysUser.getUserId())){
										usernames+=","+sysUser.getFullname();
										userids+=","+sysUser.getUserId();
										isFind=true;
										break;
									}
								}
								if(!isFind){
									//没找到对应部门的  默认取查询到的第一个
									usernames+=","+byUserName.get(0).getFullname();
									userids+=","+byUserName.get(0).getUserId();
								}
							}
						}
						if(usernames.length()>0){
							usernames=usernames.substring(1);
							userids=userids.substring(1);
						}
						if(split.length>usernames.split(",").length){
							map.put("seq:"+seq, "项目组部分成员名字 系统未查询到！");
							//continue;
						}else{
							pro.setMember(usernames);
							pro.setMemberID(userids);
						}
					}
					if(StringUtils.isNotEmpty(proStartTime)){
						Date parse = sdf.parse(proStartTime);
						pro.setExpectstarttime(parse);
					}
					if(StringUtils.isNotEmpty(proEndTime)){
						Date parse = sdf.parse(proEndTime);
						pro.setExpectendtime(parse);
					}
					listInfo.add(seq+":"+ReflectionToStringBuilder.toString(pro));
					pro.setStatus(proStatus);
					addStageAndStageTask(pro);
					
				}catch(Exception e){
					listError.add("第"+i+"行：错误:"+e.getMessage());
					log.error("错误信息",e);
				}
			}
			map.put("succData", listInfo);
			map.put("listError", listError);
		} catch (IOException e) {
			throw new RuntimeException("文件解析错误");
		}
		
		return map;
	}
	//监理部 方法名写错了
	private boolean isJlbUser(Long userId){
		boolean isHjsyb=false;
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(userId);
		for (SysOrg sysOrg : orgsByUserId) {
			String path = sysOrg.getPath();
			if(path.contains("10000007780857")){
				//监理部的人
				isHjsyb=true;
			}
		}
		return isHjsyb;
	}
	private boolean isZxbUser(Long userId){
		boolean isZxyb=false;
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(userId);
		for (SysOrg sysOrg : orgsByUserId) {
			String path = sysOrg.getPath();
			if(path.contains("10000011000055")){
				//监理部的人
				isZxyb=true;
			}
		}
		return isZxyb;
	}
	private boolean isSbbUser(Long userId){
		boolean isSbb=false;
		List<SysOrg> orgsByUserId = sysOrgService.getOrgsByUserId(userId);
		for (SysOrg sysOrg : orgsByUserId) {
			String path = sysOrg.getPath();
			if(path.contains("10000007780656")){
				//水保部的人
				isSbb=true;
			}
		}
		return isSbb;
	}
	private int getStatus(String name){
		int status=1;
		switch (name) {
		case "未开工":
			status=0;
			break;
		case "在建":
			status=1;
			break;
		case "停工":
			status=2;
			break;
		case "已完工":
			status=3;
			break;
		case "完工":
			status=3;
			break;
		case "建设期":
			status=4;
			break;
		case "运营期":
			status=5;
			break;
		default:
			break;
		}
		return status;
	}
	private String getStringCellValue(Cell cell) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String strCell = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue().trim();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				Date dateCellValue = cell.getDateCellValue();
				strCell= sdf.format(dateCellValue);
			} else
				strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell.trim();
	}
	private  void addStageAndStageTask(Project project){
		projectService.add(project);//返回主键
		int proId =project.getId(); 
		//复制阶段信息,阶段下任务信息
		List<ClassifyStage> clStages = classifyStageService.getByClassifyId(project.getClassifylibraryid());
		for(ClassifyStage stage : clStages){
			Long clStageId = stage.getId();
			ProjectStage proStage = new ProjectStage(proId,stage.getStagename(),stage.getStageno(),stage.getStagetype(),stage.getOrder(),
					stage.getId(),stage.getPrestage(),stage.getAfterstage());
			stageService.add(proStage);
			int proStageId = proStage.getId();
			List<ClassifyStageTask> clTasks = classifyTaskService.getStageTaskByStageId(clStageId);
			for(ClassifyStageTask task :clTasks){
				ProjectStageTask proStageTask = new ProjectStageTask(proStageId,task.getTaskno(),task.getTaskname(),task.getTemplatefile(),
						task.getTasktype(),task.getRemark(),task.getIsexamine(),task.getIsmore(),task.getOrder(),task.getFields(),task.getUploadfile(),
						task.getFlowid(),task.getId()+"",task.getPreclassifystagetaskid(),task.getAfterclassifystagetaskid(),task.getQueryclassifystagetaskid(),0);
				taskService.add(proStageTask);
			}
			
		}
	}
}
