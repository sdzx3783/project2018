package com.hotent.makshi.model.assetregistration;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:资产登记表 Model对象
 */
public class AssetRegistration extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *使用部门ID
	 */
	protected String  use_departmentID;
	/**
	 *保管人ID
	 */
	protected String  care_personID;
	/**
	 *取得日期
	 */
	protected java.util.Date  get_date;
	/**
	 *使用部门
	 */
	protected String  use_department;
	/**
	 *条码编号
	 */
	protected String  card_number;
	/**
	 *保管人
	 */
	protected String  care_person;
	/**
	 *资产名称
	 */
	protected String  asset_name;
	/**
	 *使用日期
	 */
	protected java.util.Date  use_date;
	/**
	 *规格型号
	 */
	protected String  specification;
	/**
	 *数量
	 */
	protected String  number;
	/**
	 *资产分类代码
	 */
	protected String  asset_code;
	/**
	 *资产编号
	 */
	protected String  asset_id;
	/**
	 *资产类别
	 */
	protected String  asset_type;
	/**
	 *使用状况
	 */
	protected String  state;
	/**
	 *取得方式
	 */
	protected String  get_type;
	/**
	 *上传贴条照片
	 */
	protected String  attachment;
	/**
	 *报废时间
	 */
	protected java.util.Date  abandonment_date;
	/**
	 *变更时间
	 */
	protected String  edit_time;
	/**
	 *资产大类
	 */
	protected String  version;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUse_departmentID(String use_departmentID) 
	{
		this.use_departmentID = use_departmentID;
	}
	/**
	 * 返回 使用部门ID
	 * @return
	 */
	public String getUse_departmentID() 
	{
		return this.use_departmentID;
	}
	public void setCare_personID(String care_personID) 
	{
		this.care_personID = care_personID;
	}
	/**
	 * 返回 保管人ID
	 * @return
	 */
	public String getCare_personID() 
	{
		return this.care_personID;
	}
	public void setGet_date(java.util.Date get_date) 
	{
		this.get_date = get_date;
	}
	/**
	 * 返回 取得日期
	 * @return
	 */
	public java.util.Date getGet_date() 
	{
		return this.get_date;
	}
	public void setUse_department(String use_department) 
	{
		this.use_department = use_department;
	}
	/**
	 * 返回 使用部门
	 * @return
	 */
	public String getUse_department() 
	{
		return this.use_department;
	}
	public void setCard_number(String card_number) 
	{
		this.card_number = card_number;
	}
	/**
	 * 返回 卡片编号
	 * @return
	 */
	public String getCard_number() 
	{
		return this.card_number;
	}
	public void setCare_person(String care_person) 
	{
		this.care_person = care_person;
	}
	/**
	 * 返回 保管人
	 * @return
	 */
	public String getCare_person() 
	{
		return this.care_person;
	}
	public void setAsset_name(String asset_name) 
	{
		this.asset_name = asset_name;
	}
	/**
	 * 返回 资产名称
	 * @return
	 */
	public String getAsset_name() 
	{
		return this.asset_name;
	}
	public void setUse_date(java.util.Date use_date) 
	{
		this.use_date = use_date;
	}
	/**
	 * 返回 使用日期
	 * @return
	 */
	public java.util.Date getUse_date() 
	{
		return this.use_date;
	}
	public void setSpecification(String specification) 
	{
		this.specification = specification;
	}
	/**
	 * 返回 规格型号
	 * @return
	 */
	public String getSpecification() 
	{
		return this.specification;
	}
	public void setNumber(String number) 
	{
		this.number = number;
	}
	/**
	 * 返回 数量
	 * @return
	 */
	public String getNumber() 
	{
		return this.number;
	}
	public void setAsset_code(String asset_code) 
	{
		this.asset_code = asset_code;
	}
	/**
	 * 返回 资产分类代码
	 * @return
	 */
	public String getAsset_code() 
	{
		return this.asset_code;
	}
	public void setAsset_id(String asset_id) 
	{
		this.asset_id = asset_id;
	}
	/**
	 * 返回 资产编号
	 * @return
	 */
	public String getAsset_id() 
	{
		return this.asset_id;
	}
	public void setAsset_type(String asset_type) 
	{
		this.asset_type = asset_type;
	}
	/**
	 * 返回 资产类别
	 * @return
	 */
	public String getAsset_type() 
	{
		return this.asset_type;
	}
	public void setState(String state) 
	{
		this.state = state;
	}
	/**
	 * 返回 使用状况
	 * @return
	 */
	public String getState() 
	{
		return this.state;
	}
	public void setGet_type(String get_type) 
	{
		this.get_type = get_type;
	}
	/**
	 * 返回 取得方式
	 * @return
	 */
	public String getGet_type() 
	{
		return this.get_type;
	}
	public void setAttachment(String attachment) 
	{
		this.attachment = attachment;
	}
	/**
	 * 返回 上传贴条照片
	 * @return
	 */
	public String getAttachment() 
	{
		return this.attachment;
	}
	public void setAbandonment_date(java.util.Date abandonment_date) 
	{
		this.abandonment_date = abandonment_date;
	}
	/**
	 * 返回 报废时间
	 * @return
	 */
	public java.util.Date getAbandonment_date() 
	{
		return this.abandonment_date;
	}
	public void setEdit_time(String edit_time) 
	{
		this.edit_time = edit_time;
	}
	/**
	 * 返回 变更时间
	 * @return
	 */
	public String getEdit_time() 
	{
		return this.edit_time;
	}
	public void setVersion(String version) 
	{
		this.version = version;
	}
	/**
	 * 返回 版本号
	 * @return
	 */
	public String getVersion() 
	{
		return this.version;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof AssetRegistration)) 
		{
			return false;
		}
		AssetRegistration rhs = (AssetRegistration) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.use_departmentID, rhs.use_departmentID)
		.append(this.care_personID, rhs.care_personID)
		.append(this.get_date, rhs.get_date)
		.append(this.use_department, rhs.use_department)
		.append(this.card_number, rhs.card_number)
		.append(this.care_person, rhs.care_person)
		.append(this.asset_name, rhs.asset_name)
		.append(this.use_date, rhs.use_date)
		.append(this.specification, rhs.specification)
		.append(this.number, rhs.number)
		.append(this.asset_code, rhs.asset_code)
		.append(this.asset_id, rhs.asset_id)
		.append(this.asset_type, rhs.asset_type)
		.append(this.state, rhs.state)
		.append(this.get_type, rhs.get_type)
		.append(this.attachment, rhs.attachment)
		.append(this.abandonment_date, rhs.abandonment_date)
		.append(this.edit_time, rhs.edit_time)
		.append(this.version, rhs.version)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.use_departmentID) 
		.append(this.care_personID) 
		.append(this.get_date) 
		.append(this.use_department) 
		.append(this.card_number) 
		.append(this.care_person) 
		.append(this.asset_name) 
		.append(this.use_date) 
		.append(this.specification) 
		.append(this.number) 
		.append(this.asset_code) 
		.append(this.asset_id) 
		.append(this.asset_type) 
		.append(this.state) 
		.append(this.get_type) 
		.append(this.attachment) 
		.append(this.abandonment_date) 
		.append(this.edit_time) 
		.append(this.version) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("use_departmentID", this.use_departmentID) 
		.append("care_personID", this.care_personID) 
		.append("get_date", this.get_date) 
		.append("use_department", this.use_department) 
		.append("card_number", this.card_number) 
		.append("care_person", this.care_person) 
		.append("asset_name", this.asset_name) 
		.append("use_date", this.use_date) 
		.append("specification", this.specification) 
		.append("number", this.number) 
		.append("asset_code", this.asset_code) 
		.append("asset_id", this.asset_id) 
		.append("asset_type", this.asset_type) 
		.append("state", this.state) 
		.append("get_type", this.get_type) 
		.append("attachment", this.attachment) 
		.append("abandonment_date", this.abandonment_date) 
		.append("edit_time", this.edit_time) 
		.append("version", this.version) 
		.toString();
	}

}