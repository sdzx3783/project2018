package com.hotent.platform.model.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.hotent.core.api.org.model.ISysUser;
import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.api.util.PropertyUtil;
import com.hotent.core.model.BaseModel;
import com.hotent.core.util.AppUtil;
import com.hotent.core.util.BeanUtils;
import com.hotent.core.util.StringUtil;
import com.hotent.platform.service.system.SysRoleService;
import com.hotent.platform.webservice.api.util.adapter.GrantedAuthorityAdapter;

/**
 * 对象功能:用户表 Model对象 
 * 开发公司:广州宏天软件有限公司 
 * 开发人员:hotent 
 * 创建时间:2011-11-03 16:02:45
 */
public class SysUser extends BaseModel implements UserDetails ,ISysUser{
	public final static String SEARCH_BY_ROL = "1";// 从角色
	public final static String SEARCH_BY_ORG = "2";// 从组织
	public final static String SEARCH_BY_POS = "3";// 从岗位
	public final static String SEARCH_BY_ONL = "4";// 从在线
	
	private static ThreadLocal<Collection<GrantedAuthority>> roleList=new ThreadLocal<Collection<GrantedAuthority>>();
	
	public static void removeRoleList(){
		roleList.remove();
	}

	/**
	 * 账号未锁定
	 */
	public final static Short UN_LOCKED = 0;
	/**
	 * 账号被锁定
	 */
	public final static Short LOCKED = 1;

	/**
	 * 账号未期
	 */
	public final static Short UN_EXPIRED = 0;

	/**
	 * 账号过期
	 */
	public final static Short EXPIRED = 1;

	/**
	 * 账号激活
	 */
	public final static Short STATUS_OK = 1;

	/**
	 * 账号禁用
	 */
	public final static Short STATUS_NO = 0;
	/**
	 * 账号删除
	 */
	public final static Short STATUS_Del = -1;
	
	/**
	 * 数据来自系统添加
	 */
	public final static Short FROMTYPE_DB=0;
	/**
	 * 数据来自AD同步,并在系统中未被设置
	 */
	public final static Short FROMTYPE_AD=1;
	/**
	 * 数据来自AD同步,并在系统中被设置过
	 */
	public final static Short FROMTYPE_AD_SET=2;

	// userOrgId
	protected Long userOrgId;
	// userId
	protected Long userId;
	// 姓名
	protected String fullname;
	// 帐号
	protected String account;
	//账号排序
	protected Integer accountNum;
	// 密码
	protected String password;
	// 是否过期
	protected Short isExpired;
	// 是否锁定
	protected Short isLock;
	// 状态
	protected Short status;
	// 邮箱
	protected String email;
	// 手机
	protected String mobile;
	// 电话
	protected String phone;

	// 性别
	protected String sex;
	// 照片
	protected String picture;
	protected String weixinid;

	
	
	
	// 入职日期
	protected Date entryDate;
	protected Date resignationDate;
	protected Date formalDate;
	protected Date updateTime;
	protected String updateor;
	protected String updateorId;
	protected String creator;
	protected String creatorId;
	
	//上级
	private Long[] superiorIds;
	
	//员工状态
	private String userStatus;
	
	//密码更新时间
	protected Date pwdUpdTime;
	//是否同步至微信(0 未同步，1，已同步，2.登陆过)
	protected int hasSyncToWx;
	
	/**
	 * 数据来源
	 */
	protected short fromType;
	
//	protected Long userPosId;
	//protected Long orgId;
	protected Long posId1;
	protected Long jobId;
	// 组织名称
	protected String orgName;
	protected String posName;
	protected String jobName;
	//生日
	private Date birthday;
	//距离生日天数
	private int intervalDay;
	//生日年龄
	private int birthAge;
	//手机短号
	private String sjdh;
	//劳动合同开始时间
	private Date laborcontstarttime;
	//劳动合同结束时间
	private Date laborcontendtime;
	
	
	public Integer getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(Integer accountNum) {
		this.accountNum = accountNum;
	}

