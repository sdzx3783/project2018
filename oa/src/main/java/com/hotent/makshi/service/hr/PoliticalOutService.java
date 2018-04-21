package com.hotent.makshi.service.hr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hr.PoliticalOut;
import com.hotent.makshi.dao.hr.PoliticalOutDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PoliticalOutService extends BaseService<PoliticalOut>
{
	@Resource
	private PoliticalOutDao dao;
	
	public PoliticalOutService()
	{
	}
	
	@Override
	protected IEntityDao<PoliticalOut,Long> getEntityDao() 
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
			PoliticalOut politicalOut=getPoliticalOut(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				politicalOut.setId(genId);
				this.add(politicalOut);
			}else{
				politicalOut.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(politicalOut);
			}
			cmd.setBusinessKey(politicalOut.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PoliticalOut对象
	 * @param json
	 * @return
	 */
	public PoliticalOut getPoliticalOut(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PoliticalOut politicalOut = (PoliticalOut)JSONObject.toBean(obj, PoliticalOut.class);
		return politicalOut;
	}
	/**
	 * 保存 党员档案转出 信息
	 * @param politicalOut
	 */

	public void save(PoliticalOut politicalOut) throws Exception{
		Long id=politicalOut.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			politicalOut.setId(id);
		    this.add(politicalOut);
		}
		else{
		    this.update(politicalOut);
		}
	}
}
