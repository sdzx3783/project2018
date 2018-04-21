package com.hotent.makshi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.makshi.model.common.FieldData;
import com.hotent.makshi.model.common.TaskDataFetcher;
import com.hotent.makshi.service.common.FieldService;
import com.hotent.makshi.service.common.TaskDataFetcherService;

@Service
public class IndiDireManager {
	
	@Autowired
	TaskDataFetcherService taskDataFetcherService;
	@Autowired
	FieldService fieldService;
	
	public static Logger logger = Logger.getLogger(IndiDireManager.class);

	/**
	 * 加载数据提取配置表
	 */
	public static Map<String,TaskDataFetcher> tMap=new HashMap<String,TaskDataFetcher>();
	
	//变更历史翻译字段
	//public static Map<String,FieldData> fieldMap = new HashMap<String,FieldData>();
	
	public static List<FieldData> fields = new ArrayList<FieldData>();
	public static List<String> fieldsNames = new ArrayList<String>();
	@PostConstruct
	public void init(){
		List<TaskDataFetcher> fetcherList=taskDataFetcherService.getAll();
		
		List<FieldData> fieldList = fieldService.getAll();
		
		if(null!=fetcherList && fetcherList.size()>0){
			logger.info("加载流程数据提取配置开始。。。");
			for(TaskDataFetcher tf:fetcherList){
				tMap.put(tf.getTableName(), tf);
			}
			logger.info("加载流程数据提取配置结束");
		}
		
		//变更历史字段翻译map集合
		if(null!=fieldList && fieldList.size()>0){
			for(FieldData field:fieldList){
				fields.add(field);
				fieldsNames.add(field.getFieldName());
			}
		}
	}
}
