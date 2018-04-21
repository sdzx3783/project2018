package com.hotent.makshi.model.waterprotectpark;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:参观登记信息-水保园 Model对象
 */
public class VisitorRegisterInfo extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *负责人ID
	 */
	protected String  chargerID;
	/**
	 *接待员ID
	 */
	protected String  greeterID;
	/**
	 *批次
	 */
	protected String  batch;
	/**
	 *参观日期
	 */
	protected java.util.Date  visitTime;
	/**
	 *团体名称
	 */
	protected String  teamName;
	/**
	 *所属类型
	 */
	protected String  type;
	/**
	 *登记时间
	 */
	protected java.util.Date  registerTime;
	/**
	 *星期
	 */
	protected String  xq;
	/**
	 *负责人
	 */
	protected String  charger;
	/**
	 *联系电话
	 */
	protected String  tel;
	/**
	 *参观人数
	 */
	protected Integer  visitorNum;
	/**
	 *是否来函
	 */
	protected String  isLetter;
	/**
	 *接待员
	 */
	protected String  greeter;
	/**
	 *备注
	 */
	protected String  remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setChargerID(String chargerID) 
	{
		this.chargerID = chargerID;
	}
	/**
	 * 返回 负责人ID
	 * @return
	 */
	public String getChargerID() 
	{
		return this.chargerID;
	}
	public void setGreeterID(String greeterID) 
	{
		this.greeterID = greeterID;
	}
	/**
	 * 返回 接待员ID
	 * @return
	 */
	public String getGreeterID() 
	{
		return this.greeterID;
	}
	public void setBatch(String batch) 
	{
		this.batch = batch;
	}
	/**
	 * 返回 批次
	 * @return
	 */
	public String getBatch() 
	{
		return this.batch;
	}
	public void setVisitTime(java.util.Date visitTime) 
	{
		this.visitTime = visitTime;
	}
	/**
	 * 返回 参观日期
	 * @return
	 */
	public java.util.Date getVisitTime() 
	{
		return this.visitTime;
	}
	public void setTeamName(String teamName) 
	{
		this.teamName = teamName;
	}
	/**
	 * 返回 团体名称
	 * @return
	 */
	public String getTeamName() 
	{
		return this.teamName;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 所属类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setRegisterTime(java.util.Date registerTime) 
	{
		this.registerTime = registerTime;
	}
	/**
	 * 返回 登记时间
	 * @return
	 */
	public java.util.Date getRegisterTime() 
	{
		return this.registerTime;
	}
	public void setXq(String xq) 
	{
		this.xq = xq;
	}
	/**
	 * 返回 星期
	 * @return
	 */
	public String getXq() 
	{
		return this.xq;
	}
	public void setCharger(String charger) 
	{
		this.charger = charger;
	}
	/**
	 * 返回 负责人
	 * @return
	 */
	public String getCharger() 
	{
		return this.charger;
	}
	public void setTel(String tel) 
	{
		this.tel = tel;
	}
	/**
	 * 返回 联系电话
	 * @return
	 */
	public String getTel() 
	{
		return this.tel;
	}
	public void setVisitorNum(Integer visitorNum) 
	{
		this.visitorNum = visitorNum;
	}
	/**
	 * 返回 参观人数
	 * @return
	 */
	public Integer getVisitorNum() 
	{
		return this.visitorNum;
	}
	public void setIsLetter(String isLetter) 
	{
		this.isLetter = isLetter;
	}
	/**
	 * 返回 是否来函
	 * @return
	 */
	public String getIsLetter() 
	{
		return this.isLetter;
	}
	public void setGreeter(String greeter) 
	{
		this.greeter = greeter;
	}
	/**
	 * 返回 接待员
	 * @return
	 */
	public String getGreeter() 
	{
		return this.greeter;
	}
	public void setRemark(String remark) 
	{
		this.remark = remark;
	}
	/**
	 * 返回 备注
	 * @return
	 */
	public String getRemark() 
	{
		return this.remark;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof VisitorRegisterInfo)) 
		{
			return false;
		}
		VisitorRegisterInfo rhs = (VisitorRegisterInfo) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.chargerID, rhs.chargerID)
		.append(this.greeterID, rhs.greeterID)
		.append(this.batch, rhs.batch)
		.append(this.visitTime, rhs.visitTime)
		.append(this.teamName, rhs.teamName)
		.append(this.type, rhs.type)
		.append(this.registerTime, rhs.registerTime)
		.append(this.xq, rhs.xq)
		.append(this.charger, rhs.charger)
		.append(this.tel, rhs.tel)
		.append(this.visitorNum, rhs.visitorNum)
		.append(this.isLetter, rhs.isLetter)
		.append(this.greeter, rhs.greeter)
		.append(this.remark, rhs.remark)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.chargerID) 
		.append(this.greeterID) 
		.append(this.batch) 
		.append(this.visitTime) 
		.append(this.teamName) 
		.append(this.type) 
		.append(this.registerTime) 
		.append(this.xq) 
		.append(this.charger) 
		.append(this.tel) 
		.append(this.visitorNum) 
		.append(this.isLetter) 
		.append(this.greeter) 
		.append(this.remark) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("chargerID", this.chargerID) 
		.append("greeterID", this.greeterID) 
		.append("batch", this.batch) 
		.append("visitTime", this.visitTime) 
		.append("teamName", this.teamName) 
		.append("type", this.type) 
		.append("registerTime", this.registerTime) 
		.append("xq", this.xq) 
		.append("charger", this.charger) 
		.append("tel", this.tel) 
		.append("visitorNum", this.visitorNum) 
		.append("isLetter", this.isLetter) 
		.append("greeter", this.greeter) 
		.append("remark", this.remark) 
		.toString();
	}

}