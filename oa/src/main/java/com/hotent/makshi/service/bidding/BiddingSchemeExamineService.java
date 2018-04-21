package com.hotent.makshi.service.bidding;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.bidding.BiddingSchemeExamine;
import com.hotent.makshi.dao.bidding.BiddingSchemeExamineDao;
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
public class BiddingSchemeExamineService extends WfBaseService<BiddingSchemeExamine>
{
	@Resource
	private BiddingSchemeExamineDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public BiddingSchemeExamineService()
	{
	}
	
	@Override
	protected IEntityDao<BiddingSchemeExamine,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<BiddingSchemeExamine> getAll(QueryFilter queryFilter){
		List<BiddingSchemeExamine> biddingSchemeExamineList=super.getAll(queryFilter);
		List<BiddingSchemeExamine> biddingSchemeExamines=new ArrayList<BiddingSchemeExamine>();
		for(BiddingSchemeExamine biddingSchemeExamine:biddingSchemeExamineList){
			ProcessRun processRun=processRunService.getByBusinessKey(biddingSchemeExamine.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				biddingSchemeExamine.setRunId(processRun.getRunId());
			}
			biddingSchemeExamines.add(biddingSchemeExamine);
		}
		return biddingSchemeExamines;
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
			BiddingSchemeExamine biddingSchemeExamine=getBiddingSchemeExamine(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				biddingSchemeExamine.setId(genId);
				this.add(biddingSchemeExamine);
			}else{
				biddingSchemeExamine.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(biddingSchemeExamine);
			}
			cmd.setBusinessKey(biddingSchemeExamine.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取BiddingSchemeExamine对象
	 * @param json
	 * @return
	 */
	public BiddingSchemeExamine getBiddingSchemeExamine(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		BiddingSchemeExamine biddingSchemeExamine = (BiddingSchemeExamine)JSONObject.toBean(obj, BiddingSchemeExamine.class);
		return biddingSchemeExamine;
	}
	/**
	 * 保存 招标方案审批 信息
	 * @param biddingSchemeExamine
	 */

	@WorkFlow(flowKey="biddings_cheme",tableName="bidding_scheme_examine")
	public void save(BiddingSchemeExamine biddingSchemeExamine) throws Exception{
		Long id=biddingSchemeExamine.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			biddingSchemeExamine.setId(id);
		    this.add(biddingSchemeExamine);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(biddingSchemeExamine.getId().toString(), null , true,  "bidding_scheme_examine");
		}
		else{
		    this.update(biddingSchemeExamine);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
