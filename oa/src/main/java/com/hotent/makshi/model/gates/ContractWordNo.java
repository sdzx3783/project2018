/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-28，cp创建。 
 */
package com.hotent.makshi.model.gates;

public class ContractWordNo {

	private String taskId;
	private String use_at;
	private Integer use_no;
	private String create_at;

	private String useNo;

	public String getUseNo() {
		if (null != use_no && use_no < 10) {
			return "0" + use_no;
		}
		return use_no + "";
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getUse_at() {
		return use_at;
	}

	public void setUse_at(String use_at) {
		this.use_at = use_at;
	}

	public Integer getUse_no() {
		return use_no;
	}

	public void setUse_no(Integer use_no) {
		this.use_no = use_no;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

}
