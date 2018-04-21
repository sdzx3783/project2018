

package com.hotent.makshi.controller.renthouseregistration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;

import org.springframework.web.servlet.ModelAndView;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.page.PageList;
import com.hotent.platform.model.system.SysUser;
import org.apache.commons.lang.exception.ExceptionUtils;
import com.hotent.core.bpm.util.BpmUtil;
import net.sf.json.JSONObject;
import com.hotent.core.util.MapUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.makshi.model.assetregistration.AssetRegistration;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.renthouseregistration.RentHouseRegistration;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.makshi.service.renthouseregistration.RentHouseRegistrationService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.makshi.utils.ExcelUtil;
import com.hotent.makshi.utils.FileUtil;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:租房登记表 控制器类
 */
@Controller		
@RequestMapping("/makshi/renthouseregistration/rentHouseRegistration/")
public class RentHouseRegistrationController extends BaseController
{
	@Resource
	private RentHouseRegistrationService rentHouseRegistrationService;
	@Resource
	private ChangeHistoryService changeHistoryService;
	/**
	 * 添加或更新租房登记表。
	 * @param request
	 * @param response
	 * @param rentHouseRegistration 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新租房登记表")
	public void save(HttpServletRequest request, HttpServletResponse response,RentHouseRegistration rentHouseRegistration) throws Exception
	{
		String resultMsg=null;		
		try{
			if(rentHouseRegistration.getId()==null){
				rentHouseRegistrationService.save(rentHouseRegistration);
				resultMsg=getText("添加","租房登记表");
			}else{
			    rentHouseRegistrationService.save(rentHouseRegistration);
				resultMsg=getText("更新","租房登记表");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得租房登记表分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看租房登记表分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<RentHouseRegistration> list=rentHouseRegistrationService.getAll(new QueryFilter(request,"rentHouseRegistrationItem"));
		ModelAndView mv=this.getAutoView().addObject("rentHouseRegistrationList",list);
		return mv;
	}
	
	/**
	 * 删除租房登记表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除租房登记表")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			rentHouseRegistrationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除租房登记表成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑租房登记表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑租房登记表")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		RentHouseRegistration rentHouseRegistration=rentHouseRegistrationService.getById(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RentHouseRegistration", rentHouseRegistration);
		map.put("queryType", "RentHouseRegistration");
		map.put("queryCondition", id+"");
		request.getSession().setAttribute("historyData", map);
		
		return getAutoView().addObject("rentHouseRegistration",rentHouseRegistration)
							.addObject("returnUrl",returnUrl);
	}
	/**
	 * 取得租房变更历史
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("historyList")
	public ModelAndView getChangeHisByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("/makshi/renthouseregistration/renthouseRegistrationHistoryList.jsp");
		mv = getChangeHisView(request, response, mv, 1);
		return mv;
	}
	/**
	 * 取得租房变更历史
	 * 
	 * @param request
	 * @param response
	 * @param isOtherLink
	 * @return
	 * @throws Exception
	 */
	@Action(description = "用户变更历史", execOrder = ActionExecOrder.AFTER, detail = "用户变更历史", exectype = "管理日志")
	public ModelAndView getChangeHisView(HttpServletRequest request, HttpServletResponse response, ModelAndView mv, int isOtherLink) throws Exception {
		Long id=RequestUtil.getLong(request,"id");
		long canReturn = RequestUtil.getLong(request, "canReturn", 0);
		//查看的类型
		String openType = RequestUtil.getString(request, "openType", "");
		//获取基本信息
		RentHouseRegistration rentHouseRegistration=rentHouseRegistrationService.getById(id);
		List<WChangeHistory> changeHisList=null;
		if (rentHouseRegistration != null) {
			changeHisList = changeHistoryService.getListByType("RentHouseRegistration", id+"");
		}
		
		String returnUrl = RequestUtil.getPrePage(request);
		return mv.addObject("assetRegistration", rentHouseRegistration).addObject("canReturn", canReturn).addObject("returnUrl", returnUrl).addObject("isOtherLink", isOtherLink).addObject("openType", openType).addObject("changeHisList", changeHisList);
	}

	/**
	 * 取得租房登记表明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看租房登记表明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		RentHouseRegistration rentHouseRegistration=rentHouseRegistrationService.getById(id);
		return getAutoView().addObject("rentHouseRegistration", rentHouseRegistration);
	}
	
	/**
	 * 导出记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("upload")
	@Action(description = "导出租房记录")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object[]> dataList = new ArrayList<>();
		String[] rowName = new String[] { "序号","租房编号","部门名称","房屋面积(平米)","房租金额(元/月)", "经办人", "房屋押金", "入住员工姓名","房屋结构", "承租人", "租房性质", "房屋地址", "住宿人数", "宿舍负责人", "租房开始日期", "租房停止日期 ", "备注" };
		QueryFilter filter = new QueryFilter(request, "rentHouseRegistrationItem");
		filter.setPageBean(null);
		List<RentHouseRegistration> list = rentHouseRegistrationService.getAll(filter);
		for (RentHouseRegistration ls : list) {
			// 填充数据
			int count = 1;
			Object[] object = new Object[rowName.length];
			object[count++] = ls.getHouse_id();
			object[count++] = ls.getDepartment();
			object[count++] = ls.getSize();
			object[count++] = ls.getMoney();
			object[count++] = ls.getHandle_person();
			object[count++] = ls.getDeposit();
			object[count++] = ls.getLandlord();
			if(StringUtil.isNotEmpty(ls.getHouse_type())){
				object[count++] = ls.getHouse_type()+"房";
			}else{
				object[count++] = ls.getHouse_type();
			}
			object[count++] = ls.getRent_person();
			object[count++] = getRentType(ls.getRent_type());
			object[count++] = ls.getAddress();
			object[count++] = ls.getNumber_people();
			object[count++] = ls.getResponsible_person();
			object[count++] = setDate(ls.getStart_date());
			object[count++] = setDate(ls.getEnd_date());
			object[count] = ls.getRemarks();
			dataList.add(object);
		}
		InputStream export = new ExcelUtil().export(rowName, dataList, "深水水务咨询有限公司租房情况登记表");
		String filename = "租房记录" + DateUtils.format(new Date(), "yyyyMMdd") + ".xlsx";
		String sheetName = FileUtil.encodeDownloadFilename(filename, request.getHeader("user-agent"));
		// 设置文件输出类型
		response.setContentType(request.getServletContext().getMimeType(filename));
		response.setHeader("Content-disposition", "attachment; filename=" + sheetName);
		// 设置输出长度
		response.setHeader("Content-Length", String.valueOf(export.available()));
		// 获取输入流
		BufferedInputStream bis = new BufferedInputStream(export);
		// 输出流
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		// 关闭流
		bis.close();
		bos.close();
	}

	private Object getRentType(String rent_type) {
		if (("1").equals(rent_type)) {
			return "办公";
		}
		if (("2").equals(rent_type)) {
			return "宿舍";
		}
		if (("3").equals(rent_type)) {
			return "办公兼宿舍";
		}
		return null;
	}

	private Object setDate(Date date) {
		if (null != date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
		return null;
	}

}