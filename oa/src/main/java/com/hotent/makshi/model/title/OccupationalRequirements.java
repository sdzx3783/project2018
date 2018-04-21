package com.hotent.makshi.model.title;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:从业资格信息管理 Model对象
 */
public class OccupationalRequirements extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5936915315017238966L;

	//主键
	protected Long id;
	
	protected String refid;
	
	protected Long linkId;
	
	public Long getLinkId() {
		return linkId;
	}
	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	/**
	 *证书类型
	 */
	protected String  occ_type;
	/**
	 *证书编号
	 */
	protected String  occ_certificate_id;
	/**
	 *发证日期
	 */
	protected java.util.Date  occ_get_date;
	/**
	 *有效日期
	 */
	protected java.util.Date  occ_period_of_validity;
	/**
	 *专业
	 */
	protected String  occ_major;
	/**
	 *转出日期
	 */
	protected java.util.Date  occ_out_date;
	/**
	 *转入日期
	 */
	protected java.util.Date  occ_in_date;
	/**
	 *发证单位
	 */
	protected String  occ_send_unit;
	/**
	 *工种
	 */
	protected String  occ_type_work;
	/**
	 *工种等级
	 */
	protected String  occ_degree_work;
	/**
	 *继续教育完成情况
	 */
	protected String  occ_contine_edu_comple;
	/**
	 *备注
	 */
	protected String  occ_remark;
	/**
	 *附件
	 */
	protected String  occ_attachment;
	/**
	 * 借用人
	 * @return
	 */
	protected String isBorrowed;
	protected String borrower;
	/**
	 * 持有人
	 * @return
	 */
	protected Long userId;
	/**
	 * 持证人
	 * @return
	 */
	protected String userName;
	/**
	 * 必修课学时
	 */
	protected String occ_compulsory;
	/**
	 * 选修课学时
	 */
	protected String occ_elective;
	/**
	 * 总学时
	 */
	protected String occ_period;
	/**
	 * 第二专业
	 * @return
	 */
	protected String occ_secondMajor;
	
	protected String account;

	
	public String getOcc_secondMajor() {
		return occ_secondMajor;
	}
	public void setOcc_secondMajor(String occ_secondMajor) {
		this.occ_secondMajor = occ_secondMajor;
	}
	public String getOcc_compulsory() {
		return occ_compulsory;
	}
	public void setOcc_compulsory(String occ_compulsory) {
		this.occ_compulsory = occ_compulsory;
	}
	public String getOcc_elective() {
		return occ_elective;
	}
	public void setOcc_elective(String occ_elective) {
		this.occ_elective = occ_elective;
	}
	public String getOcc_period() {
		return occ_period;
	}
	public void setOcc_period(String occ_period) {
		this.occ_period = occ_period;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getIsBorrowed() {
		return isBorrowed;
	}
	public void setIsBorrowed(String isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	public String getRefid() {
		return refid;
	}
	public void setRefid(String refid) {
		this.refid = refid;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOcc_type() {
		return occ_type;
	}
	public void setOcc_type(String occ_type) {
		this.occ_type = occ_type;
	}
	public String getOcc_certificate_id() {
		return occ_certificate_id;
	}
	public void setOcc_certificate_id(String occ_certificate_id) {
		this.occ_certificate_id = occ_certificate_id;
	}
	public java.util.Date getOcc_get_date() {
		return occ_get_date;
	}
	public void setOcc_get_date(java.util.Date occ_get_date) {
		this.occ_get_date = occ_get_date;
	}
	public java.util.Date getOcc_period_of_validity() {
		return occ_period_of_validity;
	}
	public void setOcc_period_of_validity(java.util.Date occ_period_of_validity) {
		this.occ_period_of_validity = occ_period_of_validity;
	}
	public String getOcc_major() {
		return occ_major;
	}
	public void setOcc_major(String occ_major) {
		this.occ_major = occ_major;
	}
	public java.util.Date getOcc_out_date() {
		return occ_out_date;
	}
	public void setOcc_out_date(java.util.Date occ_out_date) {
		this.occ_out_date = occ_out_date;
	}
	public String getOcc_send_unit() {
		return occ_send_unit;
	}
	public void setOcc_send_unit(String occ_send_unit) {
		this.occ_send_unit = occ_send_unit;
	}
	public String getOcc_type_work() {
		return occ_type_work;
	}
	public void setOcc_type_work(String occ_type_work) {
		this.occ_type_work = occ_type_work;
	}
	public String getOcc_degree_work() {
		return occ_degree_work;
	}
	public void setOcc_degree_work(String occ_degree_work) {
		this.occ_degree_work = occ_degree_work;
	}
	public String getOcc_contine_edu_comple() {
		return occ_contine_edu_comple;
	}
	public void setOcc_contine_edu_comple(String occ_contine_edu_comple) {
		this.occ_contine_edu_comple = occ_contine_edu_comple;
	}
	public String getOcc_remark() {
		return occ_remark;
	}
	public void setOcc_remark(String occ_remark) {
		this.occ_remark = occ_remark;
	}
	public String getOcc_attachment() {
		return occ_attachment;
	}
	public void setOcc_attachment(String occ_attachment) {
		this.occ_attachment = occ_attachment;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public java.util.Date getOcc_in_date() {
		return occ_in_date;
	}
	public void setOcc_in_date(java.util.Date occ_in_date) {
		this.occ_in_date = occ_in_date;
	}
	
}