	public Date getLaborcontstarttime() {
		return laborcontstarttime;
	}

	public void setLaborcontstarttime(Date laborcontstarttime) {
		this.laborcontstarttime = laborcontstarttime;
	}

	public Date getLaborcontendtime() {
		return laborcontendtime;
	}

	public void setLaborcontendtime(Date laborcontendtime) {
		this.laborcontendtime = laborcontendtime;
	}

	public String getSjdh() {
		return sjdh;
	}

	public void setSjdh(String sjdh) {
		this.sjdh = sjdh;
	}

	public int getIntervalDay() {
		return intervalDay;
	}
	
	public int getBirthAge() {
		return birthAge;
	}


	public void setBirthAge(int birthAge) {
		this.birthAge = birthAge;
	}


	public void setIntervalDay(int intervalDay) {
		this.intervalDay = intervalDay;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	

//	public Long getUserPosId() {
//		return userPosId;
//	}
//
//	public void setUserPosId(Long userPosId) {
//		this.userPosId = userPosId;
//	}

//	public Long getOrgId() {
//		return orgId;
//	}
//
//	public void setOrgId(Long orgId) {
//		this.orgId = orgId;
//	}

//	public Long getPosId() {
//		return posId;
//	}
//
//	public void setPosId(Long posId) {
//		this.posId = posId;
//	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Short getFromType() {
		return fromType;
	}
	
	public void setFromType(Short fromType) {
		this.fromType = fromType;
	}



	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	

	public Long getUserOrgId() {
		return userOrgId;
	}

