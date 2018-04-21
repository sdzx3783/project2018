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
import com.hotent.makshi.model.river.HdMeetingMinutes;
import com.hotent.makshi.dao.river.HdMeetingMinutesDao;
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
public class HdMeetingMinutesService extends WfBaseService<HdMeetingMinutes>
{
	@Resource
	private HdMeetingMinutesDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HdMeetingMinutesService()
	{
	}
	
	@Override
	protected IEntityDao<HdMeetingMinutes,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HdMeetingMinutes> getAll(QueryFilter queryFilter){
		List<HdMeetingMinutes> hdMeetingMinutesList=super.getAll(queryFilter);
		List<HdMeetingMinutes> hdMeetingMinutess=new ArrayList<HdMeetingMinutes>();
		for(HdMeetingMinutes hdMeetingMinutes:hdMeetingMinutesList){
			ProcessRun processRun=processRunService.getByBusinessKey(hdMeetingMinutes.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hdMeetingMinutes.setRunId(processRun.getRunId());
			}
			hdMeetingMinutess.add(hdMeetingMinutes);
		}
		return hdMeetingMinutess;
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
			HdMeetingMinutes hdMeetingMinutes=getHdMeetingMinutes(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hdMeetingMinutes.setId(genId);
				this.add(hdMeetingMinutes);
			}else{
				hdMeetingMinutes.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hdMeetingMinutes);
			}
			cmd.setBusinessKey(hdMeetingMinutes.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HdMeetingMinutes对象
	 * @param json
	 * @return
	 */
	public HdMeetingMinutes getHdMeetingMinutes(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HdMeetingMinutes hdMeetingMinutes = (HdMeetingMinutes)JSONObject.toBean(obj, HdMeetingMinutes.class);
		return hdMeetingMinutes;
	}
	/**
	 * 保存 会议纪要 信息
	 * @param hdMeetingMinutes
	 */

	@WorkFlow(flowKey="hyjysplc",tableName="hd_meeting_minutes")
	public void save(HdMeetingMinutes hdMeetingMinutes) throws Exception{
		Long id=hdMeetingMinutes.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hdMeetingMinutes.setId(id);
		    this.add(hdMeetingMinutes);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hdMeetingMinutes.getId().toString(), null , true,  "hd_meeting_minutes");
		}
		else{
		    this.update(hdMeetingMinutes);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
