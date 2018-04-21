package com.hotent.makshi.service.waterprotectpark;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.waterprotectpark.TechReviewWeekPlanDao;
import com.hotent.makshi.dao.waterprotectpark.WeekPlanDao;
import com.hotent.makshi.model.project.ProjectStageTaskField;
import com.hotent.makshi.model.waterprotectpark.TechReviewWeekPlan;
import com.hotent.makshi.model.waterprotectpark.WeekPlan;
import com.hotent.makshi.service.project.ProjectStageTaskFieldService;
import com.hotent.makshi.utils.DateUtils;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


@Service
public class TechReviewWeekPlanService extends BaseService<TechReviewWeekPlan>
{
	@Resource
	private TechReviewWeekPlanDao dao;
	@Resource
	private ProjectStageTaskFieldService taskFieldService;
	@Resource
	private WeekPlanDao weekPlanSbbDao;
	public TechReviewWeekPlanService()
	{
	}
	
	@Override
	protected IEntityDao<TechReviewWeekPlan,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		weekPlanSbbDao.delByMainId(id);
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
	 * @param techReviewWeekPlanSbb
	 * @throws Exception
	 */
	public void addAll(TechReviewWeekPlan techReviewWeekPlanSbb) throws Exception{
		super.add(techReviewWeekPlanSbb);
		addSubList(techReviewWeekPlanSbb);
	}
	
	/**
	 * 更新数据
	 * @param techReviewWeekPlanSbb
	 * @throws Exception
	 */
	public void updateAll(TechReviewWeekPlan techReviewWeekPlanSbb) throws Exception{
		super.update(techReviewWeekPlanSbb);
		delByPk(techReviewWeekPlanSbb.getId());
		addSubList(techReviewWeekPlanSbb);
	}
	
	/**
	 * 添加子表记录
	 * @param techReviewWeekPlanSbb
	 * @throws Exception
	 */
	public void addSubList(TechReviewWeekPlan techReviewWeekPlanSbb) throws Exception{
		
		List<WeekPlan> weekPlanSbbList=techReviewWeekPlanSbb.getWeekPlanSbbList();
		if(BeanUtils.isNotEmpty(weekPlanSbbList)){
			
			for(WeekPlan weekPlanSbb:weekPlanSbbList){
				weekPlanSbb.setRefId(techReviewWeekPlanSbb.getId());
				Long id=UniqueIdUtil.genId();
				weekPlanSbb.setId(id);				
				weekPlanSbbDao.add(weekPlanSbb);
			}
		}
	}
	
