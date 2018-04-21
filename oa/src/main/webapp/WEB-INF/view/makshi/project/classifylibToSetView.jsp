<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>文档目录 </title>
<%@include file="/commons/include/get.jsp"%>
<f:link href="tree/zTreeStyle.css"></f:link>

<script type="text/javascript" src="${ctx }/js/tree/jquery.ztree.js"></script>
<script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysOrgSearch.js"></script>
<script type="text/javascript">
	var orgTree; //树
	var rootId=0;
	var menu;
	var menu_root;
	var height;
	//左击的树节点
	var selectNode;
	var flag=false;//用户模糊查询数节点
	var _nodes=[];
	var _keyword;
	$().ready(function (){
		$("#layout").ligerLayout({
			leftWidth : 225,
			height : '100%',
			allowLeftResize : false
		}); 
		
		height = $('#layout').height();
		$("#viewFrame").height(height - 25);
		
		
		$('#orgs').change(function() {
			//$("#keyword").val('');
			loadTree("getAll");
		});
		//菜单
		getMenu();
		loadTree("init");
		
		$("#orgTree").height(height-95);
	});
	
	
	function loadTree(type) {
		var setting = {
				data: {
					key : {
						name: "name" // orgPathname
					},
				
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "supid",
						rootPId: 0
					}
				},
				// 拖动
				edit : {
					enable : true,
					showRemoveBtn : false,
					showRenameBtn : false,
					drag : {
						prev : true,
						inner : true,
						next : true,
						isMove : true,
						isCopy : true
					}
				},
				view : {
					selectedMulti : false
				},
				async: {
					enable: true,
					url:"${ctx}/makshi/project/classifylib/getCategoryTree.ht?orgId="+$("#orgs").val()+"&type="+type,
					autoParam:["id","name"],
					dataFilter: filter
				},
				callback:{
					onClick : zTreeOnLeftClick,
					onRightClick : zTreeOnRightClick,
					beforeDrop : beforeDrop,
					onDrop : onDrop,
					onAsyncSuccess: zTreeOnAsyncSuccess
				}
				
			};
			orgTree=$.fn.zTree.init($("#orgTree"), setting);
	};
	//拖放 前准备
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		var orgid=$("#orgs").val();
		if(orgid==undefined || orgid==0){
			$.ligerDialog.alert("请先选择部门，然后拖动！");
			return;
		}
		if (!treeNodes)
			return false;
		if (targetNode.isRoot == 1)
			return false;
		return true;
	};
	//拖放 后动作
	function onDrop(event, treeId, treeNodes, targetNode, moveType) {
		if (targetNode == null || targetNode == undefined)	return;
		var targetId = targetNode.id;
		var dragId = treeNodes[0].id;
		var url = __ctx + "/makshi/project/classifylib/move.ht";
		var params = {
			targetId : targetId,
			dragId : dragId,
			moveType : moveType
		};

		$.post(url, params, function(result) {
			$("#viewFrame").attr("src","about:blank");
			loadTree("getAll");
		});
	}
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
					//setIcon(node);
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
		}
	};
	//左击事件
	function zTreeOnLeftClick(event, treeId, treeNode) {
		var isRoot = treeNode.isRoot;
		if (isRoot == 1) {
			return;
		}
		selectNode=treeNode
		var id = treeNode.id;
		$("#viewFrame").attr("src", __ctx+"/makshi/project/classifylib/addCate.ht?id="+id);
	};
	
	function getSelectNode(){
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var nodes = orgTree.getSelectedNodes();
		var treeNode = nodes[0];
		return treeNode;
	}
	function searchTree(){
		loadTree("getAll");
	}
	
	/**
	 * 右击事件
	 */
	function zTreeOnRightClick(e, treeId, treeNode) {
		orgTree.selectNode(treeNode);
		menu.hide();
	 	menu_root.hide();
		if (treeNode.isRoot == 1 || treeNode.pronum>0) {//根节点时，把删除和编辑隐藏掉
			justifyRightClickPosition(e);
			menu_root.show({
				top : e.pageY,
				left : e.pageX
			});
		} else {
			justifyRightClickPosition(e);
			menu.show({
				top : e.pageY,
				left : e.pageX
			});
		}
	};
	//右键菜单
	function getMenu() {
		menu = $.ligerMenu({
			top : 100,
			left : 100,
			width : 100,
			items:<f:menu>
				[ 
				{
					text : '添加',
					click : 'addNode',
					alias:'addCategory'
				},
				  {
					text : '删除',
					click : 'delNode',
					alias:'delCategory'
				}
				]
				</f:menu>       
		});
		menu_root=$.ligerMenu({ top: 100, left: 100, width: 120, items:
			<f:menu>
				[ 
				{
					text : '添加',
					click : 'addNode',
					alias:'addCategory'
				}
				]
			</f:menu> 
	    });
		
	};
	function delNode() {
		var selectNode=getSelectNode();
		var id=selectNode.id;
		if(id==rootId){
			$.ligerDialog.warn('该节点为根节点 ,不允许该操作!');
			return;
		}
		var pronum=selectNode.pronum;
		if(pronum>0){
			$.ligerDialog.warn('改分类下已建有项目,不能删除！');
			return;
		}
		if(selectNode.isLeaf>0){
			$.ligerDialog.warn('请先删除子分类！');
			return;
		}
	 	var callback = function(rtn) {
	 		if(!rtn) return;
	 		var url="${ctx}/makshi/project/classifylib/del.ht";
	 		var params={id:id};
	 		$.post(url,params,function(responseText){
	 			var obj=new com.hotent.form.ResultMessage(responseText);
	 			if(obj.isSuccess()){//成功\
	 				var parentNode=selectNode.getParentNode();
	 				orgTree.removeNode(selectNode);
	 				reAsyncChild(parentNode);
	 				$.ligerDialog.success(obj.getMessage());
	 				$("#viewFrame").attr("src","about:blank");
	 			}
	 			else{
	 				$.ligerDialog.err("提示信息","删除资源失败!",obj.getMessage());
	 			}
	 		});
		};
		$.ligerDialog.confirm('确认删除吗？','提示信息',callback);

	};
	//刷新
	function refreshNode(){
		var selectNode=getSelectNode();
		reAsyncChild(selectNode);
	};
	//刷新节点
	function reAsyncChild(targetNode){
		var id=targetNode.id;
		if(id==0){
			loadTree("getAll");
		}else{
			orgTree = $.fn.zTree.getZTreeObj("orgTree");
			if(targetNode.isParent){
				orgTree.reAsyncChildNodes(targetNode, "refresh", false);
			}else{
				var targetParentNode = orgTree.getNodeByParam("id",targetNode.supid, null);
				orgTree.reAsyncChildNodes(targetParentNode, "refresh", false);				
			}
		}
	};
	
	//添加资源
	function addNode(){
		var orgid=$("#orgs").val();
		if(orgid==0){
			$.ligerDialog.alert("请先选择部门，然后添加！");
			return;
		}
		var url="${ctx}/makshi/project/classifylib/addCate.ht?parentId="+getSelectNode().id+"&orgid="+orgid;
		$("#viewFrame").attr("src",url);
	};
	
	//添加完成后调用该函数
	function addResource(id,text){
		var parentNode=getSelectNode();
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		var treeNode= {id:id,parentId:parentNode.id,name:text};
		orgTree.addNodes(parentNode,treeNode);
		$("#viewFrame").attr("src","about:blank");
	}
	//编辑完成后调用该函数。
	function editResource(text){
		var selectNode=getSelectNode();
		selectNode.name=text;
		//selectNode.icon=icon;
		//selectNode.isFolder=isFolder;
		orgTree = $.fn.zTree.getZTreeObj("orgTree");
		orgTree.updateNode(selectNode);
	}
