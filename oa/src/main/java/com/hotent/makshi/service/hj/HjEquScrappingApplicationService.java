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
import com.hotent.makshi.model.hj.HjEquScrappingApplication;
import com.hotent.makshi.dao.hj.HjEquScrappingApplicationDao;
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
public class HjEquScrappingApplicationService extends WfBaseService<HjEquScrappingApplication>
{
	@Resource
	private HjEquScrappingApplicationDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjEquScrappingApplicationService()
	{
	}
	
	@Override
	protected IEntityDao<HjEquScrappingApplication,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjEquScrappingApplication> getAll(QueryFilter queryFilter){
		List<HjEquScrappingApplication> hjEquScrappingApplicationList=super.getAll(queryFilter);
		List<HjEquScrappingApplication> hjEquScrappingApplications=new ArrayList<HjEquScrappingApplication>();
		for(HjEquScrappingApplication hjEquScrappingApplication:hjEquScrappingApplicationList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjEquScrappingApplication.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjEquScrappingApplication.setRunId(processRun.getRunId());
			}
			hjEquScrappingApplications.add(hjEquScrappingApplication);
		}
		return hjEquScrappingApplications;
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
			HjEquScrappingApplication hjEquScrappingApplication=getHjEquScrappingApplication(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjEquScrappingApplication.setId(genId);
				this.add(hjEquScrappingApplication);
			}else{
				hjEquScrappingApplication.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjEquScrappingApplication);
			}
			cmd.setBusinessKey(hjEquScrappingApplication.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjEquScrappingApplication对象
	 * @param json
	 * @return
	 */
	public HjEquScrappingApplication getHjEquScrappingApplication(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjEquScrappingApplication hjEquScrappingApplication = (HjEquScrappingApplication)JSONObject.toBean(obj, HjEquScrappingApplication.class);
		return hjEquScrappingApplication;
	}
	/**
	 * 保存 设备报废申请 信息
	 * @param hjEquScrappingApplication
	 */

	@WorkFlow(flowKey="sbbfsq",tableName="hj_Equ_scrapping_application")
	public void save(HjEquScrappingApplication hjEquScrappingApplication) throws Exception{
		Long id=hjEquScrappingApplication.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjEquScrappingApplication.setId(id);
		    this.add(hjEquScrappingApplication);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjEquScrappingApplication.getId().toString(), null , true,  "hj_Equ_scrapping_application");
		}
		else{
		    this.update(hjEquScrappingApplication);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
