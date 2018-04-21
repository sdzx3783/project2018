package com.hotent.makshi.model.appPush;

import com.hotent.core.model.BaseModel;
/**
 * 对象功能:推送任务表 Model对象
 */
public class AppPushJob extends BaseModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8102461813926057316L;
	//主键
	protected Long id;
	/**
	 *消息接收人
	 */
	protected Long  userId;
	
	/**
	 *流程实例id
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
	

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public void setRunId(Long runId) 
	{
		this.runId = runId;
	}
	/**
	 * 返回 流程实例id
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
	
	public void setUrl(String url) 
	{
		this.url = url;
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
	/**
	 * 返回 要打开的页面
	 * @return
	 */
	public String getUrl() 
	{
		return this.url;
	}

}