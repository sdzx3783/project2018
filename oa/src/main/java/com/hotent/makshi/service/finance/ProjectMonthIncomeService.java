package com.hotent.makshi.service.finance;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.finance.ProjectMonthIncome;
import com.hotent.makshi.model.finance.UserWorkCost;
import com.hotent.makshi.dao.finance.ProjectMonthIncomeDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class ProjectMonthIncomeService extends BaseService<ProjectMonthIncome>
{
	@Resource
	private ProjectMonthIncomeDao dao;
	
	public ProjectMonthIncomeService()
	{
	}
	
	@Override
	protected IEntityDao<ProjectMonthIncome,Long> getEntityDao() 
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
			ProjectMonthIncome projectMonthIncome=getProjectMonthIncome(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				projectMonthIncome.setId(genId);
				this.add(projectMonthIncome);
			}else{
				projectMonthIncome.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(projectMonthIncome);
			}
			cmd.setBusinessKey(projectMonthIncome.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ProjectMonthIncome对象
	 * @param json
	 * @return
	 */
	public ProjectMonthIncome getProjectMonthIncome(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ProjectMonthIncome projectMonthIncome = (ProjectMonthIncome)JSONObject.toBean(obj, ProjectMonthIncome.class);
		return projectMonthIncome;
	}
	/**
	 * 保存 项目月度收入 信息
	 * @param projectMonthIncome
	 */

	public void save(ProjectMonthIncome projectMonthIncome) throws Exception{
		Long id=projectMonthIncome.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			projectMonthIncome.setId(id);
		    this.add(projectMonthIncome);
		}
		else{
		    this.update(projectMonthIncome);
		}
	}

	public List<ProjectMonthIncome> getByProIdAndMonth(String id, String month) {
		Map<String,Object> map=new HashMap<>();
		map.put("proid", id);
		map.put("month", month);
		return dao.getBySqlKey("getByProIdAndMonth", map);
	}
}
