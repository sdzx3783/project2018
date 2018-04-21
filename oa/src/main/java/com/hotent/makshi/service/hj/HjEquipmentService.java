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
import com.hotent.makshi.model.hj.HjEquipment;
import com.hotent.makshi.dao.hj.HjEquipmentDao;
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
public class HjEquipmentService extends WfBaseService<HjEquipment>
{
	@Resource
	private HjEquipmentDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public HjEquipmentService()
	{
	}
	
	@Override
	protected IEntityDao<HjEquipment,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<HjEquipment> getAll(QueryFilter queryFilter){
		List<HjEquipment> hjEquipmentList=super.getAll(queryFilter);
		List<HjEquipment> hjEquipments=new ArrayList<HjEquipment>();
		for(HjEquipment hjEquipment:hjEquipmentList){
			ProcessRun processRun=processRunService.getByBusinessKey(hjEquipment.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				hjEquipment.setRunId(processRun.getRunId());
			}
			hjEquipments.add(hjEquipment);
		}
		return hjEquipments;
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
			HjEquipment hjEquipment=getHjEquipment(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				hjEquipment.setId(genId);
				this.add(hjEquipment);
			}else{
				hjEquipment.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(hjEquipment);
			}
			cmd.setBusinessKey(hjEquipment.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取HjEquipment对象
	 * @param json
	 * @return
	 */
	public HjEquipment getHjEquipment(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		HjEquipment hjEquipment = (HjEquipment)JSONObject.toBean(obj, HjEquipment.class);
		return hjEquipment;
	}
	/**
	 * 保存 qq 信息
	 * @param hjEquipment
	 */

	@WorkFlow(flowKey="sbwxsq",tableName="hj_equipment")
	public void save(HjEquipment hjEquipment) throws Exception{
		Long id=hjEquipment.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			hjEquipment.setId(id);
		    this.add(hjEquipment);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(hjEquipment.getId().toString(), null , true,  "hj_equipment");
		}
		else{
		    this.update(hjEquipment);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
