

package com.hotent.makshi.controller.contract;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.StringUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.contract.ContractNumAdmin;
import com.hotent.makshi.model.contract.ContractNumSecond;
import com.hotent.makshi.model.contract.ContractNumYear;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.contract.ContractNumAdminService;
import com.hotent.makshi.service.contract.ContractNumYearService;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.service.system.IdentityService;
/**
 * 对象功能:合同编号管理 控制器类
 */
@Controller
@RequestMapping("/makshi/contract/contractNumAdmin/")
public class ContractNumAdminController extends BaseController
{
	@Resource
	private ContractNumAdminService contractNumAdminService;
	@Resource
	private IdentityService identityService;
	@Resource
	private ContractinfoService contractInfoService;
	@Resource
	private ContractNumYearService contractNumYearService;
	/**
	 * 添加或更新合同编号管理。
	 * @param request
	 * @param response
	 * @param contractNumAdmin 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新合同编号管理")
	public void save(HttpServletRequest request, HttpServletResponse response,ContractNumAdmin contractNumAdmin) throws Exception
	{
		String resultMsg=null;		
		//Integer year = 2017;
		try{
			if(contractNumAdmin.getId()==null){
				contractNumAdminService.save(contractNumAdmin);			
				resultMsg=getText("添加","合同编号管理");
			}else{
			    contractNumAdminService.save(contractNumAdmin);
				resultMsg=getText("更新","合同编号管理");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得合同编号管理分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看合同编号管理分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String year = RequestUtil.getString(request, "year");
		if(StringUtil.isEmpty(year)){
			 year = DateUtils.getDefinedYear(0,"yyyy");
		}
		List<ContractNumAdmin> list=contractNumAdminService.getLoadList();
		for(ContractNumAdmin contractNumAdmin : list){
			Long contractId = contractNumAdmin.getId();
			ContractNumYear contractNumYear = contractNumYearService.getByRefIdAndYear(contractId,year);
			if(null!=contractNumYear){
				contractNumAdmin.setFlowNo(contractNumYear.getFlowNo());
			}else{
				contractNumAdmin.setFlowNo("1");
			}
		}
		ModelAndView mv=this.getAutoView().addObject("contractNumAdminList",list);
		return mv.addObject("curYear",DateUtils.getDefinedYear(0,"yyyy"));
	}
	
	/**
	 * 删除合同编号管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除合同编号管理")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			contractNumAdminService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success,"删除成功!");
			contractNumAdminService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑合同编号管理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑合同编号管理")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String year = DateUtils.getDefinedYear(0,"yyyy");
		year = RequestUtil.getString(request, "year");
		String returnUrl=RequestUtil.getPrePage(request);
		ContractNumAdmin contractNumAdmin=contractNumAdminService.getById(id);
		Long contractId = contractNumAdmin.getId();
		ContractNumYear contractNumYear = contractNumYearService.getByRefIdAndYear(contractId,year);
		if(null!=contractNumYear){
			contractNumAdmin.setFlowNo(contractNumYear.getFlowNo());
		}else{
			contractNumAdmin.setFlowNo("0");
		}
		/*
		if(null==contractNumAdmin){
			String contractNo_id=identityService.nextId("contractNo");
			contractNumAdmin.setContractNo(contractNo_id);
		}*/
		List<ContractNumSecond> contractNumSecondList=contractNumAdminService.getContractNumSecondList(id);
		return getAutoView().addObject("contractNumAdmin",contractNumAdmin)
							.addObject("contractNumSecondList",contractNumSecondList)
							.addObject("returnUrl",returnUrl)
							.addObject("curYear",year);
	}

	/**
	 * 取得合同编号管理明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看合同编号管理明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		Map<String,Object> params = new HashMap<String,Object>();
		QueryFilter filter = new QueryFilter(request,"contractinfoItem");
		ContractNumAdmin contractNumAdmin=contractNumAdminService.getById(id);
		String contracType = contractNumAdmin.getContract_num();
		String typeName = contractNumAdmin.getType();
		params.put("contracType", contracType);
		filter.addFilter("contracType", contracType);
		boolean isJL = false;
		if(id==10000000142946L){
			isJL = true;
		}
		List<ContractNumAdmin> list = contractNumAdminService.getAll(filter);
	//	List<Contractinfo> infoList = contractInfoService.getAll(filter);
		//List<ContractNumSecond> contractNumSecondList=contractNumAdminService.getContractNumSecondList(id);
		return getAutoView().addObject("infoList",list).addObject("typeName",typeName).addObject("contracType",contracType).addObject("isJL",isJL);
	}
	/**
	 * 导出通讯录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception	
	 */
	@RequestMapping("upload")
	@Action(description="导出信息")
	public void upload(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Map<String, Object> param = new HashMap<>();
		String contracType = request.getParameter("contracType");
		param.put("contracType", contracType);
		List<Object[]> dataList=new ArrayList<>();
		String[] rowName=new String[]{"序号","合同编号","使用人","合同名称"};
		if(("JL").equals(contracType)){
			 rowName=new String[]{"序号","合同编号","合同类型","使用人","合同名称"};
		}
		//filter.addFilter("orderField", "cast(SUBSTR(F_contract_num,(LOCATE('-',F_contract_num)-4),4)as signed) desc,cast( RIGHT(F_contract_num,(LENGTH(F_contract_num)-LOCATE('-',F_contract_num)))  as signed)");
		List<Contractinfo> list = contractInfoService.getNumUpLoad(param);
			for (Contractinfo ls :list) {
				//填充数据
				int count=0;
				Object[] object=new Object[rowName.length];
				object[count++]=ls.getContract_num();
				object[count++]=ls.getContract_num();
				if(("JL").equals(contracType)){
					object[count++]=ls.getType();
				}
				object[count++]=ls.getContract_handler();
				object[count++]=ls.getContract_name();
				dataList.add(object);
			}
		InputStream export = new ExcelUtil().export(rowName, dataList, "合同编号信息表");
		String filename = "合同编号信息表"+DateUtils.format(new Date(),"yyyyMMdd")+".xlsx";
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
	
}