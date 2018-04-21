package com.hotent.makshi.aop;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.hotent.core.api.util.ContextUtil;
import com.hotent.core.web.util.RequestContext;
import com.hotent.makshi.common.IndiDireManager;
import com.hotent.makshi.model.common.FieldData;
import com.hotent.makshi.model.common.WChangeHistory;
import com.hotent.makshi.service.common.ChangeHistoryService;
import com.hotent.platform.annotion.Action;
import com.hotent.platform.annotion.ActionExecOrder;
import com.hotent.platform.model.system.SysUser;

public class ChangeHisAOP {
	private final Logger log = Logger.getLogger(this.getClass());
	@Resource
	private ChangeHistoryService changeHistoryService;

	public Object addChangeHis(ProceedingJoinPoint point) throws Throwable {
		Object returnVal = null;

		String methodName = point.getSignature().getName();
		// 类
		Class<?> targetClass = point.getTarget().getClass();
		// 方法
		Method[] methods = targetClass.getMethods();
		Method method = null;
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName() == methodName) {
				method = methods[i];
				break;
			}
		}
		// 如果横切点不是方法，返回
		if (method == null)
			return point.proceed();

		// boolean hasAnnotation = method.isAnnotationPresent(Action.class);
		// 方法Action
		Action annotation = method.getAnnotation(Action.class);
		// 如果方法上没有注解@Action，返回
		if (annotation == null) {
			return point.proceed();
		}

		if (ActionExecOrder.BEFORE.equals(annotation.execOrder())) {
			doAddHis(point);
			returnVal = point.proceed();
		} else if (ActionExecOrder.AFTER.equals(annotation.execOrder())) {
			returnVal = point.proceed();
			doAddHis(point);
		} else {
			returnVal = point.proceed();
			doAddHis(point);
		}
		return returnVal;
	}

	/**
	 * 添加变更历史
	 * 
	 * @param point
	 */
	private void doAddHis(ProceedingJoinPoint point) {
		try {
			// String methodName = point.getSignature().getName();
			Object[] args = point.getArgs();
			HttpServletRequest request = RequestContext.getHttpServletRequest();
			SysUser curUser = (SysUser) ContextUtil.getCurrentUser();
			// HttpServletRequest request = null;
			Map<String, Object> hisMap = null;

			hisMap = (Map<String, Object>) request.getSession().getAttribute("historyData");
			if (hisMap != null) {
				// 将save方法中传入的参数与session中存入的历史数据比对
				for (int i = 0; i < args.length; i++) {
					Iterator<Map.Entry<String, Object>> it = hisMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, Object> entry = it.next();
						Object value = entry.getValue();
						if (args[i].getClass() == value.getClass()) {
							Class clazz = args[i].getClass();
							Field[] fields = args[i].getClass().getDeclaredFields();
							int j = 1;
							String str = "";
							for (Field field : fields) {
								if ("serialVersionUID".equals(field.getName())) {
									continue;
								}
								PropertyDescriptor pd = null;
								try {
									pd = new PropertyDescriptor(field.getName(), clazz);
									Method getMethod = pd.getReadMethod();
									Object o1 = getMethod.invoke(args[i]);
									Object o2 = getMethod.invoke(value);
									if (o1 == null || o2 == null) {
										continue;
									}
									if (!o1.toString().equals(o2.toString())) {
										if (j != 1) {
											str += "\n";
										}
										str += j + "、字段名称" + field.getName() + ",旧值:" + o2 + ",新值:" + o1;
										j++;
										String type = (String) hisMap.get("queryType");
										String queryCondition = (String) hisMap.get("queryCondition");

										String fieldName = field.getName();
										String fieldNameOld = field.getName();
										/*
										 * if(fieldName.equals(FilterFileds.CAREPERSIONID)||fieldName.equals(FilterFileds.DEPARTMENTID)){ continue; }
										 */
										if (IndiDireManager.fieldsNames.contains(fieldName)) {// 包含当前字段
											for (FieldData fieldData : IndiDireManager.fields) {
												// 查询所有拥有该字段属性的元素
												if (fieldName.equals(fieldData.getFieldName())) {
													// 判断属于哪张表
													if (type.equals(fieldData.getType())) {
														fieldName = fieldData.getValue();
													}
												}
											}
											// fieldNameType=IndiDireManager.fieldMap.get(fieldName);
										}
										if (fieldNameOld.endsWith(fieldName)) {
											continue;
										}
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
										if (o1 instanceof Date) {
											o1 = sdf.format(o1);
										}
										if (o2 instanceof Date) {
											o2 = sdf.format(o2);
										}

										WChangeHistory changeHis = new WChangeHistory(type, fieldName, (String) o2, (String) o1, curUser.getFullname(), curUser.getUserId(), queryCondition,
												new Date());
										changeHistoryService.save(changeHis);
									}
								} catch (Exception e) {
									continue;
								}
							}
							if (!str.equals("")) {
								hisMap.put(entry.getKey(), args[i]);
								request.getSession().setAttribute("historyData", hisMap);
							}
						}

					}

				}
			}

			// for (int i = 0; i < args.length; i++) {
			// if (args[i] instanceof HttpServletRequest) {
			// request = (HttpServletRequest) args[i];
			// hisMap = (Map<String, Object>) request.getSession().getAttribute("historyData");
			// }
			// }
			// System.out.println("====map:"+hisMap.toString());
			// System.out.println(args);

			// if (StringUtils.isEmpty(methodName)){
			// return;
			// }
			// //类
			// Class<?> targetClass = point.getTarget().getClass();
			// //类Action
			// Action classAction = targetClass.getAnnotation(Action.class);
			//
			// //方法
			// Method[] methods = targetClass.getMethods();
			// Method method = null;
			// for (int i = 0; i < methods.length; i++){
			// if (methods[i].getName() == methodName){
			// method = methods[i];
			// break;
			// }
			// }
			// //如果横切点不是方法，返回
			// if (method == null)
			// return ;
			//
			// //boolean hasAnnotation = method.isAnnotationPresent(Action.class);
			// //方法Action
			// Action annotation = method.getAnnotation(Action.class);
			// //如果方法上没有注解@Action，返回
			// if(annotation==null){
			// return ;
			// }
			//
			// Map<String, Object> map=new HashMap<String, Object>();
			// if(request!=null){
			// map.putAll(RequestUtil.getQueryMap(request));
			// }
			// System.out.println(map.toString());
			//

			// //Action描述
			// String methodDescp = annotation.description();
			// //增加归属模块
			// SysAuditModelType modelType = annotation.ownermodel();
			// //日志类型
			// String exectype= annotation.exectype();
			//
			// if(modelType==SysAuditModelType.NULL){
			// if(classAction!=null){
			// modelType = classAction.ownermodel();
			// }
			// }
			// String ownermodel= modelType.toString();
			//
			// // 取到当前的操作用户
			// SysUser curUser = (SysUser) ContextUtil.getCurrentUser();

		} catch (Exception ex) {
			log.error("错误信息", ex);
		}
	}
}
