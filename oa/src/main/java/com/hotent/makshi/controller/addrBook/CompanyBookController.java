

package com.hotent.makshi.controller.addrBook;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.api.org.model.ISysOrg;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.addrBook.CompanyBook;
import com.hotent.makshi.service.addrBook.CompanyBookService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.Demension;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.service.mail.OutMailUserSetingService;
import com.hotent.platform.service.system.DemensionService;
import com.hotent.platform.service.system.SysOrgService;
/**
 * 对象功能:测试表 控制器类
 */
@Controller
@RequestMapping("/makshi/addrBook/companyBook/")
public class CompanyBookController extends BaseController
{
	@Resource
	private CompanyBookService companyBookService;
	
	@Resource
	private SysOrgService sysOrgService;
	
	@Resource
	private DemensionService demensionService;
	
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	
	/**
	 * 取得测试表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception	
	 */
	@RequestMapping("list")
	@Action(description="查看测试表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Long orgId = RequestUtil.getLong(request, "orgId");
		QueryFilter queryFilter = new QueryFilter(request, "companyBookItem");
		SysOrg sysOrg = sysOrgService.getById(orgId);
		if(sysOrg!=null && "深水咨询".equals(sysOrg.getOrgName())){
			queryFilter.getFilters().remove("orgId");
		}
		List<CompanyBook> list = companyBookService.getAll(queryFilter);
		for (CompanyBook ls :list) {
			if(!ls.getOrgName().equals("深水咨询")){					
				ls.setOrgName(ls.getOrgPathName().replace("/深水咨询/", ""));
			}
		}
		ModelAndView mv=this.getAutoView().addObject("companyBookList",list);
		if(orgId!=0){
			mv.addObject("orgId", orgId);
		}
		return mv;
	}
	/**
	 * 导出通讯录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception	
	 */
	@RequestMapping("upload")
	@Action(description="导出通讯录")
	public void upload(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Map<String, Object> param = new HashMap<>();
		Map<String, Object> querymap = RequestUtil.getQueryMap(request);
		for(Map.Entry<String, Object> map:querymap.entrySet()){
			param.put(map.getKey(), map.getValue());
			if(map.getKey().equals("orgId")){			
				SysOrg sysOrg = sysOrgService.getById(Long.valueOf(map.getValue().toString()));
				if(sysOrg!=null && "深水咨询".equals(sysOrg.getOrgName())){
					param.remove(map.getKey());
				}
			}
		}
		List<Object[]> dataList=new ArrayList<>();
		String[] rowName=new String[]{"序号","工号","姓名","性别","部门","岗位","办公电话","短号","手机"};
		List<CompanyBook> list = companyBookService.exportAll(param);
		for (CompanyBook ls :list) {
			//填充数据
			int count=0;
			Object[] object=new Object[rowName.length];
			object[count++]=ls.getAccount();
			object[count++]=ls.getAccount();
			object[count++]=ls.getUserName();
			object[count++]=ls.getSex()==0?"女":"男";
			if(!ls.getOrgName().equals("深水咨询")){					
				ls.setOrgName(ls.getOrgPathName().replace("/深水咨询/", ""));
			}
			object[count++]=ls.getOrgName();
			object[count++]=ls.getPosName();
			object[count++]=ls.getPhone();
			object[count++]=ls.getSjdh();
			object[count]=ls.getMobile();
			dataList.add(object);
		}
		InputStream export = new ExcelUtil().export(rowName, dataList, "通讯录");
		String filename = "通讯录"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx";
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
	@RequestMapping("toList")
	public ModelAndView toList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView autoView = getAutoView();
		//维度下拉框回显
		List<Demension> demensionList = demensionService.getAll();
		ISysOrg currentOrg = ContextUtil.getCurrentOrg();
		String currentOrgId="-1";
		if(currentOrg!=null){
			String orgPath = currentOrg.getPath();
			if(StringUtils.isNotEmpty(orgPath)){
				String[] split = orgPath.trim().split("\\.");
				if(split.length>=0){
					currentOrgId=split[1];
					if(split.length>=3 && StringUtils.isNotEmpty(split[2])){
						currentOrgId=split[2];
					}
				}
			}
		}
		
		return autoView.addObject("demensionList", demensionList).addObject("currentOrgId", currentOrgId);
	}
	@RequestMapping("getEmpty")
	public ModelAndView getEmpty(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView autoView = getAutoView();
		return autoView;
	}
	
	@RequestMapping("getMailUserSetingCount")
	public void warn(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int count=outMailUserSetingService.getCountByUserId(ContextUtil.getCurrentUserId());
		ResultMessage resultMessage=new ResultMessage(ResultMessage.Success, String.valueOf(count));
		response.getWriter().print(resultMessage);
	}
	
	@RequestMapping("selector")
	@Action(description="联系人选择框")
	public ModelAndView selector(HttpServletRequest request, HttpServletResponse response) throws Exception
	{  
		
		QueryFilter queryFilter= new QueryFilter(request,"oaLinkmanItem");
		List<CompanyBook> list =  companyBookService.getAll(queryFilter);
		ModelAndView mv=this.getAutoView().addObject("oaLinkmanList",list);
		return mv;
	}
}