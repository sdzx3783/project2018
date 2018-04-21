package com.hotent.makshi.service.seal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.seal.DepartmentalSeal;
import com.hotent.makshi.dao.seal.DepartmentalSealDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class DepartmentalSealService extends BaseService<DepartmentalSeal>
{
	@Resource
	private DepartmentalSealDao dao;
	
	public DepartmentalSealService()
	{
	}
	
	@Override
	protected IEntityDao<DepartmentalSeal,Long> getEntityDao() 
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
			DepartmentalSeal departmentalSeal=getDepartmentalSeal(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				departmentalSeal.setId(genId);
				this.add(departmentalSeal);
			}else{
				departmentalSeal.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(departmentalSeal);
			}
			cmd.setBusinessKey(departmentalSeal.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取DepartmentalSeal对象
	 * @param json
	 * @return
	 */
	public DepartmentalSeal getDepartmentalSeal(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		DepartmentalSeal departmentalSeal = (DepartmentalSeal)JSONObject.toBean(obj, DepartmentalSeal.class);
		return departmentalSeal;
	}
	/**
	 * 保存 部门公章使用流程 信息
	 * @param departmentalSeal
	 */

	public void save(DepartmentalSeal departmentalSeal) throws Exception{
		Long id=departmentalSeal.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			departmentalSeal.setId(id);
		    this.add(departmentalSeal);
		}
		else{
		    this.update(departmentalSeal);
		}
	}
}
