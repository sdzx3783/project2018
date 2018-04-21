package com.hotent.makshi.service.contract;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.contract.Bankandaccount;
import com.hotent.makshi.dao.contract.BankandaccountDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class BankandaccountService extends BaseService<Bankandaccount>
{
	@Resource
	private BankandaccountDao dao;
	
	public BankandaccountService()
	{
	}
	
	@Override
	protected IEntityDao<Bankandaccount,Long> getEntityDao() 
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
			Bankandaccount bankandaccount=getBankandaccount(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				bankandaccount.setId(genId);
				this.add(bankandaccount);
			}else{
				bankandaccount.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(bankandaccount);
			}
			cmd.setBusinessKey(bankandaccount.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Bankandaccount对象
	 * @param json
	 * @return
	 */
	public Bankandaccount getBankandaccount(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Bankandaccount bankandaccount = (Bankandaccount)JSONObject.toBean(obj, Bankandaccount.class);
		return bankandaccount;
	}
	/**
	 * 保存 银行账户表 信息
	 * @param bankandaccount
	 */

	public void save(Bankandaccount bankandaccount) throws Exception{
		Long id=bankandaccount.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			bankandaccount.setId(id);
		    this.add(bankandaccount);
		}
		else{
		    this.update(bankandaccount);
		}
	}
}
