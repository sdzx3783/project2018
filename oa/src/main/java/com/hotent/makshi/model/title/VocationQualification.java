package com.hotent.makshi.model.title;

public class VocationQualification {
    private Long id;
    //证书关联id
    private Long linkId;
    //持有人
    private String userName;
    //是否挂靠
    private int isBinding;
    
    //资格证
    private String vocationNum;
    private String vocationName;
    private String vocationSwitchs;
    private String vocationBorrower;
    private String vocationBorrowerId;
    private String vocationStatus;
    //注册证
    protected String registCertificateRegistId;
    protected String registMajor;
    protected String registCertificateMajor;
    protected String registBorrowerID;
    protected String registBorrower;
    //从业证
    private Long occupationalId;
    private String occupationalCertificateId;
    private String occupationalType;
    private String occupationalBorrowerID;
    private String occupationalBorrower;
    
    
    
    public int getIsBinding() {
		return isBinding;
	}

	public void setIsBinding(int isBinding) {
		this.isBinding = isBinding;
	}

	public Long getOccupationalId() {
		return occupationalId;
	}

	public void setOccupationalId(Long occupationalId) {
		this.occupationalId = occupationalId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVocationBorrowerId() {
		return vocationBorrowerId;
	}

	public void setVocationBorrowerId(String vocationBorrowerId) {
		this.vocationBorrowerId = vocationBorrowerId;
	}

	public String getVocationNum() {
		return vocationNum;
	}

	public void setVocationNum(String vocationNum) {
		this.vocationNum = vocationNum;
	}

	public String getVocationName() {
		return vocationName;
	}

	public void setVocationName(String vocationName) {
		this.vocationName = vocationName;
	}

	public String getVocationSwitchs() {
		return vocationSwitchs;
	}

	public void setVocationSwitchs(String vocationSwitchs) {
		this.vocationSwitchs = vocationSwitchs;
	}

	public String getVocationBorrower() {
		return vocationBorrower;
	}

	public void setVocationBorrower(String vocationBorrower) {
		this.vocationBorrower = vocationBorrower;
	}

	public String getVocationStatus() {
		return vocationStatus;
	}

	public void setVocationStatus(String vocationStatus) {
		this.vocationStatus = vocationStatus;
	}

	public String getRegistCertificateRegistId() {
		return registCertificateRegistId;
	}

	public void setRegistCertificateRegistId(String registCertificateRegistId) {
		this.registCertificateRegistId = registCertificateRegistId;
	}

	public String getRegistMajor() {
		return registMajor;
	}

	public void setRegistMajor(String registMajor) {
		this.registMajor = registMajor;
	}

	public String getRegistCertificateMajor() {
		return registCertificateMajor;
	}

	public void setRegistCertificateMajor(String registCertificateMajor) {
		this.registCertificateMajor = registCertificateMajor;
	}

	public String getRegistBorrower() {
		return registBorrower;
	}

	public void setRegistBorrower(String registBorrower) {
		this.registBorrower = registBorrower;
	}

	public String getOccupationalCertificateId() {
		return occupationalCertificateId;
	}

	public void setOccupationalCertificateId(String occupationalCertificateId) {
		this.occupationalCertificateId = occupationalCertificateId;
	}
	public String getOccupationalType() {
		return occupationalType;
	}

	public void setOccupationalType(String occupationalType) {
		this.occupationalType = occupationalType;
	}

	public String getOccupationalBorrower() {
		return occupationalBorrower;
	}

	public void setOccupationalBorrower(String occupationalBorrower) {
		this.occupationalBorrower = occupationalBorrower;
	}


	public String getRegistBorrowerID() {
		return registBorrowerID;
	}

	public void setRegistBorrowerID(String registBorrowerID) {
		this.registBorrowerID = registBorrowerID;
	}

	public String getOccupationalBorrowerID() {
		return occupationalBorrowerID;
	}

	public void setOccupationalBorrowerID(String occupationalBorrowerID) {
		this.occupationalBorrowerID = occupationalBorrowerID;
	}


    
}