package com.hotent.makshi.service.plan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.plan.PlanDetailDao;
import com.hotent.makshi.model.contract.ContractBillingRecord;
import com.hotent.makshi.model.contract.ContractPaymentRecord;
import com.hotent.makshi.model.contract.Contractinfo;
import com.hotent.makshi.model.plan.Plan;
import com.hotent.makshi.model.plan.PlanDetail;

@Service
public class PlanDetailService extends BaseService<PlanDetail> {
	@Resource
	private PlanDetailDao dao;
	public PlanDetailService(){
		
	}
	
	@Override
	protected IEntityDao<PlanDetail, Long> getEntityDao() {
		return dao;
	}
	
	
	
	public List<PlanDetail> getDetailByPId(Integer pId){
		return dao.getBySqlKey("getDetailByPId", pId);
	}
	
	/**
	 * 添加子表记录
	 * @param contractinfo
	 * @throws Exception
	 */
	public void addSubList(Plan plan) throws Exception{
		List<PlanDetail> planDetailList = plan.getPlanDetailList();
		if(BeanUtils.isNotEmpty(planDetailList)){
			for(PlanDetail detail:planDetailList){
				detail.setPlanId(plan.getId());
				dao.add(detail);
			}
		}
	}
	
	
	/**
	 * 删除所有子表记录
	 * @param contractinfo
	 * @throws Exception
	 */
	public void delSubList(Plan plan) throws Exception{
		List<PlanDetail> planDetailList = plan.getPlanDetailList();
		if(BeanUtils.isNotEmpty(planDetailList)){
			for(PlanDetail detail:planDetailList){
				Integer pId = plan.getId();
				dao.delBySqlKey("delByPId", pId);
			}
		}
	}
	
	
	/**
	 * 更新数据
	 * @param contractinfo
	 * @throws Exception
	 */
	public void updateSubList(Plan plan) throws Exception{
		delSubList(plan);
		addSubList(plan);
	}
	
	
}