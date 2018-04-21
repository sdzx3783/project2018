package com.hotent.makshi.service.hr;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.core.bpm.BpmResult;
import com.hotent.core.bpm.model.ProcessTask;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.hr.EntryChildrenDao;
import com.hotent.makshi.dao.hr.EntryDao;
import com.hotent.makshi.dao.hr.EntryGzjlDao;
import com.hotent.makshi.dao.hr.EntryJtcyDao;
import com.hotent.makshi.dao.hr.EntryXxjlDao;
import com.hotent.makshi.dao.hr.EntryZyzcDao;
import com.hotent.makshi.dao.hr.EntryZyzgDao;
import com.hotent.makshi.dao.hr.EntryZzzgDao;
import com.hotent.makshi.model.hr.Entry;
import com.hotent.makshi.model.hr.EntryChildren;
import com.hotent.makshi.model.hr.EntryGzjl;
import com.hotent.makshi.model.hr.EntryJtcy;
import com.hotent.makshi.model.hr.EntryXxjl;
import com.hotent.makshi.model.hr.EntryZyzc;
import com.hotent.makshi.model.hr.EntryZyzg;
import com.hotent.makshi.model.hr.EntryZzzg;
import com.hotent.makshi.model.userinfo.EntryEducationHistory;
import com.hotent.makshi.model.userinfo.EntryFamily;
import com.hotent.makshi.model.userinfo.EntryProfessional;
import com.hotent.makshi.model.userinfo.EntryVocationQualification;
import com.hotent.makshi.model.userinfo.EntryWorkHistory;
import com.hotent.makshi.model.userinfo.RegistrationQualification;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.service.common.FlowToEntityService;
import com.hotent.makshi.service.userinfo.UserInfomationService;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.service.bpm.BpmBusLinkService;
import com.hotent.platform.service.bpm.BpmService;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.platform.service.system.SysOrgService;
import com.hotent.platform.service.system.SysUserService;


@Service
public class EntryService extends WfBaseService<Entry>
{
	@Resource
	private EntryDao dao;
	
	@Resource
	private EntryJtcyDao entryJtcyDao;
	@Resource
	private EntryXxjlDao entryXxjlDao;
	@Resource
	private EntryGzjlDao entryGzjlDao;
	@Resource
	private EntryZyzcDao entryZyzcDao;
	@Resource
	private EntryZyzgDao entryZyzgDao;
	@Resource
	private EntryZzzgDao entryZzzgDao;
	@Resource
	private EntryChildrenDao entryChildrenDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	@Resource
	private BpmService bpmService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrgService sysOrgService;
	@Resource
	private SysUserService userService;
	@Resource
	private UserInfomationService infomationService;
	@Resource
	private FlowToEntityService flowToEntityService;
	
	public EntryService()
	{
	}
	
	@Override
	protected IEntityDao<Entry,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		entryJtcyDao.delByMainId(id);
		entryXxjlDao.delByMainId(id);
		entryGzjlDao.delByMainId(id);
		entryZyzcDao.delByMainId(id);
		entryZyzgDao.delByMainId(id);
		entryZzzgDao.delByMainId(id);
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
	 * @param entry
	 * @throws Exception
	 */
	public void addAll(Entry entry) throws Exception{
		super.add(entry);
		addSubList(entry);
	}
	
	/**
	 * 更新数据
	 * @param entry
	 * @throws Exception
	 */
	public void updateAll(Entry entry) throws Exception{
		super.update(entry);
		delByPk(entry.getId());
		addSubList(entry);
	}
	
