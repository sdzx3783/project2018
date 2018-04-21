package com.hotent.platform.service.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.cache.ICache;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.dao.system.SysOrgDao;
import com.hotent.platform.dao.system.SysOrgTypeDao;
import com.hotent.platform.dao.system.UserPositionDao;
import com.hotent.platform.model.system.Position;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysOrgType;
import com.hotent.platform.model.system.SysPropertyConstants;
import com.hotent.platform.model.system.SysUserOrg;
import com.hotent.platform.model.system.UserPosition;

/**
 * 对象功能:组织架构SYS_ORG Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:pkq
 * 创建时间:2011-11-09 11:20:13
 */
@Service
public class SysOrgService extends BaseService<SysOrg>{
	
	@Resource
	private SysOrgTacticService sysOrgTacticService;

	@Resource
	private SysUserOrgService sysUserOrgService;
	
	@Resource
	private SysOrgRoleService sysOrgRoleService;
	
	@Resource
	private UserPositionDao userPositionDao;
	
	@Resource
	private SysOrgTypeDao sysOrgTypeDao;
	
	@Resource
	private SysOrgRoleManageService sysOrgRoleManageService;
	@Resource(name="wxOrgService")
	private com.hotent.weixin.api.IOrgService wxOrgService;
	
	@Resource
	private PositionService positionService;
	
	@Resource
	private OrgAuthService orgAuthService;

	@Resource
	private SysOrgDao dao; 
	protected static Logger logger = LoggerFactory.getLogger(SysOrgService.class);
	@Override
	protected IEntityDao<SysOrg, Long> getEntityDao() {
		return dao;
	}

	public SysOrgService() {
	}
	


	/* *
	 * 对象功能：根据条件查询组织
	 */
	public List<SysOrg> getOrgByOrgId(QueryFilter queryFilter) {
		return dao.getOrgByOrgId(queryFilter);
	}

	/**
	 * 通过维度取得组织（没有维度ID则获取所有组织）
	 * 
	 * @param demId
	 * @return
	 */
	public List<SysOrg> getOrgsByDemIdOrAll(Long demId) {
		return dao.getOrgsByDemIdOrAll(demId);		
	}
	
	
	public List<SysOrg> getOrgsAll(){
		return dao.getOrgsAll();
	}
	/**
	 * 根据组织的名称查询组织数据。
	 * @param orgName 组织的名称
	 * @return
	 */
	public List<SysOrg> getByOrgName(String orgName) {
		return dao.getByOrgName(orgName);
	}
	
	/**
	 * 根据组织的名称查询组织数据。
	 * @param orgName 组织的名称
	 * @return
	 */
	public List<SysOrg> getByOrgNameNotDel(String orgName) {
		return dao.getByOrgNameNotDel(orgName);
	}
	/* 
	 * 根据path获取所有组织
	 * @param path
	 * @return 
	 */
	public List<SysOrg> getOrgByPath(String path){
		return dao.getOrgByPath(path);
	}
	
	/**
	/**
	 * 根据维度查找组织节点
	 * @param demId
	 * @return 返回Map<Long,SysOrg>其中key为组织ID
	 */
	public Map getOrgMapByDemId(Long demId) {
		String userNameStr = "";
		String userNameCharge = "";
		Map<Long, SysOrg> orgMap = new HashMap<Long, SysOrg>();
		List<SysOrg> list = dao.getOrgsByDemIdOrAll(demId);
		for (SysOrg sysOrg : list) {
			List<UserPosition> userlist =userPositionDao.getByOrgId(sysOrg.getOrgId());
			for (SysUserOrg userOrg : userlist) {
				String isCharge = "";
				if (BeanUtils.isNotEmpty(userOrg.getIsCharge())) {
					isCharge = userOrg.getIsCharge().toString();
				}
				// 为主要负责人
				if (SysUserOrg.CHARRGE_YES.equals(isCharge)) {
					if (userNameCharge.isEmpty()) {
						userNameCharge = userOrg.getUserName();
					} else {
						userNameCharge = userNameCharge + "," + userOrg.getUserName();
					}
				}
			}

			sysOrg.setOwnUserName(userNameCharge);
			if (sysOrg.getOrgSupId() != 0)
				orgMap.put(sysOrg.getOrgId(), sysOrg);
		}
		return orgMap;
	}
	
