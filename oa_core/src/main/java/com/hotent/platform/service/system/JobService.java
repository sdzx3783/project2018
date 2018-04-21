package com.hotent.platform.service.system;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.platform.dao.system.JobDao;
import com.hotent.platform.dao.system.JobParamDao;
import com.hotent.platform.model.system.Job;
import com.hotent.platform.model.system.JobParam;
import com.hotent.platform.model.system.Position;

/**
 *<pre>
 * 对象功能:职务表 Service类
 * 开发公司:广州宏天软件有限公司
 * 开发人员:ray
 * 创建时间:2013-11-28 16:17:48
 *</pre>
 */
@Service
public class JobService extends BaseService<Job>
{
	@Resource
	private JobDao dao;
	@Resource
	private JobParamDao jobParamDao;
	@Resource 
	private UserPositionService userPositionService;
	@Resource
	private PositionService positionService;
	
	public JobService()
	{
	}
	
	@Override
	protected IEntityDao<Job, Long> getEntityDao() 
	{
		return dao;
	}
	
	
	/**
	 * 删除职务，实际是修改标志位
	 * 同时修改sys_user_pos用户岗位关系表、sys_pos岗位表
	 * @author hjx
	 * @version 创建时间：2013-12-4  上午10:50:27
	 * @param posId
	 */
	public void deleteByUpdateFlag(Long id){
		  dao.deleteByUpdateFlag(id);
		  userPositionService.delByJobId(id);
		  positionService.deleByJobId(id);
	}

	/**
	 * 判断职务名称是否存在
	 * @param jobname
	 * @return
	 */
	public boolean isExistJobCode(String jobCode) {
		return dao.isExistJobCode(jobCode);
	}
	
	
	
	public boolean isExistJobCodeForUpd(String jobCode,Long jobId) {
		return dao.isExistJobCodeForUpd(jobCode,jobId);
	}
	/**
	 * 通过职务代码获取职务
	 * @param jobCode
	 * @return
	 */
	public Job getByJobCode(String jobCode){
		return dao.getByJobCode(jobCode);
	}
	
	
	/**
	 * 保存 职务表 信息
	 * @param job
	 */
	public void save(Job job){
		Long id=job.getJobid();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			job.setJobid(id);
			this.add(job);
		}
		else{
			this.update(job);
		}
	}
	
	/**
	 * 添加数据 
	 * @param job
	 * @throws Exception
	 */
	@Override
	public void add(Job job){
		super.add(job);
		this.addSubList(job);
	}
	
	/**
	 * 更新数据
	 * @param job
	 * @throws Exception
	 */
	@Override
	public void update(Job job) {
		super.update(job);
		delByPk(job.getJobid());
		addSubList(job);
	}
	
	/**
	 * 
	 * 添加子表记录
	 * @param job
	 * @throws Exception
	 */
	private void addSubList(Job job) {
		List<JobParam> jobParamList=job.getJobParamList();
		if(BeanUtils.isNotEmpty(jobParamList)){
			for(JobParam jobParam:jobParamList){
				jobParam.setJobid(job.getJobid());
				jobParam.setId(UniqueIdUtil.genId());
				jobParamDao.add(jobParam);
			}
		}
	}
	
	/**
	 * 根据外键删除子表记录
	 * @param jobid
	 */
	private void delByPk(Long jobid){
		jobParamDao.delByMainId(jobid);
	}
	
	/**
	 * 根据外键获得职务参数列表
	 * @param jobid
	 * @return
	 */
	public List<JobParam> getJobParamList(Long jobid) {
		return jobParamDao.getByMainId(jobid);
	}
	
	/**
	 * 根据当前用户的岗位，获取用户职务相对应的value值
	 * @param key
	 * @param userId
	 * @return
	 */
	public String getJobParamValueByKey(String key){
		Position pos = (Position) ContextUtil.getCurrentPos();
		if(pos == null){
			return "";
		}
		Long jobId = pos.getJobId();
		return jobParamDao.getValueByKeyJobId(key,jobId);
	}
	
	/**
	 * 根据用户ID，获取用户主岗位的职务相对应的value值
	 * @return
	 */
	public String getJobParamValueByKeyUserId(String key,Long userId){
		Position pos = positionService.getPrimaryPositionByUserId(userId);
		if(pos == null){
			return "";
		}
		Long jobId = pos.getJobId();
		return jobParamDao.getValueByKeyJobId(key,jobId);
	}
	
	
	
}
