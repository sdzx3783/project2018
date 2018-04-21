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
import com.hotent.makshi.model.bidding.BiddingDocumentExamine;
import com.hotent.makshi.dao.bidding.BiddingDocumentExamineDao;
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
public class BiddingDocumentExamineService extends WfBaseService<BiddingDocumentExamine>
{
	@Resource
	private BiddingDocumentExamineDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public BiddingDocumentExamineService()
	{
	}
	
	@Override
	protected IEntityDao<BiddingDocumentExamine,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<BiddingDocumentExamine> getAll(QueryFilter queryFilter){
		List<BiddingDocumentExamine> biddingDocumentExamineList=super.getAll(queryFilter);
		List<BiddingDocumentExamine> biddingDocumentExamines=new ArrayList<BiddingDocumentExamine>();
		for(BiddingDocumentExamine biddingDocumentExamine:biddingDocumentExamineList){
			ProcessRun processRun=processRunService.getByBusinessKey(biddingDocumentExamine.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				biddingDocumentExamine.setRunId(processRun.getRunId());
			}
			biddingDocumentExamines.add(biddingDocumentExamine);
		}
		return biddingDocumentExamines;
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
			BiddingDocumentExamine biddingDocumentExamine=getBiddingDocumentExamine(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				biddingDocumentExamine.setId(genId);
				this.add(biddingDocumentExamine);
			}else{
				biddingDocumentExamine.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(biddingDocumentExamine);
			}
			cmd.setBusinessKey(biddingDocumentExamine.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取BiddingDocumentExamine对象
	 * @param json
	 * @return
	 */
	public BiddingDocumentExamine getBiddingDocumentExamine(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		BiddingDocumentExamine biddingDocumentExamine = (BiddingDocumentExamine)JSONObject.toBean(obj, BiddingDocumentExamine.class);
		return biddingDocumentExamine;
	}
	/**
	 * 保存 招标文件审批 信息
	 * @param biddingDocumentExamine
	 */

	@WorkFlow(flowKey="bidding_document",tableName="bidding_document_examine")
	public void save(BiddingDocumentExamine biddingDocumentExamine) throws Exception{
		Long id=biddingDocumentExamine.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			biddingDocumentExamine.setId(id);
		    this.add(biddingDocumentExamine);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(biddingDocumentExamine.getId().toString(), null , true,  "bidding_document_examine");
		}
		else{
		    this.update(biddingDocumentExamine);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
