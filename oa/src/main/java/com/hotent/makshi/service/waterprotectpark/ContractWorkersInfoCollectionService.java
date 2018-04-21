package com.hotent.makshi.service.waterprotectpark;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.waterprotectpark.ContractWorkersInfoCollection;
import com.hotent.makshi.dao.waterprotectpark.ContractWorkersInfoCollectionDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class ContractWorkersInfoCollectionService extends BaseService<ContractWorkersInfoCollection>
{
	@Resource
	private ContractWorkersInfoCollectionDao dao;
	
	public ContractWorkersInfoCollectionService()
	{
	}
	
	@Override
	protected IEntityDao<ContractWorkersInfoCollection,Long> getEntityDao() 
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
			ContractWorkersInfoCollection contractWorkersInfoCollection=getContractWorkersInfoCollection(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				contractWorkersInfoCollection.setId(genId);
				this.add(contractWorkersInfoCollection);
			}else{
				contractWorkersInfoCollection.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(contractWorkersInfoCollection);
			}
			cmd.setBusinessKey(contractWorkersInfoCollection.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取ContractWorkersInfoCollection对象
	 * @param json
	 * @return
	 */
	public ContractWorkersInfoCollection getContractWorkersInfoCollection(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ContractWorkersInfoCollection contractWorkersInfoCollection = (ContractWorkersInfoCollection)JSONObject.toBean(obj, ContractWorkersInfoCollection.class);
		return contractWorkersInfoCollection;
	}
	/**
	 * 保存 劳务人员信息采集(水保示范园流程) 信息
	 * @param contractWorkersInfoCollection
	 */

	public void save(ContractWorkersInfoCollection contractWorkersInfoCollection) throws Exception{
		Long id=contractWorkersInfoCollection.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			contractWorkersInfoCollection.setId(id);
		    this.add(contractWorkersInfoCollection);
		}
		else{
		    this.update(contractWorkersInfoCollection);
		}
	}
}
