package com.hotent.makshi.service.communicationExpense;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.communicationExpense.CommunicationExpense;
import com.hotent.makshi.dao.communicationExpense.CommunicationExpenseDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class CommunicationExpenseService extends BaseService<CommunicationExpense>
{
	@Resource
	private CommunicationExpenseDao dao;
	
	public CommunicationExpenseService()
	{
	}
	
	@Override
	protected IEntityDao<CommunicationExpense,Long> getEntityDao() 
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
			CommunicationExpense communicationExpense=getCommunicationExpense(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				communicationExpense.setId(genId);
				this.add(communicationExpense);
			}else{
				communicationExpense.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(communicationExpense);
			}
			cmd.setBusinessKey(communicationExpense.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取CommunicationExpense对象
	 * @param json
	 * @return
	 */
	public CommunicationExpense getCommunicationExpense(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		CommunicationExpense communicationExpense = (CommunicationExpense)JSONObject.toBean(obj, CommunicationExpense.class);
		return communicationExpense;
	}
	/**
	 * 保存 通讯费消费记录表 信息
	 * @param communicationExpense
	 */

	public void save(CommunicationExpense communicationExpense) throws Exception{
		Long id=communicationExpense.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			communicationExpense.setId(id);
		    this.add(communicationExpense);
		}
		else{
		    this.update(communicationExpense);
		}
	}
}
