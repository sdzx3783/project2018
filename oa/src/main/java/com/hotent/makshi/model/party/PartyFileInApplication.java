package com.hotent.makshi.model.party;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import com.hotent.core.model.BaseModel;
/**
 * 对象功能:党员档案转入申请 Model对象
 */
public class PartyFileInApplication extends BaseModel
{
	//主键
	protected Long id;
	/**
	 *员工编号
	 */
	protected String  user_num;
	/**
	 *党员基本情况编号
	 */
	protected String  party_num;
	/**
	 *与党组织联系情况
	 */
	protected String  contact_situation;
	/**
	 *工号
	 */
	protected String  account;
	/**
	 *流出支部联系人
	 */
	protected String  out_contact;
	/**
	 *入党时间
	 */
	protected java.util.Date  join_party_date;
	/**
	 *流出支部联系人联系方式
	 */
	protected String  out_tel;
	/**
	 *转正时间
	 */
	protected java.util.Date  regular_date;
	/**
	 *流入支部联系人
	 */
	protected String  in_contact;
	/**
	 *入党时所在支部
	 */
	protected String  join_party_where;
	/**
	 *流入支部联系人联系方式
	 */
	protected String  in_tel;
	/**
	 *转正时所在支部
	 */
	protected String  regular_where;
	/**
	 *流入地（单位）
	 */
	protected String  place_of_influx;
	/**
	 *入党介绍人
	 */
	protected String  introducer;
	/**
	 *失去联系日期
	 */
	protected java.util.Date  lost_date;
	/**
	 *所在支部
	 */
	protected String  branch;
	/**
	 *出国日期
	 */
	protected java.util.Date  abroad_date;
	/**
	 *进入党支部日期
	 */
	protected java.util.Date  join_branch_date;
	/**
	 *出国原因
	 */
	protected String  abroad_reason;
	/**
	 *现任党内职务
	 */
	protected String  party_post;
	/**
	 *出国后与组织联系情况
	 */
	protected String  abroad_situation;
	/**
	 *组织关系所在单位
	 */
	protected String  org_relationship;
	/**
	 *党籍处理方式
	 */
	protected String  party_handling;
	/**
	 *流出日期
	 */
	protected java.util.Date  out_date;
	/**
	 *回国日期
	 */
	protected java.util.Date  return_date;
	/**
	 *外出流向
	 */
	protected String  outgoing_flow;
	/**
	 *恢复组织生活情况
	 */
	protected String  restore_org_life;
	/**
	 *外出类型
	 */
	protected String  out_type;
	/**
	 *审核人
	 */
	protected String  reviewer;
	/**
	 *外出原因
	 */
	protected String  out_reason;
	/**
	 *支部书记审核
	 */
	protected String  branch_review;
	/**
	 *活动证号码
	 */
	protected String  activity_num;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUser_num(String user_num) 
	{
		this.user_num = user_num;
	}
	/**
	 * 返回 员工编号
	 * @return
	 */
	public String getUser_num() 
	{
		return this.user_num;
	}
	public void setParty_num(String party_num) 
	{
		this.party_num = party_num;
	}
	/**
	 * 返回 党员基本情况编号
	 * @return
	 */
	public String getParty_num() 
	{
		return this.party_num;
	}
	public void setContact_situation(String contact_situation) 
	{
		this.contact_situation = contact_situation;
	}
	/**
	 * 返回 与党组织联系情况
	 * @return
	 */
	public String getContact_situation() 
	{
		return this.contact_situation;
	}
	public void setAccount(String account) 
	{
		this.account = account;
	}
	/**
	 * 返回 工号
	 * @return
	 */
	public String getAccount() 
	{
		return this.account;
	}
	public void setOut_contact(String out_contact) 
	{
		this.out_contact = out_contact;
	}
	/**
	 * 返回 流出支部联系人
	 * @return
	 */
	public String getOut_contact() 
	{
		return this.out_contact;
	}
	public void setJoin_party_date(java.util.Date join_party_date) 
	{
		this.join_party_date = join_party_date;
	}
	/**
	 * 返回 入党时间
	 * @return
	 */
	public java.util.Date getJoin_party_date() 
	{
		return this.join_party_date;
	}
	public void setOut_tel(String out_tel) 
	{
		this.out_tel = out_tel;
	}
	/**
	 * 返回 流出支部联系人联系方式
	 * @return
	 */
	public String getOut_tel() 
	{
		return this.out_tel;
	}
	public void setRegular_date(java.util.Date regular_date) 
	{
		this.regular_date = regular_date;
	}
	/**
	 * 返回 转正时间
	 * @return
	 */
	public java.util.Date getRegular_date() 
	{
		return this.regular_date;
	}
	public void setIn_contact(String in_contact) 
	{
		this.in_contact = in_contact;
	}
	/**
	 * 返回 流入支部联系人
	 * @return
	 */
	public String getIn_contact() 
	{
		return this.in_contact;
	}
	public void setJoin_party_where(String join_party_where) 
	{
		this.join_party_where = join_party_where;
	}
	/**
	 * 返回 入党时所在支部
	 * @return
	 */
	public String getJoin_party_where() 
	{
		return this.join_party_where;
	}
	public void setIn_tel(String in_tel) 
	{
		this.in_tel = in_tel;
	}
	/**
	 * 返回 流入支部联系人联系方式
	 * @return
	 */
	public String getIn_tel() 
	{
		return this.in_tel;
	}
	public void setRegular_where(String regular_where) 
	{
		this.regular_where = regular_where;
	}
	/**
	 * 返回 转正时所在支部
	 * @return
	 */
	public String getRegular_where() 
	{
		return this.regular_where;
	}
	public void setPlace_of_influx(String place_of_influx) 
	{
		this.place_of_influx = place_of_influx;
	}
	/**
	 * 返回 流入地（单位）
	 * @return
	 */
	public String getPlace_of_influx() 
	{
		return this.place_of_influx;
	}
	public void setIntroducer(String introducer) 
	{
		this.introducer = introducer;
	}
	/**
	 * 返回 入党介绍人
	 * @return
	 */
	public String getIntroducer() 
	{
		return this.introducer;
	}
	public void setLost_date(java.util.Date lost_date) 
	{
		this.lost_date = lost_date;
	}
	/**
	 * 返回 失去联系日期
	 * @return
	 */
	public java.util.Date getLost_date() 
	{
		return this.lost_date;
	}
	public void setBranch(String branch) 
	{
		this.branch = branch;
	}
	/**
	 * 返回 所在支部
	 * @return
	 */
	public String getBranch() 
	{
		return this.branch;
	}
	public void setAbroad_date(java.util.Date abroad_date) 
	{
		this.abroad_date = abroad_date;
	}
	/**
	 * 返回 出国日期
	 * @return
	 */
	public java.util.Date getAbroad_date() 
	{
		return this.abroad_date;
	}
	public void setJoin_branch_date(java.util.Date join_branch_date) 
	{
		this.join_branch_date = join_branch_date;
	}
	/**
	 * 返回 进入党支部日期
	 * @return
	 */
	public java.util.Date getJoin_branch_date() 
	{
		return this.join_branch_date;
	}
	public void setAbroad_reason(String abroad_reason) 
	{
		this.abroad_reason = abroad_reason;
	}
	/**
	 * 返回 出国原因
	 * @return
	 */
	public String getAbroad_reason() 
	{
		return this.abroad_reason;
	}
	public void setParty_post(String party_post) 
	{
		this.party_post = party_post;
	}
	/**
	 * 返回 现任党内职务
	 * @return
	 */
	public String getParty_post() 
	{
		return this.party_post;
	}
	public void setAbroad_situation(String abroad_situation) 
	{
		this.abroad_situation = abroad_situation;
	}
	/**
	 * 返回 出国后与组织联系情况
	 * @return
	 */
	public String getAbroad_situation() 
	{
		return this.abroad_situation;
	}
	public void setOrg_relationship(String org_relationship) 
	{
		this.org_relationship = org_relationship;
	}
	/**
	 * 返回 组织关系所在单位
	 * @return
	 */
	public String getOrg_relationship() 
	{
		return this.org_relationship;
	}
	public void setParty_handling(String party_handling) 
	{
		this.party_handling = party_handling;
	}
	/**
	 * 返回 党籍处理方式
	 * @return
	 */
	public String getParty_handling() 
	{
		return this.party_handling;
	}
	public void setOut_date(java.util.Date out_date) 
	{
		this.out_date = out_date;
	}
	/**
	 * 返回 流出日期
	 * @return
	 */
	public java.util.Date getOut_date() 
	{
		return this.out_date;
	}
	public void setReturn_date(java.util.Date return_date) 
	{
		this.return_date = return_date;
	}
	/**
	 * 返回 回国日期
	 * @return
	 */
	public java.util.Date getReturn_date() 
	{
		return this.return_date;
	}
	public void setOutgoing_flow(String outgoing_flow) 
	{
		this.outgoing_flow = outgoing_flow;
	}
	/**
	 * 返回 外出流向
	 * @return
	 */
	public String getOutgoing_flow() 
	{
		return this.outgoing_flow;
	}
	public void setRestore_org_life(String restore_org_life) 
	{
		this.restore_org_life = restore_org_life;
	}
	/**
	 * 返回 恢复组织生活情况
	 * @return
	 */
	public String getRestore_org_life() 
	{
		return this.restore_org_life;
	}
	public void setOut_type(String out_type) 
	{
		this.out_type = out_type;
	}
	/**
	 * 返回 外出类型
	 * @return
	 */
	public String getOut_type() 
	{
		return this.out_type;
	}
	public void setReviewer(String reviewer) 
	{
		this.reviewer = reviewer;
	}
	/**
	 * 返回 审核人
	 * @return
	 */
	public String getReviewer() 
	{
		return this.reviewer;
	}
	public void setOut_reason(String out_reason) 
	{
		this.out_reason = out_reason;
	}
	/**
	 * 返回 外出原因
	 * @return
	 */
	public String getOut_reason() 
	{
		return this.out_reason;
	}
	public void setBranch_review(String branch_review) 
	{
		this.branch_review = branch_review;
	}
	/**
	 * 返回 支部书记审核
	 * @return
	 */
	public String getBranch_review() 
	{
		return this.branch_review;
	}
	public void setActivity_num(String activity_num) 
	{
		this.activity_num = activity_num;
	}
	/**
	 * 返回 活动证号码
	 * @return
	 */
	public String getActivity_num() 
	{
		return this.activity_num;
	}
   	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) 
	{
		if (!(object instanceof PartyFileInApplication)) 
		{
			return false;
		}
		PartyFileInApplication rhs = (PartyFileInApplication) object;
		return new EqualsBuilder()
		.append(this.id, rhs.id)
		.append(this.user_num, rhs.user_num)
		.append(this.party_num, rhs.party_num)
		.append(this.contact_situation, rhs.contact_situation)
		.append(this.account, rhs.account)
		.append(this.out_contact, rhs.out_contact)
		.append(this.join_party_date, rhs.join_party_date)
		.append(this.out_tel, rhs.out_tel)
		.append(this.regular_date, rhs.regular_date)
		.append(this.in_contact, rhs.in_contact)
		.append(this.join_party_where, rhs.join_party_where)
		.append(this.in_tel, rhs.in_tel)
		.append(this.regular_where, rhs.regular_where)
		.append(this.place_of_influx, rhs.place_of_influx)
		.append(this.introducer, rhs.introducer)
		.append(this.lost_date, rhs.lost_date)
		.append(this.branch, rhs.branch)
		.append(this.abroad_date, rhs.abroad_date)
		.append(this.join_branch_date, rhs.join_branch_date)
		.append(this.abroad_reason, rhs.abroad_reason)
		.append(this.party_post, rhs.party_post)
		.append(this.abroad_situation, rhs.abroad_situation)
		.append(this.org_relationship, rhs.org_relationship)
		.append(this.party_handling, rhs.party_handling)
		.append(this.out_date, rhs.out_date)
		.append(this.return_date, rhs.return_date)
		.append(this.outgoing_flow, rhs.outgoing_flow)
		.append(this.restore_org_life, rhs.restore_org_life)
		.append(this.out_type, rhs.out_type)
		.append(this.reviewer, rhs.reviewer)
		.append(this.out_reason, rhs.out_reason)
		.append(this.branch_review, rhs.branch_review)
		.append(this.activity_num, rhs.activity_num)
		.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return new HashCodeBuilder(-82280557, -700257973)
		.append(this.id)
		.append(this.user_num) 
		.append(this.party_num) 
		.append(this.contact_situation) 
		.append(this.account) 
		.append(this.out_contact) 
		.append(this.join_party_date) 
		.append(this.out_tel) 
		.append(this.regular_date) 
		.append(this.in_contact) 
		.append(this.join_party_where) 
		.append(this.in_tel) 
		.append(this.regular_where) 
		.append(this.place_of_influx) 
		.append(this.introducer) 
		.append(this.lost_date) 
		.append(this.branch) 
		.append(this.abroad_date) 
		.append(this.join_branch_date) 
		.append(this.abroad_reason) 
		.append(this.party_post) 
		.append(this.abroad_situation) 
		.append(this.org_relationship) 
		.append(this.party_handling) 
		.append(this.out_date) 
		.append(this.return_date) 
		.append(this.outgoing_flow) 
		.append(this.restore_org_life) 
		.append(this.out_type) 
		.append(this.reviewer) 
		.append(this.out_reason) 
		.append(this.branch_review) 
		.append(this.activity_num) 
		.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id",this.id)
		.append("user_num", this.user_num) 
		.append("party_num", this.party_num) 
		.append("contact_situation", this.contact_situation) 
		.append("account", this.account) 
		.append("out_contact", this.out_contact) 
		.append("join_party_date", this.join_party_date) 
		.append("out_tel", this.out_tel) 
		.append("regular_date", this.regular_date) 
		.append("in_contact", this.in_contact) 
		.append("join_party_where", this.join_party_where) 
		.append("in_tel", this.in_tel) 
		.append("regular_where", this.regular_where) 
		.append("place_of_influx", this.place_of_influx) 
		.append("introducer", this.introducer) 
		.append("lost_date", this.lost_date) 
		.append("branch", this.branch) 
		.append("abroad_date", this.abroad_date) 
		.append("join_branch_date", this.join_branch_date) 
		.append("abroad_reason", this.abroad_reason) 
		.append("party_post", this.party_post) 
		.append("abroad_situation", this.abroad_situation) 
		.append("org_relationship", this.org_relationship) 
		.append("party_handling", this.party_handling) 
		.append("out_date", this.out_date) 
		.append("return_date", this.return_date) 
		.append("outgoing_flow", this.outgoing_flow) 
		.append("restore_org_life", this.restore_org_life) 
		.append("out_type", this.out_type) 
		.append("reviewer", this.reviewer) 
		.append("out_reason", this.out_reason) 
		.append("branch_review", this.branch_review) 
		.append("activity_num", this.activity_num) 
		.toString();
	}

}