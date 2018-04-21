package com.hotent.makshi.controller.qualification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.controller.BaseController;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.dao.title.PersonalSealDao;
import com.hotent.makshi.model.qualification.CertificateAndBorrow;
import com.hotent.makshi.model.qualification.PersonalQualification;
import com.hotent.makshi.model.title.CertificatePerson;
import com.hotent.makshi.model.title.OccupationalRequirements;
import com.hotent.makshi.model.title.PersonalSeal;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.fetcher.QualificationFetcher;
import com.hotent.makshi.service.qualification.CertificateAndBorrowService;
import com.hotent.makshi.service.qualification.PersonalQualificationService;
import com.hotent.makshi.service.title.CertificatePersonService;
import com.hotent.makshi.service.title.OccupationalRequirementsService;
import com.hotent.makshi.service.title.PersonalSealService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.system.SysUserService;
/**
 * 对象功能:个人执业资格 控制器类
 */
@Controller
@RequestMapping("/makshi/qualification/personalQualification/")
public class PersonalQualificationController extends BaseController
{
	@Resource
	private UserInfomationService userInfomationService;
	@Resource
	private PersonalQualificationService personalQualificationService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private OccupationalRequirementsService occupationalRequirementsService;
	@Resource
	private CertificatePersonService certificatePersonService;
	@Resource
	private PersonalSealDao personalSealDao;
	@Resource
	private PersonalSealService personalSealService;
	@Resource
	private CertificateAndBorrowService certificateAndBorrowService;
	private static Logger logger=Logger.getLogger(QualificationFetcher.class);
	
