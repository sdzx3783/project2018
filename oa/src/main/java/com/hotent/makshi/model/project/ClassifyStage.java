package com.hotent.makshi.model.project;

import java.io.Serializable;

public class ClassifyStage implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 511487713585919231L;

	private Long id;

    private Long classifylibraryid;

    private String stagename;

    private Integer stageno;

    private Integer stagetype;

    private String createorg;

    private Long createorgid;

    private Integer order;

    private String prestage;

    private String afterstage;

    private Integer isdelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassifylibraryid() {
        return classifylibraryid;
    }

    public void setClassifylibraryid(Long classifylibraryid) {
        this.classifylibraryid = classifylibraryid;
    }

    public String getStagename() {
        return stagename;
    }

    public void setStagename(String stagename) {
        this.stagename = stagename;
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

    public String getCreateorg() {
        return createorg;
    }

    public void setCreateorg(String createorg) {
        this.createorg = createorg;
    }

    public Long getCreateorgid() {
        return createorgid;
    }

    public void setCreateorgid(Long createorgid) {
        this.createorgid = createorgid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getPrestage() {
        return prestage;
    }

    public void setPrestage(String prestage) {
        this.prestage = prestage;
    }

    public String getAfterstage() {
        return afterstage;
    }

    public void setAfterstage(String afterstage) {
        this.afterstage = afterstage;
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
		ClassifyStage other = (ClassifyStage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
}