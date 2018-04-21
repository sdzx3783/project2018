package com.hotent.makshi.model.project;

import java.util.Date;

import net.sf.json.JSONArray;

public class ProjectStageTask {
	private Integer id;

	private Integer prostageid;

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
	private String flowrunid;

	private String cstid;

	private String pretaskid;

	private String aftertaskid;
	private String querytaskid;

	private String charge;

	private String chargeID;

	private Date starttime;

	private Date endtime;

	private Integer status;
	private Integer endcount;
	private Integer isdelete;

	private Project project;
	private ProjectStage stage;

	private Long sysplanid;

	private String projectname;

	private String stagename;

	public String getProjectname() {
		return projectname;
	}

	public void setProjectName(String projectname) {
		this.projectname = projectname;
	}

	public String getStagename() {
		return stagename;
	}

	public void setStagename(String stagename) {
		this.stagename = stagename;
	}

	public JSONArray getFieldArr() {
		JSONArray fieldArr = new JSONArray();
		if (fields != null && fields.trim().length() > 0) {
			fieldArr = JSONArray.fromObject(fields);
		}
		return fieldArr;
	}

	public JSONArray getFileArr() {
		JSONArray fileArr = new JSONArray();
		if (uploadfile != null && uploadfile.trim().length() > 0) {
			fileArr = JSONArray.fromObject(uploadfile);
		}
		return fileArr;
	}

	public ProjectStageTask() {

	}

	public ProjectStageTask(Integer prostageid, Integer taskno, String taskname, String templatefile, Integer tasktype, String remark, Boolean isexamine, Boolean ismore, Integer order, String fields,
			String uploadfile, String flowid, String cstid, String pretaskid, String aftertaskid, String querytaskid, Integer status) {
		super();
		this.prostageid = prostageid;
		this.taskno = taskno;
		this.taskname = taskname;
		this.templatefile = templatefile;
		this.tasktype = tasktype;
		this.remark = remark;
		this.isexamine = isexamine;
		this.ismore = ismore;
		this.order = order;
		this.fields = fields;
		this.uploadfile = uploadfile;
		this.flowid = flowid;
		this.cstid = cstid;
		this.pretaskid = pretaskid;
		this.aftertaskid = aftertaskid;
		this.querytaskid = querytaskid;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProstageid() {
		return prostageid;
	}

	public void setProstageid(Integer prostageid) {
		this.prostageid = prostageid;
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
		this.taskname = taskname == null ? null : taskname.trim();
	}

	public String getTemplatefile() {
		return templatefile;
	}

	public void setTemplatefile(String templatefile) {
		this.templatefile = templatefile == null ? null : templatefile.trim();
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
		this.remark = remark == null ? null : remark.trim();
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
		this.fields = fields == null ? null : fields.trim();
	}

	public String getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile == null ? null : uploadfile.trim();
	}

	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid == null ? null : flowid.trim();
	}

	public String getCstid() {
		return cstid;
	}

	public void setCstid(String cstid) {
		this.cstid = cstid == null ? null : cstid.trim();
	}

	public String getPretaskid() {
		return pretaskid;
	}

	public void setPretaskid(String pretaskid) {
		this.pretaskid = pretaskid == null ? null : pretaskid.trim();
	}

	public String getAftertaskid() {
		return aftertaskid;
	}

	public void setAftertaskid(String aftertaskid) {
		this.aftertaskid = aftertaskid == null ? null : aftertaskid.trim();
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge == null ? null : charge.trim();
	}

	public String getChargeID() {
		return chargeID;
	}

	public void setChargeID(String chargeID) {
		this.chargeID = chargeID == null ? null : chargeID.trim();
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getQuerytaskid() {
		return querytaskid;
	}

	public void setQuerytaskid(String querytaskid) {
		this.querytaskid = querytaskid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEndcount() {
		return endcount;
	}

	public void setEndcount(Integer endcount) {
		this.endcount = endcount;
	}

	public String getFlowrunid() {
		return flowrunid;
	}

	public void setFlowrunid(String flowrunid) {
		this.flowrunid = flowrunid;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ProjectStage getStage() {
		return stage;
	}

	public void setStage(ProjectStage stage) {
		this.stage = stage;
	}

	public Long getSysplanid() {
		return sysplanid;
	}

	public void setSysplanid(Long sysplanid) {
		this.sysplanid = sysplanid;
	}

}