package com.hotent.makshi.service.hr;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hr.PoliticalIn;
import com.hotent.makshi.dao.hr.PoliticalInDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PoliticalInService extends BaseService<PoliticalIn>
{
	@Resource
	private PoliticalInDao dao;
	
	public PoliticalInService()
	{
	}
	
	@Override
	protected IEntityDao<PoliticalIn,Long> getEntityDao() 
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
			PoliticalIn politicalIn=getPoliticalIn(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				politicalIn.setId(genId);
				this.add(politicalIn);
			}else{
				politicalIn.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(politicalIn);
			}
			cmd.setBusinessKey(politicalIn.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取PoliticalIn对象
	 * @param json
	 * @return
	 */
	public PoliticalIn getPoliticalIn(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		PoliticalIn politicalIn = (PoliticalIn)JSONObject.toBean(obj, PoliticalIn.class);
		return politicalIn;
	}
	/**
	 * 保存 党员转入 信息
	 * @param politicalIn
	 */

	public void save(PoliticalIn politicalIn) throws Exception{
		Long id=politicalIn.getId();
		if(null!=politicalIn.getOut_date()){
			politicalIn.setIs_out("1");
		}
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			politicalIn.setId(id);
			politicalIn.setIs_in("1");
			politicalIn.setIn_date(new Date());
		    this.add(politicalIn);
		}
		else{
		    this.update(politicalIn);
		}
	}

	public List<PoliticalIn> getByPartyName(String user_name) {
		return dao.getBySqlKey("getByUserName",user_name);
	}
	
	public List<PoliticalIn> getByUserId(String user_id) {
		return dao.getBySqlKey("getByUserid",user_id);
	}
}
