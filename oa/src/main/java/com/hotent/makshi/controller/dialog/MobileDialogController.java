package com.hotent.makshi.controller.dialog;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.page.PageBean;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.service.contract.ContractinfoService;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping(value={"/makshi/mobiledialog/","/weixin/mobiledialog/"})
public class MobileDialogController extends BaseController{
	private final Logger log = Logger.getLogger(this.getClass());
	private static final String contractDialogalias="contractinfo";
	private static final String contractinfoHandle="contractinfoHandle";
	
	@Resource
	private ContractinfoService contractinfoService;
	@Resource
	SysUserService sysUserService;
	
	@RequestMapping("show")
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response){
		String alias = RequestUtil.getString(request, "dialog_alias");
		QueryFilter queryFilter=new QueryFilter(request);
		if(contractDialogalias.equals(alias)){
			ModelAndView mv=new ModelAndView("/makshi/dialog/contractdialogList.jsp");
			return showContract(request,response,mv,queryFilter);
		}else if(contractinfoHandle.equals(alias)){
			SysUser user = sysUserService.getUserById(RequestUtil.getLong(request, "userId"));
			queryFilter.addFilter("handlerName", user.getFullname());
			ModelAndView mv=new ModelAndView("/makshi/dialog/contractdialogHandelList.jsp");
			return showContract(request,response,mv,queryFilter);
		}
		return null;
	}
	@RequestMapping("showContract")
	public ModelAndView showContract(HttpServletRequest request, HttpServletResponse response,ModelAndView mv,QueryFilter queryFilter){
		int page = RequestUtil.getInt(request, "page",1);
		int pageSize = RequestUtil.getInt(request, "pageSize",10);
		PageBean pageBean = queryFilter.getPageBean();
		pageBean.setCurrentPage(page);
		pageBean.setPagesize(pageSize);
		List<Contractinfo> all = contractinfoService.getListForMobileDialog(queryFilter);
		JSONObject result=new JSONObject();
		if(page>1){
			//异步
			result.put("status", "1");
			result.put("data", all==null?new JSONArray().toString():JSONArray.fromObject(all));
			try {
				response.getWriter().print(result.toString());
			} catch (IOException e) {
				log.error("错误信息",e);
			}
			return null;
		}
		mv.addObject("page", pageBean).addObject("contractList", all);
		
		return mv;
	}
	
}
