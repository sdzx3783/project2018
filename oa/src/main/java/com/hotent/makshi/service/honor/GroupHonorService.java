package com.hotent.makshi.service.honor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.honor.GroupHonor;
import com.hotent.makshi.dao.honor.GroupHonorDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class GroupHonorService extends BaseService<GroupHonor>
{
	@Resource
	private GroupHonorDao dao;
	
	public GroupHonorService()
	{
	}
	
	@Override
	protected IEntityDao<GroupHonor,Long> getEntityDao() 
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
			GroupHonor groupHonor=getGroupHonor(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				groupHonor.setId(genId);
				this.add(groupHonor);
			}else{
				groupHonor.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(groupHonor);
			}
			cmd.setBusinessKey(groupHonor.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取GroupHonor对象
	 * @param json
	 * @return
	 */
	public GroupHonor getGroupHonor(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		GroupHonor groupHonor = (GroupHonor)JSONObject.toBean(obj, GroupHonor.class);
		return groupHonor;
	}
	/**
	 * 保存 集体荣誉 信息
	 * @param groupHonor
	 */

	public void save(GroupHonor groupHonor) throws Exception{
		Long id=groupHonor.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			groupHonor.setId(id);
		    this.add(groupHonor);
		}
		else{
		    this.update(groupHonor);
		}
	}
}
