package com.hotent.makshi.service.userinfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.util.RequestUtil;
import com.hotent.makshi.dao.hr.EntryChildrenDao;
import com.hotent.makshi.dao.title.OccupationalRequirementsDao;
import com.hotent.makshi.dao.userinfo.EntryEducationHistoryDao;
import com.hotent.makshi.dao.userinfo.EntryFamilyDao;
import com.hotent.makshi.dao.userinfo.EntryProfessionalDao;
import com.hotent.makshi.dao.userinfo.EntryVocationQualificationDao;
import com.hotent.makshi.dao.userinfo.EntryWorkHistoryDao;
import com.hotent.makshi.dao.userinfo.RegistrationQualificationDao;
import com.hotent.makshi.dao.userinfo.UserInfomationDao;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.model.hr.EntryChildren;
import com.hotent.makshi.model.title.OccupationalRequirements;
import com.hotent.makshi.model.userinfo.EntryEducationHistory;
import com.hotent.makshi.model.userinfo.EntryFamily;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.EntryWorkHistory;
import com.hotent.makshi.model.userinfo.RegistrationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.platform.dao.system.SysUserDao;
import com.hotent.platform.model.system.SysUser;


@Service
public class UserInfomationService extends BaseService<UserInfomation>
{
	@Resource
	private UserInfomationDao dao;
	
	@Resource
	private EntryFamilyDao entryFamilyDao;
	@Resource
	private EntryEducationHistoryDao entryEducationHistoryDao;
	@Resource
	private EntryWorkHistoryDao entryWorkHistoryDao;
	@Resource
	private EntryProfessionalDao entryProfessionalDao;
	@Resource
	private EntryVocationQualificationDao entryVocationQualificationDao;
	@Resource
	private RegistrationQualificationDao registrationQualificationDao;
	@Resource
	private OccupationalRequirementsDao occupationalRequirementsDao;
	@Resource
	private EntryChildrenDao entryChildrenDao;
	@Resource
	private ChangeHistoryService changeHistoryService;
	@Resource
	SysUserDao sysUserDao;

	
	public UserInfomationService()
	{
	}
	
