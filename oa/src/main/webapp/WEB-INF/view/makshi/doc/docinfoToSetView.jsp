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
			$("#keyword").val('');
			loadTree("init");
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
						name: "docname" // orgPathname
					},
				
					simpleData: {
						enable: true,
						idKey: "docid",
						pIdKey: "docsupid",
						rootPId: 0
					}
				},
			
				view : {
					selectedMulti : false
				},
				async: {
					enable: true,
					url:"${ctx}/makshi/doc/docinfo/getDocTree.ht?orgId="+$("#orgs").val()+"&type="+type,
					autoParam:["docid","docname"],
					dataFilter: filter
				},
				callback:{
					onClick : zTreeOnLeftClick,
					onRightClick : zTreeOnRightClick,
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
			_keyword=$("#keyword").val();
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
				
				/* if(_keyword && _keyword.length>0){
					if(node.isRoot == 1){
						_childNodes.push(node);
					}else{
						flag=false;
						isContainKey(node,_keyword);
						if(flag){
							_childNodes.push(node);
						}
					}
				} */
			}
			if(_keyword && _keyword.length>0){
					//获取模糊查询的jiedian
				return fiterNodes(childNodes,_keyword);
			}else{
				return childNodes;
			}
	};
	
	
	
	//模糊匹配
	function fiterNodes(childNodes,keyword){
		_nodes=[];
		for(var i=0;i<childNodes.length;i++){
			if(childNodes[i].isRoot == 1){
				_nodes.push(childNodes[i]);
			}else{
				flag=false;
				isContainKey(childNodes[i],childNodes);
				if(flag){
					_nodes.push(childNodes[i]);
				}
			}
		}
		return _nodes;
	}
	function isContainKey(node,nodes){
		if(node.isParent=='true'){
			if(node.docname.indexOf(_keyword)>=0){
				flag=true;
				return ;
			}
			var _nodes=getNodesByPId(node.docid,nodes);
			for(var i=0;i<_nodes.length;i++){
				isContainKey(_nodes[i],nodes);
			}
		}else{
			if(node.docname.indexOf(_keyword)>=0){
				flag=true;
			}
		}
	}
	
	function getNodesByPId(docid,nodes){
		var temp=[];
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].docsupid==docid){
				temp.push(nodes[i]);
			}
		}
		return temp;
	}
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
		var docid = treeNode.docid;
		$("#viewFrame").attr("src", __ctx+"/makshi/doc/docinfo/toSetUpdate.ht?id="+docid);
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
	 	//menu_root.hide();
		if (treeNode.isRoot == 1) {//根节点时，把删除和编辑隐藏掉
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
					text : '删除',
					click : 'delNode',
					alias:'delDoc'
				}
				]
				</f:menu>       
		});

		
	};
	function delNode() {
		var treeNode=getSelectNode();
		var callback = function(rtn) {
			if (!rtn) return;
			var params = "docid=" + treeNode.docid;
			
			$.post("delDoc.ht", params, function(data) {
				//alert(data);
				var json =JSON.parse(data);
				if(json.result=='1'){
					orgTree.removeNode(treeNode);
					$("#viewFrame").attr("src","getEmpty.ht");
					$.ligerDialog.success(json.message);
				}else{
					$.ligerDialog.warn(json.message);
				}
			});
		};
		$.ligerDialog.confirm("确认要删除此目录吗，其下子目录及文件也将被删除？", '提示信息', callback);

	};
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
<body>
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
			
			<div class="search-bar">
				<input class="search-input" type="text" id="keyword">
				<span class="search-btn icon-zoom-in" onclick="searchTree()"><span>
			</div>
			<ul id="orgTree" class="ztree" style="overflow:auto;"></ul>
		</div>
		<div position="center" id="orgView" style="height: 100%;">
			<div class="l-layout-header"></div>
			<iframe id="viewFrame" src="getEmpty.ht" frameborder="0" width="100%"
				></iframe>
		</div>
	</div>

</body>
</html>


