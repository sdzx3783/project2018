package com.hotent.makshi.service.template;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.template.RuleManager;
import com.hotent.makshi.dao.template.RuleManagerDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;

import com.hotent.makshi.model.template.RuleBookmark;
import com.hotent.makshi.dao.template.RuleBookmarkDao;

import com.hotent.core.service.BaseService;


@Service
public class RuleManagerService extends BaseService<RuleManager>
{
	@Resource
	private RuleManagerDao dao;
	
	@Resource
	private RuleBookmarkDao ruleBookmarkDao;
	public RuleManagerService()
	{
	}
	
	@Override
	protected IEntityDao<RuleManager,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param id
	 */
	private void delByPk(Long id){
		ruleBookmarkDao.delByMainId(id);
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
	 * @param ruleManager
	 * @throws Exception
	 */
	public void addAll(RuleManager ruleManager) throws Exception{
		super.add(ruleManager);
		addSubList(ruleManager);
	}
	
	/**
	 * 更新数据
	 * @param ruleManager
	 * @throws Exception
	 */
	public void updateAll(RuleManager ruleManager) throws Exception{
		super.update(ruleManager);
		delByPk(ruleManager.getId());
		addSubList(ruleManager);
	}
	
	/**
	 * 添加子表记录
	 * @param ruleManager
	 * @throws Exception
	 */
	public void addSubList(RuleManager ruleManager) throws Exception{
		
		List<RuleBookmark> ruleBookmarkList=ruleManager.getRuleBookmarkList();
		if(BeanUtils.isNotEmpty(ruleBookmarkList)){
			
			for(RuleBookmark ruleBookmark:ruleBookmarkList){
				ruleBookmark.setRefId(ruleManager.getId());
				Long id=UniqueIdUtil.genId();
				ruleBookmark.setId(id);				
				ruleBookmarkDao.add(ruleBookmark);
			}
		}
	}
	
	/**
	 * 根据外键获得rule_bookmark列表
	 * @param id
	 * @return
	 */
	public List<RuleBookmark> getRuleBookmarkList(Long id) {
		return ruleBookmarkDao.getByMainId(id);
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
			RuleManager ruleManager=getRuleManager(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				ruleManager.setId(genId);
				this.addAll(ruleManager);
			}else{
				ruleManager.setId(Long.parseLong(cmd.getBusinessKey()));
				this.updateAll(ruleManager);
			}
			cmd.setBusinessKey(ruleManager.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取RuleManager对象
	 * @param json
	 * @return
	 */
	public RuleManager getRuleManager(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Map<String,  Class> map=new HashMap<String,  Class>();
		map.put("ruleBookmarkList", RuleBookmark.class);
		RuleManager ruleManager = (RuleManager)JSONObject.toBean(obj, RuleManager.class,map);
		return ruleManager;
	}
	/**
	 * 保存 规则管理 信息
	 * @param ruleManager
	 */

	public void save(RuleManager ruleManager) throws Exception{
		Long id=ruleManager.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			ruleManager.setId(id);
			this.addAll(ruleManager);
		}
		else{
		    this.updateAll(ruleManager);
		}
	}
}
