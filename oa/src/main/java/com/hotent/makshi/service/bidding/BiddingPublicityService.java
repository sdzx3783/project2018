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
import com.hotent.makshi.model.bidding.BiddingPublicity;
import com.hotent.makshi.dao.bidding.BiddingPublicityDao;
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
public class BiddingPublicityService extends WfBaseService<BiddingPublicity>
{
	@Resource
	private BiddingPublicityDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public BiddingPublicityService()
	{
	}
	
	@Override
	protected IEntityDao<BiddingPublicity,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<BiddingPublicity> getAll(QueryFilter queryFilter){
		List<BiddingPublicity> biddingPublicityList=super.getAll(queryFilter);
		List<BiddingPublicity> biddingPublicitys=new ArrayList<BiddingPublicity>();
		for(BiddingPublicity biddingPublicity:biddingPublicityList){
			ProcessRun processRun=processRunService.getByBusinessKey(biddingPublicity.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				biddingPublicity.setRunId(processRun.getRunId());
			}
			biddingPublicitys.add(biddingPublicity);
		}
		return biddingPublicitys;
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
			BiddingPublicity biddingPublicity=getBiddingPublicity(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				biddingPublicity.setId(genId);
				this.add(biddingPublicity);
			}else{
				biddingPublicity.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(biddingPublicity);
			}
			cmd.setBusinessKey(biddingPublicity.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取BiddingPublicity对象
	 * @param json
	 * @return
	 */
	public BiddingPublicity getBiddingPublicity(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		BiddingPublicity biddingPublicity = (BiddingPublicity)JSONObject.toBean(obj, BiddingPublicity.class);
		return biddingPublicity;
	}
	/**
	 * 保存 补充通知/标底公示 信息
	 * @param biddingPublicity
	 */

	@WorkFlow(flowKey="bidding_publicity",tableName="bidding_publicity")
	public void save(BiddingPublicity biddingPublicity) throws Exception{
		Long id=biddingPublicity.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			biddingPublicity.setId(id);
		    this.add(biddingPublicity);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(biddingPublicity.getId().toString(), null , true,  "bidding_publicity");
		}
		else{
		    this.update(biddingPublicity);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
