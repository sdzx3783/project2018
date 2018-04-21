package com.hotent.makshi.model.appPush;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:推送任务失败表 Model对象
 */
public class AppPushJobHistory extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3670784479890927641L;
	//主键
	protected Long id;
	/**
	 *推送任务id
	 */
	protected Long  jobId;
	/**
	 *流程实例Id
	 */
	protected Long  runId;
	/**
	 *流程任务id
	 */
	protected Long  taskId;
	/**
	 *组织id
	 */
	protected Long  orgId;
	
	/**
	 *消息类型 0：个人 1：组织
	 */
	protected String  pushType;
	/**
	 *友盟返回的错误码
	 */
	protected Long  error_code;
	/**
	 *消息接收人
	 */
	protected Long  userId;

	/**
	 *通知栏提示文字
	 */
	protected String  ticker;
	
	/**
	 *通知标题
	 */
	protected String  title;
	
	/**
	 *推送的消息内容
	 */
	protected String  content;
	
	/**
	 *要打开的页面
	 */
	protected String  url;
	
	/**
	 *重试次数
	 */
	protected Long  resetCount;
	
	/**
	 *appKey
	 */
	protected String  appKey;
	
	/**
	 *appMastersecret
	 */
	protected String  appMastersecret;
	
	/**
	 *手机系统类型：ios 苹果, android 安卓
	 */
	protected String  appType;
	
	
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getPushType() {
		return pushType;
	}
	public void setPushType(String pushType) {
		this.pushType = pushType;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setJobId(Long jobId) 
	{
		this.jobId = jobId;
	}
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppMastersecret() {
		return appMastersecret;
	}
	public void setAppMastersecret(String appMastersecret) {
		this.appMastersecret = appMastersecret;
	}
	/**
	 * 返回 推送任务id
	 * @return
	 */
	public Long getJobId() 
	{
		return this.jobId;
	}
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 流程实例Id
	 * @return
	 */
	public Long getRunId() 
	{
		return this.runId;
	}
	public void setTaskId(Long taskId) 
	{
		this.taskId = taskId;
	}
	/**
	 * 返回 流程任务id
	 * @return
	 */
	public Long getTaskId() 
	{
		return this.taskId;
	}
	public void setError_code(Long error_code) 
	{
		this.error_code = error_code;
	}
	/**
	 * 返回 友盟返回的错误码
	 * @return
	 */
	public Long getError_code() 
	{
		return this.error_code;
	}
	public void setUserId(Long userId) 
	{
		this.userId = userId;
	}
	/**
	 * 返回 消息接收人
	 * @return
	 */
	public Long getUserId() 
	{
		return this.userId;
	}
	public void setContent(String content) 
	{
		this.content = content;
	}
	/**
	 * 返回 推送的消息内容
	 * @return
	 */
	public String getContent() 
	{
		return this.content;
	}
	public void setUrl(String url) 
	{
		this.url = url;
	}
	/**
	 * 返回 要打开的页面
	 * @return
	 */
	public String getUrl() 
	{
		return this.url;
	}
	public void setResetCount(Long resetCount) 
	{
		this.resetCount = resetCount;
	}
	/**
	 * 返回 重试次数
	 * @return
	 */
	public Long getResetCount() 
	{
		return this.resetCount;
	}
	public void setAppType(String appType) 
	{
		this.appType = appType;
	}
	/**
	 * 返回 手机系统类型：ios 苹果, android 安卓
	 * @return
	 */
	public String getAppType() 
	{
		return this.appType;
	}
}