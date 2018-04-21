package com.hotent.makshi.model.project;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hotent.core.api.util.ContextUtil;

import net.sf.json.JSONArray;

public class Project implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5236977767847297878L;

	private Integer id;

    private Long classifylibraryid;

    private String name;

    private String applicant;

    private String applicantID;

    private String member;

    private String memberID;

    private Date apptime;

    private String contractnum;

    private String constructnature;

    private Date reporttime;

    private String firstmoney;

    private String reportperson;

    private String reportPersonID;

    private Integer proresouce;

    private String procompanyLinker;//建设单位联系人
    
    private String procompanyLinkerPhone;//建设单位联系人电话
    
    
    private Integer isdelete;

    private Date ctime;

    private String cuser;

    private Date utime;

    private String uuser;
    
    private String contractname;
    private String contractmoney;
    private Date expectstarttime;
    private String charger;
    private String chargerID;

    private Integer status;
    private String legalperson;
    private String legalPersonID;

    private String tel;

    private String approvalnumber;

    private Date approvaltime;
    private String remark;
    
    private Date expectendtime;
    
    private String procompany;
    private String field;

    private List<ProjectStage> stages;
    
    private boolean isCharger;
    
    private String classifyLibName;
    
    private Long contractId;//附加字段
    
    private String proYear;
    
    
    public String getProYear() {
		return proYear;
	}

	public void setProYear(String proYear) {
		this.proYear = proYear;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	public String getProcompanyLinker() {
		return procompanyLinker;
	}

	public void setProcompanyLinker(String procompanyLinker) {
		this.procompanyLinker = procompanyLinker;
	}

	public String getProcompanyLinkerPhone() {
		return procompanyLinkerPhone;
	}

	public void setProcompanyLinkerPhone(String procompanyLinkerPhone) {
		this.procompanyLinkerPhone = procompanyLinkerPhone;
	}

	public String getClassifyLibName() {
		return classifyLibName;
	}

	public void setClassifyLibName(String classifyLibName) {
		this.classifyLibName = classifyLibName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getClassifylibraryid() {
        return classifylibraryid;
    }

    public void setClassifylibraryid(Long classifylibraryid) {
        this.classifylibraryid = classifylibraryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public String getApplicantID() {
        return applicantID;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID == null ? null : applicantID.trim();
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

    public Date getApptime() {
        return apptime;
    }

    public void setApptime(Date apptime) {
        this.apptime = apptime;
    }

    public String getContractnum() {
        return contractnum;
    }

    public void setContractnum(String contractnum) {
        this.contractnum = contractnum == null ? null : contractnum.trim();
    }

    public String getConstructnature() {
        return constructnature;
    }

    public void setConstructnature(String constructnature) {
        this.constructnature = constructnature == null ? null : constructnature.trim();
    }

    public Date getReporttime() {
        return reporttime;
    }

    public void setReporttime(Date reporttime) {
        this.reporttime = reporttime;
    }

    public String getFirstmoney() {
        return firstmoney;
    }

    public void setFirstmoney(String firstmoney) {
        this.firstmoney = firstmoney == null ? null : firstmoney.trim();
    }

    public String getReportperson() {
        return reportperson;
    }

    public void setReportperson(String reportperson) {
        this.reportperson = reportperson == null ? null : reportperson.trim();
    }



    public String getReportPersonID() {
		return reportPersonID;
	}

	public void setReportPersonID(String reportPersonID) {
		this.reportPersonID = reportPersonID;
	}

	public Integer getProresouce() {
        return proresouce;
    }

    public void setProresouce(Integer proresouce) {
        this.proresouce = proresouce;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getCuser() {
        return cuser;
    }

    public void setCuser(String cuser) {
        this.cuser = cuser == null ? null : cuser.trim();
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getUuser() {
        return uuser;
    }

    public void setUuser(String uuser) {
        this.uuser = uuser == null ? null : uuser.trim();
    }

	public List<ProjectStage> getStages() {
		return stages;
	}

	public void setStages(List<ProjectStage> stages) {
		this.stages = stages;
	}

	public Date getExpectendtime() {
		return expectendtime;
	}

	public void setExpectendtime(Date expectendtime) {
		this.expectendtime = expectendtime;
	}

	public String getProcompany() {
		return procompany;
	}

	public void setProcompany(String procompany) {
		this.procompany = procompany;
	}

	public String getField() {
		return field;
	}	

	public void setField(String field) {
		this.field = field;
	}
	
	public JSONArray getFieldArr() {//其他信息字段
    	JSONArray fieldArr=new JSONArray();
    	if(field!=null && field.trim().length()>0){
    		fieldArr = JSONArray.fromObject(field);
    	}
		return fieldArr;
	}

	public boolean getIsCharger() {
		boolean result = false;
		
		if(ContextUtil.isSuperAdmin()){
			result= true;
		}
		if(!result){
			String chargerId =this.applicantID;
			String[] ids = chargerId.split(",");
			for(String tempid : ids){
				if(tempid.equals(ContextUtil.getCurrentUserId()+"")){
					result= true;
					break;
				}
			}
		}
		return result;
	}

	public void setIsCharger(boolean isCharger) {
		this.isCharger = isCharger;
	}

	public String getContractname() {
		return contractname;
	}

	public void setContractname(String contractname) {
		this.contractname = contractname;
	}

	public Date getExpectstarttime() {
		return expectstarttime;
	}

	public void setExpectstarttime(Date expectstarttime) {
		this.expectstarttime = expectstarttime;
	}

	public String getCharger() {
		return charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}


	public String getChargerID() {
		return chargerID;
	}

	public void setChargerID(String chargerID) {
		this.chargerID = chargerID;
	}


	public String getLegalPersonID() {
		return legalPersonID;
	}

	public void setLegalPersonID(String legalPersonID) {
		this.legalPersonID = legalPersonID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLegalperson() {
		return legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}


	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getApprovalnumber() {
		return approvalnumber;
	}

	public void setApprovalnumber(String approvalnumber) {
		this.approvalnumber = approvalnumber;
	}

	public Date getApprovaltime() {
		return approvaltime;
	}

	public void setApprovaltime(Date approvaltime) {
		this.approvaltime = approvaltime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContractmoney() {
		return contractmoney;
	}

	public void setContractmoney(String contractmoney) {
		this.contractmoney = contractmoney;
	}

	

    
    
}