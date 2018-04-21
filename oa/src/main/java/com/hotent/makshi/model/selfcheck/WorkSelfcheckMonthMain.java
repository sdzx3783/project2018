package com.hotent.makshi.model.selfcheck;

public class WorkSelfcheckMonthMain {
    private Long id;

    private String username;

    private String userId;

    private String submitAt;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getSubmitAt() {
        return submitAt;
    }

    public void setSubmitAt(String submitAt) {
        this.submitAt = submitAt == null ? null : submitAt.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}