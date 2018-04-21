package com.hotent.makshi.model.project;

import java.io.Serializable;
import java.util.Date;

public class ProjectCollection implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8923078822049403088L;

	private Integer id;

    private Long projectId;

    private Integer state;

    private Long userId;

    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}