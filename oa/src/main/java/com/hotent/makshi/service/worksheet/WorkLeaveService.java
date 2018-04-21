package com.hotent.makshi.service.worksheet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.worksheet.WorkLeaveDao;
import com.hotent.makshi.model.worksheet.WorkLeave;
import com.hotent.makshi.model.worksheet.WorkSheet;


@Service
public class WorkLeaveService extends BaseService<WorkLeave>
{
	@Resource
	private WorkLeaveDao dao;
	
	public WorkLeaveService()
	{
	}
	
	@Override
	protected IEntityDao<WorkLeave,Long> getEntityDao() 
	{
		return dao;
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
			WorkLeave workLevel=getWorkLevel(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				workLevel.setId(genId);
				this.add(workLevel);
			}else{
				workLevel.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(workLevel);
			}
			cmd.setBusinessKey(workLevel.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取WorkLevel对象
	 * @param json
	 * @return
	 */
	public WorkLeave getWorkLevel(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		WorkLeave workLevel = (WorkLeave)JSONObject.toBean(obj, WorkLeave.class);
		return workLevel;
	}
	/**
	 * 保存 缺勤表 信息
	 * @param workLevel
	 */

	public void save(WorkLeave workLevel) throws Exception{
		Long id=workLevel.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			workLevel.setId(id);
		    this.add(workLevel);
		}
		else{
		    this.update(workLevel);
		}
	}
	
	/**
	 * 获取请假流程数据
	 * @param id
	 * @return
	 */
	public Map<String,Object> getVacationById(Long id){
		return dao.getVacationById(id);
	}
	
	/**
	 * 根据条件获取出勤记录
	 * @param map
	 * @return
	 */
	public List<WorkLeave> getWorkLeaveList(Map<String,Object> map){
		List<WorkLeave> list=dao.getWorkLeaveList(map);
		return list;
	}
	
	/**
	 * 批量插入出勤统计数据
	 * @param map
	 * @return
	 */
	public void batchInsert(Map<String,Object> map){
		dao.batchInsert( map);
	}
	
	
	public List<Map<String,Object>> geProcesstVacation(Map<String,Object> params){
		return dao.geProcesstVacation(params);
	}
}
