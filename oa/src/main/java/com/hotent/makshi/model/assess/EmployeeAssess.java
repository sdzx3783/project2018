package com.hotent.makshi.model.assess;

import java.util.Date;

import com.hotent.makshi.utils.ArithmeticUtil;

public class EmployeeAssess {

	private String evalAt;

	public String getEvalAt() {
		return evalAt;
	}

	public void setEvalAt(String evalAt) {
		this.evalAt = evalAt;
	}

	private Double total;

	public Double getTotal() {
		total = Double.valueOf(self_total) + Double.valueOf(depart_total) + Double.valueOf(leader_total);
		return ArithmeticUtil.round(total, 2);
	}

	private Long id;

	private String employee;

	private String employeeID;

	private String department;

	private String departmentID;

	private String post_name;

	private Date evaluation_at;

	private Short self_zy_self1;

	private String self_zy_depart1;

	private String self_zy_leader1;

	private String self_zy_self2;

	private String self_zy_depart2;

	private String self_zy_leader2;

	private String self_zy_self3;

	private String self_zy_depart3;

	private String self_zy_leader3;

	private String self_zy_self4;

	private String self_zy_depart4;

	private String self_zy_leader4;

	private Short self_td_self1;

	private String self_td_depart1;

	private String self_td_leader1;

	private String self_td_self2;

	private String self_td_depart2;

	private String self_td_leader2;

	private String self_td_self3;

	private String self_td_depart3;

	private String self_td_leader3;

	private String self_td_self4;

	private String self_td_depart4;

	private String self_td_leader4;

	private Short self_nl_self1;

	private String self_nl_depart1;

	private String self_nl_leader1;

	private String self_nl_self2;

	private String self_nl_depart2;

	private String self_nl_leader2;

	private String self_nl_self3;

	private String self_nl_depart3;

	private String self_nl_leader3;

	private String self_nl_self4;

	private String self_nl_depart4;

	private String self_nl_leader4;

	private Short self_xg_self1;

	private String self_xg_depart1;

	private String self_xg_leader1;

	private String self_xg_self2;

	private String self_xg_depart2;

	private String self_xg_leader2;

	private String self_xg_self3;

	private String self_xg_depart3;

	private String self_xg_leader3;

	private String self_xg_self4;

	private String self_xg_depart4;

	private String self_xg_leader4;

	private String self_total;

	private String depart_total;

	private String leader_total;

	private String bussinessDataId;

	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public String getPost_name() {
		return post_name;
	}

	public void setPost_name(String post_name) {
		this.post_name = post_name;
	}

	public Date getEvaluation_at() {
		return evaluation_at;
	}

	public void setEvaluation_at(Date evaluation_at) {
		this.evaluation_at = evaluation_at;
	}

	public Short getSelf_zy_self1() {
		return self_zy_self1;
	}

	public void setSelf_zy_self1(Short self_zy_self1) {
		this.self_zy_self1 = self_zy_self1;
	}

	public String getSelf_zy_depart1() {
		return self_zy_depart1;
	}

	public void setSelf_zy_depart1(String self_zy_depart1) {
		this.self_zy_depart1 = self_zy_depart1;
	}

	public String getSelf_zy_leader1() {
		return self_zy_leader1;
	}

	public void setSelf_zy_leader1(String self_zy_leader1) {
		this.self_zy_leader1 = self_zy_leader1;
	}

	public String getSelf_zy_self2() {
		return self_zy_self2;
	}

	public void setSelf_zy_self2(String self_zy_self2) {
		this.self_zy_self2 = self_zy_self2;
	}

	public String getSelf_zy_depart2() {
		return self_zy_depart2;
	}

	public void setSelf_zy_depart2(String self_zy_depart2) {
		this.self_zy_depart2 = self_zy_depart2;
	}

	public String getSelf_zy_leader2() {
		return self_zy_leader2;
	}

	public void setSelf_zy_leader2(String self_zy_leader2) {
		this.self_zy_leader2 = self_zy_leader2;
	}

	public String getSelf_zy_self3() {
		return self_zy_self3;
	}

	public void setSelf_zy_self3(String self_zy_self3) {
		this.self_zy_self3 = self_zy_self3;
	}

	public String getSelf_zy_depart3() {
		return self_zy_depart3;
	}

	public void setSelf_zy_depart3(String self_zy_depart3) {
		this.self_zy_depart3 = self_zy_depart3;
	}

