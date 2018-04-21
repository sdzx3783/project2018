package com.hotent.makshi.service.title;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.title.Practiceregist;
import com.hotent.makshi.dao.title.PracticeregistDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class PracticeregistService extends BaseService<Practiceregist>
{
	@Resource
	private PracticeregistDao dao;
	
	public PracticeregistService()
	{
	}
	
	@Override
	protected IEntityDao<Practiceregist,Long> getEntityDao() 
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
			Practiceregist practiceregist=getPracticeregist(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				practiceregist.setId(genId);
				this.add(practiceregist);
			}else{
				practiceregist.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(practiceregist);
			}
			cmd.setBusinessKey(practiceregist.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Practiceregist对象
	 * @param json
	 * @return
	 */
	public Practiceregist getPracticeregist(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Practiceregist practiceregist = (Practiceregist)JSONObject.toBean(obj, Practiceregist.class);
		return practiceregist;
	}
	/**
	 * 保存 个人执业资格初始注册及转入 信息
	 * @param practiceregist
	 */

	public void save(Practiceregist practiceregist) throws Exception{
		Long id=practiceregist.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			practiceregist.setId(id);
		    this.add(practiceregist);
		}
		else{
		    this.update(practiceregist);
		}
	}
}
