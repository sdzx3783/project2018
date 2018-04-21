package com.hotent.makshi.model.seal;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:部门公章使用流程 Model对象
 */
public class DepartmentalSeal extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *申请人ID
	 */
	protected String  applyerID;
	/**
	 *项目负责人ID
	 */
	protected String  project_leaderID;
	/**
	 *工号
	 */
	protected String  job_id;
	/**
	 *申请人
	 */
	protected String  applyer;
	/**
	 *类型
	 */
	protected String  type;
	/**
	 *使用时间
	 */
	protected java.util.Date  use_time;
	/**
	 *合同编号
	 */
	protected String  contract_id;
	/**
	 *合同名称
	 */
	protected String  contract_name;
	/**
	 *材料内容
	 */
	protected String  material_content;
	/**
	 *份数
	 */
	protected Long  copies;
	/**
	 *附件
	 */
	protected String  attachment;
	/**
	 *项目负责人
	 */
	protected String  project_leader;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setApplyerID(String applyerID) 
	{
		this.applyerID = applyerID;
	}
	/**
	 * 返回 申请人ID
	 * @return
	 */
	public String getApplyerID() 
	{
		return this.applyerID;
	}
	public void setProject_leaderID(String project_leaderID) 
	{
		this.project_leaderID = project_leaderID;
	}
	/**
	 * 返回 项目负责人ID
	 * @return
	 */
	public String getProject_leaderID() 
	{
		return this.project_leaderID;
	}
	public void setJob_id(String job_id) 
	{
		this.job_id = job_id;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getJob_id() 
	{
		return this.job_id;
	}
	public void setApplyer(String applyer) 
	{
		this.applyer = applyer;
	}
	/**
	 * 返回 申请人
	 * @return
	 */
	public String getApplyer() 
	{
		return this.applyer;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setUse_time(java.util.Date use_time) 
	{
		this.use_time = use_time;
	}
	/**
	 * 返回 使用时间
	 * @return
	 */
	public java.util.Date getUse_time() 
	{
		return this.use_time;
	}
	public void setContract_id(String contract_id) 
	{
		this.contract_id = contract_id;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getContract_id() 
	{
		return this.contract_id;
	}
	public void setContract_name(String contract_name) 
	{
		this.contract_name = contract_name;
	}
	/**
	 * 返回 合同名称
	 * @return
	 */
	public String getContract_name() 
	{
		return this.contract_name;
	}
	public void setMaterial_content(String material_content) 
	{
		this.material_content = material_content;
	}
	/**
	 * 返回 材料内容
	 * @return
	 */
	public String getMaterial_content() 
	{
		return this.material_content;
	}
	public void setCopies(Long copies) 
	{
		this.copies = copies;
	}
	/**
	 * 返回 份数
	 * @return
	 */
	public Long getCopies() 
	{
		return this.copies;
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
	public void setProject_leader(String project_leader) 
	{
		this.project_leader = project_leader;
	}
	/**
	 * 返回 项目负责人
	 * @return
	 */
	public String getProject_leader() 
	{
		return this.project_leader;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof DepartmentalSeal)) 
		{
			return false;
		}
		DepartmentalSeal rhs = (DepartmentalSeal) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.applyerID, rhs.applyerID)
		.append(this.project_leaderID, rhs.project_leaderID)
		.append(this.job_id, rhs.job_id)
		.append(this.applyer, rhs.applyer)
		.append(this.type, rhs.type)
		.append(this.use_time, rhs.use_time)
		.append(this.contract_id, rhs.contract_id)
		.append(this.contract_name, rhs.contract_name)
		.append(this.material_content, rhs.material_content)
		.append(this.copies, rhs.copies)
		.append(this.attachment, rhs.attachment)
		.append(this.project_leader, rhs.project_leader)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.applyerID) 
		.append(this.project_leaderID) 
		.append(this.job_id) 
		.append(this.applyer) 
		.append(this.type) 
		.append(this.use_time) 
		.append(this.contract_id) 
		.append(this.contract_name) 
		.append(this.material_content) 
		.append(this.copies) 
		.append(this.attachment) 
		.append(this.project_leader) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("applyerID", this.applyerID) 
		.append("project_leaderID", this.project_leaderID) 
		.append("job_id", this.job_id) 
		.append("applyer", this.applyer) 
		.append("type", this.type) 
		.append("use_time", this.use_time) 
		.append("contract_id", this.contract_id) 
		.append("contract_name", this.contract_name) 
		.append("material_content", this.material_content) 
		.append("copies", this.copies) 
		.append("attachment", this.attachment) 
		.append("project_leader", this.project_leader) 
		.toString();
	}

}