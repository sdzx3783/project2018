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
import com.hotent.makshi.model.hj.HjGoodsPurchaseds;
import com.hotent.makshi.model.river.HdProgrammeApproval;
import com.hotent.makshi.dao.river.HdProgrammeApprovalDao;
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
public class HdProgrammeApprovalService extends WfBaseService<HdProgrammeApproval>
{
	@Resource
	private HdProgrammeApprovalDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	
	public void cleanInfo() {
		dao.update("cleanInfo", null);
	}
	@Override
	protected IEntityDao<HdProgrammeApproval,Long> getEntityDao() 
	{
		return dao;
	}
	
	public List<HdProgrammeApproval> getSelect(QueryFilter queryFilter){
		return dao.getSelect(queryFilter);
	}
	
	public HdProgrammeApprovalService()
	{
	}
	

	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HdProgrammeApproval> getAll(QueryFilter queryFilter){
		List<HdProgrammeApproval> hdProgrammeApprovalList=super.getAll(queryFilter);
		List<HdProgrammeApproval> hdProgrammeApprovals=new ArrayList<HdProgrammeApproval>();
		for(HdProgrammeApproval hdProgrammeApproval:hdProgrammeApprovalList){
			ProcessRun processRun=processRunService.getByBusinessKey(hdProgrammeApproval.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hdProgrammeApproval.setRunId(processRun.getRunId());
			}
			hdProgrammeApprovals.add(hdProgrammeApproval);
		}
		return hdProgrammeApprovals;
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
			HdProgrammeApproval hdProgrammeApproval=getHdProgrammeApproval(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hdProgrammeApproval.setId(genId);
				this.add(hdProgrammeApproval);
			}else{
				hdProgrammeApproval.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hdProgrammeApproval);
			}
			cmd.setBusinessKey(hdProgrammeApproval.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HdProgrammeApproval对象
	 * @param json
	 * @return
	 */
	public HdProgrammeApproval getHdProgrammeApproval(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HdProgrammeApproval hdProgrammeApproval = (HdProgrammeApproval)JSONObject.toBean(obj, HdProgrammeApproval.class);
		return hdProgrammeApproval;
	}
	/**
	 * 保存 方案审批表 信息
	 * @param hdProgrammeApproval
	 */

	@WorkFlow(flowKey="fasplc",tableName="hd_programme_approval")
	public void save(HdProgrammeApproval hdProgrammeApproval) throws Exception{
		Long id=hdProgrammeApproval.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hdProgrammeApproval.setId(id);
		    this.add(hdProgrammeApproval);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hdProgrammeApproval.getId().toString(), null , true,  "hd_programme_approval");
		}
		else{
		    this.update(hdProgrammeApproval);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
