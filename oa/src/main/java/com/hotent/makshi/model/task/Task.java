package com.hotent.makshi.model.task;

import java.util.Date;

public class Task {
    private Integer id;

    private String name;

    private String charger;

    private String chargerID;

    private String member;

    private String memberID;

    private Integer imports;

    private Date startDate;

    private Date endDate;

    private Integer stage;

    private String file;

    private String cuser;

    private String cuserid;

    private Date ctime;

    private String uuser;

    private String uuserid;

    private Date utime;

    private Integer isdelete;
    private Integer ispub;

    private String content;
    
    private String progress;
    
    private boolean isCharge=false; 
    
    private Long sysplanid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger == null ? null : charger.trim();
    }

    public String getChargerID() {
        return chargerID;
    }

    public void setChargerID(String chargerID) {
        this.chargerID = chargerID == null ? null : chargerID.trim();
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member == null ? null : member.trim();
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID == null ? null : memberID.trim();
    }

    public Integer getImports() {
        return imports;
    }

    public void setImports(Integer imports) {
        this.imports = imports;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public String getCuserid() {
        return cuserid;
    }

    public void setCuserid(String cuserid) {
        this.cuserid = cuserid == null ? null : cuserid.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getUuser() {
        return uuser;
    }

    public void setUuser(String uuser) {
        this.uuser = uuser == null ? null : uuser.trim();
    }

    public String getUuserid() {
        return uuserid;
    }

    public void setUuserid(String uuserid) {
        this.uuserid = uuserid == null ? null : uuserid.trim();
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public boolean getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(boolean isCharge) {
		this.isCharge = isCharge;
	}

	public Integer getIspub() {
		return ispub;
	}

	public void setIspub(Integer ispub) {
		this.ispub = ispub;
	}

	public Long getSysplanid() {
		return sysplanid;
	}

	public void setSysplanid(Long sysplanid) {
		this.sysplanid = sysplanid;
	}
    
    
}