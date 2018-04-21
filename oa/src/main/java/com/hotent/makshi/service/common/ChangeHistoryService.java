package com.hotent.makshi.service.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.common.ChangeHistoryDao;
import com.hotent.makshi.model.common.WChangeHistory;


@Service
public class ChangeHistoryService extends WfBaseService<WChangeHistory>
{
	@Resource
	private ChangeHistoryDao dao;
	
	public ChangeHistoryService()
	{
	}
	
	@Override
	protected IEntityDao<WChangeHistory,Long> getEntityDao() 
	{
		return dao;
	}
	
	
	
	public List<WChangeHistory> getListByType(String type,String condition){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("type", type);
		params.put("condition", condition);
		List<WChangeHistory> changeHistoryList = dao.getBySqlKey("getListByType", params);
		return changeHistoryList;
	}
	
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<WChangeHistory> getAll(QueryFilter queryFilter){
		
		List<WChangeHistory> changeHistoryList=super.getAll(queryFilter);
		List<WChangeHistory> changeHistorys=new ArrayList<WChangeHistory>();
		for(WChangeHistory changeHistory:changeHistoryList){
			changeHistorys.add(changeHistory);
		}
		return changeHistorys;
	}
	

	
	
	/**
	 * 根据json字符串获取WChangeHistory对象
	 * @param json
	 * @return
	 */
	public WChangeHistory getWChangeHistory(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		WChangeHistory WChangeHistory = (WChangeHistory)JSONObject.toBean(obj, WChangeHistory.class);
		return WChangeHistory;
	}
	/**
	 * 保存 合同借阅申请 信息
	 * @param WChangeHistory
	 */
	public void save(WChangeHistory changeHistory) throws Exception{
		this.add(changeHistory);
	}
}