</script>
<style type="text/css">
	html,body{ padding:0px; margin:0; width:100%;height:100%;overflow: hidden;} 
	.select-wrap{
		position: relative;
		width: 90%;
		height: 30px;
		margin: auto;
	}
	#orgs{
		position: absolute;
		right: 0;
		outline: none;
		width: 86%;
		border: 1px solid #dadfed;
		display: inline-block;
		vertical-align: middle;
		padding: 5px;
		border-radius: 3px;
	}
	.icon.icon-th-list{
		position: absolute;
		left: 0;
		top: 5px;
	}
	.search-bar{
		position: relative;
		height: 30px;
		text-align: center;
	}
	.search-btn{
		position: absolute;
		right: 15px;
		top: 5px;
		color: #98a1ae;
		font-size: 20px;
	}
	.search-bar .search-input{
		height: 30px;
		line-height: 30px;
		outline: none;
		border: 1px solid #ccc;
		box-sizing: border-box;
		width: 90%;
		padding: 0 30px 0 10px;
		border: 1px solid #dadfed;
	}

	.icon{
		font-size: 20px;
		display: inline-block;
		vertical-align: middle;
	}
</style>
</head>
<body class="oa-mw-page">
	<div id="layout" style="bottom: 1; top: 1">
		<div position="left" title="目录结构选择" id="rogTree"
			style="height: 100%; background: #e1e5f0; width: 100% !important;">
			<div style="padding: 8px 0;">
				<div class="select-wrap">
					<span class="icon icon-th-list"></span>
					<select name="orgId" id="orgs" >
						<c:forEach items="${orgs}" var="org">
							<option value="${org.orgId }">${org.orgName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<!-- <div class="search-bar">
				<input class="search-input" type="text" id="keyword">
				<span class="search-btn icon-zoom-in" onclick="searchTree()"><span>
			</div> -->
			<ul id="orgTree" class="ztree" style="overflow:auto;"></ul>
		</div>
		<div position="center" id="orgView" style="height: 100%;">

			<iframe id="viewFrame" src="about:blank"  frameborder="0" width="100%"
				></iframe>
		</div>
	</div>

</body>
</html>


