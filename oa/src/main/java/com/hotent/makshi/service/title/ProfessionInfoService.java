package com.hotent.makshi.service.title;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.title.ProfessionInfoDao;
import com.hotent.makshi.model.title.ProfessionInfo;


@Service
public class ProfessionInfoService extends BaseService<ProfessionInfo>
{
	@Resource
	private ProfessionInfoDao dao;
	
	public ProfessionInfoService()
	{
	}
	
	@Override
	protected IEntityDao<ProfessionInfo,Long> getEntityDao() 
	{
		return dao;
	}
	
	
	
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
//	public void processHandler(ProcessCmd cmd)throws Exception{
//		Map data=cmd.getFormDataMap();
//		if(BeanUtils.isNotEmpty(data)){
//			String json=data.get("json").toString();
//			ProfessionInfo ProfessionInfo=getPersonalSeal(json);
//			if(StringUtil.isEmpty(cmd.getBusinessKey())){
//				Long genId=UniqueIdUtil.genId();
//				ProfessionInfo.setId(genId);
//				this.add(ProfessionInfo);
//			}else{
//				ProfessionInfo.setId(Long.parseLong(cmd.getBusinessKey()));
//				this.update(ProfessionInfo);
//			}
//			cmd.setBusinessKey(ProfessionInfo.getId().toString());
//		}
//	}
	
	/**
	 * 根据json字符串获取PersonalSeal对象
	 * @param json
	 * @return
	 */
	public ProfessionInfo getProfessionInfo(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		ProfessionInfo ProfessionInfo = (ProfessionInfo)JSONObject.toBean(obj, ProfessionInfo.class);
		return ProfessionInfo;
	}
	/**
	 * 保存 个人执业印章 信息
	 * @param ProfessionInfo
	 */

//	public void save(ProfessionInfo ProfessionInfo) throws Exception{
//		Long id=ProfessionInfo.getId();
//		if(id==null || id==0){
//			id=UniqueIdUtil.genId();
//			ProfessionInfo.setId(id);
//		    this.add(ProfessionInfo);
//		}
//		else{
//		    this.update(ProfessionInfo);
//		}
//	}
}
