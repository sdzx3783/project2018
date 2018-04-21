package com.hotent.makshi.model.hr;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.WfBaseModel;
import com.hotent.core.util.TimeUtil;
import com.hotent.makshi.model.hr.EntryJtcy;
import com.hotent.makshi.model.hr.EntryXxjl;
import com.hotent.makshi.model.hr.EntryGzjl;
import com.hotent.makshi.model.hr.EntryZyzc;
import com.hotent.makshi.model.hr.EntryZyzg;
/**
 * 对象功能:入职信息 Model对象
 */
public class Entry extends WfBaseModel
{
	//主键
	protected Long id;
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *基本工资
	 */
	protected Double  baseSalary;
	/**
	 *基本工资
	 */
	protected Double  posSalary;
	/**
	 *序号
	 */
	protected String  xh;
	/**
	 *照片
	 */
	protected String  zp;
	/**
	 *员工编号
	 */
	protected Long  ygbh;
	/**
	 *姓名
	 */
	protected String  xm;
	/**
	 *性别
	 */
	protected String  xb;
	/**
	 *出生日期
	 */
	protected java.util.Date  csrq;
	/**
	 *婚姻状况
	 */
	protected String  hyzk;
	/**
	 *曾用名
	 */
	protected String  cym;
	/**
	 *民族
	 */
	protected String  mz;
	/**
	 *籍贯
	 */
	protected String  jg;
	/**
	 *职称专业
	 */
	protected String  zczy;
	/**
	 *文化程度
	 */
	protected String  whcd;
	/**
	 *参加工作时间
	 */
	protected java.util.Date  cjgzsj;
	/**
	 *毕业院校
	 */
	protected String  byyx;
	/**
	 *政治面貌
	 */
	protected String  zzmm;
	/**
	 *专业
	 */
	protected String  zy;
	/**
	 *身份证号码
	 */
	protected String  sfzhm;
	/**
	 *职称
	 */
	protected String  zc;
	/**
	 *户籍
	 */
	protected String  hj;
	/**
	 *是否有传染病史
	 */
	protected String  sfycrbs;
	/**
	 *是否有遗传病史
	 */
	protected String  sfyycbs;
	/**
	 *社会保险电脑号
	 */
	protected String  shbxdnh;
	/**
	 *利手
	 */
	protected String  ls;
	/**
	 *特长爱好
	 */
	protected String  tzah;
	/**
	 *户籍所在地
	 */
	protected String  hjszd;
	/**
	 *配偶姓名
	 */
	protected String  poxm;
	/**
	 *父母居住地
	 */
	protected String  fmjzd;
	/**
	 *配偶身份证号码
	 */
	protected String  posfzhm;
	/**
	 *配偶居住地
	 */
	protected String  pojzd;
	/**
	 *通讯地址
	 */
	protected String  txdz;
	/**
	 *手机号码
	 */
	protected String  sjhm;
	/**
	 *家庭电话
	 */
	protected String  jtdh;
	/**
	 *手机短号
	 */
	protected String  sjdh;
	/**
	 *紧急联系人
	 */
	protected String  jjlxr;
	/**
	 *交行卡号
	 */
	protected String  jxkh;
	/**
	 *紧急联系人电话
	 */
	protected String  jjlxrdh;
	/**
	 *个人邮箱
	 */
	protected String  gryx;
	/**
	 *QQ号码
	 */
	protected String  QQhm;
	/**
	 *微信
	 */
	protected String  wx;
	
	//审批状体
	protected String processStatus;
	//创建人
	protected String creator;
	//创建时间
	protected java.util.Date createTime;
	//工单号
	protected String globalFlowNo;
	
	//申请人所属部门
	protected String creatorOrgName;
	//入职日期
	protected java.util.Date entryTime;
	
	protected Long  runId=0L;
	
