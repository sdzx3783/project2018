

package com.hotent.makshi.controller.fontsize;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotent.platform.annotion.Action;
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

import com.hotent.makshi.model.fontsize.FontSize;
import com.hotent.makshi.service.fontsize.FontSizeService;
import com.hotent.core.web.ResultMessage;
/**
 * 对象功能:公文字号 控制器类
 */
@Controller
@RequestMapping("/makshi/fontsize/fontSize/")
public class FontSizeController extends BaseController
{
	@Resource
	private FontSizeService fontSizeService;
	/**
	 * 添加或更新公文字号。
	 * @param request
	 * @param response
	 * @param fontSize 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新公文字号")
	public void save(HttpServletRequest request, HttpServletResponse response,FontSize fontSize) throws Exception
	{
		String resultMsg=null;	
		try{
			String type = request.getParameter("mfont_sizetype_id");
			fontSize.setType(type);
			if(fontSize.getId()==null){
				fontSizeService.save(fontSize);
				resultMsg=getText("添加","公文字号");
			}else{
			    fontSizeService.save(fontSize);
				resultMsg=getText("更新","公文字号");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得公文字号分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看公文字号分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<FontSize> list=fontSizeService.getAll(new QueryFilter(request,"fontSizeItem"));
		
		ModelAndView mv=this.getAutoView().addObject("fontSizeList",list);
		return mv;
	}
	
	@RequestMapping("selectType")
	@ResponseBody
	@Action(description="查看公文字号分页列表")
	public String selectType(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Map<String,String> params = new HashMap<String,String>();
		String type = request.getParameter("type");
		params.put("type", type);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		String year = sdf.format(date);
		FontSize fontSize=fontSizeService.selectFontSize(type);
		if(!year.equals(fontSize.getDispatch_year())||null==fontSize){
			fontSize.setDispatch_year(year);
			fontSize.setFont_size("1");
			fontSizeService.update(fontSize);
		}
		return fontSize.getTypeName()+"["+fontSize.getDispatch_year()+"]"+fontSize.getFont_size()+"号";
	}
	@RequestMapping("update")
	@ResponseBody
	@Action(description="查看公文字号分页列表")
	public String updateFontSize(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		String result = "Ok";
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			String year = sdf.format(date);
			String type = request.getParameter("type");
			FontSize fontSize = fontSizeService.selectFontSize(type);
			if(year.equals(fontSize.getDispatch_year())){
				fontSize.setFont_size(String.valueOf((Integer.valueOf(fontSize.getFont_size())+1)));
				fontSizeService.update(fontSize);
			}else{
				fontSize.setDispatch_year(year);
				fontSize.setFont_size("1");
				fontSizeService.update(fontSize);
			}
			return result;
		} catch (Exception e) {
		    result = "false";
			return result;
		}
	}
	/**
	 * 删除公文字号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除公文字号")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			fontSizeService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除公文字号成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑公文字号
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑公文字号")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		String returnUrl=RequestUtil.getPrePage(request);
		FontSize fontSize=fontSizeService.getById(id);
		
		return getAutoView().addObject("fontSize",fontSize)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得公文字号明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看公文字号明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		FontSize fontSize=fontSizeService.getById(id);
		return getAutoView().addObject("fontSize", fontSize);
	}
	
}