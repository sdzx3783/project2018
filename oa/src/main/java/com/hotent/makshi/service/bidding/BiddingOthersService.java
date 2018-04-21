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
import com.hotent.makshi.model.bidding.BiddingOthers;
import com.hotent.makshi.dao.bidding.BiddingOthersDao;
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
public class BiddingOthersService extends WfBaseService<BiddingOthers>
{
	@Resource
	private BiddingOthersDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public BiddingOthersService()
	{
	}
	
	@Override
	protected IEntityDao<BiddingOthers,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<BiddingOthers> getAll(QueryFilter queryFilter){
		List<BiddingOthers> biddingOthersList=super.getAll(queryFilter);
		List<BiddingOthers> biddingOtherss=new ArrayList<BiddingOthers>();
		for(BiddingOthers biddingOthers:biddingOthersList){
			ProcessRun processRun=processRunService.getByBusinessKey(biddingOthers.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				biddingOthers.setRunId(processRun.getRunId());
			}
			biddingOtherss.add(biddingOthers);
		}
		return biddingOtherss;
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
			BiddingOthers biddingOthers=getBiddingOthers(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				biddingOthers.setId(genId);
				this.add(biddingOthers);
			}else{
				biddingOthers.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(biddingOthers);
			}
			cmd.setBusinessKey(biddingOthers.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取BiddingOthers对象
	 * @param json
	 * @return
	 */
	public BiddingOthers getBiddingOthers(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		BiddingOthers biddingOthers = (BiddingOthers)JSONObject.toBean(obj, BiddingOthers.class);
		return biddingOthers;
	}
	/**
	 * 保存 其他事项审批 信息
	 * @param biddingOthers
	 */

	@WorkFlow(flowKey="bidding_others",tableName="bidding_others")
	public void save(BiddingOthers biddingOthers) throws Exception{
		Long id=biddingOthers.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			biddingOthers.setId(id);
		    this.add(biddingOthers);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(biddingOthers.getId().toString(), null , true,  "bidding_others");
		}
		else{
		    this.update(biddingOthers);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
