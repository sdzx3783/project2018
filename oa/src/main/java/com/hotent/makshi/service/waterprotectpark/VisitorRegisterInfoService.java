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
import com.hotent.makshi.model.waterprotectpark.VisitorRegisterInfo;
import com.hotent.makshi.dao.waterprotectpark.VisitorRegisterInfoDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class VisitorRegisterInfoService extends BaseService<VisitorRegisterInfo>
{
	@Resource
	private VisitorRegisterInfoDao dao;
	
	public VisitorRegisterInfoService()
	{
	}
	
	@Override
	protected IEntityDao<VisitorRegisterInfo,Long> getEntityDao() 
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
			VisitorRegisterInfo visitorRegisterInfo=getVisitorRegisterInfo(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				visitorRegisterInfo.setId(genId);
				this.add(visitorRegisterInfo);
			}else{
				visitorRegisterInfo.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(visitorRegisterInfo);
			}
			cmd.setBusinessKey(visitorRegisterInfo.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取VisitorRegisterInfo对象
	 * @param json
	 * @return
	 */
	public VisitorRegisterInfo getVisitorRegisterInfo(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		VisitorRegisterInfo visitorRegisterInfo = (VisitorRegisterInfo)JSONObject.toBean(obj, VisitorRegisterInfo.class);
		return visitorRegisterInfo;
	}
	/**
	 * 保存 参观登记信息-水保园 信息
	 * @param visitorRegisterInfo
	 */

	public void save(VisitorRegisterInfo visitorRegisterInfo) throws Exception{
		Long id=visitorRegisterInfo.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			visitorRegisterInfo.setId(id);
		    this.add(visitorRegisterInfo);
		}
		else{
		    this.update(visitorRegisterInfo);
		}
	}
}