	/**
	 * 添加或更新个人执业资格。
	 * @param request
	 * @param response
	 * @param personalQualification 添加或更新的实体
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("save")
	@Action(description="添加或更新个人执业资格")
	public void save(HttpServletRequest request, HttpServletResponse response,PersonalQualification personalQualification) throws Exception
	{
		String resultMsg=null;		
		try{
			if(personalQualification.getId()==null){
				SysUser user =  sysUserService.getByAccount(personalQualification.getAccount());
				personalQualification.setUserId(user.getUserId());
				personalQualificationService.save(personalQualification);
				resultMsg=getText("添加","个人执业资格");
			}else{
			    personalQualificationService.save(personalQualification);
				resultMsg=getText("更新","个人执业资格");
			}
			writeResultMessage(response.getWriter(),resultMsg,ResultMessage.Success);
		}catch(Exception e){
			writeResultMessage(response.getWriter(),resultMsg+","+e.getMessage(),ResultMessage.Fail);
		}
	}
	
	/**
	 * 取得个人执业资格分页列表
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	@Action(description="查看个人执业资格分页列表")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		List<PersonalQualification> list=personalQualificationService.getAll(new QueryFilter(request,"personalQualificationItem"));
		ModelAndView mv=this.getAutoView().addObject("personalQualificationList",list);
		return mv;
	}
	
	/**
	 * 删除个人执业资格
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("del")
	@Action(description="删除个人执业资格")
	public void del(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String preUrl= RequestUtil.getPrePage(request);
		ResultMessage message=null;
		try{
			Long[]  lAryId=RequestUtil.getLongAryByStr(request,"id");
			personalQualificationService.delByIds(lAryId);
			message=new ResultMessage(ResultMessage.Success, "删除个人执业资格成功!");
		}catch(Exception ex){
			message=new ResultMessage(ResultMessage.Fail, "删除失败" + ex.getMessage());
		}
		addMessage(message, request);
		response.sendRedirect(preUrl);
	}
	
	/**
	 * 	编辑个人执业资格
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("edit")
	@Action(description="编辑个人执业资格")
	public ModelAndView edit(HttpServletRequest request) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		
		String returnUrl=RequestUtil.getPrePage(request);
		PersonalQualification personalQualification=personalQualificationService.getById(id);
		
		return getAutoView().addObject("personalQualification",personalQualification)
							.addObject("returnUrl",returnUrl);
	}

	/**
	 * 取得个人执业资格明细
	 * @param request   
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("get")
	@Action(description="查看个人执业资格明细")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long id=RequestUtil.getLong(request,"id");
		PersonalQualification personalQualification=personalQualificationService.getById(id);
		return getAutoView().addObject("personalQualification", personalQualification);
	}
	
	@RequestMapping("getUserIdByAccount")
	@ResponseBody
	public UserInfomation getUserIdByAccount(String account){
		UserInfomation user = userInfomationService.getUserInfomationByAccount(account);
		return user;
	}

	@RequestMapping("certificatePerson")
	public ModelAndView getCertificatePersonList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ModelAndView mv = new ModelAndView("/makshi/title/certificatePersonList.jsp");
		List<CertificatePerson> certificatePersonlist = new ArrayList<CertificatePerson>();
		String selectYear = request.getParameter("selectYear");
		Date defDate = new Date();
		if(selectYear==null){
			Calendar c = Calendar.getInstance();
			c.setTime(defDate);
			int year = c.get(Calendar.YEAR);
			selectYear = Integer.valueOf(year).toString();
		}
		certificatePersonlist = certificatePersonService.getAllinfo(selectYear);
		CertificatePerson totalcertificatePerson= new CertificatePerson();
		Integer existNum =0;
		Integer registNum = 0;
		Integer inNum = 0;
		Integer outNum = 0 ;
		Integer trainingNum = 0;
		for(CertificatePerson certificatePerson:certificatePersonlist){
			existNum = existNum+certificatePerson.getExistNum();
			registNum = registNum + certificatePerson.getRegistNum();
			inNum = inNum+certificatePerson.getInNum();
			outNum = outNum + certificatePerson.getOutNum();
			trainingNum = trainingNum + certificatePerson.getTrainingNum();
		}
		totalcertificatePerson.setCertificate_type("合计");
		totalcertificatePerson.setExistNum(existNum);
		totalcertificatePerson.setInNum(inNum);
		totalcertificatePerson.setRegistNum(registNum);
		totalcertificatePerson.setOutNum(outNum);
		totalcertificatePerson.setTrainingNum(trainingNum);
		certificatePersonlist.add(totalcertificatePerson);
		return mv.addObject("certificatePersonlist",certificatePersonlist).addObject("syear",selectYear);
	}
	
	@RequestMapping("testInfo")
	public void compoleteInfo() throws Exception{
		List<PersonalQualification> list = personalQualificationService.getAllInfo();
		for(PersonalQualification personalQualification:list){
			String carId = personalQualification.getID_number();
			SysUser user = sysUserService.getByIdNumber(carId);
			if(user!=null){
				//资格
				String cerId = personalQualification.getCertificate_id();
				
				
				
				
				//注册
				String registId = personalQualification.getCertificate_regist_id();
				//从业
				String occId = personalQualification.getOcc_certificate_id();
				//添加个人信息
			//	UserInfomation info = userInfomationService.getUserInfomationByAccount(user.getAccount());
				personalQualificationService.completeInfo(user,carId);
			}else{
				personalQualificationService.backInfo(carId);
			}
	}
	}
	
	@RequestMapping("addRegist")
	public void addRegist() throws Exception{
		//将申请信息转化为保存信息
		List<PersonalQualification> list = personalQualificationService.getAllInfo();
		for(PersonalQualification personalQualification:list){
			Long userId = personalQualification.getUserId();
			if(null==userId){
				//设置为挂靠状态
				personalQualification.setIsBinding(1);
				//设置为转入状态
				personalQualification.setSwitchs(1);
			}
			personalQualificationService.add(personalQualification);
		}
	}
	
	@RequestMapping("voAndre")
	public void voAndre() throws Exception{
		//根据regist信息完善个人信息
		List<PersonalQualification> list = personalQualificationService.getAllRegist();
		for(PersonalQualification personalQualification:list){
			//设置转入状态
			if(personalQualification.getUserId()!=null){
				//更改个人证书信息
				//personalQualificationService.changeInfoTest(personalQualification);
				//跟新证书借阅信息
				personalQualificationService.addInfo(personalQualification);
			}else{
				personalQualification.setAccount(null);
				personalQualification.setUserId(null);
				personalQualificationService.addInfo(personalQualification);
			}
		}
	}
	
	@RequestMapping("sealInfo")
	public void sealInfo(){
	
	List<PersonalQualification> list = personalQualificationService.getAllInfoSeal();
	
	PersonalSeal newPersonalSeal = new PersonalSeal();
	for(PersonalQualification personalSeal:list){
		newPersonalSeal.setHolderID(null);
		Long userId = personalSeal.getUserId();
		newPersonalSeal.setId(UniqueIdUtil.genId());
	    newPersonalSeal.setSeal_num(personalSeal.getSeal_id());
	    newPersonalSeal.setSeal_name(personalSeal.getCertificate_type());
	    newPersonalSeal.setEffictiveDate(personalSeal.getEffictiveDate());
	    newPersonalSeal.setSwitchs("1");
	    newPersonalSeal.setStatus("0");
	    newPersonalSeal.setHolder(personalSeal.getName());
	    newPersonalSeal.setLinkId(personalSeal.getId());
	    if(null!=userId){
	    	newPersonalSeal.setHolderID(userId.toString());
	    }
	    personalSealDao.add(newPersonalSeal);
	}
}
	@RequestMapping("perSealInfo")
	public void perSealInfo() throws Exception{
		//从登记表中获取印章信息
		List<PersonalQualification> list2 = personalQualificationService.getAllRegist();
		for(PersonalQualification personalQualification:list2){
			//获取类型和编号
			String name = personalQualification.getCertificate_type();
			String num = personalQualification.getSeal_id();
			//通过名称和编号取得印章信息
			PersonalSeal personalSeal = personalSealService.getBySealNumAndName(num, name);
			if(null!=personalSeal){
				//跟新借阅表：1.删除原纪录.2插入新纪录
				CertificateAndBorrow certificateAndBorrow = new CertificateAndBorrow();
				Long id = personalSeal.getId();
				certificateAndBorrowService.delByLinkId(id);
				Long linkId = personalQualification.getId();
				Integer switchs = personalQualification.getSwitchs();
				Long userId = personalQualification.getUserId();
				certificateAndBorrow.setCertifateId(num);
				certificateAndBorrow.setName(name);
				certificateAndBorrow.setUserId(userId);
				certificateAndBorrow.setType(3);
				certificateAndBorrow.setSwitchs(switchs);
				certificateAndBorrow.setEffictivedate(personalQualification.getEffictiveDate());
				certificateAndBorrow.setLinkId(linkId);
				certificateAndBorrow.setBorrower(personalSeal.getBorrower());
				certificateAndBorrow.setBorrowerId(personalSeal.getBorrowerID());
				certificateAndBorrowService.add(certificateAndBorrow);
			}else{
				personalQualificationService.addInfo(personalQualification);
			}
		}
}
	
	@RequestMapping("occtestInfo")
	public void comOcc(){
		List<OccupationalRequirements> occList = occupationalRequirementsService.getAllInfo();
		for(OccupationalRequirements occupationalRequirements:occList){
			String account = occupationalRequirements.getAccount();
			SysUser user = new SysUser();
			if(account!=null && account.length()>0){
				 user = sysUserService.getByAccount(account);
			}else{
				List<SysUser> list = sysUserService.getByUserName(occupationalRequirements.getUserName());
				if(list.size()>0){
					user = list.get(0);
				}
			}
			if(user!=null&&user.getUserId()!=null){
				//添加从业信息
				UserInfomation info = userInfomationService.getUserInfomationByAccount(user.getAccount());
				occupationalRequirementsService.completeInfo(user,info);
			}else{
				occupationalRequirementsService.backInfo(occupationalRequirements.getUserName());
			}
		}
	}
		
		@RequestMapping("occtestInfoToRegist")
		public void occtestInfoToRegist() throws Exception{
			
		//获取从业信息集合
		List<OccupationalRequirements> occList = occupationalRequirementsService.getAllInfo();
		//遍历集合并插入到统计表中
		for(OccupationalRequirements occupationalRequirements:occList){
			Map<String,Object> param = new HashMap<String,Object>(); 
			SysUser user = new SysUser();
			UserInfomation userInfomation = new UserInfomation();
			Long userId = occupationalRequirements.getUserId();
			if(userId!=null&&userId>0){
				param.put("userId", userId);
			}
			String type = occupationalRequirements.getOcc_type();
			if(type!=null && type.length()>0){
				param.put("type", type);
			}
			String userName = occupationalRequirements.getUserName();
			if(userName!=null && userName.length()>0){
				param.put("userName", userName);
			}
			String certificateId = occupationalRequirements.getOcc_certificate_id();
			if(certificateId!=null && certificateId.length()>0){
				param.put("certificateId", certificateId);
			}
			PersonalQualification personalQualification = new PersonalQualification();
			//1.有linkId
			if(occupationalRequirements.getLinkId()!=null&&occupationalRequirements.getLinkId()>0){
				continue;
				/*Long linkId = occupationalRequirements.getLinkId();
				//通过LinkId获取PersonalQualification对象
				List<PersonalQualification> list = personalQualificationService.getByLinkId(linkId);
				if(list.size()>0){
					personalQualification = list.get(0);
				}*/
			}else{
			//2.无linkId
				//通过姓名、证书类型、证书编号获取记录
				List<PersonalQualification> list = personalQualificationService.getOccByInfo(param);
				//跟新
				if(list.size()>0){
					personalQualification = list.get(0);
					continue;
				}else{
				//新增
				//判断是否有userId,设置是否为挂靠
				if(null!=userId){
					user = sysUserService.getUserById(userId);
				    userInfomation = userInfomationService.getUserInfomationByUid(userId);
					if(user!=null){
						personalQualification.setAccount(user.getAccount());
					}
					personalQualification.setUserId(occupationalRequirements.getUserId());
					personalQualification.setIsBinding(0);
					if("1".equals(user.getSex())){
						personalQualification.setSex("男");
					}else{
						personalQualification.setSex("女");
						
					}
					personalQualification.setNation(userInfomation.getNation());
					personalQualification.setID_number(userInfomation.getIdentification_id());
					personalQualification.setGraduation_school(userInfomation.getGraduate_school());
					personalQualification.setLearnMajor(userInfomation.getMajor());
					personalQualification.setXl(userInfomation.getEducation());
					personalQualification.setGraduation_date(userInfomation.getGraduate_time());
				}else{
					personalQualification.setIsBinding(1);
				}
					
				}
			//设置信息
			personalQualification.setName(occupationalRequirements.getUserName());
			personalQualification.setOcc_certificate_id(occupationalRequirements.getOcc_certificate_id());
			personalQualification.setOcc_attachment(occupationalRequirements.getOcc_attachment());
			personalQualification.setOcc_compulsory(occupationalRequirements.getOcc_compulsory());
			personalQualification.setOcc_contine_edu_comple(occupationalRequirements.getOcc_contine_edu_comple());
			personalQualification.setOcc_degree_work(occupationalRequirements.getOcc_degree_work());
			personalQualification.setOcc_elective(occupationalRequirements.getOcc_elective());
			personalQualification.setOcc_get_date(occupationalRequirements.getOcc_get_date());
			personalQualification.setOcc_major(occupationalRequirements.getOcc_major());
			
			personalQualification.setOcc_out_date(occupationalRequirements.getOcc_out_date());
			if(occupationalRequirements.getOcc_out_date()!=null){
				personalQualification.setSwitchs(0);
			}else{
				personalQualification.setSwitchs(1);
			}
			personalQualification.setOcc_remark(occupationalRequirements.getOcc_remark());
			personalQualification.setOcc_secondMajor(occupationalRequirements.getOcc_secondMajor());
			personalQualification.setOcc_type(occupationalRequirements.getOcc_type());
			personalQualification.setOcc_period_of_validity(occupationalRequirements.getOcc_period_of_validity());
			personalQualification.setId(occupationalRequirements.getId());
			personalQualificationService.add(personalQualification);
			personalQualificationService.addInfo(personalQualification);
		}
			
		}
	}
		

		

}
