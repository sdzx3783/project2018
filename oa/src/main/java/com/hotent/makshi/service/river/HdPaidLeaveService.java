package com.hotent.makshi.service.river;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.operation.OverAndAdjust;
import com.hotent.makshi.model.river.HdPaidLeave;
import com.hotent.makshi.dao.river.HdPaidLeaveDao;
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
public class HdPaidLeaveService extends BaseService<HdPaidLeave>
{
	@Resource
	private HdPaidLeaveDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	
	protected IEntityDao<HdPaidLeave,Long> getEntityDao() 
	{
		return dao;
	}
	public List<HdPaidLeave> getOverListByUsrId(Long id) {
		return dao.getBySqlKey("getOverListByUsrId",id);
	}
	public List<HdPaidLeave> getAdjustListByUsrId(Long id) {
		return dao.getBySqlKey("getAdjustListByUsrId",id);
	}
	public void cleanInfo() {
		dao.update("cleanInfo", null);
	}
	
	
	
	
	
	
	
	
	public HdPaidLeaveService()
	{
	}
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HdPaidLeave> getAll(QueryFilter queryFilter){
		List<HdPaidLeave> hdPaidLeaveList=super.getAll(queryFilter);
		List<HdPaidLeave> hdPaidLeaves=new ArrayList<HdPaidLeave>();
		for(HdPaidLeave hdPaidLeave:hdPaidLeaveList){
			ProcessRun processRun=processRunService.getByBusinessKey(hdPaidLeave.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hdPaidLeave.setRunId(processRun.getRunId());
			}
			hdPaidLeaves.add(hdPaidLeave);
		}
		return hdPaidLeaves;
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
			HdPaidLeave hdPaidLeave=getHdPaidLeave(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hdPaidLeave.setId(genId);
				this.add(hdPaidLeave);
			}else{
				hdPaidLeave.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hdPaidLeave);
			}
			cmd.setBusinessKey(hdPaidLeave.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HdPaidLeave对象
	 * @param json
	 * @return
	 */
	public HdPaidLeave getHdPaidLeave(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HdPaidLeave hdPaidLeave = (HdPaidLeave)JSONObject.toBean(obj, HdPaidLeave.class);
		return hdPaidLeave;
	}
	/**
	 * 保存 调休表 信息
	 * @param hdPaidLeave
	 */

	@WorkFlow(flowKey="dxsq",tableName="hd_paid_leave")
	public void save(HdPaidLeave hdPaidLeave) throws Exception{
		Long id=hdPaidLeave.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hdPaidLeave.setId(id);
		    this.add(hdPaidLeave);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hdPaidLeave.getId().toString(), null , true,  "hd_paid_leave");
		}
		else{
		    this.update(hdPaidLeave);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