	/**
	 * 根据外键获得周计划列表
	 * @param id
	 * @return
	 */
	public List<WeekPlan> getWeekPlanSbbList(Long id) {
		return weekPlanSbbDao.getByMainId(id);
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
			TechReviewWeekPlan techReviewWeekPlanSbb=getTechReviewWeekPlan(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				techReviewWeekPlanSbb.setId(genId);
				this.addAll(techReviewWeekPlanSbb);
			}else{
				techReviewWeekPlanSbb.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(techReviewWeekPlanSbb);
			}
			cmd.setBusinessKey(techReviewWeekPlanSbb.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取TechReviewWeekPlan对象
	 * @param json
	 * @return
	 */
	public TechReviewWeekPlan getTechReviewWeekPlan(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("weekPlanSbbList", WeekPlan.class);
		TechReviewWeekPlan techReviewWeekPlanSbb = (TechReviewWeekPlan)JSONObject.toBean(obj, TechReviewWeekPlan.class,map);
		return techReviewWeekPlanSbb;
	}
	/**
	 * 保存 技术评审周计划(水保部) 信息
	 * @param techReviewWeekPlanSbb
	 */

	public void save(TechReviewWeekPlan techReviewWeekPlanSbb) throws Exception{
		Long id=techReviewWeekPlanSbb.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			techReviewWeekPlanSbb.setId(id);
			this.addAll(techReviewWeekPlanSbb);
		}
		else{
		    this.updateAll(techReviewWeekPlanSbb);
		}
	}
	
	public String getRefRunIdsById(Long id){
		Object obj=dao.getOne("getRefRunIdsById", id);
		if(obj==null){
			return null;
		}else{
			return (String) obj;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WeekPlan> getWeekPlanInfo(Map<String, Object> param) {
		List<Map<String,Object>> listMap = (List<Map<String,Object>>) weekPlanSbbDao.getListBySqlKey("getWeekPlanInfo", param);
		List<WeekPlan> list=new ArrayList<>();
		if(listMap!=null && listMap.size()>0){
			for (Map<String,Object> map : listMap) {
				String projectId=null;
				String projectName=null;
				String stageId=null;
				String stageName=null;
				String taskName=null;
				String taskId=null;
				String meetingTime=null;
				String meetingAddr=null;
				String taskAssignees=null;//任务被分配人
				String attendMans=null;
				String simpleAttendMans=null;
				
				boolean isSimple=false;
				if(map.get("projectId")!=null){
					projectId=map.get("projectId").toString();
				}
				if(map.get("projectName")!=null){
					projectName=(String) map.get("projectName");
				}
				if(map.get("stageId")!=null){
					stageId=(String) map.get("stageId").toString();
				}
				if(map.get("stageName")!=null){
					stageName=(String) map.get("stageName");
				}
				if(map.get("taskName")!=null){
					taskName=(String) map.get("taskName");
				}
				if(map.get("taskId")!=null){
					taskId=(String) map.get("taskId").toString();
				}
				if(taskId!=null){
					List<ProjectStageTaskField> fieldsInfo = taskFieldService.getFieldsInfoByTaskId(Integer.valueOf(taskId));
					
					for (ProjectStageTaskField projectStageTaskField : fieldsInfo) {
						if("预计开会时间".equals(projectStageTaskField.getFieldName()) 
								&& "date".equals(projectStageTaskField.getFieldType())){
							String fieldValue = projectStageTaskField.getFieldValue();
							meetingTime=fieldValue;
						}
						if("会议地点".equals(projectStageTaskField.getFieldName()) 
								&& "varchar".equals(projectStageTaskField.getFieldType())){
							String fieldValue = projectStageTaskField.getFieldValue();
							meetingAddr=fieldValue;
						}
						if("参会人员".equals(projectStageTaskField.getFieldName()) 
								&& "varchar".equals(projectStageTaskField.getFieldType())){
							attendMans = projectStageTaskField.getFieldValue();
						}
						if("简单项目参会人员".equals(projectStageTaskField.getFieldName()) 
								&& "user".equals(projectStageTaskField.getFieldType())){
							simpleAttendMans = projectStageTaskField.getFieldValue();
						}
						if("是否简单项目".equals(projectStageTaskField.getFieldName()) && 
								"varchar".equals(projectStageTaskField.getFieldType())){
							String isSimpleField=projectStageTaskField.getFieldValue();
							if("是".equals(isSimpleField)){
								isSimple=true;
							}
						}
					}
					if(stageId!=null){
						param.clear();
						param.put("stageId", Integer.valueOf(stageId));
						Object one = weekPlanSbbDao.getListBySqlKey("getTaskAssigneeFields", param);
						if(one!=null){
							List<String> tempList=(List<String>) one;
							if(tempList.size()>0){
								taskAssignees = tempList.get(0);
							}
						}
					}
				}
				if(StringUtil.isNotEmpty(taskId) && StringUtil.isNotEmpty(meetingTime)
						&& StringUtil.isNotEmpty(taskAssignees)){
					WeekPlan weekPlan=new WeekPlan();
					weekPlan.setMeeting_time(DateUtils.parseDateS(meetingTime));
					weekPlan.setAssigners(taskAssignees.trim().split("\\|")[0]);
					weekPlan.setAssignersID(taskAssignees.trim().split("\\|")[1]);
					weekPlan.setTaskName(projectName);
					if(isSimple){
						if(StringUtil.isNotEmpty(simpleAttendMans)){
							weekPlan.setAssessors(simpleAttendMans.trim().split("\\|")[0]);
						}else{
							weekPlan.setAssessors("");
						}
					}else{
						if(StringUtil.isNotEmpty(attendMans)){
							weekPlan.setAssessors(attendMans.trim());
						}else{
							weekPlan.setAssessors("");
						}
					}
					if(StringUtil.isNotEmpty(meetingAddr)){
						weekPlan.setMeeting_addr(meetingAddr);
					}else{
						weekPlan.setMeeting_addr("");
					}
					list.add(weekPlan);
				}
			}
		}
		return list;
	}
}
