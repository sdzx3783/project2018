package com.hotent.makshi.service.makechapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.makechapter.MakeChapter;
import com.hotent.makshi.dao.makechapter.MakeChapterDao;
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
public class MakeChapterService extends WfBaseService<MakeChapter>
{
	@Resource
	private MakeChapterDao dao;
	
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpmBusLinkService bpmBusLinkService;
	public MakeChapterService()
	{
	}
	
	@Override
	protected IEntityDao<MakeChapter,Long> getEntityDao() 
	{
		return dao;
	}
	
	/**
	 * 重写getAll方法绑定流程runId
	 * @param queryFilter
	 */
	public List<MakeChapter> getAll(QueryFilter queryFilter){
		List<MakeChapter> makeChapterList=super.getAll(queryFilter);
		List<MakeChapter> makeChapters=new ArrayList<MakeChapter>();
		for(MakeChapter makeChapter:makeChapterList){
			ProcessRun processRun=processRunService.getByBusinessKey(makeChapter.getId().toString());
			if(BeanUtils.isNotEmpty(processRun)){
				makeChapter.setRunId(processRun.getRunId());
			}
			makeChapters.add(makeChapter);
		}
		return makeChapters;
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
			MakeChapter makeChapter=getMakeChapter(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				makeChapter.setId(genId);
				this.add(makeChapter);
			}else{
				makeChapter.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(makeChapter);
			}
			cmd.setBusinessKey(makeChapter.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取MakeChapter对象
	 * @param json
	 * @return
	 */
	public MakeChapter getMakeChapter(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		MakeChapter makeChapter = (MakeChapter)JSONObject.toBean(obj, MakeChapter.class);
		return makeChapter;
	}
	/**
	 * 保存 印章表 信息
	 * @param makeChapter
	 */

	@WorkFlow(flowKey="make_chapter",tableName="make_chapter")
	public void save(MakeChapter makeChapter) throws Exception{
		Long id=makeChapter.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			
			makeChapter.setId(id);
		    this.add(makeChapter);
		    //添加到业务关联数据表
		    bpmBusLinkService.add(makeChapter.getId().toString(), null , true,  "make_chapter");
		}
		else{
		    this.update(makeChapter);
		}
		BpmResult result=new BpmResult();
		//添加流程变量
//		result.addVariable("", "");
		result.setBusinessKey(id.toString());
		BpmAspectUtil.setBpmResult(result);
	}
}
