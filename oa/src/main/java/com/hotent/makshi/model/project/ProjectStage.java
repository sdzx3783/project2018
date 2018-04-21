package com.hotent.makshi.model.project;

import java.util.List;

public class ProjectStage {
    private Integer id;

    private Integer proid;

    private String stagename;

    private Integer stageno;

    private Integer stagetype;

    private Integer order;

    private Long csid;

    private String prestage;

    private String afterstage;
    
    private Integer status;
    
    private List<ProjectStageTask> tasks;
    
    private int ends=0;;
    
    private int taskStatus = 0;
    
    
    
    
    
    public ProjectStage(){
    	
    }

    public ProjectStage(Integer proid, String stagename, Integer stageno,
			Integer stagetype, Integer order, Long csid, String prestage,
			String afterstage) {
		super();
		this.proid = proid;
		this.stagename = stagename;
		this.stageno = stageno;
		this.stagetype = stagetype;
		this.order = order;
		this.csid = csid;
		this.prestage = prestage;
		this.afterstage = afterstage;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProid() {
        return proid;
    }

    public void setProid(Integer proid) {
        this.proid = proid;
    }

    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename == null ? null : stagename.trim();
    }

    public Integer getStageno() {
        return stageno;
    }

    public void setStageno(Integer stageno) {
        this.stageno = stageno;
    }

    public Integer getStagetype() {
        return stagetype;
    }

    public void setStagetype(Integer stagetype) {
        this.stagetype = stagetype;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getCsid() {
        return csid;
    }

    public void setCsid(Long csid) {
        this.csid = csid;
    }

    public String getPrestage() {
        return prestage;
    }

    public void setPrestage(String prestage) {
        this.prestage = prestage == null ? null : prestage.trim();
    }

    public String getAfterstage() {
        return afterstage;
    }

    public void setAfterstage(String afterstage) {
        this.afterstage = afterstage == null ? null : afterstage.trim();
    }

	public List<ProjectStageTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<ProjectStageTask> tasks) {
		this.tasks = tasks;
	}

	public int getEnds() {
		return ends;
	}

	public void setEnds(int ends) {
		this.ends = ends;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
    
    
}