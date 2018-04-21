package com.hotent.makshi.service.project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.core.web.ResultMessage;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.makshi.dao.project.ClassifyStageDao;
import com.hotent.makshi.dao.project.ClassifyStageTaskDao;
import com.hotent.makshi.dao.project.StageLibraryDao;
import com.hotent.makshi.model.project.ClassifyStage;
import com.hotent.makshi.model.project.ClassifyStageTask;
import com.hotent.makshi.model.project.FlowChartJsonData;
import com.hotent.makshi.model.project.FlowChartJsonLink;
import com.hotent.makshi.model.project.FlowChartJsonResult;
import com.hotent.makshi.model.project.StageLibrary;
import com.hotent.makshi.model.project.StageTaskLibrary;

@Service
public class ClassifyStageService extends BaseService<ClassifyStage> {
	@Resource
	private ClassifyStageDao dao;
	
	@Resource
	private StageLibraryDao stageLibrarydao;
	
	@Resource
	private ClassifyStageTaskDao classifyStageTaskDao;
	
	@Resource
	private StageTaskLibraryService stageTaskLibraryService;
	
	@Resource
	private ClassifyStageTaskService classifyStageTaskService;
	
	public ClassifyStageService(){
		
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
		super.delById(id);
		deleteRelation(id);
	}



	/**
	 * 根据阶段id解除阶段的前后置关系
	 * @param id
	 */
	public void deleteRelation(Long id) {
		//将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
		List<ClassifyStage> prelist= getNodesOfPrestageContainId(id);
		for (ClassifyStage classifyStage : prelist) {
			String _prestage = classifyStage.getPrestage();
			if(_prestage!=null && _prestage.trim().split(",").length>0){
				String[] split = _prestage.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList=new ArrayList<>(listTemp);
				if(asList.contains(id+"")){
					asList.remove(id+"");
					if(asList.size()==0){
						classifyStage.setPrestage("");
					}else{
						String join = StringUtils.join(asList, ",");
						classifyStage.setPrestage(join);
					}
					dao.update(classifyStage);
				}
			}
		}
		//将前置节点为baseId的阶段查找出来 afterstage过滤掉baseid
		List<ClassifyStage> afterlist= getNodesOfAfterstageContainId(id);
		for (ClassifyStage classifyStage : afterlist) {
			String _afterstage = classifyStage.getAfterstage();
			if(_afterstage!=null && _afterstage.trim().split(",").length>0){
				String[] split = _afterstage.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList=new ArrayList<>(listTemp);
				if(asList.contains(id+"")){
					asList.remove(id+"");
					if(asList.size()==0){
						classifyStage.setAfterstage("");
					}else{
						String join = StringUtils.join(asList, ",");
						classifyStage.setAfterstage(join);
					}
					dao.update(classifyStage);
				}
			}
		}
	}

	public void update(ClassifyStage entity,boolean updatePreAndAfterRelation) {
		if(entity.getStagetype()!=null && entity.getStagetype()==1){
			entity.setPrestage("");
		}else if(entity.getStagetype()!=null && entity.getStagetype()==3){
			entity.setAfterstage("");
		}
		super.update(entity);
		if(updatePreAndAfterRelation){
			updatePreAndAfterRelation(entity);
		}
	}

