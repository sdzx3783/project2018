package com.hotent.makshi.model.project;

import java.util.Date;

public class ProjectStageTaskField {
    private Integer id;

    private Integer taskId;

    private String fieldName;
    private String fieldType;

    private String fieldValue;
    //附加字段
    private String fieldValueID;

    private String cuser;

    private Date ctime;

    private Integer isdelete;

    
    
    public ProjectStageTaskField(){
    	
    }
    
   
    
    
    public ProjectStageTaskField(Integer taskId, String fieldName,String fieldType,
			String fieldValue, String cuser, Date ctime, Integer isdelete) {
		super();
		this.taskId = taskId;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldValue = fieldValue;
		this.cuser = cuser;
		this.ctime = ctime;
		this.isdelete = isdelete;
	}




	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue == null ? null : fieldValue.trim();
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }




	public String getFieldType() {
		return fieldType;
	}




	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}




	public String getFieldValueID() {
		return fieldValueID;
	}




	public void setFieldValueID(String fieldValueID) {
		this.fieldValueID = fieldValueID;
	}
    
    
    
}