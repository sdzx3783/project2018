package com.hotent.makshi.service.river;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.river.HdDocumentReview;
import com.hotent.makshi.dao.river.HdDocumentReviewDao;
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
public class HdDocumentReviewService extends WfBaseService<HdDocumentReview>
{
	@Resource
	private HdDocumentReviewDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HdDocumentReviewService()
	{
	}
	
	@Override
	protected IEntityDao<HdDocumentReview,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HdDocumentReview> getAll(QueryFilter queryFilter){
		List<HdDocumentReview> hdDocumentReviewList=super.getAll(queryFilter);
		List<HdDocumentReview> hdDocumentReviews=new ArrayList<HdDocumentReview>();
		for(HdDocumentReview hdDocumentReview:hdDocumentReviewList){
			ProcessRun processRun=processRunService.getByBusinessKey(hdDocumentReview.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hdDocumentReview.setRunId(processRun.getRunId());
			}
			hdDocumentReviews.add(hdDocumentReview);
		}
		return hdDocumentReviews;
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
			HdDocumentReview hdDocumentReview=getHdDocumentReview(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hdDocumentReview.setId(genId);
				this.add(hdDocumentReview);
			}else{
				hdDocumentReview.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hdDocumentReview);
			}
			cmd.setBusinessKey(hdDocumentReview.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HdDocumentReview对象
	 * @param json
	 * @return
	 */
	public HdDocumentReview getHdDocumentReview(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HdDocumentReview hdDocumentReview = (HdDocumentReview)JSONObject.toBean(obj, HdDocumentReview.class);
		return hdDocumentReview;
	}
	/**
	 * 保存 文件 信息
	 * @param hdDocumentReview
	 */

	@WorkFlow(flowKey="wjsclc",tableName="hd_document_review")
	public void save(HdDocumentReview hdDocumentReview) throws Exception{
		Long id=hdDocumentReview.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hdDocumentReview.setId(id);
		    this.add(hdDocumentReview);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hdDocumentReview.getId().toString(), null , true,  "hd_document_review");
		}
		else{
		    this.update(hdDocumentReview);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
