package com.hotent.makshi.service.title;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.title.OccupationalRequirements;
import com.hotent.makshi.model.userinfo.UserInfomation;
import com.hotent.makshi.dao.title.OccupationalRequirementsDao;
import com.hotent.platform.model.system.SysUser;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;

@Service
public class OccupationalRequirementsService extends BaseService<OccupationalRequirements>
{
	@Resource
	private OccupationalRequirementsDao dao;
	
	public OccupationalRequirementsService()
	{
	}
	
	@Override
	protected IEntityDao<OccupationalRequirements,Long> getEntityDao() 
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
			OccupationalRequirements occupationalRequirements=getOccupationalRequirements(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				occupationalRequirements.setId(genId);
				this.add(occupationalRequirements);
			}else{
				occupationalRequirements.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(occupationalRequirements);
			}
			cmd.setBusinessKey(occupationalRequirements.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取OccupationalRequirements对象
	 * @param json
	 * @return
	 */
	public OccupationalRequirements getOccupationalRequirements(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		OccupationalRequirements occupationalRequirements = (OccupationalRequirements)JSONObject.toBean(obj, OccupationalRequirements.class);
		return occupationalRequirements;
	}
	/**
	 * 保存 从业资格信息管理 信息
	 * @param occupationalRequirements
	 */

	public void save(OccupationalRequirements occupationalRequirements) throws Exception{
		if(null==occupationalRequirements.getOcc_type() || ("0").equals(occupationalRequirements.getOcc_type())){
			return;
		}
		Long id=occupationalRequirements.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			occupationalRequirements.setId(id);
		    this.add(occupationalRequirements);
		}
		else{
		    this.update(occupationalRequirements);
		}
	}

	public void completeInfo(SysUser user, UserInfomation info) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", user.getFullname());
		params.put("account", user.getAccount());
		params.put("userId", (user.getUserId()).toString());
		dao.update("completeInfo", params);
	}

	public List<OccupationalRequirements> getAllInfo() {
		return dao.getBySqlKey("getAllInfo");
	}

	public void backInfo(String name) {
		dao.update("backInfo", name);
		
	}
}