	public void setUserOrgId(Long userOrgId) {
		this.userOrgId = userOrgId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 返回 userId
	 * 
	 * @return
	 */
	public Long getUserId() {
		return userId;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	/**
	 * 返回 姓名
	 * 
	 * @return
	 */
	public String getFullname() {
		return fullname;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 返回 帐号
	 * 
	 * @return
	 */
	public String getAccount() {
		return account;
	}

	public void setPassword(String password) {
		this.setPwdUpdTime(new Date());//更新密码时间
		this.password = password;
	}

	/**
	 * 返回 密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	public void setIsExpired(Short isExpired) {
		this.isExpired = isExpired;
	}

	/**
	 * 返回 是否过期
	 * 
	 * @return
	 */
	public Short getIsExpired() {
		return isExpired;
	}

	public void setIsLock(Short isLock) {
		this.isLock = isLock;
	}

	/**
	 * 返回 是否锁定
	 * 
	 * @return
	 */
	public Short getIsLock() {
		return isLock;
	}

	public String getWeixinid() {
		return weixinid;
	}

	public void setWeixinid(String weixinid) {
		this.weixinid = weixinid;
	}

	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * 返回 创建时间
	 * 
	 * @return
	 */
	public java.util.Date getCreatetime() {
		return createtime;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public int getHasSyncToWx() {
		return hasSyncToWx;
	}

	public void setHasSyncToWx(int hasSyncToWx) {
		this.hasSyncToWx = hasSyncToWx;
	}

	/**
	 * 返回 状态
	 * 
	 * @return
	 */
	public Short getStatus() {
		return status;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 返回 邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 返回 手机
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 返回 电话
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}
	
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	
	@Override
	public boolean equals(Object rhs) {
		
        if (rhs instanceof SysUser) {
            return this.account.equals(((SysUser) rhs).account);
        }
        return false;
    }

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.account.hashCode();

		
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("userId", this.userId).append("fullname", this.fullname).append("account", this.account)
				.append("password", this.password).append("isExpired", this.isExpired).append("isLock", this.isLock).append("createtime", this.createtime)
				.append("status", this.status).append("email", this.email).append("mobile", this.mobile).append("phone", this.phone)
				.append("orgName", this.orgName).append("sex", this.sex).append("picture", this.picture).append("retype").toString();
	}
	
	/**
	 * 返回角色。
	 * @return
	 */
	public String getRoles() {
		String str="";
		Collection<GrantedAuthority> roles= getAuthorities();
		for(GrantedAuthority role:roles){
			if("".equals(str)){
				str=role.getAuthority();
			}
			else{
				str+="," + role.getAuthority();
			}
		}
		return str;
	}
	

	/**
	 * 重写UserDetails 的getAuthorities方法。
	 * 
	 * <pre>
	 * 1.首先从缓存中读取。
	 * 2.判断帐号在缓存中是否存在帐号，若存在直接重缓存中读取。
	 * 3.如果不存在则从数据库中读取并加入缓存。
	 * 
	 * 目前角色支持两种方式。
	 * 1.用户和角色的映射。
	 * 2.部门和角色的映射。
	 * 
	 * 两种角色进行合并构成当前用户的角色。
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public @XmlJavaTypeAdapter(GrantedAuthorityAdapter.class) Collection<GrantedAuthority> getAuthorities() {
		if(roleList.get()!=null) return roleList.get();
		Collection<GrantedAuthority> rtnList= new ArrayList<GrantedAuthority>();
		SysRoleService sysRoleService = (SysRoleService) AppUtil.getBean(SysRoleService.class);
		SysOrg curOrg=(SysOrg) ContextUtil.getCurrentOrg();
		Long orgId=curOrg==null?0:curOrg.getOrgId();
		//Long orgId=(long) 0;
		Collection<String> totalRoleCol=  sysRoleService.getRolesByUserIdAndOrgId(userId, orgId);
		if(BeanUtils.isNotEmpty(totalRoleCol)){
			for(String role:totalRoleCol){
				rtnList.add(new GrantedAuthorityImpl(role));
			}
		}
		String admin=SysUser.getAdminAccount();
		// 添加超级管理员角色。
		if (admin.equals(this.account)) {
			rtnList.add(SystemConst.ROLE_GRANT_SUPER);
		}
		roleList.set(rtnList);
		return rtnList;
	}
	
	/**
	 * 取得管理员帐号。
	 * @return
	 */
	public static String getAdminAccount(){
		String admin=PropertyUtil.getByAlias("account");
		if(StringUtil.isEmpty(admin)){
			admin="admin";
		}
		return admin;
	}
	
	

	@Override
	public String getUsername() {
		return account;
	}

	@Override
	public boolean isAccountNonExpired() {
		if(isExpired==null) return true;
		if (isExpired.shortValue() == UN_EXPIRED.shortValue()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isAccountNonLocked() {
		if(isLock==null) return true;
		if (isLock.shortValue() == UN_LOCKED.shortValue()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return status == STATUS_OK ? true : false;

	}

	public Long[] getSuperiorIds() {
		return superiorIds;
	}

	public void setSuperiorIds(Long[] superiorIds) {
		this.superiorIds = superiorIds;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * pwdUpdTime
	 * @return  the pwdUpdTime
	 * @since   1.0.0
	 */
	
	public Date getPwdUpdTime() {
		return pwdUpdTime;
	}

	/**
	 * @param pwdUpdTime the pwdUpdTime to set
	 */
	public void setPwdUpdTime(Date pwdUpdTime) {
		this.pwdUpdTime = pwdUpdTime;
	}

	public Date getResignationDate() {
		return resignationDate;
	}

	public void setResignationDate(Date resignationDate) {
		this.resignationDate = resignationDate;
	}

	public Date getFormalDate() {
		return formalDate;
	}

	public void setFormalDate(Date formalDate) {
		this.formalDate = formalDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateor() {
		return updateor;
	}

	public void setUpdateor(String updateor) {
		this.updateor = updateor;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdateorId() {
		return updateorId;
	}

	public void setUpdateorId(String updateorId) {
		this.updateorId = updateorId;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Long getPosId1() {
		return posId1;
	}

	public void setPosId1(Long posId1) {
		this.posId1 = posId1;
	}

	

	
}