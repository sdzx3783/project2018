package com.hotent.makshi.service.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.web.ResultMessage;
import com.hotent.makshi.dao.project.ClassifyLibraryDao;
import com.hotent.makshi.model.project.ClassifyLibrary;
import com.hotent.makshi.model.project.ClassifyStage;
import com.hotent.makshi.model.project.FlowChartJsonData;
import com.hotent.makshi.model.project.FlowChartJsonLink;
import com.hotent.makshi.model.project.FlowChartJsonResult;
import com.hotent.platform.model.system.SysOrg;

import java.util.Arrays;

@Service
public class ClassifyLibraryService extends BaseService<ClassifyLibrary> {
	@Resource
	private ClassifyLibraryDao dao;
	
	@Resource
	private ClassifyStageService classifyStageService;
	
	@Autowired
	private ProjectService projectService;
	
	@Resource
	private ClassifyStageTaskService classifyStageTaskService;
	public ClassifyLibraryService(){
		
	}
	
	@Override
	protected IEntityDao<ClassifyLibrary, Long> getEntityDao() {
		return dao;
	}
	
	
	
	@Override
	public void delByIds(Long[] ids) {
		if(ids==null || ids.length==0){
			return ;
		}
		for (Long id : ids) {
			delById(id);
		}
	}
	
	@Override
	public void delById(Long id) {
		long count = projectService.getCountByClassifyLibId(id);
		if(count>0){
			throw new RuntimeException("分类下有已创建的项目！");
		}
		super.delById(id);
	}
	
	public ResultMessage checkStage(Long id){
		ClassifyLibrary classifyLibrary = dao.getById(id);
		if(classifyLibrary==null || classifyLibrary.getIsdelete()==1){
			return new ResultMessage(ResultMessage.Fail, "该分类已经被删除！");
		}else{
			List<ClassifyStage> classifyStages = classifyStageService.getByClassifyId(id);
			if(classifyStages==null || classifyStages.size()==0){
				return new ResultMessage(ResultMessage.Fail, "该分类下没有配置可用的阶段！");
			}
			List<ClassifyStage> startStage=new ArrayList<>();
			List<ClassifyStage> endStage=new ArrayList<>();
			List<String> checkIds=new ArrayList<>();
			Map<String,ClassifyStage> map=new HashMap<>();//键为阶段id字符串
			boolean flag=true;
			for (ClassifyStage classifyStage : classifyStages) {
				if(classifyStage.getStagetype()==1){
					startStage.add(classifyStage);
				}
				if(classifyStage.getStagetype()==3){
					endStage.add(classifyStage);
				}
				checkIds.add(classifyStage.getId()+"");
				map.put(""+classifyStage.getId(), classifyStage);
			}
			if(startStage.size()==0){
				return new ResultMessage(ResultMessage.Fail, "该分类下没有配置起始阶段！");
			}
			if(endStage.size()==0){
				return new ResultMessage(ResultMessage.Fail, "该分类下没有配置结束阶段！");
			}
			if(startStage.size()>1){
				return new ResultMessage(ResultMessage.Fail, "该分类下配置了多个起始阶段！");
			}
			if(endStage.size()>1){
				return new ResultMessage(ResultMessage.Fail, "该分类下配置了多个结束阶段！");
			}
			if(!StringUtils.isBlank(startStage.get(0).getPrestage())){
				return new ResultMessage(ResultMessage.Fail, "阶段编号为"+startStage.get(0).getStageno()+"的类型为起始阶段，不能配置前置阶段！");
			}
			if(!StringUtils.isBlank(endStage.get(0).getAfterstage())){
				return new ResultMessage(ResultMessage.Fail, "阶段编号为"+startStage.get(0).getStageno()+"的类型为结束阶段，不能配置后置阶段！");
			}
			for (ClassifyStage classifyStage : classifyStages) {
				if(!checkId(classifyStage,checkIds)){
					return new ResultMessage(ResultMessage.Fail, "阶段编号为"+classifyStage.getStageno()+"的前后置阶段中存在已经被删除的阶段！");
				}
				long tasknum=classifyStageTaskService.getTaskCountByStageid(classifyStage.getId());
				if(tasknum<=0){
					return new ResultMessage(ResultMessage.Fail, "阶段编号为"+classifyStage.getStageno()+"的阶段没有配置阶段任务！");
				}
			}
			for (ClassifyStage classifyStage : classifyStages) {
				if(!check(classifyStage,map,0)){
					//校验前后置关系
					flag=false;
					break;
				}
			}
			if(!flag){
				return new ResultMessage(ResultMessage.Fail, "阶段前后置关系配置错误，请注意检查！");
			}
			//检查任务
			for (String string : checkIds) {
				ResultMessage checkTask = classifyStageService.checkTask(Long.valueOf(string));
				if(checkTask.getResult()!=1){
					ClassifyStage classifyStage = map.get(string);
					return new ResultMessage(ResultMessage.Fail, "阶段编号为"+classifyStage.getStageno()+"下的任务检查失败："+checkTask.getMessage());
				}
			}
			return new ResultMessage(ResultMessage.Success, "检查校验通过！");
		}
	}

