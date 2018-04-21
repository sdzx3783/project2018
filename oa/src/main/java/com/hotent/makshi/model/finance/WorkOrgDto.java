package com.hotent.makshi.model.finance;

import java.util.ArrayList;
import java.util.List;

public class WorkOrgDto {
	public static final String COMPANYORGID="10000007780597";
	private String orgid;
	
	private String orgname;
	
	private String path;
	
	private List<WorkCount>  workcounts=new ArrayList<>();
	
	private List<WorkOrgDto> subworkorg=new ArrayList<>();
	
	private Integer colspan;
	
	private Integer rowspan;

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<WorkCount> getWorkcounts() {
		return workcounts;
	}

	public void setWorkcounts(List<WorkCount> workcounts) {
		this.workcounts = workcounts;
	}

	public List<WorkOrgDto> getSubworkorg() {
		return subworkorg;
	}

	public void setSubworkorg(List<WorkOrgDto> subworkorg) {
		this.subworkorg = subworkorg;
	}

	public Integer getColspan() {
		if(subworkorg.size()==0)
			return 2;
		return 1;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	public Integer getRowspan() {
		int count=0;
		for (WorkOrgDto workOrgDto : subworkorg) {
			count+=workOrgDto.getWorkcounts().size();
		}
		count+=workcounts.size();
		return count;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}
	
}
