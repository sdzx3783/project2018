package com.hotent.platform.xml.form;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import com.hotent.platform.model.bpm.FormDefTree;
import com.hotent.platform.model.form.BpmDataTemplate;
import com.hotent.platform.model.form.BpmFormDef;
import com.hotent.platform.model.form.BpmFormRights;
import com.hotent.platform.model.system.SysBusEvent;
import com.hotent.platform.model.system.SysFile;
import com.hotent.platform.xml.constant.XmlConstant;
import com.hotent.platform.xml.table.BpmFormTableXml;

/**
 * <pre>
 * 对象功能:表单定义的XMl配置
 * 开发公司:广州宏天软件有限公司
 * 开发人员:zxh
 * 创建时间:2012-12-29 13:59:56
 * </pre>
 */
@XmlRootElement(name = "formDefs")
@XmlAccessorType(XmlAccessType.FIELD)
public class BpmFormDefXml {
	/**
	 * 自定义表单
	 */
	@XmlElement(name = XmlConstant.BPM_FORM_DEF, type = BpmFormDef.class)
	private BpmFormDef bpmFormDef;

	/**
	 * 其它版本的 自定义表单 List
	 */
	@XmlElementWrapper(name =XmlConstant.BPM_FORM_DEF_LIST)
	@XmlElements({ @XmlElement(name = "formDefs", type = BpmFormDefXml.class) })
	private List<BpmFormDefXml> bpmFormDefXmlList;

	/**
	 * 表单权限List
	 */
	@XmlElementWrapper(name = XmlConstant.BPM_FORM_RIGHTS_LIST)
	@XmlElements({ @XmlElement(name = "bpmFormRights", type = BpmFormRights.class) })
	private List<BpmFormRights> bpmFormRightsList;


	/**
	 * 对于的表
	 */
	@XmlElement(name = XmlConstant.FORM_TABLE, type = BpmFormTableXml.class)
	private BpmFormTableXml bpmFormTableXml;
	
	/**
	 * 附件或者帮助文档
	 */
	@XmlElementWrapper(name =XmlConstant.SYS_FILE_LIST )
	@XmlElements({ @XmlElement(name = "sysFile", type = SysFile.class) })
	private List<SysFile> sysFileList;
	
	/**
	 * 数据模版
	 */
	@XmlElement(name =XmlConstant.BPM_DATA_TEMPLATE , type = BpmDataTemplate.class)
	private BpmDataTemplate bpmDataTemplate;
	
	/**
	 * 业务保存设置
	 */
	@XmlElement(name =XmlConstant.SYS_BUS_EVENT, type = SysBusEvent.class)
	private SysBusEvent sysBusEvent;
	/**
	 * 树结构设置
	 * 
	 * 
	 */
	@XmlElement(name=XmlConstant.FORM_DEF_TREE,type=FormDefTree.class)
	private FormDefTree formDefTree;
	
	

	// ==========以下是getting和setting的方法================
	public BpmFormDef getBpmFormDef() {
		
		return bpmFormDef;
	}

	public void setBpmFormDef(BpmFormDef bpmFormDef) {
		this.bpmFormDef = bpmFormDef;
	}

	public List<BpmFormDefXml> getBpmFormDefXmlList() {
		return bpmFormDefXmlList;
	}

	public void setBpmFormDefXmlList(List<BpmFormDefXml> bpmFormDefXmlList) {
		this.bpmFormDefXmlList = bpmFormDefXmlList;
	}

	public List<BpmFormRights> getBpmFormRightsList() {
		return bpmFormRightsList;
	}

	public void setBpmFormRightsList(List<BpmFormRights> bpmFormRightsList) {
		this.bpmFormRightsList = bpmFormRightsList;
	}



	public BpmFormTableXml getBpmFormTableXml() {
		return bpmFormTableXml;
	}

	public void setBpmFormTableXml(BpmFormTableXml bpmFormTableXml) {
		this.bpmFormTableXml = bpmFormTableXml;
	}
	
	/**
	 * @return the sysFileList
	 */
	public List<SysFile> getSysFileList() {
		return sysFileList;
	}

	/**
	 * @param sysFileList the sysFileList to set
	 */
	public void setSysFileList(List<SysFile> sysFileList) {
		this.sysFileList = sysFileList;
	}

	public BpmDataTemplate getBpmDataTemplate() {
		return bpmDataTemplate;
	}

	public void setBpmDataTemplate(BpmDataTemplate bpmDataTemplate) {
		this.bpmDataTemplate = bpmDataTemplate;
	}

	public SysBusEvent getSysBusEvent() {
		return sysBusEvent;
	}

	public void setSysBusEvent(SysBusEvent sysBusEvent) {
		this.sysBusEvent = sysBusEvent;
	}

	public FormDefTree getFormDefTree() {
		return formDefTree;
	}

	public void setFormDefTree(FormDefTree formDefTree) {
		this.formDefTree = formDefTree;
	}

}
