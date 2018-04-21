package com.hotent.makshi.model.project;

import java.io.Serializable;

import net.sf.json.JSONArray;

public class ClassifyStageTask implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1817898801327763575L;

	private Integer id;

    private Long classifystageid;

    private Integer taskno;

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

    private String preclassifystagetaskid;

    private String afterclassifystagetaskid;
    
    private String queryclassifystagetaskid;
    
    private Integer isdelete;

    public Integer getId() {
        return id;
    }
    public JSONArray getFieldArr() {//附件字段 页面显示
    	JSONArray fieldArr=new JSONArray();
    	if(fields!=null && fields.trim().length()>0){
    		fieldArr = JSONArray.fromObject(fields);
    	}
		return fieldArr;
	}
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getClassifystageid() {
        return classifystageid;
    }

    public void setClassifystageid(Long classifystageid) {
        this.classifystageid = classifystageid;
    }

    public Integer getTaskno() {
        return taskno;
    }

    public void setTaskno(Integer taskno) {
        this.taskno = taskno;
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

    public String getPreclassifystagetaskid() {
        return preclassifystagetaskid;
    }

    public void setPreclassifystagetaskid(String preclassifystagetaskid) {
        this.preclassifystagetaskid = preclassifystagetaskid;
    }

    public String getAfterclassifystagetaskid() {
        return afterclassifystagetaskid;
    }

    public void setAfterclassifystagetaskid(String afterclassifystagetaskid) {
        this.afterclassifystagetaskid = afterclassifystagetaskid;
    }

    
    public String getQueryclassifystagetaskid() {
		return queryclassifystagetaskid;
	}

	public void setQueryclassifystagetaskid(String queryclassifystagetaskid) {
		this.queryclassifystagetaskid = queryclassifystagetaskid;
	}

	public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassifyStageTask other = (ClassifyStageTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
}