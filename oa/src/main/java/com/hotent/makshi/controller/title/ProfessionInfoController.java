

package com.hotent.makshi.controller.title;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.db.datasource.DbContextHolder;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.title.ProfessionInfo;
import com.hotent.makshi.service.title.ProfessionInfoService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.bpm.BpmFormQuery;
import com.hotent.platform.model.form.QueryResult;
import com.hotent.platform.service.bpm.BpmFormQueryService;
/**
 * 对象功能:个人执业印章 控制器类
 */
@Controller
@RequestMapping("/makshi/title/professionInfo/")
public class ProfessionInfoController extends BaseController
{
	@Resource
	private ProfessionInfoService professionInfoService;
	
	@Resource
	private BpmFormQueryService bpmFormQueryService;
	
	/**
	 * 添加或更新个人执业印章。
	 * @param request
	 * @param response
	 * @param personalSeal 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping("save")
//	@Action(description="添加或更新个人执业印章")
//	public void save(HttpServletRequest request, HttpServletResponse response,PersonalSeal personalSeal) throws Exception
//	{
//		String resultMsg=null;		
//		try{
//			if(personalSeal.getId()==null){
//				professionInfoService.save(personalSeal);
//				resultMsg=getText("添加","个人执业印章");
//			}else{
//			    professionInfoService.save(personalSeal);
//				resultMsg=getText("更新","个人执业印章");
//			}
//			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
//		}catch(Exception e){
//			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
//		}
//	}
	
	/**
	 * 职素报表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看个人执业印章分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ProfessionInfo> list=professionInfoService.getAll(new QueryFilter(request,"professionInfoItem"));
		
		QueryResult result=getProfessionName();
		ModelAndView mv=this.getAutoView();
		if(null!=result && result.getList()!=null && result.getList().size()>0){
			mv.addObject("professionNameList", result.getList());
		}
		mv.addObject("professionInfoList",list);
		return mv;
	}
	
	@RequestMapping("export")
	@Action(description="查看个人执业印章分页列表")
	public void export(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<Object[]> dataList=new ArrayList<>();
		String[] rowName=new String[]{"序号","员工编号","姓名","部门","性别","入职日期","身份证号","专业","职称","毕业院校",
				"建设部监理工程师","建设部造价工程师","一级建造师","二级建造师","水利部总监证","水利部监理工程师","水利部造价工程师",
				"水利部监理员","一级结构工程师","二级结构工程师","招标师","注册设备监理工程师","注册公用设备工程师","注册安全工程师",
				"咨询工程师(投资)","投资项目管理师","测绘师","信息监理工程师","系统集成项目管理工程师"};
		QueryFilter queryFilter = new QueryFilter(request,"professionInfoItem");
		queryFilter.setPageBean(null);
		List<ProfessionInfo> list=professionInfoService.getAll(queryFilter);
		for (ProfessionInfo ls :list) {
			//填充数据
			int count=1;
			Object[] object=new Object[rowName.length];
			object[count++]=ls.getAccount();
			object[count++]=ls.getName();
			if(!ls.getOrgName().equals("深水咨询")){					
				ls.setOrgName(ls.getOrgName().replace("/深水咨询/", ""));
			}
			object[count++]=ls.getOrgName();
			object[count++]=Integer.valueOf(ls.getSex())==0?"女":"男";
			object[count++]=DateUtil.formatDate(ls.getIntime(), "yyyy-MM-dd");
			object[count++]=ls.getIdcard();
			object[count++]=ls.getProfession();
			object[count++]=ls.getTitle();
			object[count++]=ls.getSchool();
			object[count++]=Integer.valueOf(ls.getBuildSupervisionEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getBuildCostEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getFirstBuilder())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getSecondBuilder())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getWaterDirector())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getWaterSupervisionEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getWaterCostEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getSupervisor())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getFirstStructuralEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getSecondStructuralEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getBidder())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getRegisteredEquipmentEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getRegisteredUtilityEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getRegisteredSafetyEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getConsultingEngineer())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getInvestmentSubjectManage())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getCartographers())==0?"":"√";
			object[count++]=Integer.valueOf(ls.getInformationEngineer())==0?"":"√";
			object[count]=Integer.valueOf(ls.getProjectManagementEngineer())==0?"":"√";
			//object[count]=ls.getTitle();
			dataList.add(object);
		}
		InputStream export = new ExcelUtil().export(rowName, dataList, "职素报表");
		String filename = "职素报表"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		//设置文件输出类型
	    response.setContentType(request.getServletContext().getMimeType(filename));  
	    response.setHeader("Content-disposition", "attachment; filename="  
	        + sheetName); 
	    //设置输出长度
	    response.setHeader("Content-Length", String.valueOf(export.available()));  
	    //获取输入流
	    BufferedInputStream bis = new BufferedInputStream(export);  
	    //输出流
	    BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());  
	    byte[] buff = new byte[2048];  
	    int bytesRead;  
	    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	      bos.write(buff, 0, bytesRead);  
	    }  
	    //关闭流
	    bis.close();
	    bos.close(); 
	}
	
	
	public QueryResult getProfessionName() throws Exception{
		QueryResult result = new QueryResult();
		String alias = "professionNames";
		String queryData ="";
		BpmFormQuery bpmFormQuery = bpmFormQueryService.getByAlias(alias);
		//TODO 为什么不能在bpmFormQueryService换？因为service有事务保护着，只用一个链接
//		DbContextHolder.setDataSource(bpmFormQuery.getDsalias());
		
		if (bpmFormQuery != null) {
			result = bpmFormQueryService.getData(bpmFormQuery, queryData, 0, 0);
		} else
			result.setErrors("查询别名不正确，未能获取到该别名的查询对象。");
		
		//TODO 这样不太妥，应该在清空数据源之前调用的service中增加一句- -
		DbContextHolder.clearDataSource();
		DbContextHolder.setDefaultDataSource();
		return result;
	}

}