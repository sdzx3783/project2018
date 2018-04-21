package com.hotent.makshi.service.finance;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hotent.core.db.IEntityDao;
import com.hotent.core.service.BaseService;
import com.hotent.core.util.StringUtil;
import com.hotent.core.util.UniqueIdUtil;
import com.hotent.makshi.dao.finance.ProjectTaskHourDao;
import com.hotent.makshi.dao.finance.UserWorkCostDao;
import com.hotent.makshi.model.finance.ProjectTaskHour;
import com.hotent.makshi.model.finance.UserWorkCost;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;


@Service
public class UserWorkCostService extends BaseService<UserWorkCost>
{
	@Resource
	private UserWorkCostDao dao;
	@Resource
	private ProjectTaskHourDao projectTaskHourDao;	
	public UserWorkCostService()
	{
	}
	
	@Override
	protected IEntityDao<UserWorkCost,Long> getEntityDao() 
	{
		return dao;
	}
	

	
	/**
	 * 根据json字符串获取UserWorkCost对象
	 * @param json
	 * @return
	 */
	public UserWorkCost getUserWorkCost(String json){
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher((new String[] { "yyyy-MM-dd" })));
		if(StringUtil.isEmpty(json))return null;
		JSONObject obj = JSONObject.fromObject(json);
		UserWorkCost userWorkCost = (UserWorkCost)JSONObject.toBean(obj, UserWorkCost.class);
		return userWorkCost;
	}
	/**
	 * 保存 员工工时成本 信息
	 * @param userWorkCost
	 */

	public void save(UserWorkCost userWorkCost) throws Exception{
		Long id=userWorkCost.getId();
		if(id==null || id==0){
			id=UniqueIdUtil.genId();
			userWorkCost.setId(id);
		    this.add(userWorkCost);
		}
		else{
		    this.update(userWorkCost);
		}
	}


	public List<UserWorkCost> getByUserIdAndMonth(Long userId, String month) {
		Map<String,Object> map=new HashMap<>();
		map.put("userid", userId);
		map.put("month", month);
		return dao.getBySqlKey("getByUserIdAndMonth", map);
	}


	public double getYearCostByUserIdAndMon(String proid,String userid, String queryMonth) {
		String[] split = queryMonth.split("-");
		int mon=Integer.valueOf(split[1]);
		double cnt=0d;
		for(int i=1;i<=mon;i++){
			String yearmon=split[0]+"-"+String.format("%02d",i);
			Map<String, Object> filter=new HashMap<>();
			filter.put("userid", userid);
			filter.put("queryProid", proid);
			filter.put("queryTime", yearmon);
			List<ProjectTaskHour> queryProHour = projectTaskHourDao.queryProHour(filter);
			if(queryProHour!=null && queryProHour.size()>0){
				ProjectTaskHour projectTaskHour = queryProHour.get(0);
				List<UserWorkCost> list = getByUserIdAndMonth(Long.valueOf(userid),yearmon);
				if(list!=null && list.size()>0){
					UserWorkCost userWorkCost = list.get(0);
					String workcost = String.valueOf(userWorkCost.getWork_hour_cost());
					String overcost = String.valueOf(userWorkCost.getOver_hour_cost());
					String workhour = projectTaskHour.getWorkcount();
					String overhour = projectTaskHour.getOvercount();
					double cal = cal(workhour, overhour, workcost, overcost);
					cnt+=cal;
				}else{
					continue;
				}
			}else{
				continue;
			}
		}
		return cnt;
	}
	
	
	private double cal(String workhour, String overhour, String workcost, String overcost) {
		double wh=0;
		double oh=0;
		double wc=0;
		double oo=0;
		try {
			wh=Double.valueOf(workhour);
		} catch (NumberFormatException e) {
		}
		try {
			oh=Double.valueOf(overhour);
		} catch (NumberFormatException e) {
		}
		try {
			wc=Double.valueOf(workcost);
		} catch (NumberFormatException e) {
		}
		try {
			oo=Double.valueOf(overcost);
		} catch (NumberFormatException e) {
		}
		double val=wh*wc+oh*oo;
		return val;
	}
	/**
	 * 获取某月某个项目某个人的成本
	 * @param proid
	 * @param userid
	 * @param yearmon
	 * @return
	 */
	public Double getMonthProCostByUserIdAndMon(String proid, String userid, String yearmon) {
		double d=0d;
		Map<String, Object> filter=new HashMap<>();
		filter.put("userid", userid);
		filter.put("queryProid", proid);
		filter.put("queryTime", yearmon);
		List<ProjectTaskHour> queryProHour = projectTaskHourDao.queryProHour(filter);
		if(queryProHour!=null && queryProHour.size()>0){
			ProjectTaskHour projectTaskHour = queryProHour.get(0);
			List<UserWorkCost> list = getByUserIdAndMonth(Long.valueOf(userid),yearmon);
			if(list!=null && list.size()>0){
				UserWorkCost userWorkCost = list.get(0);
				String workcost = String.valueOf(userWorkCost.getWork_hour_cost());
				String overcost = String.valueOf(userWorkCost.getOver_hour_cost());
				String workhour = projectTaskHour.getWorkcount();
				String overhour = projectTaskHour.getOvercount();
				d = cal(workhour, overhour, workcost, overcost);
			}
		}
		return d;
	}
}
