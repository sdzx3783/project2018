package com.hotent.makshi.model.assettransfer;

import java.util.Date;
import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:资产移交表 Model对象
 */
public class AssetTransfer extends WfBaseModel
{
	//主键
	protected Long id;
	protected String  transfer_person_id;
	protected String  application_Person;
	protected String  account;
	protected String  transfer_person;
	protected String  transfer_first_department;
	protected String  transfer_first_departmentid;
	protected String  receiption_person;
	protected String  receiption_personID;
	protected String receiption_department;
	protected String  receiption_departmentID;
	protected String  asset_id;
	protected String  asset_name;
	protected String  specifications;
	protected Integer  number;
	protected String  reason;
	protected Date  time;
	protected String  attachment;
	protected Date  application_time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTransfer_person_id() {
		return transfer_person_id;
	}
	public void setTransfer_person_id(String transfer_person_id) {
		this.transfer_person_id = transfer_person_id;
	}
	public String getApplication_Person() {
		return application_Person;
	}
	public void setApplication_Person(String application_Person) {
		this.application_Person = application_Person;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTransfer_person() {
		return transfer_person;
	}
	public void setTransfer_person(String transfer_person) {
		this.transfer_person = transfer_person;
	}
	public String getTransfer_first_department() {
		return transfer_first_department;
	}
	public void setTransfer_first_department(String transfer_first_department) {
		this.transfer_first_department = transfer_first_department;
	}
	public String getTransfer_first_departmentid() {
		return transfer_first_departmentid;
	}
	public void setTransfer_first_departmentid(String transfer_first_departmentid) {
		this.transfer_first_departmentid = transfer_first_departmentid;
	}
	public String getReceiption_person() {
		return receiption_person;
	}
	public void setReceiption_person(String receiption_person) {
		this.receiption_person = receiption_person;
	}
	public String getReceiption_personID() {
		return receiption_personID;
	}
	public void setReceiption_personID(String receiption_personID) {
		this.receiption_personID = receiption_personID;
	}
	public String getReceiption_department() {
		return receiption_department;
	}
	public void setReceiption_department(String receiption_department) {
		this.receiption_department = receiption_department;
	}
	public String getReceiption_departmentID() {
		return receiption_departmentID;
	}
	public void setReceiption_departmentID(String receiption_departmentID) {
		this.receiption_departmentID = receiption_departmentID;
	}
	public String getAsset_id() {
		return asset_id;
	}
	public void setAsset_id(String asset_id) {
		this.asset_id = asset_id;
	}
	public String getAsset_name() {
		return asset_name;
	}
	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Date getApplication_time() {
		return application_time;
	}
	public void setApplication_time(Date application_time) {
		this.application_time = application_time;
	} 

	
}