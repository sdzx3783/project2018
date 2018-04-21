package com.hotent.makshi.model.operation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:车辆登记 Model对象
 */
public class CarRegist extends BaseModel
{

	//主键
	protected Long id;
	/**
	 *车牌号
	 */
	protected String  carId;
	/**
	 *车辆型号
	 */
	protected String  version;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *项目部ID
	 */
	protected String  departmentID;
	/**
	 *保管人ID
	 */
	protected String  kepperID;
	/**
	 *加油卡
	 */
	protected String  oil_card;
	/**
	 *项目部
	 */
	protected String  department;
	/**
	 *保管人
	 */
	protected String  kepper;
	/**
	 *粤通卡
	 */
	protected String  pass_card;
	/**
	 *状态
	 */
	protected String  status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCarId(String carId) 
	{
		this.carId = carId;
	}
	/**
	 * 返回 车牌号
	 * @return
	 */
	public String getCarId() 
	{
		return this.carId;
	}
	public void setVersion(String version) 
	{
		this.version = version;
	}
	/**
	 * 返回 车辆型号
	 * @return
	 */
	public String getVersion() 
	{
		return this.version;
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
	public void setDepartmentID(String departmentID) 
	{
		this.departmentID = departmentID;
	}
	/**
	 * 返回 项目部ID
	 * @return
	 */
	public String getDepartmentID() 
	{
		return this.departmentID;
	}
	public void setKepperID(String kepperID) 
	{
		this.kepperID = kepperID;
	}
	/**
	 * 返回 保管人ID
	 * @return
	 */
	public String getKepperID() 
	{
		return this.kepperID;
	}
	public void setOil_card(String oil_card) 
	{
		this.oil_card = oil_card;
	}
	/**
	 * 返回 加油卡
	 * @return
	 */
	public String getOil_card() 
	{
		return this.oil_card;
	}
	public void setDepartment(String department) 
	{
		this.department = department;
	}
	/**
	 * 返回 项目部
	 * @return
	 */
	public String getDepartment() 
	{
		return this.department;
	}
	public void setKepper(String kepper) 
	{
		this.kepper = kepper;
	}
	/**
	 * 返回 保管人
	 * @return
	 */
	public String getKepper() 
	{
		return this.kepper;
	}
	public void setPass_card(String pass_card) 
	{
		this.pass_card = pass_card;
	}
	/**
	 * 返回 粤通卡
	 * @return
	 */
	public String getPass_card() 
	{
		return this.pass_card;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof CarRegist)) 
		{
			return false;
		}
		CarRegist rhs = (CarRegist) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.carId, rhs.carId)
		.append(this.version, rhs.version)
		.append(this.attachment, rhs.attachment)
		.append(this.departmentID, rhs.departmentID)
		.append(this.kepperID, rhs.kepperID)
		.append(this.oil_card, rhs.oil_card)
		.append(this.department, rhs.department)
		.append(this.kepper, rhs.kepper)
		.append(this.pass_card, rhs.pass_card)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.carId) 
		.append(this.version) 
		.append(this.attachment) 
		.append(this.departmentID) 
		.append(this.kepperID) 
		.append(this.oil_card) 
		.append(this.department) 
		.append(this.kepper) 
		.append(this.pass_card) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("carId", this.carId) 
		.append("version", this.version) 
		.append("attachment", this.attachment) 
		.append("departmentID", this.departmentID) 
		.append("kepperID", this.kepperID) 
		.append("oil_card", this.oil_card) 
		.append("department", this.department) 
		.append("kepper", this.kepper) 
		.append("pass_card", this.pass_card) 
		.toString();
	}
}