package com.hotent.platform.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.page.PageBean;
import com.hotent.core.page.PageList;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.system.SysBulletinDao;
import com.hotent.platform.model.system.SysBulletin;

@Service
public class SysBulletinService extends BaseService<SysBulletin> {
	@Resource
	private SysBulletinDao dao;
	@Resource
	private SysOrgTacticService orgTacticService;

	public SysBulletinService() {
	}

	@Override
	protected IEntityDao<SysBulletin, Long> getEntityDao() {
		return dao;
	}

	/**
	 * 保存 公告表 信息
	 * 
	 * @param sysBulletin
	 */
	public void save(SysBulletin sysBulletin) {
		Long id = sysBulletin.getId();
		if (id == null || id == 0) {
			id = UniqueIdUtil.genId();
			sysBulletin.setId(id);
			this.add(sysBulletin);
		} else {
			this.update(sysBulletin);
		}
	}
	
	public void saveByFlow(ProcessCmd  cmd){
		Map map= cmd.getFormDataMap();
		String json =  JSONObject.toJSON(map).toString();
		SysBulletin bulletin = JSONObject.parseObject(json,SysBulletin.class);
		if(StringUtil.isEmpty( bulletin.getSubject())) return;
		
		bulletin.setStatus(0);
		bulletin.setCreator(ContextUtil.getCurrentUser().getFullname());
		bulletin.setCreatorid(ContextUtil.getCurrentUserId());
		
		cmd.addVariable("type", bulletin.getColumnid()+"");
		if(StringUtil.isNotEmpty(cmd.getBusinessKey())){
			bulletin.setId(Long.parseLong(cmd.getBusinessKey()) );
			this.update(bulletin);
			return;
		}
		
		Long id=UniqueIdUtil.genId();
		bulletin.setId(id);
		//写回主键，这个数据将保存到流程中。
		cmd.setBusinessKey(id.toString()); 
		this.add(bulletin);
	}
	
	public void updateStatus(Long id,int status){
		dao.updateStatus( id, status);
	}

	
	/**
	 * 根据栏目id取得list
	 * 
	 * @param columnId
	 */
	public void delByColumnId(Long columnId) {
		dao.delByColumnId(columnId);
	}
	
	/**
	 * 根据别名取list
	 * 
	 * @param tenantId
	 * @param alias
	 * @param pb
	 * @return
	 */
	public List<SysBulletin> getAllByAlias(String alias) {
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(10);
		pb.setShowTotal(false);
		List<SysBulletin> list = dao.getAllByAlias(alias, pb);
		return list;
	}
	
	/**
	 * 
	 * @param queryFilter
	 * @return
	 */
	public List<SysBulletin> getAllByAlias(QueryFilter queryFilter) {
		return dao.getAllByAlias(queryFilter);
	}
	
	/**
	 * 取当前用户的可以看到的公告
	 */
	public List<SysBulletin> getTopBulletin(int pageSize){
		List<SysBulletin> list = new ArrayList<SysBulletin>();
		Map<String, Object> map =new HashMap<String, Object>();
		PageBean pb = new PageBean();
		pb.setCurrentPage(1);
		pb.setPagesize(pageSize);
		if(!ContextUtil.isSuperAdmin()){
			Long companyId = ContextUtil.getCurrentCompanyId();
			map.put("companyId", companyId);
		}
		list = dao.getBySqlKey("getTopBulletin", map, pb);
		return list;
	}
	
	
	public PageList<SysBulletin> getByColumnId(QueryFilter queryFilter){
		PageList<SysBulletin> list = dao.getByColumnId(queryFilter);
		return list;
	}
	
	
}
