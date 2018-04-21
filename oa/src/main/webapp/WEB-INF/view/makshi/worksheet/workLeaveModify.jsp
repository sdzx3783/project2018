<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/js/hotent/scriptMgr.js"></script>
<script type="text/javascript">
	function afterOnload() {
		var afterLoadJs = [ '${ctx}/js/hotent/formdata.js',
				'${ctx}/js/hotent/subform.js' ];
		ScriptMgr.load({
			scripts : afterLoadJs
		});
	}
</script>
<table class="formTable" border="1" cellspacing="0" cellpadding="2"
	parser="addpermission">
	<tbody>
		<tr class="firstRow">
			<td class="formHead" colspan="2">主表</td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">姓名:</td>
			<td style="width: 80%" class="formInput"><br /> <input
				name="m:work_level:fullname" type="text" ctltype="selector"
				class="users" lablename="姓名" value="${workLevel.fullname}"
				validate="{empty:false}" readonly="readonly" /></td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">部门:</td>
			<td style="width: 80%" class="formInput"><br /> <input
				name="m:work_level:department" type="text" ctltype="selector"
				class="org" lablename="部门" value="${workLevel.department}"
				validate="{empty:false}" readonly="readonly" showcurorg="0" /></td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">职务:</td>
			<td style="width: 80%" class="formInput"><input
				name="m:work_level:position" type="text" ctltype="selector"
				class="positions" lablename="岗位" value="${workLevel.position}"
				validate="{empty:false}" readonly="readonly" /></td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">类型:</td>
			<td style="width: 80%" class="formInput"><br /> <span
				name="editable-input" style="display: inline-block; padding: 2px;"
				class="selectinput"> <select name="m:work_level:type"
					lablename="类型" controltype="select" validate="{empty:false}"
					val="${workLevel.type}">
						<option value="因公办事">因公办事</option>
						<option value="年假">年假</option>
						<option value="调休">调休</option>
						<option value="事假">事假</option>
						<option value="病假">病假</option>
				</select>
			</span></td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">缺勤时间:</td>
			<td style="width: 80%; word-break: break-all;" class="formInput"><br /></td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">登记时间:</td>
			<td style="width: 80%" class="formInput"><span
				name="editable-input" style="display: inline-block;"
				isflag="tableflag"> <input type="text"
					name="m:work_level:create_time" lablename="登记时间"
					class="inputText date" value="${workLevel.createtime}"
					validate="{maxlength:1000}" isflag="tableflag" />
			</span></td>
		</tr>
		<tr>
			<td style="width: 20%; word-break: break-all;" class="formTitle"
				align="right">备注:</td>
			<td style="width: 80%" class="formInput"><span
				name="editable-input" style="display: inline-block;"
				isflag="tableflag"> <input type="text"
					name="m:work_level:remark" lablename="备注" class="inputText"
					value="${workLevel.remark}" validate="{maxlength:1000}"
					isflag="tableflag" />
			</span></td>
		</tr>
	</tbody>
</table>
