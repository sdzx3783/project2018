package com.hotent.makshi.service.qualification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.qualification.AttorneyStamp;
import com.hotent.makshi.dao.qualification.AttorneyStampDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class AttorneyStampService extends BaseService<AttorneyStamp>
{
	@Resource
	private AttorneyStampDao dao;
	
	public AttorneyStampService()
	{
	}
	
	@Override
	protected IEntityDao<AttorneyStamp,Long> getEntityDao() 
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
			AttorneyStamp attorneyStamp=getAttorneyStamp(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				attorneyStamp.setId(genId);
				this.add(attorneyStamp);
			}else{
				attorneyStamp.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(attorneyStamp);
			}
			cmd.setBusinessKey(attorneyStamp.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取AttorneyStamp对象
	 * @param json
	 * @return
	 */
	public AttorneyStamp getAttorneyStamp(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		AttorneyStamp attorneyStamp = (AttorneyStamp)JSONObject.toBean(obj, AttorneyStamp.class);
		return attorneyStamp;
	}
	/**
	 * 保存 法定委托书盖章申请 信息
	 * @param attorneyStamp
	 */

	public void save(AttorneyStamp attorneyStamp) throws Exception{
		Long id=attorneyStamp.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			attorneyStamp.setId(id);
		    this.add(attorneyStamp);
		}
		else{
		    this.update(attorneyStamp);
		}
	}
}