	public String getSelf_zy_leader3() {
		return self_zy_leader3;
	}

	public void setSelf_zy_leader3(String self_zy_leader3) {
		this.self_zy_leader3 = self_zy_leader3;
	}

	public String getSelf_zy_self4() {
		return self_zy_self4;
	}

	public void setSelf_zy_self4(String self_zy_self4) {
		this.self_zy_self4 = self_zy_self4;
	}

	public String getSelf_zy_depart4() {
		return self_zy_depart4;
	}

	public void setSelf_zy_depart4(String self_zy_depart4) {
		this.self_zy_depart4 = self_zy_depart4;
	}

	public String getSelf_zy_leader4() {
		return self_zy_leader4;
	}

	public void setSelf_zy_leader4(String self_zy_leader4) {
		this.self_zy_leader4 = self_zy_leader4;
	}

	public Short getSelf_td_self1() {
		return self_td_self1;
	}

	public void setSelf_td_self1(Short self_td_self1) {
		this.self_td_self1 = self_td_self1;
	}

	public String getSelf_td_depart1() {
		return self_td_depart1;
	}

	public void setSelf_td_depart1(String self_td_depart1) {
		this.self_td_depart1 = self_td_depart1;
	}

	public String getSelf_td_leader1() {
		return self_td_leader1;
	}

	public void setSelf_td_leader1(String self_td_leader1) {
		this.self_td_leader1 = self_td_leader1;
	}

	public String getSelf_td_self2() {
		return self_td_self2;
	}

	public void setSelf_td_self2(String self_td_self2) {
		this.self_td_self2 = self_td_self2;
	}

	public String getSelf_td_depart2() {
		return self_td_depart2;
	}

	public void setSelf_td_depart2(String self_td_depart2) {
		this.self_td_depart2 = self_td_depart2;
	}

	public String getSelf_td_leader2() {
		return self_td_leader2;
	}

	public void setSelf_td_leader2(String self_td_leader2) {
		this.self_td_leader2 = self_td_leader2;
	}

	public String getSelf_td_self3() {
		return self_td_self3;
	}

	public void setSelf_td_self3(String self_td_self3) {
		this.self_td_self3 = self_td_self3;
	}

	public String getSelf_td_depart3() {
		return self_td_depart3;
	}

	public void setSelf_td_depart3(String self_td_depart3) {
		this.self_td_depart3 = self_td_depart3;
	}

	public String getSelf_td_leader3() {
		return self_td_leader3;
	}

	public void setSelf_td_leader3(String self_td_leader3) {
		this.self_td_leader3 = self_td_leader3;
	}

	public String getSelf_td_self4() {
		return self_td_self4;
	}

	public void setSelf_td_self4(String self_td_self4) {
		this.self_td_self4 = self_td_self4;
	}

	public String getSelf_td_depart4() {
		return self_td_depart4;
	}

	public void setSelf_td_depart4(String self_td_depart4) {
		this.self_td_depart4 = self_td_depart4;
	}

	public String getSelf_td_leader4() {
		return self_td_leader4;
	}

	public void setSelf_td_leader4(String self_td_leader4) {
		this.self_td_leader4 = self_td_leader4;
	}

	public Short getSelf_nl_self1() {
		return self_nl_self1;
	}

	public void setSelf_nl_self1(Short self_nl_self1) {
		this.self_nl_self1 = self_nl_self1;
	}

	public String getSelf_nl_depart1() {
		return self_nl_depart1;
	}

	public void setSelf_nl_depart1(String self_nl_depart1) {
		this.self_nl_depart1 = self_nl_depart1;
	}

	public String getSelf_nl_leader1() {
		return self_nl_leader1;
	}

	public void setSelf_nl_leader1(String self_nl_leader1) {
		this.self_nl_leader1 = self_nl_leader1;
	}

	public String getSelf_nl_self2() {
		return self_nl_self2;
	}

	public void setSelf_nl_self2(String self_nl_self2) {
		this.self_nl_self2 = self_nl_self2;
	}

	public String getSelf_nl_depart2() {
		return self_nl_depart2;
	}

	public void setSelf_nl_depart2(String self_nl_depart2) {
		this.self_nl_depart2 = self_nl_depart2;
	}

	public String getSelf_nl_leader2() {
		return self_nl_leader2;
	}

	public void setSelf_nl_leader2(String self_nl_leader2) {
		this.self_nl_leader2 = self_nl_leader2;
	}

