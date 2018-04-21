<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>组织架构</title>
<%@include file="/commons/include/get.jsp"%>
<f:link href="tree/zTreeStyle.css"></f:link>

<script type="text/javascript" src="${ctx }/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysOrgSearch.js"></script>
<script type="text/javascript">
	var orgTree; //树
	var menu;
	var menu_root;
	var height;
	var expandDepth = 2; 
	var type ="system";
	var typeVal="all";
	//左击的树节点
	var selectNode;
	var currentOrgId='${currentOrgId}';
	var initFlag=0;
	$().ready(function (){
		ligerLayoutIns = $("#layout").ligerLayout({
			leftWidth : 225,
			height : '100%',
			allowLeftResize : false
		}); 
		
		height = $('#layout').height();
		$("#viewFrame").height(height - 25);
		$('#demensionId').change(function() {
			var demensionId = $(this).val();
			loadTree(demensionId);
		});
		$("#treeReFresh").click(function() {
			var demensionId = $("#demensionId").val();
			loadTree(demensionId);
		});
		$("#treeExpand").click(function() {
			orgTree = $.fn.zTree.getZTreeObj("orgTree");
			var treeNodes = orgTree.transformToArray(orgTree.getNodes());
			for(var i=1;i<treeNodes.length;i++){
				if(treeNodes[i].children){
					orgTree.expandNode(treeNodes[i], true, false, false);
				}
			}
		});
		$("#treeCollapse").click(function() {
			orgTree.expandAll(false);
		});
		loadTree(1);
		
		$("#orgTree").height(height-95);
	});
	
	
	//刷新
	function refreshNode(){
		var selectNode=getSelectNode();
		reAsyncChild(selectNode);
	};
	//刷新节点
	function reAsyncChild(targetNode){
		var orgId=targetNode.orgId;
		if(orgId==0){
			loadTree(selid);
		}else{
			orgTree = $.fn.zTree.getZTreeObj("orgTree");
			if(targetNode.isParent){
				orgTree.reAsyncChildNodes(targetNode, "refresh", false);
			}else{
				var targetParentNode = orgTree.getNodeByParam("orgId",targetNode.orgSupId, null);
				orgTree.reAsyncChildNodes(targetParentNode, "refresh", false);				
			}
		}
	};
	
	function loadTree(selid) {
		var setting = {
				data: {
					key : {
						name: "orgName" // orgPathname
					},
				
					simpleData: {
						enable: true,
						idKey: "orgId",
						pIdKey: "orgSupId",
						rootPId: 0
					}
				},
			
				view : {
					selectedMulti : false
				},
				async: {
					enable: true,
					url:"${ctx}/platform/system/sysOrg/getTreeData.ht?demId="+selid+"&type="+type+"&typeVal="+typeVal,
					autoParam:["orgId","orgSupId"],
					dataFilter: filter
				},
				callback:{
					onClick : zTreeOnLeftClick,
					/* onRightClick : zTreeOnRightClick, */
					/* beforeDrop : beforeDrop, */
					/* onDrop : onDrop, */
					onAsyncSuccess: zTreeOnAsyncSuccess
				}
				
			};
			orgTree=$.fn.zTree.init($("#orgTree"), setting);
	};
	
	//过滤节点
	function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				var node = childNodes[i];
				if (node.isRoot == 1) {
					node.icon = __ctx + "/styles/default/images/icon/root.png";
				} else {
					if (node.ownUser == null || node.ownUser.length < 1) {
					//	node.orgName += "[未]";
					}
// 					setIcon(node);
				}
			}
			return childNodes;
	};
	
	//判断是否为子结点,以改变图标	
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
		if(treeNode){
	  	 	var children=treeNode.children;
		  	 if(children.length==0){
		  		treeNode.isParent=true;
		  		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		  		orgTree.updateNode(treeNode);
		  	 }
		  	 if(children.length>0){
		  		 for(var i=0;i<children.length;i++){
		  			 if(children[i].orgId==currentOrgId && initFlag==0){
		  				orgTree.setting.callback.onClick(null,"orgTree",children[i]);
				  		orgTree.expandNode(children[i],true,false,true,true);
				  		initFlag=1;
				  		break;
		  			 }
		  		 }
		  	 }
		}else{
			//初始化时为undinfined
			setTimeout(function(){
				orgTree = $.fn.zTree.getZTreeObj("orgTree");
				var children=orgTree.getNodeByTId("1").children;
				orgTree.expandNode(children[0],true,false,true,true);
				if(currentOrgId==-1 || currentOrgId==children[0].orgId){
					orgTree.setting.callback.onClick(null,"orgTree",children[0]);
				}
			},100);
		}
	};
	//左击事件
	function zTreeOnLeftClick(event, treeId, treeNode) {
		var isRoot = treeNode.isRoot;
		if (isRoot == 1) {
			return;
		}
		selectNode=treeNode
		var orgId = treeNode.orgId;
		//$("#viewFrame").attr("src", __ctx+"/platform/system/sysUserOrg/userList.ht?orgId=" + orgId);
		$("#viewFrame").attr("src", __ctx+"/makshi/addrBook/companyBook/list.ht?orgId="+orgId);
	};
	
	function getSelectNode(){
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var treeNode = nodes[0];
		return treeNode;
	}
</script>
<style type="text/css">
	html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;} 
</style>
</head>
<body>

	<div id="layout" style="bottom: 1; top: 1">
		<div position="left" title="组织机构选择" id="rogTree"
			style="height: 100%; width: 100% !important;">
			<div style="width: 100%;">
				<select id="demensionId" style="width: 99.8% !important;">
					<option value="0">---------全部---------</option>
					<c:forEach var="dem" items="${demensionList}">
						<option value="${dem.demId}">${dem.demName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="tree-toolbar" id="pToolbar">
					<div class="toolBar"
						style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap">
						<div class="group" >
							<a class="link reload" id="treeReFresh" ></a>
						</div>
						<div class="l-bar-separator"></div>
						<div class="group" >
							<a class="link expand" id="treeExpand" ></a>
						</div>
						<div class="l-bar-separator"></div>
						<div class="group" >
							<a class="link collapse" id="treeCollapse" ></a>
						</div>
						<div class="l-bar-separator"></div>
						<!-- <div class="group" >
							<a class="link search" id="treeSearch" ></a>	
						</div> -->
					</div>
				</div>
			
			<ul id="orgTree" class="ztree" style="overflow:auto;"></ul>
		</div>
		<div position="center" id="orgView" style="height: 100%;">
			<!-- <div class="l-layout-header">通讯录信息</div> -->
			<iframe id="viewFrame" src="getEmpty.ht" frameborder="0" width="100%"
				height="100%" scrolling="auto"></iframe>
		</div>
	</div>


<script>
	$(function(){

		$('#viewFrame').on('load', function(){
			// 监听子iframe页面中的自定义事件，触发时收起侧边栏
			$($('#viewFrame')[0].contentWindow).on('collapseClose', function(){
	            ligerLayoutIns.setLeftCollapse(true);
	        });

	        $($('#viewFrame')[0].contentWindow).on('collapseOpen', function(){
	            ligerLayoutIns.setLeftCollapse(false);
	        });
		});
	});
</script>
</body>
</html>


