package com.hotent.makshi.model.addrBook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hotent.platform.model.system.UserPosition;

public class CompanyBook extends UserPosition implements Comparable<CompanyBook>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3864879813122052023L;

	public CompanyBook() {
	}
	private Long rowNo;//数据行号用于排序
	
	private String orgPath;
	private Integer sex;
	
	private String mobile;
	
	private String phone;
	
	private String email;

	private String sjdh;
	
	private String department;
	
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSjdh() {
		return sjdh;
	}

	public void setSjdh(String sjdh) {
		this.sjdh = sjdh;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSex() {
		return sex;
	}

	public void setRowNo(Long rowNo) {
		this.rowNo = rowNo;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	public Long getRowNo() {
		return rowNo;
	}

	public void companyBook(Long rowNo) {
		this.rowNo = rowNo;
	}

	public String getOrgPath() {
		return orgPath;
	}

	public void setOrgPath(String orgPath) {
		this.orgPath = orgPath;
	}

	@Override
	public int compareTo(CompanyBook o) {
		String orgPath = this.getOrgPath();
		String orgPath2 = o.getOrgPath();
		String desChar=".";
		int appearNumber = appearNumber(orgPath,desChar);
		int appearNumber2 = appearNumber(orgPath2,desChar);
		if(appearNumber==appearNumber2){
			//组织结构级数相同 比较行号
			int compareTo = orgPath.compareTo(orgPath2);
			if(compareTo==0){
				return (int) (rowNo-o.getRowNo());
			}else{
				return compareTo;
			}
		}else{
			if(orgPath.contains(orgPath2)){
				return orgPath.compareTo(orgPath2);
			}
			return appearNumber-appearNumber2;
		}
	}
	
	public static int appearNumber(String srcText, String findText) {
	    int count = 0;
	    Pattern p = Pattern.compile(findText);
	    Matcher m = p.matcher(srcText);
	    while (m.find()) {
	        count++;
	    }
	    return count;
	}
}
