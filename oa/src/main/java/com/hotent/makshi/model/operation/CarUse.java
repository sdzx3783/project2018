package com.hotent.makshi.model.operation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:大车使用申请 Model对象
 */
public class CarUse extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
	 */
	protected String  applicantID;
	/**
	 *安排司机ID
	 */
	protected String  driverID;
	/**
	 *申请车辆
	 */
	protected String  applicar;
	/**
	 *申请人
	 */
	protected String  applicant;
	/**
	 *申请时间
	 */
	protected java.util.Date  appliDate;
	/**
	 *使用地点
	 */
	protected String  address;
	/**
	 *是否需要下井
	 */
	protected String  down;
	/**
	 *使用开始时间
	 */
	protected java.util.Date  startTime;
	/**
	 *使用结束时间
	 */
	protected java.util.Date  endTime;
	/**
	 *用车原因
	 */
	protected String  reason;
	/**
	 *安排车辆
	 */
	protected String  carName;
	
	protected String  carNameId;
	/**
	 *安排司机
	 */
	protected String  driver;
	/**
	 *附件
	 */
	protected String  attachment;
	
	
	
	public String getCarNameId() {
		return carNameId;
	}
	public void setCarNameId(String carNameId) {
		this.carNameId = carNameId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setApplicantID(String applicantID) 
	{
		this.applicantID = applicantID;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public String getApplicantID() 
	{
		return this.applicantID;
	}
	public void setDriverID(String driverID) 
	{
		this.driverID = driverID;
	}
	/**
	 * 返回 安排司机ID
	 * @return
	 */
	public String getDriverID() 
	{
		return this.driverID;
	}
	public void setApplicar(String applicar) 
	{
		this.applicar = applicar;
	}
	/**
	 * 返回 申请车辆
	 * @return
	 */
	public String getApplicar() 
	{
		return this.applicar;
	}
	public void setApplicant(String applicant) 
	{
		this.applicant = applicant;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplicant() 
	{
		return this.applicant;
	}
	public void setAppliDate(java.util.Date appliDate) 
	{
		this.appliDate = appliDate;
	}
	/**
	 * 返回 申请时间
	 * @return
	 */
	public java.util.Date getAppliDate() 
	{
		return this.appliDate;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	/**
	 * 返回 使用地点
	 * @return
	 */
	public String getAddress() 
	{
		return this.address;
	}
	public void setDown(String down) 
	{
		this.down = down;
	}
	/**
	 * 返回 是否需要下井
	 * @return
	 */
	public String getDown() 
	{
		return this.down;
	}
	public void setStartTime(java.util.Date startTime) 
	{
		this.startTime = startTime;
	}
	/**
	 * 返回 使用开始时间
	 * @return
	 */
	public java.util.Date getStartTime() 
	{
		return this.startTime;
	}
	public void setEndTime(java.util.Date endTime) 
	{
		this.endTime = endTime;
	}
	/**
	 * 返回 使用结束时间
	 * @return
	 */
	public java.util.Date getEndTime() 
	{
		return this.endTime;
	}
	public void setReason(String reason) 
	{
		this.reason = reason;
	}
	/**
	 * 返回 用车原因
	 * @return
	 */
	public String getReason() 
	{
		return this.reason;
	}
	public void setCarName(String carName) 
	{
		this.carName = carName;
	}
	/**
	 * 返回 安排车辆
	 * @return
	 */
	public String getCarName() 
	{
		return this.carName;
	}
	public void setDriver(String driver) 
	{
		this.driver = driver;
	}
	/**
	 * 返回 安排司机
	 * @return
	 */
	public String getDriver() 
	{
		return this.driver;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 附件
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CarUse)) 
		{
			return false;
		}
		CarUse rhs = (CarUse) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applicantID, rhs.applicantID)
		.append(this.driverID, rhs.driverID)
		.append(this.applicar, rhs.applicar)
		.append(this.applicant, rhs.applicant)
		.append(this.appliDate, rhs.appliDate)
		.append(this.address, rhs.address)
		.append(this.down, rhs.down)
		.append(this.startTime, rhs.startTime)
		.append(this.endTime, rhs.endTime)
		.append(this.reason, rhs.reason)
		.append(this.carName, rhs.carName)
		.append(this.driver, rhs.driver)
		.append(this.attachment, rhs.attachment)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.applicantID) 
		.append(this.driverID) 
		.append(this.applicar) 
		.append(this.applicant) 
		.append(this.appliDate) 
		.append(this.address) 
		.append(this.down) 
		.append(this.startTime) 
		.append(this.endTime) 
		.append(this.reason) 
		.append(this.carName) 
		.append(this.driver) 
		.append(this.attachment) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("applicantID", this.applicantID) 
		.append("driverID", this.driverID) 
		.append("applicar", this.applicar) 
		.append("applicant", this.applicant) 
		.append("appliDate", this.appliDate) 
		.append("address", this.address) 
		.append("down", this.down) 
		.append("startTime", this.startTime) 
		.append("endTime", this.endTime) 
		.append("reason", this.reason) 
		.append("carName", this.carName) 
		.append("driver", this.driver) 
		.append("attachment", this.attachment) 
		.toString();
	}

}