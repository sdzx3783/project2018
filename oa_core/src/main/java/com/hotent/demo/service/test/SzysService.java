package com.hotent.demo.service.test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.demo.model.test.Szys;
import com.hotent.demo.dao.test.SzysDao;
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
public class SzysService extends WfBaseService<Szys>
{
	@Resource
	private SzysDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public SzysService()
	{
	}
	
	@Override
	protected IEntityDao<Szys,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<Szys> getAll(QueryFilter queryFilter){
		List<Szys> szysList=super.getAll(queryFilter);
		List<Szys> szyss=new ArrayList<Szys>();
		for(Szys szys:szysList){
			ProcessRun processRun=processRunService.getByBusinessKey(szys.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				szys.setRunId(processRun.getRunId());
			}
			szyss.add(szys);
		}
		return szyss;
	}
	

		
	
	/**
	 * 流程处理器方法 用于处理业务数据
	 * @param cmd
	 * @throws Exception
	 */
	public void processHandler(ProcessCmd cmd) throws Exception{
		Map data=cmd.getFormDataMap();
		if(BeanUtils.isNotEmpty(data)){
			String json=data.get("json").toString();
			Szys szys=getSzys(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				szys.setId(genId);
				this.add(szys);
			}else{
				szys.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(szys);
			}
			cmd.setBusinessKey(szys.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取Szys对象
	 * @param json
	 * @return
	 */
	public Szys getSzys(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		Szys szys = (Szys)JSONObject.toBean(obj, Szys.class);
		return szys;
	}
	/**
	 * 保存 数字运算 信息
	 * @param szys
	 */

	@WorkFlow(flowKey="sjdlc",tableName="szys")
	public void save(Szys szys) throws Exception{
		Long id=szys.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			szys.setId(id);
		    this.add(szys);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(szys.getId().toString(), null , true,  "szys");
		}
		else{
		    this.update(szys);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
