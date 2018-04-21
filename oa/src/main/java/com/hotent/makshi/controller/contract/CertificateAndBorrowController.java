

package com.hotent.makshi.controller.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.model.contract.ContractBorrowApplication;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;
import com.hotent.makshi.model.qualification.PersonalQualification;
import com.hotent.makshi.service.contract.ContractBorrowApplicationService;
import com.hotent.makshi.service.qualification.CertificateAndBorrowService;
import com.hotent.makshi.service.qualification.PersonalQualificationService;
/**
 * 对象功能:合同借阅申请 控制器类
 */
@Controller
@RequestMapping("/makshi/contract/contractBorrowApplication/")
public class CertificateAndBorrowController extends BaseController
{
	@Resource
	private CertificateAndBorrowService certificateAndBorrowService;
	@Resource
	private PersonalQualificationService personalQualificationService;
	@Resource
	private ContractBorrowApplicationService contractBorrowApplicationService;
	
	
/**
 * 获取证书信息
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	@RequestMapping("getInfoById")
	@ResponseBody
	public Map<String, Object> getInfoById(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ok", 0);
		String type = request.getParameter("type");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		List<CertificateAndBorrow> list = new ArrayList<CertificateAndBorrow>();
		if(null==userId || ("null").equals(userId) || userId.length()==0||Long.valueOf(userId)==0){
			list = certificateAndBorrowService.getInfoByUserName(userName,type);
		}else{
			list = certificateAndBorrowService.getInfoByUserId(userId,type);
		}
		if(list.size()>0){
			result.put("data", list);
			result.put("ok", 1);
		}
		return result;
	}
	@RequestMapping("getDuplicateName")
	@ResponseBody
	public Map<String, Object> getDuplicateName(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ok", 0);
		String type = request.getParameter("type");
		String userName = request.getParameter("userName");
		String userId = request.getParameter("userId");
		List<CertificateAndBorrow> list = new ArrayList<CertificateAndBorrow>();
		//一：根据名字和类型进行重名过滤
		List<PersonalQualification> personList = new ArrayList<PersonalQualification>();
		//先判断持证人是否有员工编号并不重名
		list = certificateAndBorrowService.getDuplicateName(userName,type,userId);
		Long userIdTem = 0L;
		if(list.size()==1){
			//无重名,判断是否有员工编号,进而赋值userId
			//List<CertificateAndBorrow> listAccount  = certificateAndBorrowService.getAccount(userName,type,userId);
			//if(listAccount.size()>0){
				if(null != list.get(0).getUserId()){
					userIdTem = list.get(0).getUserId();
				}
				result.put("userId", userIdTem);
				return result;	
		}
		if(list.size()>1){
			//有重名
			PersonalQualification personalQualification = new PersonalQualification();
			for(CertificateAndBorrow certificateAndBorrow : list){
				 personalQualification = personalQualificationService.getByIdTable(certificateAndBorrow.getLinkId());
				 personList.add(personalQualification);
			}
			result.put("data", personList);
			result.put("ok", 1);
			return result;	
		}
		if(list.size()>0){
			userIdTem = list.get(0).getUserId();
		}
		result.put("userId", userIdTem);
			
		return result;	
	}
	
	@RequestMapping("list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<ContractBorrowApplication> list=contractBorrowApplicationService.getAll(new QueryFilter(request,"contractBorrowApplicationItem"));
		ModelAndView mv=this.getAutoView().addObject("contractBorrowApplicationList",list);
		return mv;
	}
}