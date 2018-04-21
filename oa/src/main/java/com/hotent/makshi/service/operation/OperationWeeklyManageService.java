package com.hotent.makshi.service.operation;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.operation.OperationWeeklyManageDao;
import com.hotent.makshi.model.operation.AccessExamine;
import com.hotent.makshi.model.operation.OperationWeeklyManage;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;


@Service
public class OperationWeeklyManageService extends BaseService<OperationWeeklyManage>
{
	@Resource
	private OperationWeeklyManageDao dao;
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource 
	private SysRoleService sysRoleService;
	
	public OperationWeeklyManageService()
	{
	}
	
	@Override
	protected IEntityDao<OperationWeeklyManage,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 根据json字符串获取OperationWeeklyManage对象
	 * @param json
	 * @return
	 */
	public OperationWeeklyManage getOperationWeeklyManage(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		OperationWeeklyManage operationWeeklyManage = (OperationWeeklyManage)JSONObject.toBean(obj, OperationWeeklyManage.class);
		return operationWeeklyManage;
	}
	/**
	 * 保存 运维部周报管理 信息
	 * @param operationWeeklyManage
	 */

	public void save(OperationWeeklyManage operationWeeklyManage) throws Exception{
		Long id=operationWeeklyManage.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			operationWeeklyManage.setId(id);
		    this.add(operationWeeklyManage);
		}
		else{
		    this.update(operationWeeklyManage);
		}
	}
	
	//检测是否拥有编辑权限
	public AccessExamine examineIsEditer(Boolean isEditer,QueryFilter fiter,Integer type){
		AccessExamine accessExamine= new AccessExamine();
		//获取当前登录用户角色,判断是否具有编辑权限
		Long userId = ContextUtil.getCurrentUserId();
		
		if(userId==1L){
			isEditer = true;
		}
		List<SysRole> sysRoleList = sysRoleService.getByUserId(userId);
		//周报管理员
		if(type==1){
			for (SysRole sysRole : sysRoleList) {
				if (sysRole.getRoleId()==10000008510001L) {
					isEditer = true;
				}
			}
		}
		//车务管理员
		if(type==2){
			for (SysRole sysRole : sysRoleList) {
				if (sysRole.getRoleId()==20000001090260L) {
					isEditer = true;
				}
			}
		}
		//物品采购管理管理员
		if(type==3){
			for (SysRole sysRole : sysRoleList) {
				String alias = sysRole.getAlias();
				if (alias!=null && alias.equals("wpcggly-ywb")) {
					isEditer = true;
				}
			}
		}
		//获取当前登录用户组织
		SysOrg sysOrg = (SysOrg) ContextUtil.getCurrentOrg();
		Long orgId = 0L;
		if(null!=sysOrg){
			Long orgType = sysOrg.getOrgType();
			//添加限制条件,只能看当前组织的记录
			if(orgType!=null&&orgType>=3){
				String orgName = sysOrg.getOrgName();
				if(type==1){
					fiter.addFilter("grop", orgName);
				}
				if(type==2){
					String list = "";
					if(sysOrg.getOrgType()==3){
						orgId = sysOrg.getOrgId();
						list = getIncludeOrg(orgId);
					}
					if(sysOrg.getOrgType()>3){
						orgId = sysOrg.getOrgSupId();
						list = getIncludeOrg(orgId);
					}
					fiter.addFilter("departmentID", list);
				}
			}
		}
		accessExamine.setFiter(fiter);
		accessExamine.setIsEditer(isEditer);
		return accessExamine;
	}
	
	public String getIncludeOrg(Long orgId){
		String list = "";
		list = list+orgId.toString();
		List<SysOrg> orgList = sysOrgDao.getAllOrgIdByOrgId(orgId);
		if(orgList.size()>0){
			for(SysOrg sysOrg:orgList){
				list = list+","+sysOrg.getOrgId().toString();
			}
		}
		return list;
	}
}
