package com.hotent.makshi.model.title;

import com.hotent.core.model.WfBaseModel;
/**
 * 对象功能:个人证书借阅申请 Model对象
 */
public class CertificatePerson extends WfBaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1566511891300545622L;

	protected String certificate_type;
	
	protected Integer existNum;
	
	protected Integer registNum;
	
	protected Integer inNum;
	
	protected Integer outNum;
	
	protected Integer trainingNum;

	public String getCertificate_type() {
		return certificate_type;
	}

	public void setCertificate_type(String certificate_type) {
		this.certificate_type = certificate_type;
	}

	public Integer getExistNum() {
		return existNum;
	}

	public void setExistNum(Integer existNum) {
		this.existNum = existNum;
	}

	public Integer getRegistNum() {
		return registNum;
	}

	public void setRegistNum(Integer registNum) {
		this.registNum = registNum;
	}

	public Integer getInNum() {
		return inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}

	public Integer getOutNum() {
		return outNum;
	}

	public void setOutNum(Integer outNum) {
		this.outNum = outNum;
	}

	public Integer getTrainingNum() {
		return trainingNum;
	}

	public void setTrainingNum(Integer trainingNum) {
		this.trainingNum = trainingNum;
	}
	
	
}