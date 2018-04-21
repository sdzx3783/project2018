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
import com.hotent.makshi.model.party.PartyFileInApplication;
import com.hotent.makshi.dao.party.PartyFileInApplicationDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PartyFileInApplicationService extends BaseService<PartyFileInApplication>
{
	@Resource
	private PartyFileInApplicationDao dao;
	
	public PartyFileInApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<PartyFileInApplication,Long> getEntityDao() 
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
			PartyFileInApplication partyFileInApplication=getPartyFileInApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				partyFileInApplication.setId(genId);
				this.add(partyFileInApplication);
			}else{
				partyFileInApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(partyFileInApplication);
			}
			cmd.setBusinessKey(partyFileInApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PartyFileInApplication对象
	 * @param json
	 * @return
	 */
	public PartyFileInApplication getPartyFileInApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PartyFileInApplication partyFileInApplication = (PartyFileInApplication)JSONObject.toBean(obj, PartyFileInApplication.class);
		return partyFileInApplication;
	}
	/**
	 * 保存 党员档案转入申请 信息
	 * @param partyFileInApplication
	 */

	public void save(PartyFileInApplication partyFileInApplication) throws Exception{
		Long id=partyFileInApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			partyFileInApplication.setId(id);
		    this.add(partyFileInApplication);
		}
		else{
		    this.update(partyFileInApplication);
		}
	}
}
