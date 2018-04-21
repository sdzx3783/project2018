package com.hotent.makshi.service.appPush;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.appPush.AppPushJobDao;
import com.hotent.makshi.dao.appPush.AppPushJobHistoryDao;
import com.hotent.makshi.model.appPush.AppPushJob;
import com.hotent.makshi.model.appPush.AppPushJobHistory;


@Service
public class AppPushJobService extends BaseService<AppPushJob>
{
	@Resource
	private AppPushJobDao dao;
	@Resource
	private AppPushJobHistoryDao historyDao;
	
	public AppPushJobService()
	{
	}
	
	@Override
	protected IEntityDao<AppPushJob,Long> getEntityDao() 
	{
		return dao;
	}
	
	@Transactional
	public void delJobAndHistory(Long jobId){
		dao.delById(jobId);
		historyDao.delBySqlKey("delByJobId", jobId);
	}
	
	/**
	 * 删除任务并添加或更新历史数据
	 * @param jobId
	 */
	@Transactional
	public void delJobAndAddOrUpdateHistory(AppPushJobHistory appPushJobHistory){
		Long jobId =  appPushJobHistory.getJobId();
		dao.delById(jobId);
		List<AppPushJobHistory> appPushJobHistories=historyDao.getBySqlKey("getByJobId", jobId);
		if(appPushJobHistories.size()>0){
			historyDao.update(appPushJobHistory);
		}else{			
			historyDao.add(appPushJobHistory);
		}
	}

	public AppPushJob getByRunId(Long runId) {
	    List<AppPushJob> list = dao.getBySqlKey("getByRunId", runId);
	 	if(list.size()>0){
	 		return list.get(0);
	 	}
	    return null;
	}
}
