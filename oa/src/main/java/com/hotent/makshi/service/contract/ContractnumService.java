package com.hotent.makshi.service.contract;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.makshi.dao.contract.ContractnumDao;
import com.hotent.makshi.model.contract.Contractnum;
import com.hotent.makshi.utils.DateUtils;


@Service
public class ContractnumService extends BaseService<Contractnum>
{
	@Resource
	private ContractnumDao dao;
	
	
	public ContractnumService()
	{
	}
	
	@Override
	protected IEntityDao<Contractnum,Long> getEntityDao() 
	{
		return dao;
	}
	/**
	 * 获取可用的合同编号
	 * @param contracttype
	 * @return
	 */
	public Contractnum getByContracttype(String contracttype,String year){
		Map<String,Object> params=new HashMap<>();
		params.put("contracttype", contracttype);
		params.put("year", year);
		List<Contractnum> bySqlKey = dao.getBySqlKey("getByContracttype", params);
		if(bySqlKey!=null && bySqlKey.size()>0)
			return bySqlKey.get(0);
		return null;
	}
	
	public void delByYearAndNum(String year,String num,String contracttype){
		Map<String,Object> params=new HashMap<>();
		params.put("contracttype", contracttype);
		params.put("year", year);
		params.put("num", num);
		dao.getBySqlKey("delByYearAndNum", params);
	}
	
	public void addByContractnum(String contractnum,String contracttype){
		if(StringUtils.isNotEmpty(contractnum)){
			String[] split = contractnum.trim().split("-");
			if(split.length==2){
				String s1 = split[0].trim();//年份
				String s2 = split[1].trim();//编号
				s1 = s1.replaceAll("[^(0-9)]", "");
				Map<String,Object> params=new HashMap<>();
				params.put("contractNum", contractnum);
				List<Contractnum> list = dao.getBySqlKey("getByContractNum", params);
				if(list!=null && list.size()>0) {
					Contractnum entity = list.get(0);
					entity.setIsdelete(0);
					dao.update(entity);
				} else if(StringUtils.isNumeric(s1) && StringUtils.isNumeric(s2)){
					Contractnum entity=new Contractnum();
					entity.setContractNum(contractnum);
					entity.setNum(Integer.valueOf(Integer.valueOf(s2)).toString());
					entity.setYear(s1);
					entity.setContracttype(contracttype);
					entity.setCtime(new Date());
					entity.setIsdelete(0);
					add(entity);
				}
			}
		}
	}
	
	/**
	 * 获取流程中除了草稿状态、除了指定runid外已存在的表单数据
	 * @param params
	 * @return
	 */
	public long getBpmDataCountByContractnum(String contractNum,Long runid){
		Map<String,Object> params=new HashMap<>();
		params.put("contractNum", contractNum);
		params.put("runid", runid);
		long one = (long)dao.getOne("getBpmDataCountByContractnum", params);
		return one;
	}
	
	public void updateContractnum(String contractNum){
		Map<String,Object> params=new HashMap<>();
		params.put("contractNum", contractNum);
		dao.getBySqlKey("updateContractnum", params);
	}
	
	public void updateContractFlowNo(String contractNum,String contracttype){
		Map<String,Object> params=new HashMap<>();
		params.put("contractNum", contractNum);
		params.put("contracttype", contracttype);
		dao.getBySqlKey("updateContractFlowNo", params);
	}
	/**
	 * 矫正合同编号相关数据
	 * @param contractnum
	 * @param contracttype
	 */
	public void adjustContractnum(String contractnum, String contracttype) {
		updateContractnum(contractnum);
		updateContractFlowNo(contractnum, contracttype);
	}
}
