package com.hotent.makshi.model.project;

import java.io.Serializable;

import net.sf.json.JSONArray;

public class StageTaskLibrary implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1923613113798967385L;

	private Integer id;

    private Integer stageno;

    private String taskname;

    private String templatefile;

    private Integer tasktype;

    private String remark;

    private Boolean isexamine;

    private Boolean ismore;

    private Integer order;

    private String fields;

    private String uploadfile;

    private String flowid;

    private Integer isdelete;

    public JSONArray getFieldArr() {//附件字段 页面显示
    	JSONArray fieldArr=new JSONArray();
    	if(fields!=null && fields.trim().length()>0){
    		fieldArr = JSONArray.fromObject(fields);
    	}
		return fieldArr;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStageno() {
        return stageno;
    }

    public void setStageno(Integer stageno) {
        this.stageno = stageno;
    }

    public String getTaskname() {
        return taskname;
    }

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public String getTemplatefile() {
        return templatefile;
    }

    public void setTemplatefile(String templatefile) {
        this.templatefile = templatefile;
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsexamine() {
        return isexamine;
    }

    public void setIsexamine(Boolean isexamine) {
        this.isexamine = isexamine;
    }

    public Boolean getIsmore() {
        return ismore;
    }

    public void setIsmore(Boolean ismore) {
        this.ismore = ismore;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(String uploadfile) {
        this.uploadfile = uploadfile;
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
}