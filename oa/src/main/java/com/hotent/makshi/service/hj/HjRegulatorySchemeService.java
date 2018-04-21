package com.hotent.makshi.service.hj;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.hj.HjRegulatoryScheme;
import com.hotent.makshi.dao.hj.HjRegulatorySchemeDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.bpm.ProcessRun;
import com.hotent.platform.service.bpm.ProcessRunService;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.annotion.WorkFlow;
import com.hotent.platform.service.bpm.util.BpmAspectUtil;
import com.hotent.core.bpm.BpmResult;
import com.hotent.platform.service.bpm.BpmBusLinkService;


@Service
public class HjRegulatorySchemeService extends WfBaseService<HjRegulatoryScheme>
{
	@Resource
	private HjRegulatorySchemeDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjRegulatorySchemeService()
	{
	}
	
	@Override
	protected IEntityDao<HjRegulatoryScheme,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjRegulatoryScheme> getAll(QueryFilter queryFilter){
		List<HjRegulatoryScheme> hjRegulatorySchemeList=super.getAll(queryFilter);
		List<HjRegulatoryScheme> hjRegulatorySchemes=new ArrayList<HjRegulatoryScheme>();
		for(HjRegulatoryScheme hjRegulatoryScheme:hjRegulatorySchemeList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjRegulatoryScheme.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjRegulatoryScheme.setRunId(processRun.getRunId());
			}
			hjRegulatorySchemes.add(hjRegulatoryScheme);
		}
		return hjRegulatorySchemes;
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
			HjRegulatoryScheme hjRegulatoryScheme=getHjRegulatoryScheme(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjRegulatoryScheme.setId(genId);
				this.add(hjRegulatoryScheme);
			}else{
				hjRegulatoryScheme.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjRegulatoryScheme);
			}
			cmd.setBusinessKey(hjRegulatoryScheme.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjRegulatoryScheme对象
	 * @param json
	 * @return
	 */
	public HjRegulatoryScheme getHjRegulatoryScheme(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjRegulatoryScheme hjRegulatoryScheme = (HjRegulatoryScheme)JSONObject.toBean(obj, HjRegulatoryScheme.class);
		return hjRegulatoryScheme;
	}
	/**
	 * 保存 jgfa 信息
	 * @param hjRegulatoryScheme
	 */

	@WorkFlow(flowKey="jgfabs",tableName="hj_Regulatory_scheme")
	public void save(HjRegulatoryScheme hjRegulatoryScheme) throws Exception{
		Long id=hjRegulatoryScheme.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjRegulatoryScheme.setId(id);
		    this.add(hjRegulatoryScheme);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjRegulatoryScheme.getId().toString(), null , true,  "hj_Regulatory_scheme");
		}
		else{
		    this.update(hjRegulatoryScheme);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