	public String getSelf_nl_self3() {
		return self_nl_self3;
	}

	public void setSelf_nl_self3(String self_nl_self3) {
		this.self_nl_self3 = self_nl_self3;
	}

	public String getSelf_nl_depart3() {
		return self_nl_depart3;
	}

	public void setSelf_nl_depart3(String self_nl_depart3) {
		this.self_nl_depart3 = self_nl_depart3;
	}

	public String getSelf_nl_leader3() {
		return self_nl_leader3;
	}

	public void setSelf_nl_leader3(String self_nl_leader3) {
		this.self_nl_leader3 = self_nl_leader3;
	}

	public String getSelf_nl_self4() {
		return self_nl_self4;
	}

	public void setSelf_nl_self4(String self_nl_self4) {
		this.self_nl_self4 = self_nl_self4;
	}

	public String getSelf_nl_depart4() {
		return self_nl_depart4;
	}

	public void setSelf_nl_depart4(String self_nl_depart4) {
		this.self_nl_depart4 = self_nl_depart4;
	}

	public String getSelf_nl_leader4() {
		return self_nl_leader4;
	}

	public void setSelf_nl_leader4(String self_nl_leader4) {
		this.self_nl_leader4 = self_nl_leader4;
	}

	public Short getSelf_xg_self1() {
		return self_xg_self1;
	}

	public void setSelf_xg_self1(Short self_xg_self1) {
		this.self_xg_self1 = self_xg_self1;
	}

	public String getSelf_xg_depart1() {
		return self_xg_depart1;
	}

	public void setSelf_xg_depart1(String self_xg_depart1) {
		this.self_xg_depart1 = self_xg_depart1;
	}

	public String getSelf_xg_leader1() {
		return self_xg_leader1;
	}

	public void setSelf_xg_leader1(String self_xg_leader1) {
		this.self_xg_leader1 = self_xg_leader1;
	}

	public String getSelf_xg_self2() {
		return self_xg_self2;
	}

	public void setSelf_xg_self2(String self_xg_self2) {
		this.self_xg_self2 = self_xg_self2;
	}

	public String getSelf_xg_depart2() {
		return self_xg_depart2;
	}

	public void setSelf_xg_depart2(String self_xg_depart2) {
		this.self_xg_depart2 = self_xg_depart2;
	}

	public String getSelf_xg_leader2() {
		return self_xg_leader2;
	}

	public void setSelf_xg_leader2(String self_xg_leader2) {
		this.self_xg_leader2 = self_xg_leader2;
	}

	public String getSelf_xg_self3() {
		return self_xg_self3;
	}

	public void setSelf_xg_self3(String self_xg_self3) {
		this.self_xg_self3 = self_xg_self3;
	}

	public String getSelf_xg_depart3() {
		return self_xg_depart3;
	}

	public void setSelf_xg_depart3(String self_xg_depart3) {
		this.self_xg_depart3 = self_xg_depart3;
	}

	public String getSelf_xg_leader3() {
		return self_xg_leader3;
	}

	public void setSelf_xg_leader3(String self_xg_leader3) {
		this.self_xg_leader3 = self_xg_leader3;
	}

	public String getSelf_xg_self4() {
		return self_xg_self4;
	}

	public void setSelf_xg_self4(String self_xg_self4) {
		this.self_xg_self4 = self_xg_self4;
	}

	public String getSelf_xg_depart4() {
		return self_xg_depart4;
	}

	public void setSelf_xg_depart4(String self_xg_depart4) {
		this.self_xg_depart4 = self_xg_depart4;
	}

	public String getSelf_xg_leader4() {
		return self_xg_leader4;
	}

	public void setSelf_xg_leader4(String self_xg_leader4) {
		this.self_xg_leader4 = self_xg_leader4;
	}

	public String getSelf_total() {
		return self_total;
	}

	public void setSelf_total(String self_total) {
		this.self_total = self_total;
	}

	public String getDepart_total() {
		return depart_total;
	}

	public void setDepart_total(String depart_total) {
		this.depart_total = depart_total;
	}

	public String getLeader_total() {
		return leader_total;
	}

	public void setLeader_total(String leader_total) {
		this.leader_total = leader_total;
	}

	public String getBussinessDataId() {
		return bussinessDataId;
	}

	public void setBussinessDataId(String bussinessDataId) {
		this.bussinessDataId = bussinessDataId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}