	/**
	 * 添加子表记录
	 * @param entry
	 * @throws Exception
	 */
	public void addSubList(Entry entry) throws Exception{
		
		List<EntryJtcy> entryJtcyList=entry.getEntryJtcyList();
		if(BeanUtils.isNotEmpty(entryJtcyList)){
			
			for(EntryJtcy entryJtcy:entryJtcyList){
				entryJtcy.setRefId(entry.getId());
				Long id=UniqueIdUtil.genId();
				entryJtcy.setId(id);				
				entryJtcyDao.add(entryJtcy);
			}
		}
		
		List<EntryXxjl> entryXxjlList=entry.getEntryXxjlList();
		if(BeanUtils.isNotEmpty(entryXxjlList)){
			
			for(EntryXxjl entryXxjl:entryXxjlList){
				entryXxjl.setRefId(entry.getId());
				Long id=UniqueIdUtil.genId();
				entryXxjl.setId(id);				
				entryXxjlDao.add(entryXxjl);
			}
		}
		
		List<EntryGzjl> entryGzjlList=entry.getEntryGzjlList();
		if(BeanUtils.isNotEmpty(entryGzjlList)){
			
			for(EntryGzjl entryGzjl:entryGzjlList){
				entryGzjl.setRefId(entry.getId());
				Long id=UniqueIdUtil.genId();
				entryGzjl.setId(id);				
				entryGzjlDao.add(entryGzjl);
			}
		}
		
		List<EntryZyzc> entryZyzcList=entry.getEntryZyzcList();
		if(BeanUtils.isNotEmpty(entryZyzcList)){
			
			for(EntryZyzc entryZyzc:entryZyzcList){
				entryZyzc.setRefId(entry.getId());
				Long id=UniqueIdUtil.genId();
				entryZyzc.setId(id);				
				entryZyzcDao.add(entryZyzc);
			}
		}
		
		List<EntryZyzg> entryZyzgList=entry.getEntryZyzgList();
		if(BeanUtils.isNotEmpty(entryZyzgList)){
			
			for(EntryZyzg entryZyzg:entryZyzgList){
				entryZyzg.setRefId(entry.getId());
				Long id=UniqueIdUtil.genId();
				entryZyzg.setId(id);				
				entryZyzgDao.add(entryZyzg);
			}
		}
		
		List<EntryZzzg> entryZzzgList=entry.getEntryZzzgList();
		if(BeanUtils.isNotEmpty(entryZzzgList)){
			
			for(EntryZzzg entryZzzg:entryZzzgList){
				entryZzzg.setRefId(entry.getId());
				Long id=UniqueIdUtil.genId();
				entryZzzg.setId(id);				
				entryZzzgDao.add(entryZzzg);
			}
		}
		
		List<EntryChildren> entryChildrenList=entry.getEntryChildrenList();
		if(BeanUtils.isNotEmpty(entryChildrenList)){
			for(EntryChildren entryChildren:entryChildrenList){
				entryChildren.setRefId(entry.getId());
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
	public List<EntryJtcy> getEntryJtcyList(Long id) {
		return entryJtcyDao.getByMainId(id);
	}
	/**
	 * 根据外键获得学习经历列表
	 * @param id
	 * @return
	 */
	public List<EntryXxjl> getEntryXxjlList(Long id) {
		return entryXxjlDao.getByMainId(id);
	}
	/**
	 * 根据外键获得工作经历列表
	 * @param id
	 * @return
	 */
	public List<EntryGzjl> getEntryGzjlList(Long id) {
		return entryGzjlDao.getByMainId(id);
	}
	/**
	 * 根据外键获得专业职称列表
	 * @param id
	 * @return
	 */
	public List<EntryZyzc> getEntryZyzcList(Long id) {
		return entryZyzcDao.getByMainId(id);
	}
	/**
	 * 根据外键获得执业资格列表
	 * @param id
	 * @return
	 */
	public List<EntryZyzg> getEntryZyzgList(Long id) {
		return entryZyzgDao.getByMainId(id);
	}
	
	public List<EntryZzzg> getEntryZzzgList(Long id) {
		return entryZzzgDao.getByMainId(id);
	}
	
	public List<EntryChildren> getEntryChildrenList(Long id) {
		return entryChildrenDao.getByMainId(id);
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<Entry> getAll(QueryFilter queryFilter){
		List<Entry> entryList=super.getAll(queryFilter);
		List<Entry> entrys=new ArrayList<Entry>();
		for(Entry entry:entryList){
			if(entry.getId()!=null){
				ProcessRun processRun=processRunService.getByBusinessKey(entry.getId().toString());
			List<ProcessTask> tasks = null;
			if(BeanUtils.isNotEmpty(processRun)){ 
				if(processRun.getActInstId()==null) continue;
				tasks = bpmService.getTasks(processRun.getActInstId());
				entry.setRunId(processRun.getRunId());
			}
			entry.setProcessStatus("已结束");
			if(tasks!=null && tasks.size()>0){
				entry.setProcessStatus(tasks.get(0).getName());
			}
			entrys.add(entry);
			}
		}
		return entrys;
	}
		
	public List<Entry> getFininshedByUserId(Long userId){
		Map<String,Object> param=new HashMap<>();
		param.put("userId", userId);
		return dao.getBySqlKey("getFininshedByUserId", param);
	}
	/**
	 * 保存 入职信息 信息
	 * @param entry
	 */

	@WorkFlow(flowKey="rzlc",tableName="Entry")
	public void save(Entry entry) throws Exception{
		
		Long id=entry.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			entry.setId(id);
			this.addAll(entry);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(entry.getId().toString(), null , true,  "Entry");
		}
		else{
		    this.updateAll(entry);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
	
	public void addEntry(String businessKey) throws Exception{
		String message ="";
		try {
			if(!StringUtil.isEmpty(businessKey)){
				Entry entry = this.getById(Long.parseLong(businessKey));
				if(entry!=null){
					SysUser user = userService.getById(entry.getYgbh());
					if(user !=null){
						user.setFullname(entry.getXm());
						user.setSex(entry.getXb());
						user.setEmail(entry.getGryx());
						user.setWeixinid(entry.getWx());
						user.setMobile(entry.getSjhm());
						user.setPhone(entry.getJtdh());
						user.setUserStatus("实习");
						String photoJson = entry.getZp();
						if(!StringUtil.isEmpty(photoJson)){
							JSONObject jsStr = JSONObject.parseObject(photoJson);
							JSONArray jsonObject = jsStr.getJSONArray("jsonArry");
							String fileId="";
							for(Object obj :jsonObject){
								JSONObject json=JSONObject.parseObject(obj.toString());
								fileId=json.getString("id");
							}
							String picture = "/platform/system/sysFile/getFileById.ht?fileId="+fileId;
							user.setPicture(picture);
						}
						
						UserInfomation userInfomationByUid = infomationService.getUserInfomationByUid(entry.getYgbh());
						Date bithDay = userInfomationByUid.getBirthday();
						if(null!=bithDay){
							return;
						}
						UserInfomation infomation = new UserInfomation(userInfomationByUid.getId(),entry.getYgbh(),entry.getCsrq(),entry.getHyzk(),entry.getCym(),entry.getMz()
								,entry.getJg(),entry.getZczy(),entry.getWhcd(),entry.getCjgzsj(),entry.getByyx(),entry.getZzmm(),entry.getZy()
								,entry.getSfzhm(),entry.getZc(),entry.getHj(),entry.getSfycrbs(),entry.getSfyycbs(),entry.getShbxdnh(),entry.getLs()
								,entry.getTzah(),entry.getHjszd(),entry.getPoxm(),entry.getFmjzd(),entry.getPosfzhm(),entry.getPojzd(),entry.getTxdz()
								,entry.getSjdh(),entry.getJjlxr(),entry.getJxkh(),entry.getJjlxrdh(),entry.getQQhm(),entry.getWx());
						
					
						UserInfomation temp = infomationService.getUserInfomationByUid(entry.getYgbh());
						
						//获取工作经历
						List<EntryGzjl> entryGzjlList = this.getEntryGzjlList(entry.getId());
						if(entryGzjlList!=null && entryGzjlList.size()>0){
							List<EntryWorkHistory> workList = new ArrayList<EntryWorkHistory>();
							for (EntryGzjl gzjl  : entryGzjlList) {
								EntryWorkHistory workHis = new EntryWorkHistory(temp.getId(), gzjl.getQzsj(),gzjl.getEndDate(), gzjl.getGzdw(), gzjl.getBmgw(), gzjl.getJszw(), flowToEntityService.flowToEntityFile(gzjl.getFj()));
								workList.add(workHis);
							}
							infomation.setEntryWorkHistoryList(workList);
						}
						//获取家庭成员
						List<EntryJtcy> entryJtcyList = this.getEntryJtcyList(entry.getId());
						if(entryJtcyList!=null && entryJtcyList.size()>0){
							List<EntryFamily> familyList = new ArrayList<EntryFamily>();
							for (EntryJtcy jtcy  : entryJtcyList) {
								EntryFamily family = new EntryFamily(temp.getId(), jtcy.getGx(), jtcy.getXm(), jtcy.getXb(), jtcy.getCsn(), jtcy.getCsny(), jtcy.getLxdh(), flowToEntityService.flowToEntityFile(jtcy.getFj()));
								familyList.add(family);
							}
							infomation.setEntryFamilyList(familyList);
						}
						//获取学习经历
						List<EntryXxjl> entryXxjlList = this.getEntryXxjlList(entry.getId());
						if(entryXxjlList!=null && entryXxjlList.size()>0){
							List<EntryEducationHistory> eduList = new ArrayList<EntryEducationHistory>();
							Date lastEndDate  =entryXxjlList.get(0).getEndDate();
							for (EntryXxjl xxjl  : entryXxjlList) {
								EntryEducationHistory edu = new EntryEducationHistory(temp.getId(),xxjl.getQzsj(),xxjl.getEndDate(),xxjl.getJdxxhjg(),xxjl.getZy(),xxjl.getShzs(),flowToEntityService.flowToEntityFile(xxjl.getFj()));
								Date temDate = edu.getEndDate();
								if(temDate.getTime()>lastEndDate.getTime()){
									lastEndDate = temDate;
								}
								eduList.add(edu);
							}
							//设置毕业时间
							infomation.setGraduate_time(lastEndDate);
							infomation.setEntryEducationHistoryList(eduList);
						}
						//获取专业职称
						List<EntryZyzc> entryZyzcList = this.getEntryZyzcList(entry.getId());
						if(entryZyzcList!=null && entryZyzcList.size()>0){
							List<EntryProfessional> professionalList = new ArrayList<EntryProfessional>();
							for (EntryZyzc zyzc  : entryZyzcList) {
								EntryProfessional professional = new EntryProfessional(temp.getId(),zyzc.getZcbh(), zyzc.getZcmc(),zyzc.getFzjg(),zyzc.getZczy(),zyzc.getQdzcsj(),flowToEntityService.flowToEntityFile(zyzc.getFj()));
								professionalList.add(professional);
							}
							infomation.setEntryProfessionalList(professionalList);
						}
						//获取执业资格
						List<EntryZyzg> entryZyzgList = this.getEntryZyzgList(entry.getId());
						if(entryZyzgList!=null && entryZyzgList.size()>0){
							List<EntryVocationQualification> vocationQualificationList = new ArrayList<EntryVocationQualification>();
							for (EntryZyzg zyzg  : entryZyzgList) {
								EntryVocationQualification vocationQualification = new EntryVocationQualification(temp.getId(),zyzg.getZyzcbh(),zyzg.getZyzgmc(),zyzg.getFzjg(),zyzg.getZyzgzzy(),zyzg.getQdzssj(),flowToEntityService.flowToEntityFile(zyzg.getFj()),zyzg.getSfzrbgs(),"1");
								vocationQualificationList.add(vocationQualification);
							}
							infomation.setEntryVocationQualificationList(vocationQualificationList);
						}
						//获取注册资格
						List<EntryZzzg> entryZzgList = this.getEntryZzzgList(entry.getId());
						if(entryZzgList!=null && entryZzgList.size()>0){
							List<RegistrationQualification> registrationQualificationList = new ArrayList<RegistrationQualification>();
							for (EntryZzzg zzzg  : entryZzgList) {
								RegistrationQualification registrationQualification = new RegistrationQualification();
								registrationQualification.setCertificate_regist_id(zzzg.getCertificate_regist_id());
								registrationQualification.setCreateBy(zzzg.getCreateBy());
								registrationQualification.setCreatetime(zzzg.getCreatetime());
								registrationQualification.setIsregist(zzzg.getIsregist());
								registrationQualification.setGet_date(zzzg.getGet_date());
								registrationQualification.setLast_effectice_date(zzzg.getLast_effectice_date());
								registrationQualification.setRegist_major(zzzg.getRegist_major());
								registrationQualification.setRegist_attachment(zzzg.getRegist_attachment());
								registrationQualification.setRegist_send_unit(zzzg.getRegist_send_unit());
								registrationQualificationList.add(registrationQualification);
							}
							infomation.setRegistrationQualificationList(registrationQualificationList);
						}
						userService.update(user);
						infomationService.save(infomation);
				}else{
					message = "用户不存在";
					throw new Exception(message);
				}
			}
		}else{
			message = "该流程数据已删除";
			throw new Exception(message);
		}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw  new Exception(message);
		}
	}

	//删除同步的个人
	public void delEntry(String businessKey) throws Exception {
		try {
			if(!StringUtil.isEmpty(businessKey)){
				Entry entry = this.getById(Long.parseLong(businessKey));
				if(entry!=null){
					Long[] ids = new Long[1];
					ids[0] = entry.getId();
					infomationService.delAll(ids);
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw  new Exception("删除失败",e.getCause());
		}	
	}
}
