/**
 * 描述：TODO
 * 包名：com.hotent.platform.service.form.parser.util
 * 文件名：ParserParam.java
 * 作者：User-mailto:liyj@jee-soft.cn
 * 日期2016-1-20-下午3:29:36
 *  2016广州宏天软件有限公司版权所有
 * 
 */
package com.hotent.platform.service.form.parser.util;

import java.util.HashMap;
import java.util.Map;

import com.hotent.platform.model.form.BpmFormData;

/**
 * <pre>
 * 描述：解释器所用到的参数对象
 * 构建组：bpm33
 * 作者：aschs
 * 邮箱:liyj@jee-soft.cn
 * 日期:2016-1-20-下午3:29:36
 * 版权：广州宏天软件有限公司版权所有
 * </pre>
 */
public class ParserParam {
	private BpmFormData bpmFormData;//表单字段
	private Map<String, Object> permission;//表单权限
	/**
	 * <pre>
	 * 变量参数，供一些特殊组件使用
	 * 例如流程图 flowchar组件需要actfid流程定义Id
	 * 流程意见，需要instanceId流程实例id等等
	 *</pre>
	 */
	private Map<String, Object> vars = new HashMap<String, Object>();

	/**
	 * 创建一个新的实例 ParserParam.
	 * 
	 * @param bpmFormData
	 * @param permission
	 */
	public ParserParam(BpmFormData bpmFormData, Map<String, Object> permission) {
		super();
		this.bpmFormData = bpmFormData;
		this.permission = permission;
	}
	
	/**
	 * bpmFormData
	 * 
	 * @return the bpmFormData
	 * @since 1.0.0
	 */

	public BpmFormData getBpmFormData() {
		return bpmFormData;
	}

	/**
	 * permission
	 * 
	 * @return the permission
	 * @since 1.0.0
	 */

	public Map<String, Object> getPermission() {
		return permission;
	}
	
	public void putVar(String key,Object obj){
		this.vars.put(key, obj);
	}
	
	public Object getVar(String key){
		return vars.get(key);
	}
	
}