	private boolean checkId(ClassifyStage classifyStage, List<String> checkIds) {
		String prestage = classifyStage.getPrestage();
		String afterstage = classifyStage.getAfterstage();
		if(!StringUtils.isEmpty(prestage)){
			String[] split = prestage.trim().split(",");
			for (String string : split) {
				if(!checkIds.contains(string)){
					return false;
				}
			}
		}
		if(!StringUtils.isEmpty(afterstage)){
			String[] split = afterstage.trim().split(",");
			for (String string : split) {
				if(!checkIds.contains(string)){
					return false;
				}
			}
		}
		return true;
	}

	private boolean check(ClassifyStage classifyStage, Map<String,ClassifyStage> map,int deep) {
		boolean flag=true;
		if((++deep)>=32){
			return false;
		}
		String prestage = classifyStage.getPrestage();
		if(StringUtils.isEmpty(prestage) || classifyStage.getStagetype()==1){
			return true;
		}else{
			String[] split = prestage.trim().split(",");
			for (String string : split) {
				ClassifyStage cs = map.get(string);
				if(!check(cs,map,deep)){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}

	
	public FlowChartJsonResult structflowChartData(Long classifyid){
		FlowChartJsonResult result=null;
		List<ClassifyStage> list = classifyStageService.getByClassifyId(classifyid);
		List<ClassifyStage> listOfFirstDeep=new ArrayList<>();
		ClassifyStage stageOfStartType=null;
		ClassifyStage stageOfEndType=null;
		Map<String,ClassifyStage> dataMap=new HashMap<>();
		Map<Integer,List<ClassifyStage>> map=new HashMap<>();
		for (ClassifyStage classifyStage : list) {
			removeInvalidPreNodeId(classifyStage,list);
			removeInvalidAfterNodeId(classifyStage,list);
		}
		
		for (ClassifyStage classifyStage : list) {
			if(classifyStage.getStagetype()==1 || classifyStage.getPrestage().trim().length()==0){
				if(classifyStage.getStagetype()==1){
					stageOfStartType=classifyStage;
				}
				if(classifyStage.getStagetype()!=3){//第一列不会有结束节点
					listOfFirstDeep.add(classifyStage);
				}
			}
			if(classifyStage.getStagetype()==3){
				stageOfEndType=classifyStage;
			}
			dataMap.put(classifyStage.getId()+"", classifyStage);
		}
		if(stageOfEndType.getPrestage().trim().length()==0){//如果结束的没有前置节点 那么默认前置为开始节点
			stageOfEndType.setPrestage(stageOfStartType.getId()+"");
			String afterstage = stageOfStartType.getAfterstage();
			if(afterstage.trim().length()==0){
				stageOfStartType.setAfterstage(stageOfEndType.getId()+"");
			}else{
				stageOfStartType.setAfterstage(afterstage.trim()+","+stageOfEndType.getId());
			}
		}
		
		map.put(0, listOfFirstDeep);
		structMap(map,0,dataMap);
		result=getJsonResult(map);
		return result;
		
	}
	private FlowChartJsonResult getJsonResult(Map<Integer, List<ClassifyStage>> map) {
		FlowChartJsonResult result=new FlowChartJsonResult();
		List<FlowChartJsonData> datas = result.getDatas();
		List<FlowChartJsonLink> links = result.getLinks();
		int size = map.size();
		if(size==0){
			return result;
		}
		Set<Entry<Integer,List<ClassifyStage>>> entrySet = map.entrySet();
		Iterator<Entry<Integer, List<ClassifyStage>>> iterator = entrySet.iterator();
		float xOffset=(0.8f)/(size-1);
		while(iterator.hasNext()){
			Entry<Integer, List<ClassifyStage>> next = iterator.next();
			Integer key = next.getKey();
			List<ClassifyStage> value = next.getValue();
			float yOffset=0f;
			if(value.size()>1){
				yOffset=(0.8f)/(value.size()-1);
			}
			
			for(int i=0;i<value.size();i++) {
				FlowChartJsonData data=new FlowChartJsonData();
				data.setName(value.get(i).getStagename());
				data.setCode(value.get(i).getId()+"");
				data.setX(0.1f+(xOffset*key));
				if(yOffset==0f){
					data.setY(0.5f);
				}else{
					data.setY(0.9f-(yOffset*i));
				}
				datas.add(data);
			}
		}
		for(int i=0;i<size-1;i++){
			List<ClassifyStage> first = map.get(i);
			List<ClassifyStage> second = map.get(i+1);
			for (ClassifyStage c1 : first) {
				for (ClassifyStage c2 : second) {
					String[] split = c2.getPrestage().trim().split(",");
					boolean flag=false;
					for (String string : split) {
						if(string.equals(c1.getId()+"")){
							flag=true;
							break;
						}
					}
					if(flag){
						FlowChartJsonLink link=new FlowChartJsonLink();
						link.setSource(c1.getId()+"");
						link.setTarget(c2.getId()+"");
						links.add(link);
					}
				}
			}
		}
		
		
		return result;
	}

	private void structMap(Map<Integer, List<ClassifyStage>> map,Integer deep, Map<String, ClassifyStage> dataMap) {
		List<ClassifyStage> list = map.get(deep);
		if(list==null){
			return ;
		}
		for (ClassifyStage classifyStage : list) {
			String afterstage = classifyStage.getAfterstage();
			if(afterstage.trim().length()>0){
				String[] split = afterstage.trim().split(",");
				for (String string : split) {
					if(map.get(deep+1)==null){
						List<ClassifyStage> listTemp=new ArrayList<>();
						map.put(deep+1, listTemp);
					}
					ClassifyStage temp = dataMap.get(string);
					List<ClassifyStage> nextDeep = map.get(deep+1);
					if(!nextDeep.contains(temp)){
						nextDeep.add(temp);
					}
				}
			}
		}
		structMap(map,deep+1,dataMap);
	}

	/**
	 * 将某个阶段节点的prestage中本身存在前后置关系的节点 移除掉与其间接产生前置关系的id
	 * @param classifyStage
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void removeInvalidPreNodeId(ClassifyStage classifyStage, List<ClassifyStage> list) {
		String prestage = classifyStage.getPrestage();
		if(classifyStage.getStagetype()==1 || prestage.trim().length()<=1){
			return ;
		}else{
			String[] split = prestage.trim().split(",");
			List <String> temp=Arrays.asList(split);
			List <String> prestageIds=new ArrayList<>(temp);
			for(int i=0;i<split.length-1;i++){
				for(int j=i+1;j<split.length;j++){
					if(existRelation(split[i],split[j],list)){
						prestageIds.remove(split[i]);
					}
					if(existRelation(split[j],split[i],list)){
						prestageIds.remove(split[j]);
					}
				}
			}
			if(prestageIds.size()==0){
				classifyStage.setPrestage("");
			}else{
				classifyStage.setPrestage(StringUtils.join(prestageIds, ","));
			}
		}
	}
	
	/**
	 * 将某个阶段节点的afterstage中本身存在前后置关系的节点 移除掉与其间接产生后置关系的id
	 * @param classifyStage
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void removeInvalidAfterNodeId(ClassifyStage classifyStage, List<ClassifyStage> list) {
		String afterstage = classifyStage.getAfterstage();
		if(classifyStage.getStagetype()==3 || afterstage.trim().length()<=1){
			return ;
		}else{
			String[] split = afterstage.trim().split(",");
			List <String> temp=Arrays.asList(split);
			List <String> afterstageIds=new ArrayList<>(temp);
			for(int i=0;i<split.length-1;i++){
				for(int j=i+1;j<split.length;j++){
					if(existRelation(split[i],split[j],list)){
						afterstageIds.remove(split[j]);
					}
					if(existRelation(split[j],split[i],list)){
						afterstageIds.remove(split[i]);
					}
				}
			}
			if(afterstageIds.size()==0){
				classifyStage.setAfterstage("");
			}else{
				classifyStage.setAfterstage(StringUtils.join(afterstageIds, ","));
			}
		}
	}
	/**
	 * 判断s1是否是s2的上级节点
	 * @param s1
	 * @param s2
	 * @return
	 */
	private boolean existRelation(String s1, String s2,List<ClassifyStage> list) {
		Map<String,ClassifyStage> stageDataMap=new HashMap<>();
		for (ClassifyStage classifyStage : list) {
			stageDataMap.put(classifyStage.getId()+"", classifyStage);
		}
		ClassifyStage classifyStage = stageDataMap.get(s2);
		boolean flag=false;
		if(classifyStage.getStagetype()!=1 && classifyStage.getPrestage().trim().length()>0){
			String prestage = classifyStage.getPrestage();
			String[] split = prestage.trim().split(",");
			for (String string : split) {
				if(string.equals(s1)){
					flag=true;
					break;
				}else{
					if(existRelation(s1,string,list)){
						flag=true;
						break;
					}
				}
			}
		}
		return flag;
	}

	public void updateOrder(Long classifyid, Integer classifylibOrder) {
		ClassifyLibrary classifyLibrary = new ClassifyLibrary();
		classifyLibrary.setId(classifyid);
		classifyLibrary.setOrder(classifylibOrder);
		dao.update(classifyLibrary);
	}

	public List<ClassifyLibrary> getClassifyByCateId(Long categoryid) {
		return dao.getBySqlKey("getClassifyByCateId", categoryid);
	}
	
	public List<ClassifyLibrary> getCatesByOrg(Long orgId, Long supid) {
		Map<String,Object> filter=new HashMap<>();
		filter.put("orgID", orgId);
		filter.put("supid", supid);
		return dao.getBySqlKey("getAll", filter);
	}
	
	/**
	 *拖动分类库进行排序。
	 * @param targetId 目标组织ID
	 * @param dragId 	拖动的分类ID
	 * @param moveType 拖动类型 (prev,next,inner);
	 */
	public void move(Long targetId, Long dragId, String moveType) {
		ClassifyLibrary target = dao.getById(targetId);
		ClassifyLibrary dragged = dao.getById(dragId);
		Integer recordOrder=0;
		if(!target.getOrgID().equals(dragged.getOrgID()))
			return;
			
		String nodePath=dragged.getPath();
		//根据拖动节点的路径找到其下所有的子组织。
		List<ClassifyLibrary> list=dao.getByCatePath(nodePath);
		
		for(ClassifyLibrary cate:list){
			//向目标节点的前后拖动。
			if ("prev".equals(moveType) || "next".equals(moveType)) {
				String targetPath=target.getPath();
				String parentPath=targetPath.endsWith(".")?targetPath.substring(0,targetPath.length()-1):targetPath;
				//这个路径尾部带 "." 。
				parentPath=parentPath.substring(0,parentPath.lastIndexOf(".")+1) ;
				
				if(cate.getId().equals(dragId)){
					cate.setSupid(target.getSupid());
					cate.setPath(parentPath + dragId +".");
				}
				else{
					String path = cate.getPath();
					String tmpPath =parentPath + dragId +"." +   path.replaceAll(nodePath, "");
					cate.setPath(tmpPath);
				}
				
				if ("prev".equals(moveType)) {
					cate.setOrder(target.getOrder() - 1);
				} else {
					cate.setOrder(target.getOrder() + 1);
				}
				recordOrder=cate.getOrder();
			}
			//作为目标节点的子节点。
			else{
				//如果是被拖动的节点。
				////需改父节点
				if(cate.getId().equals(dragId)){
					//修改拖动的分类对象
					cate.setSupid(targetId);
					// 修改nodepath
					cate.setPath(target.getPath() + cate.getId() + ".");
					//需要修改排序字段为最大值+1
					List<ClassifyLibrary> bySupid = getBySupid(targetId);
					Integer order=0;
					if(bySupid!=null && bySupid.size()>0){
						Integer order2 = bySupid.get(0).getOrder();
						order=order2+1;
					}
					cate.setOrder(order);
				} else {
					// 带点的路径
					String path = cate.getPath();
					// 替换父节点的路径。
					String tmpPath = path.replaceAll(nodePath, "");
					// 新的父节路径
					String targetPath = target.getPath();
					// 新的父节点 +拖动的节点id + 后缀
					String tmp = targetPath + dragged.getId() + "." + tmpPath;
					cate.setPath(tmp);					
				}
			}
			dao.update(cate);
		}
		//需要target节点的兄弟节点更新排序
		if("prev".equals(moveType)){
			Long targetsupid = target.getSupid();
			
		}else if("next".equals(moveType)){
			
		}
	}
	
	public List<ClassifyLibrary> getBySupid(Long supid){
		Map<String,Object> map=new HashMap<>();
		map.put("supid", supid);
		return dao.getBySqlKey("getBySupid", map);
	}
	
	public void updateOrder(Long targetsupid,Long dragId,String moveType,Integer order){
		Map<String,Object> map=new HashMap<>();
		map.put("targetsupid", targetsupid);
		map.put("orderNum", order);
		map.put("dragId", dragId);
		if("prev".equals(moveType)){
			dao.update("updateDecOrder", map);
		}else if("next".equals(moveType)){
			dao.update("updateAddOrder", map);
		}
		dao.update("getBySupid", map);
	}

	public ClassifyLibrary getByNameAndOrgId(String name,String orgId) {
		HashMap<String,String> map=new HashMap<>();
		map.put("name", name);
		map.put("orgId", orgId);
		List<ClassifyLibrary> bySqlKey = dao.getBySqlKey("getByNameAndOrgId", map);
		ClassifyLibrary classifyLibrary=null;
		if(bySqlKey!=null && bySqlKey.size()>0){
			classifyLibrary = bySqlKey.get(0);
		}
		return classifyLibrary;
	}
	
}
