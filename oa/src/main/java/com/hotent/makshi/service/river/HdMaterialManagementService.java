package com.hotent.makshi.service.river;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.operation.AccessExamine;
import com.hotent.makshi.model.river.HdMaterialManagement;
import com.hotent.makshi.dao.river.HdMaterialManagementDao;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;

import net.sf.json.JSONObject;





import com.hotent.core.service.BaseService;


@Service
public class HdMaterialManagementService extends BaseService<HdMaterialManagement>
{
	@Resource
	private HdMaterialManagementDao dao;
	
	@Resource
	private SysOrgDao sysOrgDao;
	@Resource 
	private SysRoleService sysRoleService;
	public HdMaterialManagementService()
	{
	}
	
	@Override
	protected IEntityDao<HdMaterialManagement,Long> getEntityDao() 
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
			HdMaterialManagement hdMaterialManagement=getHdMaterialManagement(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hdMaterialManagement.setId(genId);
				this.add(hdMaterialManagement);
			}else{
				hdMaterialManagement.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hdMaterialManagement);
			}
			cmd.setBusinessKey(hdMaterialManagement.getId().toString());
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
		//部门内部物资管理员
		if(type==2){
			for (SysRole sysRole : sysRoleList) {
				if (sysRole.getRoleId()==20000001740137L) {
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
	
	/**
	 * 根据json字符串获取HdMaterialManagement对象
	 * @param json
	 * @return
	 */
	public HdMaterialManagement getHdMaterialManagement(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HdMaterialManagement hdMaterialManagement = (HdMaterialManagement)JSONObject.toBean(obj, HdMaterialManagement.class);
		return hdMaterialManagement;
	}
	/**
	 * 保存 部门内部物资管理 信息
	 * @param hdMaterialManagement
	 */

	public void save(HdMaterialManagement hdMaterialManagement) throws Exception{
		Long id=hdMaterialManagement.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hdMaterialManagement.setId(id);
		    this.add(hdMaterialManagement);
		}
		else{
		    this.update(hdMaterialManagement);
		}
	}
}
