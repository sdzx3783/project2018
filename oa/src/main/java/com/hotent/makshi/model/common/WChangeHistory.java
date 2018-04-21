package com.hotent.makshi.model.common;

import java.util.Date;

public class WChangeHistory {
    private Integer id;

    private String type;

    private String field;

    private String before;

    private String after;

    private String creater;
    
    private Long createId;
    
    private String condition;

    private Date createTime;

    
    
    public WChangeHistory(){
    	
    }
    
    public WChangeHistory(String type, String field, String before,
			String after, String creater,Long createId,String condition,  Date createTime) {
		super();
		this.type = type;
		this.field = field;
		this.before = before;
		this.after = after;
		this.creater = creater;
		this.createId= createId;
		this.condition= condition;
		this.createTime = createTime;
	}
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before == null ? null : before.trim();
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after == null ? null : after.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

    
    
    
	
    
    
    
}