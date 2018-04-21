package com.hotent.makshi.service.msgscheduler;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.WfBaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.msgscheduler.SchedulerParamsDao;
import com.hotent.makshi.dao.msgscheduler.SendMsgUserDao;
import com.hotent.makshi.model.msgscheduler.SchedulerParams;
import com.hotent.makshi.model.msgscheduler.SendMsgUser;


@Service
public class SchedulerParamsService extends WfBaseService<SchedulerParams>
{
	@Resource
	private SchedulerParamsDao dao;
	
	@Resource
	private SendMsgUserDao sendMsgUserDao;
	
	public SchedulerParamsService()
	{
	}
	
	@Override
	protected IEntityDao<SchedulerParams, Long> getEntityDao() {
		return dao;
	}

	public void save(SchedulerParams schedulerParams) throws Exception{
		Long id=schedulerParams.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			schedulerParams.setId(id);
			this.addAll(schedulerParams);
		}
		else{
		    this.updateAll(schedulerParams);
		}
	}
	
	private void delByPk(Long id){
		sendMsgUserDao.delByMainId(id);
	}
	
	public void delAll(Long[] lAryId) {
		for(Long id:lAryId){	
			delByPk(id);
			dao.delById(id);	
		}	
	}
	
	public void addAll(SchedulerParams schedulerParams) throws Exception{
		super.add(schedulerParams);
		addSubList(schedulerParams);
	}
	
	public void updateAll(SchedulerParams schedulerParams) throws Exception{
		super.update(schedulerParams);
		delByPk(schedulerParams.getId());
		addSubList(schedulerParams);
	}

	public List<SendMsgUser> getSendMsgUserList(Long id) {
		return sendMsgUserDao.getByMainId(id);
	}
	
	private void addSubList(SchedulerParams schedulerParams) {
		List<SendMsgUser> sendMsgUserList = schedulerParams.getSendMsgUserList();
		if(BeanUtils.isNotEmpty(sendMsgUserList)){
			for(SendMsgUser sendMsgUser:sendMsgUserList){
				sendMsgUser.setRefId(schedulerParams.getId());
				Long id=UniqueIdUtil.genId();
				sendMsgUser.setId(id);				
				sendMsgUserDao.add(sendMsgUser);
			}
		}
	}

	public List<SchedulerParams> getByTriggerName(String triggerName) {
		return dao.getBySqlKey("getByTriggerName",triggerName);
	}
	
}
