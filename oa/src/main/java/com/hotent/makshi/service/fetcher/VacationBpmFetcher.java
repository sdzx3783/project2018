/**
 * 
 */
package com.hotent.makshi.service.fetcher;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hotent.makshi.service.vacation.AnuualLeaveService;

/**
 * @author sammy
 *
 */
@Component("vactionBpmFetcher")
public class VacationBpmFetcher implements IFetcher {
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	private static Logger logger=Logger.getLogger(VacationBpmFetcher.class);
	
	@Resource
	private AnuualLeaveService anuualLeaveService;
	/**
	 * 添加请假流程数据到缺勤表
	 */
	@Override
	public void fetcheData(String tableName, String businessKey) {
		logger.info("--------请假流程年假销假开始---------------");
		try{
			String sql="SELECT F_user_num,F_leave_days,F_leave_type,F_needActual,F_actualleave_days  from w_vaction where id="+Long.valueOf(businessKey);
			VactionInfo vaction = jdbcTemplate.queryForObject(sql,new VactionInfo());
			if(vaction!=null && vaction.getUserid()!=null
					&& "年假".equals(vaction.getLeavetype())){
				Integer needActual = vaction.getNeedActual();
				Double actualleavedays =0.0d;
				if(needActual!=null && 1==needActual){
					actualleavedays=vaction.getActualleavedays();
				}else{
					actualleavedays=vaction.getLeavedays();
				}
				if(actualleavedays>0){
					String sqltemp="UPDATE w_anuual_leave set hasleaveCurrentyear=hasleaveCurrentyear+"+actualleavedays+",utime=NOW() "
							+" where userid="+vaction.getUserid();
					jdbcTemplate.update(sqltemp);
				}
			}
			
			//触发年假计算事件
			if(vaction!=null && vaction.getUserid()!=null){
				anuualLeaveService.calculateAnuualVacation(vaction.getUserid());
			}
			logger.info("--------请假流程年假销假结束---------------");
		}catch (Exception e) {
			logger.error("请假流程年假销假异常  businessKey="+businessKey,e);
		}
	}
}
