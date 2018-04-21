package com.hotent.makshi.model.vacation;

import java.util.Date;
import java.util.List;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.model.BaseModel;
import com.hotent.core.util.AppUtil;
import com.hotent.makshi.service.vacation.CompanyYearVactionService;
import com.hotent.makshi.utils.DateUtils;
import com.hotent.platform.model.system.SysUser;
import com.hotent.platform.model.worktime.Vacation;
import com.hotent.platform.service.system.SysUserService;

public class AnuualLeave  extends BaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -9168554329297111115L;

	private Long id;

    private Long userid;

    private Double leaveLastyear;

    private Double leaveCurrentyear;
    
    private Double hasleaveCurrentyear;//今年已休年假
    
    private String currentyear;

    private Date ctime;

    private Date utime;

    private Double isadd;//负值代表：去年剩余年假-今年公司发放年假 仍然大于0 用于第二年年初继续销掉这一年已请的年假hasleaveCurrentyear
    
    
    
    public AnuualLeave() {
		super();
	}

	public AnuualLeave(Double leaveLastyear, Double leaveCurrentyear, Double hasleaveCurrentyear) {
		super();
		this.leaveLastyear = leaveLastyear;
		this.leaveCurrentyear = leaveCurrentyear;
		this.hasleaveCurrentyear = hasleaveCurrentyear;
	}
	public AnuualLeave(Double leaveLastyear, Double leaveCurrentyear, Double hasleaveCurrentyear,Double isadd) {
		super();
		this.leaveLastyear = leaveLastyear;
		this.leaveCurrentyear = leaveCurrentyear;
		this.hasleaveCurrentyear = hasleaveCurrentyear;
		this.isadd=isadd;
	}

	public Double getActualLeaveVacation() {
		CompanyYearVactionService bean = AppUtil.getBean(com.hotent.makshi.service.vacation.CompanyYearVactionService.class);
		SysUserService userService = AppUtil.getBean(com.hotent.platform.service.system.SysUserService.class);
		
		Date now=new Date();
		String year = DateUtils.getYear(now);
		CompanyYearVaction byYear = bean.getByYear(year);
		double temp=0.0;
		if(byYear!=null){
			temp=byYear.getDays();
		}
		if(userid==null){
			return 0d;
		}
		SysUser byId = userService.getById(userid);
		Date entryDate = byId.getEntryDate();
		List<Vacation> vactionByYearAndName = bean.getVactionByYearAndName(year, "年假");
		Date lastYearDate=null;//年假最后一天  年假后入职的不扣
		if(vactionByYearAndName!=null && vactionByYearAndName.size()>0){
			for (Vacation vacation : vactionByYearAndName) {
				Date endTime = vacation.getEndTime();
				if(lastYearDate==null){
					lastYearDate=endTime;
				}else if(lastYearDate.before(endTime)){
					lastYearDate=endTime;
				}
			}
			if(lastYearDate!=null && entryDate!=null && entryDate.after(lastYearDate)){
				temp=0.0;
			}
		}
		
		
		//判断今天是不是12月31号  12月31号定时任务:已做年假抵消处理=去年剩余年假-公司发放的年假天数,就不需要减去公司发放的年假了
		String formatDateS = DateUtils.formatDateS(now);
		if(formatDateS.contains("-12-31")){   
			temp=0.0;
		}
		if(isadd<0){
			double diff=(-isadd)-hasleaveCurrentyear;
			if(diff>=0d){
				return leaveCurrentyear-temp;
			}else{
				return leaveCurrentyear+diff-temp;
			}
		}
		return leaveLastyear+leaveCurrentyear-hasleaveCurrentyear-temp;
	}
    
	
    public Double getIsadd() {
		return isadd;
	}

	public void setIsadd(Double isadd) {
		this.isadd = isadd;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHasleaveCurrentyear() {
		return hasleaveCurrentyear;
	}

	public void setHasleaveCurrentyear(Double hasleaveCurrentyear) {
		this.hasleaveCurrentyear = hasleaveCurrentyear;
	}

	public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Double getLeaveLastyear() {
        return leaveLastyear;
    }

    public void setLeaveLastyear(Double leaveLastyear) {
        this.leaveLastyear = leaveLastyear;
    }

    public Double getLeaveCurrentyear() {
        return leaveCurrentyear;
    }

    public void setLeaveCurrentyear(Double leaveCurrentyear) {
        this.leaveCurrentyear = leaveCurrentyear;
    }

    public String getCurrentyear() {
        return currentyear;
    }

    public void setCurrentyear(String currentyear) {
        this.currentyear = currentyear;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}