	/**
	 *入职信息列表
	 */
	protected List<EntryJtcy> entryJtcyList=new ArrayList<EntryJtcy>();
	/**
	 *入职信息列表
	 */
	protected List<EntryXxjl> entryXxjlList=new ArrayList<EntryXxjl>();
	/**
	 *入职信息列表
	 */
	protected List<EntryGzjl> entryGzjlList=new ArrayList<EntryGzjl>();
	/**
	 *入职信息列表
	 */
	protected List<EntryZyzc> entryZyzcList=new ArrayList<EntryZyzc>();
	/**
	 *入职信息列表
	 */
	protected List<EntryZyzg> entryZyzgList=new ArrayList<EntryZyzg>();
	/**
	 *入职信息列表
	 */
	protected List<EntryZzzg> entryZzzgList=new ArrayList<EntryZzzg>();
	/**
	 *子女信息
	 */
	protected List<EntryChildren> entryChildrenList=new ArrayList<EntryChildren>();
	
	
	public List<EntryChildren> getEntryChildrenList() {
		return entryChildrenList;
	}
	public void setEntryChildrenList(List<EntryChildren> entryChildrenList) {
		this.entryChildrenList = entryChildrenList;
	}
	public List<EntryZzzg> getEntryZzzgList() {
		return entryZzzgList;
	}
	public void setEntryZzzgList(List<EntryZzzg> entryZzzgList) {
		this.entryZzzgList = entryZzzgList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setXh(String xh) 
	{
		this.xh = xh;
	}
	/**
	 * 返回 序号
	 * @return
	 */
	public String getXh() 
	{
		return this.xh;
	}
	public void setZp(String zp) 
	{
		this.zp = zp;
	}
	/**
	 * 返回 照片
	 * @return
	 */
	public String getZp() 
	{
		return this.zp;
	}
	public void setYgbh(Long ygbh) 
	{
		this.ygbh = ygbh;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public Long getYgbh() 
	{
		return this.ygbh;
	}
	public void setXm(String xm) 
	{
		this.xm = xm;
	}
	/**
	 * 返回 姓名
	 * @return
	 */
	public String getXm() 
	{
		return this.xm;
	}
	public void setXb(String xb) 
	{
		this.xb = xb;
	}
	/**
	 * 返回 性别
	 * @return
	 */
	public String getXb() 
	{
		return this.xb;
	}
	public void setCsrq(java.util.Date csrq) 
	{
		this.csrq = csrq;
	}
	/**
	 * 返回 出生日期
	 * @return
	 */
	public java.util.Date getCsrq() 
	{
		return this.csrq;
	}
	public void setHyzk(String hyzk) 
	{
		this.hyzk = hyzk;
	}
	/**
	 * 返回 婚姻状况
	 * @return
	 */
	public String getHyzk() 
	{
		return this.hyzk;
	}
	public void setCym(String cym) 
	{
		this.cym = cym;
	}
	/**
	 * 返回 曾用名
	 * @return
	 */
	public String getCym() 
	{
		return this.cym;
	}
	public void setMz(String mz) 
	{
		this.mz = mz;
	}
	/**
	 * 返回 民族
	 * @return
	 */
	public String getMz() 
	{
		return this.mz;
	}
	public void setJg(String jg) 
	{
		this.jg = jg;
	}
	/**
	 * 返回 籍贯
	 * @return
	 */
	public String getJg() 
	{
		return this.jg;
	}
	public void setZczy(String zczy) 
	{
		this.zczy = zczy;
	}
	/**
	 * 返回 职称专业
	 * @return
	 */
	public String getZczy() 
	{
		return this.zczy;
	}
	public void setWhcd(String whcd) 
	{
		this.whcd = whcd;
	}
	/**
	 * 返回 文化程度
	 * @return
	 */
	public String getWhcd() 
	{
		return this.whcd;
	}
	public void setCjgzsj(java.util.Date cjgzsj) 
	{
		this.cjgzsj = cjgzsj;
	}
	/**
	 * 返回 参加工作时间
	 * @return
	 */
	public java.util.Date getCjgzsj() 
	{
		return this.cjgzsj;
	}
	public void setByyx(String byyx) 
	{
		this.byyx = byyx;
	}
	/**
	 * 返回 毕业院校
	 * @return
	 */
	public String getByyx() 
	{
		return this.byyx;
	}
	public void setZzmm(String zzmm) 
	{
		this.zzmm = zzmm;
	}
	/**
	 * 返回 政治面貌
	 * @return
	 */
	public String getZzmm() 
	{
		return this.zzmm;
	}
	public void setZy(String zy) 
	{
		this.zy = zy;
	}
	/**
	 * 返回 专业
	 * @return
	 */
	public String getZy() 
	{
		return this.zy;
	}
	public void setSfzhm(String sfzhm) 
	{
		this.sfzhm = sfzhm;
	}
	/**
	 * 返回 身份证号码
	 * @return
	 */
	public String getSfzhm() 
	{
		return this.sfzhm;
	}
	public void setZc(String zc) 
	{
		this.zc = zc;
	}
	/**
	 * 返回 职称
	 * @return
	 */
	public String getZc() 
	{
		return this.zc;
	}
	public void setHj(String hj) 
	{
		this.hj = hj;
	}
	/**
	 * 返回 户籍
	 * @return
	 */
	public String getHj() 
	{
		return this.hj;
	}
	public void setSfycrbs(String sfycrbs) 
	{
		this.sfycrbs = sfycrbs;
	}
	/**
	 * 返回 是否有传染病史
	 * @return
	 */
	public String getSfycrbs() 
	{
		return this.sfycrbs;
	}
	public void setSfyycbs(String sfyycbs) 
	{
		this.sfyycbs = sfyycbs;
	}
	/**
	 * 返回 是否有遗传病史
	 * @return
	 */
	public String getSfyycbs() 
	{
		return this.sfyycbs;
	}
	public void setShbxdnh(String shbxdnh) 
	{
		this.shbxdnh = shbxdnh;
	}
	/**
	 * 返回 社会保险电脑号
	 * @return
	 */
	public String getShbxdnh() 
	{
		return this.shbxdnh;
	}
	public void setLs(String ls) 
	{
		this.ls = ls;
	}
	/**
	 * 返回 利手
	 * @return
	 */
	public String getLs() 
	{
		return this.ls;
	}
	public void setTzah(String tzah) 
	{
		this.tzah = tzah;
	}
	/**
	 * 返回 特长爱好
	 * @return
	 */
	public String getTzah() 
	{
		return this.tzah;
	}
	public void setHjszd(String hjszd) 
	{
		this.hjszd = hjszd;
	}
	/**
	 * 返回 户籍所在地
	 * @return
	 */
	public String getHjszd() 
	{
		return this.hjszd;
	}
	public void setPoxm(String poxm) 
	{
		this.poxm = poxm;
	}
	/**
	 * 返回 配偶姓名
	 * @return
	 */
	public String getPoxm() 
	{
		return this.poxm;
	}
	public void setFmjzd(String fmjzd) 
	{
		this.fmjzd = fmjzd;
	}
	/**
	 * 返回 父母居住地
	 * @return
	 */
	public String getFmjzd() 
	{
		return this.fmjzd;
	}
	public void setPosfzhm(String posfzhm) 
	{
		this.posfzhm = posfzhm;
	}
	/**
	 * 返回 配偶身份证号码
	 * @return
	 */
	public String getPosfzhm() 
	{
		return this.posfzhm;
	}
	public void setPojzd(String pojzd) 
	{
		this.pojzd = pojzd;
	}
	/**
	 * 返回 配偶居住地
	 * @return
	 */
	public String getPojzd() 
	{
		return this.pojzd;
	}
	public void setTxdz(String txdz) 
	{
		this.txdz = txdz;
	}
	/**
	 * 返回 通讯地址
	 * @return
	 */
	public String getTxdz() 
	{
		return this.txdz;
	}
	public void setSjhm(String sjhm) 
	{
		this.sjhm = sjhm;
	}
	/**
	 * 返回 手机号码
	 * @return
	 */
	public String getSjhm() 
	{
		return this.sjhm;
	}
	public void setJtdh(String jtdh) 
	{
		this.jtdh = jtdh;
	}
	/**
	 * 返回 家庭电话
	 * @return
	 */
	public String getJtdh() 
	{
		return this.jtdh;
	}
	public void setSjdh(String sjdh) 
	{
		this.sjdh = sjdh;
	}
	/**
	 * 返回 手机短号
	 * @return
	 */
	public String getSjdh() 
	{
		return this.sjdh;
	}
	public void setJjlxr(String jjlxr) 
	{
		this.jjlxr = jjlxr;
	}
	/**
	 * 返回 紧急联系人
	 * @return
	 */
	public String getJjlxr() 
	{
		return this.jjlxr;
	}
	public void setJxkh(String jxkh) 
	{
		this.jxkh = jxkh;
	}
	/**
	 * 返回 交行卡号
	 * @return
	 */
	public String getJxkh() 
	{
		return this.jxkh;
	}
	public void setJjlxrdh(String jjlxrdh) 
	{
		this.jjlxrdh = jjlxrdh;
	}
	/**
	 * 返回 紧急联系人电话
	 * @return
	 */
	public String getJjlxrdh() 
	{
		return this.jjlxrdh;
	}
	public void setGryx(String gryx) 
	{
		this.gryx = gryx;
	}
	/**
	 * 返回 个人邮箱
	 * @return
	 */
	public String getGryx() 
	{
		return this.gryx;
	}
	public void setQQhm(String QQhm) 
	{
		this.QQhm = QQhm;
	}
	/**
	 * 返回 QQ号码
	 * @return
	 */
	public String getQQhm() 
	{
		return this.QQhm;
	}
	public void setWx(String wx) 
	{
		this.wx = wx;
	}
	/**
	 * 返回 微信
	 * @return
	 */
	public String getWx() 
	{
		return this.wx;
	}
	public void setEntryJtcyList(List<EntryJtcy> entryJtcyList) 
	{
		this.entryJtcyList = entryJtcyList;
	}
	/**
	 * 返回 入职信息列表
	 * @return
	 */
	public List<EntryJtcy> getEntryJtcyList() 
	{
		return this.entryJtcyList;
	}
	public void setEntryXxjlList(List<EntryXxjl> entryXxjlList) 
	{
		this.entryXxjlList = entryXxjlList;
	}
	/**
	 * 返回 入职信息列表
	 * @return
	 */
	public List<EntryXxjl> getEntryXxjlList() 
	{
		return this.entryXxjlList;
	}
	public void setEntryGzjlList(List<EntryGzjl> entryGzjlList) 
	{
		this.entryGzjlList = entryGzjlList;
	}
	/**
	 * 返回 入职信息列表
	 * @return
	 */
	public List<EntryGzjl> getEntryGzjlList() 
	{
		return this.entryGzjlList;
	}
	public void setEntryZyzcList(List<EntryZyzc> entryZyzcList) 
	{
		this.entryZyzcList = entryZyzcList;
	}
	/**
	 * 返回 入职信息列表
	 * @return
	 */
	public List<EntryZyzc> getEntryZyzcList() 
	{
		return this.entryZyzcList;
	}
	public void setEntryZyzgList(List<EntryZyzg> entryZyzgList) 
	{
		this.entryZyzgList = entryZyzgList;
	}
	/**
	 * 返回 入职信息列表
	 * @return
	 */
	public List<EntryZyzg> getEntryZyzgList() 
	{
		return this.entryZyzgList;
	}
	public Long getRunId() {
		return runId;
	}
	public void setRunId(Long runId) {
		this.runId = runId;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof Entry)) 
		{
			return false;
		}
		Entry rhs = (Entry) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.xh, rhs.xh)
		.append(this.zp, rhs.zp)
		.append(this.ygbh, rhs.ygbh)
		.append(this.xm, rhs.xm)
		.append(this.xb, rhs.xb)
		.append(this.csrq, rhs.csrq)
		.append(this.hyzk, rhs.hyzk)
		.append(this.cym, rhs.cym)
		.append(this.mz, rhs.mz)
		.append(this.jg, rhs.jg)
		.append(this.zczy, rhs.zczy)
		.append(this.whcd, rhs.whcd)
		.append(this.cjgzsj, rhs.cjgzsj)
		.append(this.byyx, rhs.byyx)
		.append(this.zzmm, rhs.zzmm)
		.append(this.zy, rhs.zy)
		.append(this.sfzhm, rhs.sfzhm)
		.append(this.zc, rhs.zc)
		.append(this.hj, rhs.hj)
		.append(this.sfycrbs, rhs.sfycrbs)
		.append(this.sfyycbs, rhs.sfyycbs)
		.append(this.shbxdnh, rhs.shbxdnh)
		.append(this.ls, rhs.ls)
		.append(this.tzah, rhs.tzah)
		.append(this.hjszd, rhs.hjszd)
		.append(this.poxm, rhs.poxm)
		.append(this.fmjzd, rhs.fmjzd)
		.append(this.posfzhm, rhs.posfzhm)
		.append(this.pojzd, rhs.pojzd)
		.append(this.txdz, rhs.txdz)
		.append(this.sjhm, rhs.sjhm)
		.append(this.jtdh, rhs.jtdh)
		.append(this.sjdh, rhs.sjdh)
		.append(this.jjlxr, rhs.jjlxr)
		.append(this.jxkh, rhs.jxkh)
		.append(this.jjlxrdh, rhs.jjlxrdh)
		.append(this.gryx, rhs.gryx)
		.append(this.QQhm, rhs.QQhm)
		.append(this.wx, rhs.wx)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.xh) 
		.append(this.zp) 
		.append(this.ygbh) 
		.append(this.xm) 
		.append(this.xb) 
		.append(this.csrq) 
		.append(this.hyzk) 
		.append(this.cym) 
		.append(this.mz) 
		.append(this.jg) 
		.append(this.zczy) 
		.append(this.whcd) 
		.append(this.cjgzsj) 
		.append(this.byyx) 
		.append(this.zzmm) 
		.append(this.zy) 
		.append(this.sfzhm) 
		.append(this.zc) 
		.append(this.hj) 
		.append(this.sfycrbs) 
		.append(this.sfyycbs) 
		.append(this.shbxdnh) 
		.append(this.ls) 
		.append(this.tzah) 
		.append(this.hjszd) 
		.append(this.poxm) 
		.append(this.fmjzd) 
		.append(this.posfzhm) 
		.append(this.pojzd) 
		.append(this.txdz) 
		.append(this.sjhm) 
		.append(this.jtdh) 
		.append(this.sjdh) 
		.append(this.jjlxr) 
		.append(this.jxkh) 
		.append(this.jjlxrdh) 
		.append(this.gryx) 
		.append(this.QQhm) 
		.append(this.wx) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("xh", this.xh) 
		.append("zp", this.zp) 
		.append("ygbh", this.ygbh) 
		.append("xm", this.xm) 
		.append("xb", this.xb) 
		.append("csrq", this.csrq) 
		.append("hyzk", this.hyzk) 
		.append("cym", this.cym) 
		.append("mz", this.mz) 
		.append("jg", this.jg) 
		.append("zczy", this.zczy) 
		.append("whcd", this.whcd) 
		.append("cjgzsj", this.cjgzsj) 
		.append("byyx", this.byyx) 
		.append("zzmm", this.zzmm) 
		.append("zy", this.zy) 
		.append("sfzhm", this.sfzhm) 
		.append("zc", this.zc) 
		.append("hj", this.hj) 
		.append("sfycrbs", this.sfycrbs) 
		.append("sfyycbs", this.sfyycbs) 
		.append("shbxdnh", this.shbxdnh) 
		.append("ls", this.ls) 
		.append("tzah", this.tzah) 
		.append("hjszd", this.hjszd) 
		.append("poxm", this.poxm) 
		.append("fmjzd", this.fmjzd) 
		.append("posfzhm", this.posfzhm) 
		.append("pojzd", this.pojzd) 
		.append("txdz", this.txdz) 
		.append("sjhm", this.sjhm) 
		.append("jtdh", this.jtdh) 
		.append("sjdh", this.sjdh) 
		.append("jjlxr", this.jjlxr) 
		.append("jxkh", this.jxkh) 
		.append("jjlxrdh", this.jjlxrdh) 
		.append("gryx", this.gryx) 
		.append("QQhm", this.QQhm) 
		.append("wx", this.wx) 
		.toString();
	}
	public String getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		//TimeUtil.getDateString(createTime);
		return TimeUtil.getDateString(createTime);
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public String getGlobalFlowNo() {
		return globalFlowNo;
	}
	public void setGlobalFlowNo(String globalFlowNo) {
		this.globalFlowNo = globalFlowNo;
	}
	public String getCreatorOrgName() {
		return creatorOrgName;
	}
	public void setCreatorOrgName(String creatorOrgName) {
		this.creatorOrgName = creatorOrgName;
	}
	public String getEntryTime() {
		if(entryTime!=null){
			return TimeUtil.getDateString(entryTime);
		}
		return "";
	}
	public void setEntryTime(java.util.Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Double getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(Double baseSalary) {
		this.baseSalary = baseSalary;
	}
	public Double getPosSalary() {
		return posSalary;
	}
	public void setPosSalary(Double posSalary) {
		this.posSalary = posSalary;
	}

}