	/**
	 * 维护前后置节点的更新
	 * @param entity
	 */
	public void updatePreAndAfterRelation(ClassifyStage entity) {
		Long baseId = entity.getId();
		String afterstage = entity.getAfterstage();
		String prestage = entity.getPrestage();
		
		//将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
		List<ClassifyStage> prelist= getNodesOfPrestageContainId(baseId);
		for (ClassifyStage classifyStage : prelist) {
			String _prestage = classifyStage.getPrestage();
			if(_prestage!=null && _prestage.trim().split(",").length>0){
				String[] split = _prestage.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList=new ArrayList<>(listTemp);
				if(asList.contains(baseId+"")){
					asList.remove(baseId+"");
					if(asList.size()==0){
						classifyStage.setPrestage("");
					}else{
						String join = StringUtils.join(asList, ",");
						classifyStage.setPrestage(join);
					}
					dao.update(classifyStage);
				}
			}
		}
		
		if(afterstage!=null && afterstage.trim().length()>0 && entity.getStagetype()!=3){
			String[] split = afterstage.trim().split(",");
			for (String string : split) {
				ClassifyStage afternode = dao.getById(Long.parseLong(string));
				if(afternode!=null && afternode.getStagetype()!=1){
					String prestageOfAfterNode = afternode.getPrestage();
					if(prestageOfAfterNode!=null && prestageOfAfterNode.trim().length()>0){
						String[] split2 = prestageOfAfterNode.trim().split(",");
						List<String> listTemp = Arrays.asList(split2);
						List<String> list=new ArrayList<>(listTemp);
						if(!list.contains(baseId+"")){
							list.add(baseId+"");
							String join = StringUtils.join(list, ",");
							afternode.setPrestage(join);
							dao.update(afternode);
						}
						
					}else{
						afternode.setPrestage(baseId+"");
						dao.update(afternode);
					}
				}
			}
		}/*else{
			//将前置节点为baseId的阶段查找出来 prestage过滤掉baseid
			List<ClassifyStage> list= getNodesOfPrestageContainId(baseId);
			for (ClassifyStage classifyStage : list) {
				String _prestage = classifyStage.getPrestage();
				if(_prestage!=null && _prestage.trim().split(",").length>0){
					String[] split = _prestage.split(",");
					List<String> listTemp = Arrays.asList(split);
					List<String> asList=new ArrayList<>(listTemp);
					if(asList.contains(baseId+"")){
						asList.remove(baseId+"");
						if(asList.size()==0){
							classifyStage.setPrestage("");
						}else{
							String join = StringUtils.join(asList, ",");
							classifyStage.setPrestage(join);
						}
						dao.update(classifyStage);
					}
				}
			}
		}*/
		
		List<ClassifyStage> afterlist= getNodesOfAfterstageContainId(baseId);
		for (ClassifyStage classifyStage : afterlist) {
			String _afterstage = classifyStage.getAfterstage();
			if(_afterstage!=null && _afterstage.trim().split(",").length>0){
				String[] split = _afterstage.split(",");
				List<String> listTemp = Arrays.asList(split);
				List<String> asList=new ArrayList<>(listTemp);
				if(asList.contains(baseId+"")){
					asList.remove(baseId+"");
					if(asList.size()==0){
						classifyStage.setAfterstage("");
					}else{
						String join = StringUtils.join(asList, ",");
						classifyStage.setAfterstage(join);
					}
					dao.update(classifyStage);
				}
			}
		}
		
		if(prestage!=null && prestage.trim().length()>0 && entity.getStagetype()!=1){
			String[] split = prestage.trim().split(",");
			for (String string : split) {
				ClassifyStage prenode = dao.getById(Long.parseLong(string));
				if(prenode!=null && prenode.getStagetype()!=3){
					String afterstageOfPreNode = prenode.getAfterstage();
					if(afterstageOfPreNode!=null && afterstageOfPreNode.trim().length()>0){
						String[] split2 = afterstageOfPreNode.trim().split(",");
						List<String> listTemp = Arrays.asList(split2);
						List<String> list=new ArrayList<>(listTemp);
						if(!list.contains(baseId+"")){
							list.add(baseId+"");
							String join = StringUtils.join(list, ",");
							prenode.setAfterstage(join);
							dao.update(prenode);
						}
						
					}else{
						prenode.setAfterstage(baseId+"");
						dao.update(prenode);
					}
				}
			}
		}/*else{
			//将前置节点为baseId的阶段查找出来 afterstage过滤掉baseid
			List<ClassifyStage> list= getNodesOfAfterstageContainId(baseId);
			for (ClassifyStage classifyStage : list) {
				String _afterstage = classifyStage.getAfterstage();
				if(_afterstage!=null && _afterstage.trim().split(",").length>0){
					String[] split = _afterstage.split(",");
					List<String> listTemp = Arrays.asList(split);
					List<String> asList=new ArrayList<>(listTemp);
					if(asList.contains(baseId+"")){
						asList.remove(baseId+"");
						if(asList.size()==0){
							classifyStage.setAfterstage("");
						}else{
							String join = StringUtils.join(asList, ",");
							classifyStage.setAfterstage(join);
						}
						dao.update(classifyStage);
					}
				}
			}
		}*/
	}


