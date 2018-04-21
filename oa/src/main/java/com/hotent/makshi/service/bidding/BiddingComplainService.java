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
import com.hotent.makshi.model.bidding.BiddingComplain;
import com.hotent.makshi.dao.bidding.BiddingComplainDao;
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
public class BiddingComplainService extends WfBaseService<BiddingComplain>
{
	@Resource
	private BiddingComplainDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public BiddingComplainService()
	{
	}
	
	@Override
	protected IEntityDao<BiddingComplain,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<BiddingComplain> getAll(QueryFilter queryFilter){
		List<BiddingComplain> biddingComplainList=super.getAll(queryFilter);
		List<BiddingComplain> biddingComplains=new ArrayList<BiddingComplain>();
		for(BiddingComplain biddingComplain:biddingComplainList){
			ProcessRun processRun=processRunService.getByBusinessKey(biddingComplain.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				biddingComplain.setRunId(processRun.getRunId());
			}
			biddingComplains.add(biddingComplain);
		}
		return biddingComplains;
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
			BiddingComplain biddingComplain=getBiddingComplain(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				biddingComplain.setId(genId);
				this.add(biddingComplain);
			}else{
				biddingComplain.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(biddingComplain);
			}
			cmd.setBusinessKey(biddingComplain.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取BiddingComplain对象
	 * @param json
	 * @return
	 */
	public BiddingComplain getBiddingComplain(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		BiddingComplain biddingComplain = (BiddingComplain)JSONObject.toBean(obj, BiddingComplain.class);
		return biddingComplain;
	}
	/**
	 * 保存 投诉处理审批 信息
	 * @param biddingComplain
	 */

	@WorkFlow(flowKey="bidding_complain",tableName="bidding_complain")
	public void save(BiddingComplain biddingComplain) throws Exception{
		Long id=biddingComplain.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			biddingComplain.setId(id);
		    this.add(biddingComplain);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(biddingComplain.getId().toString(), null , true,  "bidding_complain");
		}
		else{
		    this.update(biddingComplain);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
