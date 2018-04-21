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
import com.hotent.makshi.dao.worksheet.WorkSheetDao;
import com.hotent.makshi.model.worksheet.WorkSheet;


@Service
public class WorkSheetService extends BaseService<WorkSheet>
{
	@Resource
	private WorkSheetDao dao;
	
	public WorkSheetService()
	{
	}
	
	@Override
	protected IEntityDao<WorkSheet,Long> getEntityDao() 
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
			WorkSheet workSheet=getWorkSheet(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				workSheet.setId(genId);
				this.add(workSheet);
			}else{
				workSheet.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(workSheet);
			}
			cmd.setBusinessKey(workSheet.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取WorkSheet对象
	 * @param json
	 * @return
	 */
	public WorkSheet getWorkSheet(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		WorkSheet workSheet = (WorkSheet)JSONObject.toBean(obj, WorkSheet.class);
		return workSheet;
	}
	/**
	 * 保存 考勤记录 信息
	 * @param workSheet
	 */

	public void save(WorkSheet workSheet) throws Exception{
		Long id=workSheet.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			workSheet.setId(id);
		    this.add(workSheet);
		}
		else{
		    this.update(workSheet);
		}
	}
	
	/**
	 * 根据条件获取出勤记录
	 * @param map
	 * @return
	 */
	public List<WorkSheet> getWorkSheetList(Map<String,Object> map){
		List<WorkSheet> list=dao.getWorkSheetList(map);
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
}
