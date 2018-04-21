package com.hotent.makshi.model.contract;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:合同编号管理 Model对象
 */
public class ContractNumYear extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1463057470741021321L;
	//主键
	protected Long id;
	protected String year;
	protected Long  contractId;
	protected String  flowNo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Long getContractId() {
		return contractId;
	}
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	public String getFlowNo() {
		return flowNo;
	}
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	

}