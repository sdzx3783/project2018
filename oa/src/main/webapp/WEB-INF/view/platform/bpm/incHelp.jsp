<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:if test="${bpmDefinition.attachment!=''}">
<c:if test="${sysFile !=null }">
	<div style="float:right;margin-right:30px;">
		<c:choose>
			<c:when test="0">
				<a href="javascript:;" onclick="openHelpDoc(${sysFile.fileId})"  class="link help" title="${sysFile.fileName}">帮助</a>
			</c:when>
			<c:otherwise>
				<div class="group"><a id="file" class="link help"  href="${ctx}/platform/system/sysFile/file_${sysFile.fileId}.ht" target="_blank" ><span></span>帮助</a></div>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>
</c:if>
<a href="/makshi/doc/docinfo/getDocFilesByNum.ht?filenum=${bpmDefinition.defId}" target="_blank"  class="link help" style="display: none;" id="filehelp" >帮助</a>

<script type="text/javascript">
$(function(){
	$.getJSON("/makshi/doc/docinfo/isExistByNum.ht",{filenum:${bpmDefinition.defId}},function(data){
		if(data.result == 1){
			$("#filehelp").css({"display":""});
		}
	});
});
</script>