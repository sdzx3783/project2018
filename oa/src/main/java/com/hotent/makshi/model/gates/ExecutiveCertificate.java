/*
 * 版权所有 (C) 2014-2017 深圳掌趣互动科技有限公司。保留所有权利。
 * 版本：
 * 修改记录：
 *		1、2017-11-15，cp创建。 
 */
package com.hotent.makshi.model.gates;

public class ExecutiveCertificate {

	private String typeDesc;

	public String getTypeDesc() {
		switch (type) {
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

	private String certificate_total;
	private int type;

	public String getCertificate_total() {
		return certificate_total;
	}

	public void setCertificate_total(String certificate_total) {
		this.certificate_total = certificate_total;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
