package com.hotent.makshi.model.doc;

import java.util.Date;

public class DocCollection 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841191281840009577L;

	//主键
	protected Long id;

	protected Long  docid;
	
	protected Integer  state;
	
	protected Long  userid;
	
	protected Integer  type;
	
	protected Date  ctime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDocid(Long docid) 
	{
		this.docid = docid;
	}

	public Long getDocid() 
	{
		return this.docid;
	}
	public void setState(Integer state) 
	{
		this.state = state;
	}
	
	public Integer getState() 
	{
		return this.state;
	}
	public void setUserid(Long userid) 
	{
		this.userid = userid;
	}
	
	public Long getUserid() 
	{
		return this.userid;
	}
	public void setType(Integer type) 
	{
		this.type = type;
	}
	
	public Integer getType() 
	{
		return this.type;
	}
	public void setCtime(Date ctime) 
	{
		this.ctime = ctime;
	}
	
	public Date getCtime() 
	{
		return this.ctime;
	}
	
}