package com.hotent.makshi.service.operation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.GenericService;
import com.hotent.core.util.BeanUtils;
import com.hotent.makshi.model.operation.TestReport;
import com.hotent.makshi.dao.operation.TestReportDao;
import com.hotent.core.util.UniqueIdUtil;

import net.sf.json.util.JSONUtils;
import net.sf.ezmorph.object.DateMorpher;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.util.StringUtil;
import net.sf.json.JSONObject;


import com.hotent.core.service.BaseService;


@Service
public class TestReportService extends BaseService<TestReport>
{
	@Resource
	private TestReportDao dao;
	
	public TestReportService()
	{
	}
	
	@Override
	protected IEntityDao<TestReport,Long> getEntityDao() 
	{
		return dao;
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
			TestReport testReport=getTestReport(json);
			if(StringUtil.isEmpty(cmd.getBusinessKey())){
				Long genId=UniqueIdUtil.genId();
				testReport.setId(genId);
				this.add(testReport);
			}else{
				testReport.setId(Long.parseLong(cmd.getBusinessKey()));
				this.update(testReport);
			}
			cmd.setBusinessKey(testReport.getId().toString());
		}
	}
	
	/**
	 * 根据json字符串获取TestReport对象
	 * @param json
	 * @return
	 */
	public TestReport getTestReport(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		TestReport testReport = (TestReport)JSONObject.toBean(obj, TestReport.class);
		return testReport;
	}
	/**
	 * 保存 运维部检测报告表 信息
	 * @param testReport
	 */

	public void save(TestReport testReport) throws Exception{
		Long id=testReport.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			testReport.setId(id);
		    this.add(testReport);
		}
		else{
		    this.update(testReport);
		}
	}
}