	public List<ClassifyStage> getNodesOfPrestageContainId(Long baseId) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("preStage", 1);
		List<ClassifyStage> list = dao.getBySqlKey("getNodesOfstageContainId", params);
		return list;
	}

	public List<ClassifyStage> getNodesOfAfterstageContainId(Long baseId) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("containedId", baseId);
		params.put("afterStage", 1);
		List<ClassifyStage> list = dao.getBySqlKey("getNodesOfstageContainId", params);
		return list;
	}


	@Override
	protected IEntityDao<ClassifyStage, Long> getEntityDao() {
		return dao;
	}

	public void addStageByStageLibIds(Long[] lAryId,Long classifylibraryid) {
		if(lAryId==null || lAryId.length==0 || classifylibraryid==null || classifylibraryid==0){
			return ;
		}else{
			for (Long long1 : lAryId) {
				addStageByStageLibId(long1,classifylibraryid);
			}
		}
		
	}
	/**
	 * 
	 * @param id 根据阶段库的id同步到分类库的阶段数据
	 * @param classifylibraryid 分类库id
	 */
	private void addStageByStageLibId(Long id,Long classifylibraryid) {
		StageLibrary stageLibrary = stageLibrarydao.getById(id);
		
		if(stageLibrary!=null){
			//判断待同步的阶段的阶段编号在该分类下是否已经存在
			Integer stageno = stageLibrary.getStageno();
			if(existStageno(classifylibraryid,stageno)){
				return ;
			}
			
			ClassifyStage classifyStage=new ClassifyStage();
			
			classifyStage.setStagename(stageLibrary.getStagename());
			classifyStage.setStageno(stageLibrary.getStageno());
			classifyStage.setStagetype(1);//默认是起始阶段
			classifyStage.setCreateorgid(stageLibrary.getCreateorgid());
			classifyStage.setCreateorg(stageLibrary.getCreateorg());
			classifyStage.setOrder(0);//默认排序为0
			classifyStage.setPrestage("");
			classifyStage.setAfterstage("");
			classifyStage.setIsdelete(0);
			classifyStage.setId(UniqueIdUtil.genId());
			classifyStage.setClassifylibraryid(classifylibraryid);
			dao.add(classifyStage);
			
			//同步阶段任务
			List<StageTaskLibrary> list = stageTaskLibraryService.getTasklibByStageno(stageLibrary.getStageno());
			for (StageTaskLibrary stageTaskLibrary : list) {
				ClassifyStageTask classifyStageTask=new ClassifyStageTask();
				classifyStageTask.setClassifystageid(classifyStage.getId());
				classifyStageTask.setAfterclassifystagetaskid("");
				classifyStageTask.setPreclassifystagetaskid("");
				classifyStageTask.setQueryclassifystagetaskid("");
				classifyStageTask.setTaskno(stageTaskLibrary.getId());
				classifyStageTask.setTaskname(stageTaskLibrary.getTaskname());
				classifyStageTask.setTemplatefile(stageTaskLibrary.getTemplatefile());
				classifyStageTask.setTasktype(stageTaskLibrary.getTasktype());
				classifyStageTask.setRemark(stageTaskLibrary.getRemark());
				classifyStageTask.setIsexamine(stageTaskLibrary.getIsexamine());
				classifyStageTask.setIsmore(stageTaskLibrary.getIsmore());
				classifyStageTask.setOrder(stageTaskLibrary.getOrder());
				classifyStageTask.setFields(stageTaskLibrary.getFields());
				classifyStageTask.setUploadfile(stageTaskLibrary.getUploadfile());
				classifyStageTask.setFlowid(stageTaskLibrary.getFlowid());
				classifyStageTask.setIsdelete(0);
				classifyStageTaskDao.add(classifyStageTask);
			}
		}
	}

	
	/**
	 * 判断该阶段下是否已经存在
	 * @param classifylibraryid
	 * @param stageno
	 * @return
	 */
	private boolean existStageno(Long classifylibraryid, Integer stageno) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", classifylibraryid);
		params.put("stageno", stageno);
		params.put("isdelete", 0);
		List<ClassifyStage> list = dao.getBySqlKey("getStageByParams", params);
		if(list!=null && list.size()>0){
			return true;
		}else{
			return false;
		}
	}




	public List<ClassifyStage> getByClassifyId(Long id) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", id);
		List<ClassifyStage> list = dao.getBySqlKey("getByClassifyId", params);
		return list;
	}
	
	public List<ClassifyStage> getByClassifyId(Long id,QueryFilter filter) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", id);
		List<ClassifyStage> list = dao.getBySqlKey("getByClassifyId",params, filter.getPageBean());
		return list;
	}

	public void updateTaskOrder(Long classifyStageid, Integer classifyStageorder) {
		Map<String,Object> params= new HashMap<String, Object>();
		params.put("id", classifyStageid);
		params.put("order", classifyStageorder);
		dao.getBySqlKey("updateOrderById", params);
	}


	public long getCountByClassifyid(Long id) {
		Map<String,Object>  params=new HashMap<String, Object>();
		params.put("classifylibraryid", id);
		long count = (long) dao.getOne("getCountByClassifyId", params);
		return count;
	}




	public ResultMessage checkTask(Long stageId) {
		ClassifyStage classifyStage = dao.getById(stageId);
		if (classifyStage == null || classifyStage.getIsdelete() == 1) {
			return new ResultMessage(ResultMessage.Fail, "该分类阶段已经被删除！");
		} else {
			List<ClassifyStageTask> classifyStageTasks = classifyStageTaskService.getStageTaskByStageId(stageId);
			if (classifyStageTasks == null || classifyStageTasks.size() == 0) {
				return new ResultMessage(ResultMessage.Fail, "该分类阶段没有配置可用的任务！");
			}
			List<String> checkIds = new ArrayList<>();
			Map<String, ClassifyStageTask> map = new HashMap<>();// 键为阶段任务id字符串
			boolean flag = true;
			for (ClassifyStageTask classifyStageTask : classifyStageTasks) {

				checkIds.add(classifyStageTask.getId() + "");
				map.put("" + classifyStageTask.getId(), classifyStageTask);
			}
			for (ClassifyStageTask classifyStageTask : classifyStageTasks) {
				if (!checkId(classifyStageTask, checkIds)) {
					return new ResultMessage(ResultMessage.Fail,
							"任务编号为" + classifyStageTask.getTaskno() + "的前后置任务中存在已经被删除的任务！");
				}
			}
			for (ClassifyStageTask classifyStageTask : classifyStageTasks) {
				if (!check(classifyStageTask, map, 0)) {
					// 校验前后置关系
					flag = false;
					break;
				}
			}
			if (!flag) {
				return new ResultMessage(ResultMessage.Fail, "任务前后置关系配置错误，请注意检查！");
			}

			return new ResultMessage(ResultMessage.Success, "检查校验通过！");
		}
	}



	private boolean check(ClassifyStageTask classifyStageTask, Map<String, ClassifyStageTask> map, int deep) {
		boolean flag=true;
		if((++deep)>=32){
			return false;
		}
		String prestagetask = classifyStageTask.getPreclassifystagetaskid();
		if(StringUtils.isEmpty(prestagetask)){
			return true;
		}else{
			String[] split = prestagetask.trim().split(",");
			for (String string : split) {
				ClassifyStageTask cs = map.get(string);
				if(!check(cs,map,deep)){
					flag=false;
					break;
				}
			}
		}
		return flag;
	}




	private boolean checkId(ClassifyStageTask classifyStageTask, List<String> checkIds) {
		String prestagetask = classifyStageTask.getPreclassifystagetaskid();
		String afterstagetask = classifyStageTask.getAfterclassifystagetaskid();
		if(!StringUtils.isEmpty(prestagetask)){
			String[] split = prestagetask.trim().split(",");
			for (String string : split) {
				if(!checkIds.contains(string)){
					return false;
				}
			}
		}
		if(!StringUtils.isEmpty(afterstagetask)){
			String[] split = afterstagetask.trim().split(",");
			for (String string : split) {
				if(!checkIds.contains(string)){
					return false;
				}
			}
		}
		return true;

	}
	
	
	public FlowChartJsonResult structflowChartData(Long stageId){
		FlowChartJsonResult result=null;
		List<ClassifyStageTask> list = classifyStageTaskService.getStageTaskByStageId(stageId);
		List<ClassifyStageTask> listOfFirstDeep=new ArrayList<>();
		Map<String,ClassifyStageTask> dataMap=new HashMap<>();
		Map<Integer,List<ClassifyStageTask>> map=new HashMap<>();
		for (ClassifyStageTask classifyStageTask : list) {
			removeInvalidPreNodeId(classifyStageTask,list);
			removeInvalidAfterNodeId(classifyStageTask,list);
		}
		
		for (ClassifyStageTask classifyStageTask : list) {
			if(classifyStageTask.getPreclassifystagetaskid().trim().length()==0){
					listOfFirstDeep.add(classifyStageTask);
			}
			
			dataMap.put(classifyStageTask.getId()+"", classifyStageTask);
		}
		
		map.put(0, listOfFirstDeep);
		structMap(map,0,dataMap);
		result=getJsonResult(map);
		return result;
		
	}
	private FlowChartJsonResult getJsonResult(Map<Integer, List<ClassifyStageTask>> map) {
		FlowChartJsonResult result=new FlowChartJsonResult();
		List<FlowChartJsonData> datas = result.getDatas();
		List<FlowChartJsonLink> links = result.getLinks();
		int size = map.size();
		if(size==0){
			return result;
		}
		Set<Entry<Integer,List<ClassifyStageTask>>> entrySet = map.entrySet();
		Iterator<Entry<Integer, List<ClassifyStageTask>>> iterator = entrySet.iterator();
		float xOffset=0f;
		if(size>1){
			xOffset=(0.8f)/(size-1);
		}
		while(iterator.hasNext()){
			Entry<Integer, List<ClassifyStageTask>> next = iterator.next();
			Integer key = next.getKey();
			List<ClassifyStageTask> value = next.getValue();
			float yOffset=0f;
			if(value.size()>1){
				yOffset=(0.8f)/(value.size()-1);
			}
			
			for(int i=0;i<value.size();i++) {
				FlowChartJsonData data=new FlowChartJsonData();
				data.setName(value.get(i).getTaskname());
				data.setCode(value.get(i).getId()+"");
				if(xOffset==0f){
					data.setX(0.5f);
				}else{
					data.setX(0.1f+(xOffset*key));
				}
				if(yOffset==0f){
					data.setY(0.5f);
				}else{
					data.setY(0.9f-(yOffset*i));
				}
				datas.add(data);
			}
		}
		for(int i=0;i<size-1;i++){
			List<ClassifyStageTask> first = map.get(i);
			List<ClassifyStageTask> second = map.get(i+1);
			for (ClassifyStageTask c1 : first) {
				for (ClassifyStageTask c2 : second) {
					String[] split = c2.getPreclassifystagetaskid().trim().split(",");
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

	private void structMap(Map<Integer, List<ClassifyStageTask>> map,Integer deep, Map<String, ClassifyStageTask> dataMap) {
		List<ClassifyStageTask> list = map.get(deep);
		if(list==null){
			return ;
		}
		for (ClassifyStageTask classifyStageTask : list) {
			String afterstagetask = classifyStageTask.getAfterclassifystagetaskid();
			if(afterstagetask.trim().length()>0){
				String[] split = afterstagetask.trim().split(",");
				for (String string : split) {
					if(map.get(deep+1)==null){
						List<ClassifyStageTask> listTemp=new ArrayList<>();
						map.put(deep+1, listTemp);
					}
					ClassifyStageTask temp = dataMap.get(string);
					List<ClassifyStageTask> nextDeep = map.get(deep+1);
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
	private void removeInvalidPreNodeId(ClassifyStageTask classifyStageTask, List<ClassifyStageTask> list) {
		String prestagetask = classifyStageTask.getPreclassifystagetaskid();
		if(prestagetask.trim().length()<=1){
			return ;
		}else{
			String[] split = prestagetask.trim().split(",");
			List <String> temp=Arrays.asList(split);
			List <String> prestageTaskIds=new ArrayList<>(temp);
			for(int i=0;i<split.length-1;i++){
				for(int j=i+1;j<split.length;j++){
					if(existRelation(split[i],split[j],list)){
						prestageTaskIds.remove(split[i]);
					}
					if(existRelation(split[j],split[i],list)){
						prestageTaskIds.remove(split[j]);
					}
				}
			}
			if(prestageTaskIds.size()==0){
				classifyStageTask.setPreclassifystagetaskid("");
			}else{
				classifyStageTask.setPreclassifystagetaskid(StringUtils.join(prestageTaskIds, ","));
			}
		}
	}
	
	/**
	 * 将某个阶段节点的afterstage中本身存在前后置关系的节点 移除掉与其间接产生后置关系的id
	 * @param classifyStage
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void removeInvalidAfterNodeId(ClassifyStageTask classifyStageTask, List<ClassifyStageTask> list) {
		String afterstagetask = classifyStageTask.getAfterclassifystagetaskid();
		if(afterstagetask.trim().length()<=1){
			return ;
		}else{
			String[] split = afterstagetask.trim().split(",");
			List <String> temp=Arrays.asList(split);
			List <String> afterstagetaskIds=new ArrayList<>(temp);
			for(int i=0;i<split.length-1;i++){
				for(int j=i+1;j<split.length;j++){
					if(existRelation(split[i],split[j],list)){
						afterstagetaskIds.remove(split[j]);
					}
					if(existRelation(split[j],split[i],list)){
						afterstagetaskIds.remove(split[i]);
					}
				}
			}
			if(afterstagetaskIds.size()==0){
				classifyStageTask.setAfterclassifystagetaskid("");
			}else{
				classifyStageTask.setAfterclassifystagetaskid(StringUtils.join(afterstagetaskIds, ","));
			}
		}
	}
	/**
	 * 判断s1是否是s2的上级节点
	 * @param s1
	 * @param s2
	 * @return
	 */
	private boolean existRelation(String s1, String s2,List<ClassifyStageTask> list) {
		Map<String,ClassifyStageTask> stageTaskDataMap=new HashMap<>();
		for (ClassifyStageTask classifyStageTask : list) {
			stageTaskDataMap.put(classifyStageTask.getId()+"", classifyStageTask);
		}
		ClassifyStageTask classifyStageTask = stageTaskDataMap.get(s2);
		boolean flag=false;
		if(classifyStageTask.getPreclassifystagetaskid().trim().length()>0){
			String prestagetask = classifyStageTask.getPreclassifystagetaskid();
			String[] split = prestagetask.trim().split(",");
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
}
