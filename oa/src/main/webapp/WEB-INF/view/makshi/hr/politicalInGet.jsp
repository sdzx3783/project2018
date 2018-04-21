
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>党员转入明细</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
</head>
<body>
	<div class="panel">
		<div class="panel-top">
			<div class="tbar-title">
				<span class="tbar-label">党员转入详细信息</span>
			</div>
			<div class="panel-toolbar">
				<div class="toolBar">
					<div class="group">
						<a class="link back" href="list.ht"><span></span>返回</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<table class="formTable" parser="addpermission" data-sort="sortDisabled" cellspacing="0" cellpadding="2" border="1">
 <tbody>
  <tr class="firstRow">
   <td class="formHead" colspan="8" style="word-break: break-all;">党员转入申请<br /></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">姓名<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" width="18"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">性别</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.sex} </span> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">出生年月</td>
   <td style="width: 15%" class="formInput"> <fmt:formatDate value='${politicalIn.birthday}' pattern='yyyy-MM-dd'/> </td>
   <td style="width: 10%" class="formInput" rowspan="4" colspan="2"> 
    <div id="div_pic" style="width:400px;height:340px" class="pictureShow-div"> 
     <div id="div_pic_container"></div> 
     <table id="pictureShow_pic_Toolbar"> 
      <tbody>
       <tr right="r"> 
        <td width="80"> <a href="javascript:;" field="pic" class="link selectFile" atype="uploadPicture" onclick="{PictureShowPlugin.upLoadPictureFile(this);}">上传图片</a> </td> 
        <td width="80"> <a href="javascript:;" field="pic" class="link del" atype="delPicture" onclick="{PictureShowPlugin.deletePictureFile(this);}">删除图片</a> </td> 
       </tr> 
      </tbody>
     </table> 
    </div> <input type="hidden" class="hidden" name="pic" lablename="照片" controltype="pictureShow" value="${politicalIn.pic}" right="r" /> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">民族</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" width="18"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.nation} </span> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">籍贯</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.home} </span> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">婚姻状况</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">入党时间<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" width="18"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">转正时间</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">参加工作时间</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">入党时所在支部</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" width="18"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">转正时所在支部</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">入党介绍人</td>
   <td style="width: 15%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.introducer} </span> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">所在支部</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" width="18"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">进入党支部日期<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">现任党内职务</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">学历</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" width="18"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.edu} </span> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">学位<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.degree} </span> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">毕业院校</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.college} </span> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">专业</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.major} </span> </td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">所在部门</td>
   <td style="width: 15%" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.department} </span> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">组织关系所在地<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="7"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">户籍所在地<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="7"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">现居住地<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="7"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">身份证号码<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="7"></td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">联系电话<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">QQ号码</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.qq} </span> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">党费缴纳金额<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">微信号</td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"> <span name="editable-input" style="display:inline-block;" isflag="tableflag"> ${politicalIn.weixin} </span> </td>
  </tr>
  <tr>
   <td style="width: 10%; word-break: break-all;" class="formTitle" width="102" align="right">党费缴纳至<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /></td>
   <td style="width: 10%; word-break: break-all;" class="formTitle" align="right">是否转入<br /></td>
   <td style="width: 15%; word-break: break-all;" class="formInput" rowspan="1" colspan="3"><br /></td>
  </tr>
 </tbody>
</table>
</body>
</html>

