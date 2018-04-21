package com.hotent.makshi.service.party;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.party.PartyFileOutApplication;
import com.hotent.makshi.dao.party.PartyFileOutApplicationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PartyFileOutApplicationService extends BaseService<PartyFileOutApplication>
{
	@Resource
	private PartyFileOutApplicationDao dao;
	
	public PartyFileOutApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<PartyFileOutApplication,Long> getEntityDao() 
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
			PartyFileOutApplication partyFileOutApplication=getPartyFileOutApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				partyFileOutApplication.setId(genId);
				this.add(partyFileOutApplication);
			}else{
				partyFileOutApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(partyFileOutApplication);
			}
			cmd.setBusinessKey(partyFileOutApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PartyFileOutApplication对象
	 * @param json
	 * @return
	 */
	public PartyFileOutApplication getPartyFileOutApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PartyFileOutApplication partyFileOutApplication = (PartyFileOutApplication)JSONObject.toBean(obj, PartyFileOutApplication.class);
		return partyFileOutApplication;
	}
	/**
	 * 保存 党员档案转出 信息
	 * @param partyFileOutApplication
	 */

	public void save(PartyFileOutApplication partyFileOutApplication) throws Exception{
		Long id=partyFileOutApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			partyFileOutApplication.setId(id);
		    this.add(partyFileOutApplication);
		}
		else{
		    this.update(partyFileOutApplication);
		}
	}
}
