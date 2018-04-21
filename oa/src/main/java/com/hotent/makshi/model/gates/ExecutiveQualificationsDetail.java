package com.hotent.makshi.model.gates;

public class ExecutiveQualificationsDetail {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String typeDesc;

	public String getTypeDesc() {
		switch (ctype) {
		case 1:
			typeDesc = "企业资质";
			break;
		case 2:
			typeDesc = "施工监理";
			break;
		case 3:
			typeDesc = "工程咨询";
			break;
		case 4:
			typeDesc = "招标代理";
			break;
		case 5:
			typeDesc = "造价咨询";
			break;
		case 6:
			typeDesc = "水土保持";
			break;
		case 7:
			typeDesc = "污水运营、环境";
			break;
		case 8:
			typeDesc = "信息";
			break;
		case 9:
			typeDesc = "测绘";
			break;
		case 10:
			typeDesc = "施工";
			break;
		default:
			typeDesc = "其它";
			break;
		}
		return typeDesc;
	}

	private String cno;
	private String cname;
	private int ctype;
	private String institution;
	private String certificationtime;
	private String certificationlimit;

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getCtype() {
		return ctype;
	}

	public void setCtype(int ctype) {
		this.ctype = ctype;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getCertificationtime() {
		return certificationtime;
	}

	public void setCertificationtime(String certificationtime) {
		this.certificationtime = certificationtime;
	}

	public String getCertificationlimit() {
		return certificationlimit;
	}

	public void setCertificationlimit(String certificationlimit) {
		this.certificationlimit = certificationlimit;
	}

}
