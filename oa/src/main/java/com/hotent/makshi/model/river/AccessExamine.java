package com.hotent.makshi.model.river;

import com.hotent.core.web.query.QueryFilter;

public class AccessExamine {
	private Boolean isEditer;
	private QueryFilter fiter;

	public Boolean getIsEditer() {
		return isEditer;
	}

	public void setIsEditer(Boolean isEditer) {
		this.isEditer = isEditer;
	}

	public QueryFilter getFiter() {
		return fiter;
	}

	public void setFiter(QueryFilter fiter) {
		this.fiter = fiter;
	}

	public AccessExamine() {
		super();
	}

}
