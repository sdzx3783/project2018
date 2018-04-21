package com.hotent.makshi.model.contract;

import java.util.Date;

import com.hotent.core.model.BaseModel;

public class Contractnum extends BaseModel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8645745716953266189L;

	private Long id;

    private String contracttype;

    private String contractNum;

    private String year;

    private String num;

    private Integer isdelete;

    private Date ctime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
    
    
}