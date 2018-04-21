package com.hotent.makshi.model.dispatch;

/**
 * 对象功能:发文总表 Model对象
 */
public class Dic 
{
	//主键
	protected Long dicId;
	
	protected Long docId;
	
	protected String dicName;

	public Long getDicId() {
		return dicId;
	}

	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

}