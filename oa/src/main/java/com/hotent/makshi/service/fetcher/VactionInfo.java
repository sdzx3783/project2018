package com.hotent.makshi.service.fetcher;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VactionInfo implements RowMapper<VactionInfo>, Serializable{
	private Long userid;
	private Double leavedays;//请假天数
	private String leavetype;//请假类型
	private Integer needActual;//需要确认销假
	private Double actualleavedays;//确认请假天数
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Double getLeavedays() {
		return leavedays;
	}
	public void setLeavedays(Double leavedays) {
		this.leavedays = leavedays;
	}
	public String getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
	}
	public Integer getNeedActual() {
		return needActual;
	}
	public void setNeedActual(Integer needActual) {
		this.needActual = needActual;
	}
	public Double getActualleavedays() {
		return actualleavedays;
	}
	public void setActualleavedays(Double actualleavedays) {
		this.actualleavedays = actualleavedays;
	}
	@Override
	public VactionInfo mapRow(ResultSet rs, int arg1) throws SQLException {
		if(rs==null){
			return null;
		}else{
			VactionInfo info=new VactionInfo();
			info.setUserid(rs.getString("F_user_num")==null?
					null:Long.valueOf(rs.getString("F_user_num")));
			info.setLeavedays(rs.getString("F_leave_days")==null?
					null:Double.valueOf(rs.getString("F_leave_days")));
			info.setNeedActual(rs.getString("F_needActual")==null?
					null:Integer.valueOf(rs.getString("F_needActual")));
			info.setActualleavedays(rs.getString("F_actualleave_days")==null?
					null:Double.valueOf(rs.getString("F_actualleave_days")));
			info.setLeavetype(rs.getString("F_leave_type"));
			return info;
		}
	}
	
}
