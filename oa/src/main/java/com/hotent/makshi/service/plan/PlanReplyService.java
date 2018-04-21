package com.hotent.makshi.service.plan;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.plan.PlanReplyDao;
import com.hotent.makshi.model.plan.PlanReply;

@Service
public class PlanReplyService extends BaseService<PlanReply> {
	@Resource
	private PlanReplyDao dao;
	public PlanReplyService(){
		
	}
	
	@Override
	protected IEntityDao<PlanReply, Long> getEntityDao() {
		return dao;
	}
	
	public List<PlanReply> getReplyByPId(Integer pId){
		List<PlanReply> list = dao.getBySqlKey("getReplyByPId",pId);
		return list;
	}
	
}