	@Override
	protected IEntityDao<UserInfomation,Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 更新sysuer以及userinfomation
	 * @param sysUser
	 * @param userInfomation
	 */
	@Transactional
	public void updateSysuserAndUserInfomation(SysUser sysUser,UserInfomation userInfomation){
		sysUserDao.updateCommon(sysUser);
		if(userInfomation!=null){
			dao.update("updateSjdh", userInfomation);
		}
	}
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		entryFamilyDao.delByMainId(id);
		entryEducationHistoryDao.delByMainId(id);
		entryWorkHistoryDao.delByMainId(id);
		entryProfessionalDao.delByMainId(id);
		entryVocationQualificationDao.delByMainId(id);
		registrationQualificationDao.delByMainId(id);
		entryChildrenDao.delByMainId(id);
	}
	
	/**
	 * 删除数据 包含相应子表记录
	 * @param lAryId
	 */
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	/**
	 * 添加数据 
	 * @param userInfomation
	 * @throws Exception
	 */
	public void addAll(UserInfomation userInfomation) throws Exception{
		super.add(userInfomation);
		addSubList(userInfomation);
	}
	
	/**
	 * 更新数据
	 * @param userInfomation
	 * @throws Exception
	 */
	public void updateAll(UserInfomation userInfomation) throws Exception{
		super.update(userInfomation);
		delByPk(userInfomation.getId());
		addSubList(userInfomation);
	}
	
	/**
	 * 添加子表记录
	 * @param userInfomation
	 * @throws Exception
	 */
	public void addSubList(UserInfomation userInfomation) throws Exception{
		
		List<EntryFamily> entryFamilyList=userInfomation.getEntryFamilyList();
		if(BeanUtils.isNotEmpty(entryFamilyList)){
			
			for(EntryFamily entryFamily:entryFamilyList){
				entryFamily.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				entryFamily.setId(id);				
				entryFamilyDao.add(entryFamily);
			}
		}
		
		List<EntryEducationHistory> entryEducationHistoryList=userInfomation.getEntryEducationHistoryList();
		if(BeanUtils.isNotEmpty(entryEducationHistoryList)){
			
			for(EntryEducationHistory entryEducationHistory:entryEducationHistoryList){
				entryEducationHistory.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				entryEducationHistory.setId(id);				
				entryEducationHistoryDao.add(entryEducationHistory);
			}
		}
		
		List<EntryWorkHistory> entryWorkHistoryList=userInfomation.getEntryWorkHistoryList();
		if(BeanUtils.isNotEmpty(entryWorkHistoryList)){
			
			for(EntryWorkHistory entryWorkHistory:entryWorkHistoryList){
				entryWorkHistory.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				entryWorkHistory.setId(id);				
				entryWorkHistoryDao.add(entryWorkHistory);
			}
		}
		
		List<EntryProfessional> entryProfessionalList=userInfomation.getEntryProfessionalList();
		if(BeanUtils.isNotEmpty(entryProfessionalList)){
			
			for(EntryProfessional entryProfessional:entryProfessionalList){
				entryProfessional.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				entryProfessional.setId(id);				
				entryProfessionalDao.add(entryProfessional);
			}
		}
		
		List<EntryVocationQualification> entryVocationQualificationList=userInfomation.getEntryVocationQualificationList();
		if(BeanUtils.isNotEmpty(entryVocationQualificationList)){
			
			for(EntryVocationQualification entryVocationQualification:entryVocationQualificationList){
				entryVocationQualification.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				entryVocationQualification.setId(id);				
				entryVocationQualificationDao.add(entryVocationQualification);
			}
		}
		
		List<RegistrationQualification> registrationQualificationList=userInfomation.getRegistrationQualificationList();
		if(BeanUtils.isNotEmpty(registrationQualificationList)){
			for(RegistrationQualification registrationQualification:registrationQualificationList){
				registrationQualification.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				registrationQualification.setId(id);				
				registrationQualificationDao.add(registrationQualification);
			}
		}
		
		List<EntryChildren> entryChildrenList=userInfomation.getEntryChildrenList();
		if(BeanUtils.isNotEmpty(entryChildrenList)){
			for(EntryChildren entryChildren:entryChildrenList){
				entryChildren.setRefId(userInfomation.getId());
				Long id=UniqueIdUtil.genId();
				entryChildren.setId(id);				
				entryChildrenDao.add(entryChildren);
			}
		}
	}
	
	/**
	 * 根据外键获得家庭成员列表
	 * @param id
	 * @return
	 */
	public List<EntryFamily> getEntryFamilyList(Long id) {
		return entryFamilyDao.getByMainId(id);
	}
	/**
	 * 根据外键获得学习经历列表
	 * @param id
	 * @return
	 */
	public List<EntryEducationHistory> getEntryEducationHistoryList(Long id) {
		return entryEducationHistoryDao.getByMainId(id);
	}
	/**
	 * 根据外键获得工作经历列表
	 * @param id
	 * @return
	 */
	public List<EntryWorkHistory> getEntryWorkHistoryList(Long id) {
		return entryWorkHistoryDao.getByMainId(id);
	}
	/**
	 * 根据外键获得专业职称列表
	 * @param id
	 * @return
	 */
	public List<EntryProfessional> getEntryProfessionalList(Long id) {
		return entryProfessionalDao.getByMainId(id);
	}
	/**
	 * 根据外键获得执业资格列表
	 * @param id
	 * @return
	 */
	public List<EntryVocationQualification> getEntryVocationQualificationList(Long id) {
		return entryVocationQualificationDao.getByMainId(id);
	}
	public List<EntryVocationQualification> getEntryVocationQualificationListByUserId(Long id) {
		return entryVocationQualificationDao.getByUserId(id);
	}
	
	//注册资格
	public List<RegistrationQualification> getRegistrationQualificationList(Long id) {
		return registrationQualificationDao.getByMainId(id);
	}
	/**
	 * 根据userid获得从业资格列表
	 * @param id
	 * @return
	 */
	public List<OccupationalRequirements> getOccupationalRequirementList(Long userid) {
		return occupationalRequirementsDao.getByUserId(userid);
	}
	
	
	public List<EntryChildren> getEntryChildrenList(Long id) {
		return entryChildrenDao.getByMainId(id);
	}
	
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd)throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			UserInfomation userInfomation=getUserInfomation(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				userInfomation.setId(genId);
				this.addAll(userInfomation);
			}else{
				userInfomation.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(userInfomation);
			}
			cmd.setBusinessKey(userInfomation.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取UserInfomation对象
	 * @param json
	 * @return
	 */
	public UserInfomation getUserInfomation(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("entryFamilyList", EntryFamily.class);
		map.put("entryEducationHistoryList", EntryEducationHistory.class);
		map.put("entryWorkHistoryList", EntryWorkHistory.class);
		map.put("entryProfessionalList", EntryProfessional.class);
		map.put("entryVocationQualificationList", EntryVocationQualification.class);
		map.put("registrationQualificationList", RegistrationQualification.class);
		UserInfomation userInfomation = (UserInfomation)JSONObject.toBean(obj, UserInfomation.class,map);
		return userInfomation;
	}
	/**
	 * 保存 用户信息档案表 信息
	 * @param userInfomation
	 */

	public void save(UserInfomation userInfomation) throws Exception{
		Long id=userInfomation.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			userInfomation.setId(id);
			this.addAll(userInfomation);
		}
		else{
		    this.updateAll(userInfomation);
		}
	}
	
	
	
	public UserInfomation getUserInfomationByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<UserInfomation> userInfomationList = dao.getBySqlKey("getUserDataByYgbh", params);
		if(userInfomationList!=null && userInfomationList.size()>0){
			UserInfomation userInfo = userInfomationList.get(0);
			return userInfo;
		}
		
		return null;
	}
	
	
	
	public UserInfomation getUserInfomationById(Long id){
		UserInfomation userInfomation = dao.getById(id);
		return userInfomation;
	}
	
	public UserInfomation getUserInfomationByAccount(String account){
		List<UserInfomation> userInfomationList = dao.getBySqlKey("getByAccount", account);
		if(userInfomationList!=null && userInfomationList.size()>0){
			UserInfomation userInfo = userInfomationList.get(0);
			return userInfo;
		}
		return null;
	}
	/**
	 * 更新手机短号 以及紧急联系人和紧急联系电话
	 * 
	 * @param userInfomation
	 */
	public void updateSjdh(UserInfomation userInfomation) {
		dao.update("updateSjdh", userInfomation);
	}
	
	public List<WChangeHistory> getChangeHis(Long userId) throws Exception{
		UserInfomation userInfomation = getUserInfomationByUid(userId);
		List<WChangeHistory> changeHisList=null;
		if (userInfomation != null) {
			changeHisList = changeHistoryService.getListByType("infoChange", userId+"");
		}
		return changeHisList;
	}
}
