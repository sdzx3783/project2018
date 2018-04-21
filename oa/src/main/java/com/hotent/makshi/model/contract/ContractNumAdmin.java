package com.hotent.makshi.model.contract;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
import com.hotent.makshi.model.contract.ContractNumSecond;
/**
 * 对象功能:合同编号管理 Model对象
 */
public class ContractNumAdmin extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *序号
	 */
	protected String  contractNo;
	/**
	 *合同类型
	 */
	protected String  type;
	/**
	 *合同编号
	 */
	protected String  contract_num;
	/**
	 *流水号
	 */
	protected String  flowNo;
	
	/**
	 * 年份
	 */
	protected String year;
	/**
	 *合同编号管理列表
	 */
	protected List<ContractNumSecond> contractNumSecondList=new ArrayList<ContractNumSecond>();
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setContractNo(String contractNo) 
	{
		this.contractNo = contractNo;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public String getContractNo() 
	{
		return this.contractNo;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	/**
	 * 返回 合同类型
	 * @return
	 */
	public String getType() 
	{
		return this.type;
	}
	public void setContract_num(String contract_num) 
	{
		this.contract_num = contract_num;
	}
	/**
	 * 返回 合同编号
	 * @return
	 */
	public String getContract_num() 
	{
		return this.contract_num;
	}
	public void setFlowNo(String flowNo) 
	{
		this.flowNo = flowNo;
	}
	/**
	 * 返回 流水号
	 * @return
	 */
	public String getFlowNo() 
	{
		return this.flowNo;
	}
	public void setContractNumSecondList(List<ContractNumSecond> contractNumSecondList) 
	{
		this.contractNumSecondList = contractNumSecondList;
	}
	/**
	 * 返回 合同编号管理列表
	 * @return
	 */
	public List<ContractNumSecond> getContractNumSecondList() 
	{
		return this.contractNumSecondList;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof ContractNumAdmin)) 
		{
			return false;
		}
		ContractNumAdmin rhs = (ContractNumAdmin) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.contractNo, rhs.contractNo)
		.append(this.type, rhs.type)
		.append(this.contract_num, rhs.contract_num)
		.append(this.flowNo, rhs.flowNo)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.contractNo) 
		.append(this.type) 
		.append(this.contract_num) 
		.append(this.flowNo) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("contractNo", this.contractNo) 
		.append("type", this.type) 
		.append("contract_num", this.contract_num) 
		.append("flowNo", this.flowNo) 
		.toString();
	}

}