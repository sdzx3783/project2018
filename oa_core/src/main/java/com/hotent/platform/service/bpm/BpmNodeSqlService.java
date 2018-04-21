package com.hotent.platform.service.bpm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.hotent.core.api.bpm.model.IProcessRun;
import com.hotent.core.bpm.model.ProcessCmd;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.engine.FreemarkEngine;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.FileUtil;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.model.bpm.BpmNodeSql;
import com.hotent.platform.model.bpm.NodeMsgTemplate;
import com.hotent.platform.model.form.BpmFormData;
import com.hotent.platform.model.form.SubTable;
import com.hotent.platform.service.bpm.thread.TaskThreadService;
import com.hotent.platform.dao.bpm.BpmNodeSqlDao;
import com.hotent.core.service.BaseService;

import freemarker.template.TemplateException;

/**
 * <pre>
 * 对象功能:bpm_node_sql Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:liyj
 * 创建时间:2015-03-05 09:59:29
 * </pre>
 */
@Service
public class BpmNodeSqlService extends BaseService<BpmNodeSql> {
	@Resource
	private BpmNodeSqlDao dao;

	public BpmNodeSqlService() {
	}

	@Override
	protected IEntityDao<BpmNodeSql, Long> getEntityDao() {
		return dao;
	}

	public BpmNodeSql getByNodeIdAndActdefId(String nodeId, String actdefId) {
		List<BpmNodeSql> bpmNodeSqls = getByNodeIdAndActdefIdAndAction(nodeId, actdefId, null);
		if (bpmNodeSqls.isEmpty())
			return null;
		return bpmNodeSqls.get(0);
	}

	public List<BpmNodeSql> getByNodeIdAndActdefIdAndAction(String nodeId, String actdefId, String action) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(action)) {
			map.put("action", action);
		}
		if (StringUtil.isNotEmpty(nodeId)) {
			map.put("nodeId", nodeId);
		}
		if (StringUtil.isNotEmpty(actdefId)) {
			map.put("actdefId", actdefId);
		}
		return dao.getList("getByNodeIdAndActdefIdAndAction", map);
	}
	
	
	
	/**
	 * 
	 * 处理流程时表单数据跟流程数据
	 * @param processCmd
	 * @param bpmFormData 
	 * void
	 */
	public static void handleData(ProcessCmd processCmd,BpmFormData bpmFormData){
		Map<String, Object> map = new HashMap<String, Object>();
		Map formDataMap= processCmd.getFormDataMap();
		Map data=new HashMap();
		data.putAll(formDataMap);
		data.putAll(bpmFormData.getMainFields());
		data.remove("formData");
		IProcessRun processRun = processCmd.getProcessRun();
		//made by sammy start
		if(processRun!=null){
			data.put("runId", processRun.getRunId());
		}//made by sammy end
		processCmd.addTransientVar("bpmFormData", bpmFormData);
		processCmd.addTransientVar("mainData", data);
		
		
		
	}
}
