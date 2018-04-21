package com.hotent.makshi.service.honor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.makshi.model.honor.AccessExamine;
import com.hotent.makshi.model.honor.PersonalHonor;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.honor.PersonalHonor;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.dao.honor.PersonalHonorDao;
import com.hotent.platform.model.system.SysOrg;
import com.hotent.platform.model.system.SysRole;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;

import net.sf.json.JSONObject;








import com.hotent.core.service.BaseService;


@Service
public class PersonalHonorService extends BaseService<PersonalHonor>
{
	@Resource
	private PersonalHonorDao dao;
	@Resource 
	private SysRoleService sysRoleService;
	
	public PersonalHonorService()
	{
	}
	
	@Override
	protected IEntityDao<PersonalHonor,Long> getEntityDao() 
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
			PersonalHonor personalHonor=getPersonalHonor(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				personalHonor.setId(genId);
				this.add(personalHonor);
			}else{
				personalHonor.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(personalHonor);
			}
			cmd.setBusinessKey(personalHonor.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PersonalHonor对象
	 * @param json
	 * @return
	 */
	public PersonalHonor getPersonalHonor(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PersonalHonor personalHonor = (PersonalHonor)JSONObject.toBean(obj, PersonalHonor.class);
		return personalHonor;
	}
	
//	//检测是否拥有编辑权限
//		public AccessExamine examineIsEditer(Boolean isEditer,QueryFilter fiter,Integer type){
//			AccessExamine accessExamine= new AccessExamine();
//			//获取当前登录用户角色,判断是否具有编辑权限
//			Long userId = ContextUtil.getCurrentUserId();
//			
//			if(userId==1L){
//				isEditer = true;
//			}
//			//List<SysRole> sysRoleList = sysRoleService.getByUserId(userId);
//			List<PersonalHonor> personalList=getAll();
//			ISysUser sys=ContextUtil.getCurrentUser();
//			String sysuserId=sys.getAccount();
//			if(type==2){
//				for (PersonalHonor personal : personalList) {
//					  String user_num=personal.getUser_num();
//					if (user_num.equals(sysuserId)) {
//						isEditer = true;
//					}
//				}
//			}
//			
//			
//			accessExamine.setFiter(fiter);
//			accessExamine.setIsEditer(isEditer);
//			return accessExamine;
//		}
	
	

	/**
	 * 保存 个人荣誉 信息
	 * @param personalHonor
	 */

	public void save(PersonalHonor personalHonor) throws Exception{
		Long id=personalHonor.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			personalHonor.setId(id);
		    this.add(personalHonor);
		}
		else{
		    this.update(personalHonor);
		}
	}
	
	
	public List<PersonalHonor> getByUid(Long uid){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("uid", uid);
		List<PersonalHonor> personalHonorList = dao.getBySqlKey("getByUid", params);
		return personalHonorList;
	}
	
	
}
