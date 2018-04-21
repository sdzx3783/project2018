package com.hotent.makshi.service.operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.operation.MonthlyManage;
import com.hotent.makshi.dao.operation.MonthlyManageDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class MonthlyManageService extends BaseService<MonthlyManage>
{
	@Resource
	private MonthlyManageDao dao;
	
	public MonthlyManageService()
	{
	}
	
	@Override
	protected IEntityDao<MonthlyManage,Long> getEntityDao() 
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
			MonthlyManage monthlyManage=getMonthlyManage(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				monthlyManage.setId(genId);
				this.add(monthlyManage);
			}else{
				monthlyManage.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(monthlyManage);
			}
			cmd.setBusinessKey(monthlyManage.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取MonthlyManage对象
	 * @param json
	 * @return
	 */
	public MonthlyManage getMonthlyManage(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		MonthlyManage monthlyManage = (MonthlyManage)JSONObject.toBean(obj, MonthlyManage.class);
		return monthlyManage;
	}
	/**
	 * 保存 月报管理 信息
	 * @param monthlyManage
	 */

	public void save(MonthlyManage monthlyManage) throws Exception{
		Long id=monthlyManage.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			monthlyManage.setId(id);
		    this.add(monthlyManage);
		}
		else{
		    this.update(monthlyManage);
		}
	}
}
