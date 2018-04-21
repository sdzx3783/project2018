

package com.hotent.makshi.controller.operation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.model.operation.AccessExamine;
import com.hotent.makshi.model.operation.CarRegist;
import com.hotent.makshi.model.operation.CarUse;
import com.hotent.makshi.model.operation.CarUseSegment;
import com.hotent.makshi.service.operation.CarRegistService;
import com.hotent.makshi.service.operation.CarUseService;
import com.hotent.makshi.service.operation.OperationWeeklyManageService;
import com.hotent.platform.annotion.Action;
/**
 * 对象功能:车辆登记 控制器类
 */
@Controller
@RequestMapping("/makshi/operation/carRegist/")
public class CarRegistController extends BaseController
{
	@Resource
	private CarRegistService carRegistService;
	@Resource
	private CarUseService carUseService;
	@Resource
	private OperationWeeklyManageService operationWeeklyManageService;
	/**
	 * 添加或更新车辆登记。
	 * @param request
	 * @param response
	 * @param carRegist 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新车辆登记")
	public void save(HttpServletRequest request, HttpServletResponse response,CarRegist carRegist) throws Exception
	{
		String resultMsg=null;		
		try{
			if(carRegist.getId()==null){
				carRegistService.save(carRegist);
				resultMsg=getText("添加","车辆登记");
			}else{
			    carRegistService.save(carRegist);
				resultMsg=getText("更新","车辆登记");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得车辆登记分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看车辆登记分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		QueryFilter fiter = new QueryFilter(request,"carRegistItem");
		Boolean isEditer = false;
		AccessExamine accessExamine = operationWeeklyManageService.examineIsEditer(isEditer,fiter,2);
		List<CarRegist> list=carRegistService.getAll(accessExamine.getFiter());
		ModelAndView mv=this.getAutoView().addObject("carRegistList",list).addObject("isEditer",accessExamine.getIsEditer());
		return mv;
	}
	
	/**
	 * 删除车辆登记
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除车辆登记")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			carRegistService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除车辆登记成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑车辆登记
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑车辆登记")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		CarRegist carRegist=carRegistService.getById(id);
		
		return getAutoView().addObject("carRegist",carRegist)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得车辆登记明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看车辆登记明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		CarRegist carRegist=carRegistService.getById(id);
		return getAutoView().addObject("carRegist", carRegist);
	}

	@RequestMapping("getUseInfoList")
	@Action(description="查看车辆登记明细")
	public ModelAndView getUseInfo(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mv = new ModelAndView("/makshi/operation/getUseInfoList.jsp");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowdate = new Date();
		String date = sdf.format(nowdate);
		String seachDay = request.getParameter("date");
		if(null!=seachDay){
			date = seachDay;
		}
		List<CarUseSegment> useInfoList = carRegistService.getTimePoint(date);
		//QueryFilter queryFilter=new QueryFilter(request, "carRegistItem");
		return mv.addObject("useInfoList", useInfoList);
		/*.addObject("pageBeancarRegistItem", queryFilter.getPageBean())// 此两项用于分页标签
		.addObject("requestURIcarRegistItem", request.getRequestURI());*/
	}
	
	   /**
     * 跟新车辆使用状态
     */
	@RequestMapping("updateStatus")
	@ResponseBody
	public void updateCarUseStatus(){
		List<CarUse> list = carUseService.getAllByTime();
		List<String> carIdList = new ArrayList<String>();
		for(CarUse carUse:list){
			carIdList.add(carUse.getCarName());
		}
		if(carIdList.size()>0){
			carRegistService.updateStatus(carIdList);
		}
	}
	
	@RequestMapping("appli")
	@Action(description="查看车辆登记明细")
	public ModelAndView carUseEdit(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ModelAndView mv = new ModelAndView("/makshi/operation/carUseEdit.jsp");
		return mv;
	}
	
	@RequestMapping("getUseInfo")
	@ResponseBody
	public List<CarRegist> getCarUseInfo(HttpServletRequest request, HttpServletResponse response){
		Long id = Long.valueOf(request.getParameter("id"));
		List<String> idList = new ArrayList<String>();
		List<CarRegist> unUseCarList = new ArrayList<CarRegist>();
		List<CarRegist> allCarList = new ArrayList<CarRegist>();
		List<CarRegist> resultCarList = new ArrayList<CarRegist>();
		allCarList = carRegistService.getAll();
		CarUse carUse = carUseService.getById(id);
		Date startTime = carUse.getStartTime();
		Date endTime = carUse.getEndTime();
		List<CarUse> uselist = carUseService.getUnuseCar(startTime, endTime);
		for(CarUse caruse:uselist){
			if(null!=caruse.getCarNameId()){
				idList.add(caruse.getCarNameId());
			}
		}
		if(null!=idList &&  idList.size() > 0){
			 unUseCarList = carRegistService.getUnuseCar(idList);
			 for(CarRegist carRegist : allCarList){
				 if(!unUseCarList.contains(carRegist)){
					 carRegist.setStatus("1");
					 resultCarList.add(carRegist);
				 }
			 }
			 for(CarRegist carRegist:unUseCarList){
				 carRegist.setStatus("0");
			 }
			 resultCarList.addAll(unUseCarList);
			 return resultCarList;
		}else{
			 for(CarRegist carRegist : allCarList){
					 carRegist.setStatus("0");
			 }
			 return allCarList;
		}
		
	}
	
	}
