<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    function showXmlWindow(obj){
        var url="";
        if($(obj).text().trim()=='BPMN-XML'){
            url="${ctx}/platform/bpm/bpmDefinition/bpmnXml.ht?defId=${bpmDefinition.defId}";
        }else{
            url="${ctx}/platform/bpm/bpmDefinition/designXml.ht?defId=${bpmDefinition.defId}";
        }
        url=url.getNewUrl();
        window.open(url);
    }

</script>
<style>
body{ padding:0px; margin:0;overflow-x:hidden;} 

/*流程定义管理-设置页面顶部排列label的样式修改*/
.panel-container .l-tab-links li:after{
    content: none;
}
.panel-container .l-tab-links li:before{
    content: none;
}
.panel-container .l-tab-links li{
    margin: 0;
    background: transparent;
}
.panel-container .l-tab-links li a{
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    background: #eef1f7;
    line-height: 23px;
    padding: 5px 10px;
}
.panel-container .l-tab-links .l-selected a{
    border-radius: 0;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    padding: 5px 10px;
}
/*流程定义管理-设置页面顶部排列label的样式修改*/

.panel-container{
    width: 100%;
    border-radius: 0;
    border-color: #dadfed;
    margin-top: 0;
    margin: 0;
}
.panel-container .l-tab-links li {
    
}
.l-tab-links {
    overflow-x: auto;
    height: auto;
}
.l-tab-links ul {
    overflow: visible;
    position: static;
    width: auto;
    white-space: nowrap;
}
.l-tab-links li {
    display: inline-block;
    float: none;
}
.panel-container .l-tab-links li a{
    line-height: 42px;
}
</style>

<div  useSclFlw="false">
<div class="panel-toolbar" style="float:left;width:100%">
        <div class="panel-top-left">流程定义管理->${bpmDefinition.subject }</div>
        <div class="panel-top-right" >
                    
                            <div class="toolBar" style="margin:0;">
                                <div class="l-bar-separator"></div>
                                    <div class="group"><a class="link download"  href="${ctx}/bpmImage?definitionId=${bpmDefinition.actDefId}&download=1"><span></span>流程图</a></div>
                                <div class="l-bar-separator"></div>
                                    <div class="group"><a class="link xml-bpm"  onclick="showXmlWindow(this);"><span></span>BPMN-XML</a></div>
                                <div class="l-bar-separator"></div>
                                    <div class="group"><a class="link xml-design" onclick="showXmlWindow(this);"><span></span>DESIGN-XML</a></div>
                                <div class="l-bar-separator"></div>
                                    <div class="group">
                                    <c:choose>
                                        <c:when test="${not empty param.defIdForReturn}">
                                            <a class="link back" href="${ctx}/platform/bpm/bpmDefinition/versions.ht?defId=${param.defIdForReturn}"><span></span>返回</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="link back" href="${ctx}/platform/bpm/bpmDefinition/list.ht"><span></span>返回</a>
                                        </c:otherwise>
                                    </c:choose>
                                    </div>
                            </div>
                    </div>
                                        
        </div>

</div>