	public SysOrg getByCode(String code) {
		SysOrg sysOrg = dao.getUnique("getByCode", code);
		return  sysOrg;
	}
	
	/**
	 * 
	 * 判断该组织下是否有人员存在
	 * @param id 组织id
	 * @return 
	 */
	public boolean isUserExistFromOrg(Long id){
		boolean flag = false;
		SysOrg sysOrg=dao.getById(id);
		String path = sysOrg.getPath();
		path = StringUtil.isNotEmpty(path)?(path+"%"):"";
		//查找出该组织下的所有子组织
		List<SysOrg> sysOrgs = dao.getByOrgPath(path);
		for(int i=0;i<sysOrgs.size();i++){
			if(userPositionDao.isUserExistFromOrg(sysOrgs.get(i).getOrgId())){
				flag=true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 删除组织，需要把组织下相关的信息 级联删除
	 * 1、标识删除用户岗位和组织的关系
	 * 2、删除组织下绑定的角色
	 * 3、删除组织本身
	 * 4.删除组织定义的岗位
	 * 5.删除组织分配的角色
	 */
	public void delById(Long id) {
		SysOrg sysOrg=dao.getById(id);
		String path = sysOrg.getPath();
		path = StringUtil.isNotEmpty(path)?(path+"%"):"";
		List<SysOrg> sysOrgs = getOrgByPath(path);

		//删除微信
		boolean isSupportWeixin = PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT,false);
		if(isSupportWeixin) this.delWeiXinOrg(sysOrg);
		//删除组织和人员关联关系
		sysUserOrgService.delByOrgPath(path);
		//删除人员和角色的关系
		sysOrgRoleService.delByOrgPath(path);
		//删除子组织和自身
		dao.delByPath(path);
		//删除组织定义的岗位
		positionService.delByOrgId(sysOrgs);
		//删除组织分配的角色 
		for(int i=0;i<sysOrgs.size();i++){
			//删除组织分配的角色
			sysOrgRoleManageService.delByOrgId(sysOrgs.get(i).getOrgId());
			//删除组织下的分级管理员
			orgAuthService.delByOrgId(sysOrgs.get(i).getOrgId());
		}
	}
	/**
	 * dao.getByOrgPath(path); 需要注意一定以path 排序。默认是父亲在前面
	 * @param sysOrg
	 */
	private void delWeiXinOrg(SysOrg sysOrg) {
		String path = sysOrg.getPath();
		path = StringUtil.isNotEmpty(path)?(path+"%"):"";
		
		List<SysOrg> orgChildren =dao.getByOrgPath(path);
		for (int i = orgChildren.size(); i>0 ; i--) {
			wxOrgService.delete(orgChildren.get(i-1).getCode());
		} 
	}

	public List<SysOrg> getOrgsByUserId(Long userId) {
		return dao.getOrgsByUserId(userId);
	}

	/**
	 * 取得某个用户所在的组织ID字符串
	 * 
	 * @param userId
	 * @return 返回格式如1,2
	 */
	public String getOrgIdsByUserId(Long userId) {
		StringBuffer sb = new StringBuffer();
		List<SysOrg> orgList = dao.getOrgsByUserId(userId);
		for (SysOrg org : orgList) {
			sb.append(org.getOrgId() + ",");
		}
		if (orgList.size() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	/**
	 * 
	 * @param userId
	 * @return 返回格式如1,2
	 */
	public Long getOrgIdByUserId(Long userId) {

		PositionService positionService=AppUtil.getBean(PositionService.class);
		SysOrgService sysOrgService=AppUtil.getBean(SysOrgService.class);
		Position position=positionService.getDefaultPosByUserId(userId);
		if(position!=null){
			Long orgId=position.getOrgId();
			SysOrg sysOrg = sysOrgService.getById(orgId);
			if(sysOrg!=null) return sysOrg.getOrgId();
		}
		return null;
	}

	/**
	 * 转达化数据格式,把List<SysOrg>转化为Map<Long,List<SysOrg>>。
	 * 
	 * @param rootId
	 * @param instList
	 * @return
	 */
	private Map<Long, List<SysOrg>> coverTreeData(Long rootId, List<SysOrg> instList) {
		Map<Long, List<SysOrg>> dataMap = new HashMap<Long, List<SysOrg>>();
		dataMap.put(rootId.longValue(), new ArrayList<SysOrg>());
		if (instList != null && instList.size() > 0) {
			for (SysOrg sysOrg : instList) {
				long parentId = sysOrg.getOrgSupId().longValue();
				if (dataMap.get(parentId) == null) {
					dataMap.put(parentId, new ArrayList<SysOrg>());
				}
				dataMap.get(parentId).add(sysOrg);
			}
		}
		return dataMap;
	}

	/**
	 * 转化数所格式,将原如list 重新按树形结构排序
	 * 
	 * @param rootId
	 * @param instList
	 * @return
	 */
	public List<SysOrg> coverTreeList(Long rootId, List<SysOrg> instList) {

		Map<Long, List<SysOrg>> dataMap = coverTreeData(rootId, instList);

		List<SysOrg> list = new ArrayList<SysOrg>();

		list.addAll(getChildList(rootId, dataMap));

		return list;
	}

	/**
	 * 转化数所格式,将原如list 重新按树形结构排序
	 * 
	 * @param parentId
	 * @param dataMap
	 * @return
	 */
	private List<SysOrg> getChildList(Long parentId, Map<Long, List<SysOrg>> dataMap) {
		List<SysOrg> list = new ArrayList<SysOrg>();

		List<SysOrg> orgList = dataMap.get(parentId.longValue());
		if (orgList != null && orgList.size() > 0) {
			for (SysOrg sysOrg : orgList) {
				list.add(sysOrg);
				List<SysOrg> childList = getChildList(sysOrg.getOrgId(), dataMap);
				list.addAll(childList);
			}
		}
		return list;
	}

	public List<SysOrg> getByUserIdAndDemId(Long userId, Long demId) {
		return dao.getByUserIdAndDemId(userId, demId);
	}

	

	/**
	 *拖动组织进行排序。
	 * @param targetId 目标组织ID
	 * @param dragId 	拖动的组织ID
	 * @param moveType 拖动类型 (prev,next,inner);
	 */
	public void move(Long targetId, Long dragId, String moveType) {
		SysOrg target = dao.getById(targetId);
		SysOrg dragged = dao.getById(dragId);
		
		if(!target.getDemId().equals(dragged.getDemId()))
			return;
			
		String nodePath=dragged.getPath();
		//根据拖动节点的路径找到其下所有的子组织。
		List<SysOrg> list=dao.getByOrgPath(nodePath);
		
		for(SysOrg org:list){
			//向目标节点的前后拖动。
			if ("prev".equals(moveType) || "next".equals(moveType)) {
				String targetPath=target.getPath();
				String parentPath=targetPath.endsWith(".")?targetPath.substring(0,targetPath.length()-1):targetPath;
				//这个路径尾部带 "." 。
				parentPath=parentPath.substring(0,parentPath.lastIndexOf(".")+1) ;
				
				if(org.getOrgId().equals(dragId)){
					org.setOrgSupId(target.getOrgSupId());
					org.setPath(parentPath + dragId +".");
				}
				else{
					String path = org.getPath();
					String tmpPath =parentPath + dragId +"." +   path.replaceAll(nodePath, "");
					org.setPath(tmpPath);
				}
				
				if ("prev".equals(moveType)) {
					org.setSn(target.getSn() - 1);
				} else {
					org.setSn(target.getSn() + 1);
				}
			}
			//作为目标节点的子节点。
			else{
				//如果是被拖动的节点。
				////需改父节点
				if(org.getOrgId().equals(dragId)){
					//修改拖动的分类对象
					org.setOrgSupId(targetId);
					// 修改nodepath
					org.setPath(target.getPath() + org.getOrgId() + ".");					
				} else {
					// 带点的路径
					String path = org.getPath();
					// 替换父节点的路径。
					String tmpPath = path.replaceAll(nodePath, "");
					// 新的父节路径
					String targetPath = target.getPath();
					// 新的父节点 +拖动的节点id + 后缀
					String tmp = targetPath + dragged.getOrgId() + "." + tmpPath;
					org.setPath(tmp);					
				}
			}
			dao.update(org);
		}
	}
	
	
	
	/**
	 * 添加组织。
	 * @param sysOrg
	 * @throws Exception
	 */
	public void addOrg(SysOrg sysOrg) throws Exception{
		sysOrg.setSn(sysOrg.getOrgId());
		//添加组织
		dao.add(sysOrg);
		
		//如果支持微信添加至微信通讯录
		if(!PropertyUtil.getBooleanByAlias(SysPropertyConstants.WX_IS_SUPPORT,false)) return;
		
		SysOrg parentOrg = this.getById(sysOrg.getOrgSupId());
		if(parentOrg!=null) {
			sysOrg.setSupCode(parentOrg.getCode());
		}
		else{
			sysOrg.setSupCode("1");
		}
		wxOrgService.create(sysOrg);
	}
	
	/**
	 * 更新组织数据。
	 * @param sysOrg
	 * @throws Exception
	 */
	public void updOrg(SysOrg sysOrg) throws Exception{
		//添加组织
		dao.update(sysOrg);
		
		//如果支持微信添加至微信通讯录
		if(!PropertyUtil.getBooleanByAlias("",false)) return;
		SysOrg parentOrg = this.getById(sysOrg.getOrgSupId());
		sysOrg.setSupCode(parentOrg.getCode());
		wxOrgService.update(sysOrg);
	}
	
	/**
	 * 根据用户取得主组织。
	 * @param userId
	 * @return
	 */
	public SysOrg getPrimaryOrgByUserId(Long userId){
		return dao.getPrimaryOrgByUserId(userId);
	}
	
	/**
	 * 根据路径得到组织集合 
	 * @param path
	 * @return
	 */
	public List<SysOrg> getByOrgPath(String path){	
		return dao.getByOrgPath(path);
	}
	
	/**
	 * 获取组织最近一级带有类型的父节点 
	 * @param path
	 * @return
	 */
	public SysOrg getParentWithType(SysOrg sysOrg){		
		Long parentOrgId=sysOrg.getOrgSupId();
		if(parentOrgId.equals(Long.parseLong("1"))) return null;
		SysOrg parentOrg=dao.getById(parentOrgId);
		if(parentOrg==null ) return null;		
		if(parentOrg.getOrgType()!=null && sysOrgTypeDao.getById(parentOrg.getOrgType())!=null){
			return parentOrg;
		}	else{
			parentOrg=getParentWithType(parentOrg);
		}			
		return parentOrg;
	}
	/**
	 * 根据指定组织和组织类型，往上找到与指定组织类型相同或比指定组织类型更大一级的父组织 
	 * @param sysOrg 当前组织
	 * @return sysOrgType 要查找的组织类型
	 */
	public SysOrg   getParentWithTypeLevel(SysOrg sysOrg,SysOrgType	sysOrgType){	
		 //获取指定部门类型的类型记录
		SysOrg parentOrg=getParentWithType(sysOrg);  //查找所需的父级部门		
		if(parentOrg==null) return parentOrg;
		SysOrgType	currentSysOrgType=sysOrgTypeDao.getById(parentOrg.getOrgType());
		//如果返回的类型比指定类型还小的话，继续查询
		if(currentSysOrgType!=null && sysOrgType.getLevels()<currentSysOrgType.getLevels()){  
			parentOrg=getParentWithTypeLevel(parentOrg,sysOrgType);	
		}
		return parentOrg;
	}
	
	/**
	 * 根据用户ID获取默认的的组织。
	 * @param userId
	 * @return
	 */
	public SysOrg getDefaultOrgByUserId(Long userId){
		
		List<UserPosition>  list= userPositionDao.getByUserId(userId);
		Long orgId=0L;
		//个人不属于任何一个部门。
		if(BeanUtils.isEmpty(list)) return null;
		if(list.size()==1) {
			orgId=list.get(0).getOrgId();
		}else{
			//获取主要的组织。
			for(UserPosition userPosition:list){
				if(userPosition.getIsPrimary()==1){
					orgId=userPosition.getOrgId();
					break;
				}
			}
			//没有获取到主组织，从列表中获取一个组织作为当前组织。
			if(orgId==0L) orgId=list.get(0).getOrgId();
		}
		return  dao.getById(orgId);
	}
	
	//更新sn
	public void updSn(Long orgId, long sn) {
		dao.updSn(orgId,sn);
		
	}
	/**
	 * 根据上级组织ID获取组织列表。
	 * @param userId
	 * @return
	 */
	public List<SysOrg> getOrgByOrgSupId(Long orgSupId){
		List<SysOrg> list= dao.getOrgByOrgSupId(orgSupId);
		return list;
	}
	
	/**
	 * 根据上级组织ID获取此上级组织下level级组织列表，level为配置参数。
	 * @param orgSupId
	 * @param demId
	 * @return 
	 */
	public List<SysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId){
		List<SysOrg> childList= dao.getOrgByOrgSupId(orgSupId);
		
		int level =PropertyUtil.getIntByAlias("orgExpandLevel", 0);
		int childSize=childList.size();
		if(level==0){
			for(int i=0;i<childSize;i++){
				List<SysOrg> MoreList=getOrgByOrgSupIdAndLevel(childList.get(i).getOrgId());
				childList.addAll(MoreList);
			}
		} 
		if(level>1){
			level--;
			for(int i=0;i<childSize;i++){
				List<SysOrg> MoreList = getOrgByOrgSupIdAndLevel(childList.get(i).getOrgId(),level);
				childList.addAll(MoreList)	;
			}
		}
		return childList;
	}

	public List<SysOrg> getOrgByOrgSupIdAndLevel(Long orgSupId,int level) {
		List<SysOrg> childList = new ArrayList<SysOrg>();
		if(level>0){
			childList= dao.getOrgByOrgSupId(orgSupId);
			level--;
			int childSize=childList.size();
			for(int i=0;i<childSize;i++){
				List<SysOrg> MoreList=getOrgByOrgSupIdAndLevel(childList.get(i).getOrgId(),level);
				childList.addAll(MoreList)	;
			}
		}
		return childList;
	}
	
	/**
	 * 根据分组获取组织列表。
	 * @param groupId
	 * @return
	 */
	public List<SysOrg> getByOrgMonGroup(Long groupId){
		return dao.getByOrgMonGroup(groupId);
	}

	
	/**
	 * mobile 取组织结构
	 * @param orgid
	 * @param orgName
	 * @return
	 */
	public List<SysOrg> getOrgForMobile(QueryFilter filter){
		return  dao.getBySqlKey("getBySupId4MobileOrg", filter);
	}

	public List<SysOrg> getByOrgType(Long orgType) {
		return  dao.getByOrgType(orgType);
	}
	
	private Map<String,SysOrg> convertToMap(List<SysOrg> companyList){
		Map<String,SysOrg> map=new HashMap<String, SysOrg>();
		for(SysOrg org:companyList){
			map.put(org.getOrgId().toString(), org);
		}
		return map;
	}
	
	/**
	 * 更新组织的分公司字段。
	 */
	public void updCompany(){
		//获取所有的分公司
		List<SysOrg> companyList= sysOrgTacticService.getSysOrgListByOrgTactic();
		Map<String,SysOrg> companyMap=convertToMap(companyList);
		List<SysOrg> list= dao.getAll();
		
		this.clearCompany();	// 先清空原来的已经生产的策略
		
		for(SysOrg sysOrg:list){ 
			String path=sysOrg.getPath();
			path= StringUtil.trimPrefix(path, SysOrg.BEGIN_ORGID +".");
			path= StringUtil.trimPrefix(path, ".");
			String[] aryPath=path.split("[.]");
			for(int i=aryPath.length-1;i>=0;i--){
				if(companyMap.containsKey(aryPath[i])){
					SysOrg org= dao.getById(new Long(aryPath[i]));
					sysOrg.setCompanyId(org.getOrgId());
					sysOrg.setCompany(org.getOrgName());
					dao.update(sysOrg);
					break;
				}
			}
		}
	}
	
	/**
	 * 清空原来分公司策略
	 */
	private void clearCompany() {
		dao.clearCompany();
	}

	/**
	 * 获取所有分公司
	 * @return
	 */
	public List<Map<String,Object>> getCompany(){
		return dao.getCompany();
	}
	
	
	/**
	 * 根据code和id判断组织是否存在。
	 * @param code	组织代码
	 * @param id	组织ID
	 * @return 
	 * Integer
	 */
	public Integer getCountByCode(String code,Long id){
		return (Integer)dao.getCountByCode(code, id);
	}


	public List getByParentId(Long parentId, Map<String, Object> params) {
		return dao.getOrgByOrgSupId(parentId);
	}

	
	/**同步所有组织至微信通讯录**/
	public void syncAllOrg() {
		//添加根组织
		List<SysOrg> orgs = this.getByParentId(1L, new HashMap<String,Object>());
		for (SysOrg rootOrg : orgs) { 
			rootOrg.setSupCode("1"); 
			wxOrgService.create(rootOrg);
			syncOrgsByParentId(rootOrg.getOrgId(),rootOrg.getCode());
		}
	}
	
	/**根据父亲同步孩子*/
	public void syncOrgsByParentId(Long parentId,String parentCode){
		Map<String,Object> map = new HashMap<String,Object>();
		List<SysOrg> orgs = this.getByParentId(parentId, map);
		for (SysOrg org : orgs) { org.setSupCode(parentCode); }
		wxOrgService.addAll((List)orgs);
		
		for (SysOrg org : orgs) {
			syncOrgsByParentId(org.getOrgId(),org.getCode());
		}
	}
	/**
	 * 将某种来源的组织全部置为删除状态
	 * @param orgFromTypeHnair
	 */
	public void setDelStatusByFromType(Short fromType) {
		dao.update("setDelStatusByFromType", fromType);
	}
	
	/**
	 * 资源部门Id转化为实际部门Id
	 * @param resId
	 * @return
	 */
	public Long changeResIdToOrgId(String resId){
		String result="-1";
		switch (resId) {
		case "10000005010038"://公司
			result="-1";
			break;
		case "10000010330001"://咨询部
			result="10000011000055";
			break;
		case "10000008060007"://招标部
			result="10000011000053";
			break;
		case "10000010880097"://环境事业部
			result="10000011000078";
			break;
		case "10000008480017"://运维部
			result="10000011000073";
			break;
		case "10000011390000"://水保部
			result="10000007780656";
			break;
		case "10000014580000"://河道管养部
			result="10000011000075";
			break;
		case "10000000430001"://综合部
			result="10000011000072";
			break;
		case "10000000430002"://监理部
			result="10000007780857";
			break;
		case "40000003690095":
			result="10000007780616";
			break;
		}
		return Long.parseLong(result);
	}
	
	
	
	/**
	 * 资源部门Id转化为实际部门Id
	 * @param resId
	 * @return
	 */
	public String changeOrgIdToCh(String orgId){
		String result="";
		switch (orgId) {
		case "-1"://公司
			result="";
			break;
		case "10000011000055":
			result="咨询部";
			break;
		case "10000011000053"://
			result="招标代理部";
			break;
		case "10000011000078"://
			result="环境事业部";
			break;
		case "10000011000073"://
			result="运维部";
			break;
		case "10000007780656"://
			result="水保部";
			break;
		case "10000011000075"://
			result="河道管养部";
			break;
		case "10000011000072"://
			result="综合部";
			break;
		case "10000007780857"://
			result="监理部";
			break;
		case "10000007780616":
			result="办公室";
			break;
		}
		return result;
	}
}

