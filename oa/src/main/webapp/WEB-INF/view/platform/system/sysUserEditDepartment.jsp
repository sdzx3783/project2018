<%@page language="java" pageEncoding="UTF-8" import="com.hotent.platform.model.system.SysUser,com.hotent.makshi.model.userinfo.UserInfomation,com.hotent.core.api.util.PropertyUtil"%>
<%@include file="/commons/include/html_doctype.html"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="http://www.jee-soft.cn/functions"%>
<html>
<head>
<title>编辑 用户表</title>
<%@include file="/commons/include/form.jsp" %>
<%@include file="/codegen/include/customForm.jsp" %>
<script src="${ctx}/js/hotent/platform/form/AttachMent.js"></script>
<%@include file="/commons/include/ueditor.jsp" %>
<script src="${ctx}/servlet/ValidJs?form=sysUser"></script>
<f:link href="tree/zTreeStyle.css"></f:link>
<link href="${ctx}/styles/default/css/form.css" rel="stylesheet" />
<script src="${ctx}/js/tree/jquery.ztree.js"></script>
<script src="${ctx}/js/lg/plugins/ligerTab.js" ></script>
<script src="${ctx}/js/hotent/displaytag.js" ></script>
<script src="${ctx}/js/lg/plugins/ligerWindow.js" ></script>
<script src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
<script src="${ctx}/js/hotent/platform/system/FlexUploadDialog.js"></script>
<script src="${ctx}/js/handlebars/handlebars.min.js"></script>
<script src="${ctx}/js/hotent/platform/system/HtmlUploadDialog.js"></script>
<script src="${ctx}/js/hotent/platform/form/OfficePlugin.js"></script>
<script src="${ctx}/js/hotent/platform/form/rule.js"></script>
<script src="${ctx}/js/hotent/formdata.js"></script>
<script src="${ctx}/js/hotent/CustomValid.js"></script>
<script src="${ctx}/js/hotent/subform.js"></script>
<script src="${ctx}/js/jquery/plugins/jquery.attach.js" ></script>
<script src="${ctx}/js/ntkoWebSign/WebSignPlugin.js"></script>
<script src="${ctx}/js/hotent/platform/form/FormUtil.js"></script>
<script src="${ctx}/js/hotent/platform/form/ReadOnlyQuery.js"></script>
<script src="${ctx}/js/pictureShow/PictureShowPlugin.js"></script>
<script src="${ctx}/js/hotent/platform/form/FormMath.js"></script>
<script src="${ctx}/js/hotent/platform/form/Cascadequery.js"></script>
<script src="${ctx}/js/hotent/platform/form/TablePermission.js"></script>
<script src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
  
    <script type="text/javascript">
    
    var orgTree;    //组织树
    var posTree;    //岗位树
    var rolTree;    //角色树
    
    var orgPosTree;    //组织岗位树
    
    var height;
    var expandDepth =2; 
     
    var action="${action}";
    
    $(function ()
    {   
       
        AttachMent.init();
        
        //右键菜单,暂时去掉右键菜单
        height=$('body').height();
        $("#tabMyInfo").ligerTab({ });
        function showRequest(formData, jqForm, options) { 
            return true;
        }
        function showResponse(responseText, statusText)  {
            var self=this;
            var obj=new com.hotent.form.ResultMessage(responseText);
            if(obj.isSuccess()){//成功
                $.ligerDialog.confirm( obj.getMessage()+",是否继续操作","提示信息",function(rtn){
                    if(rtn){
                        if(self.isReset==1){
                            window.location.reload(true);
                        }
                    }else {
                        window.location.href="${returnUrl}";
                    }
                });
            }else{//失败
                $.ligerDialog.err("提示信息","用户保存失败!",obj.getMessage());
            }
        };

        var options={};
        if(showResponse){
            options.success=showResponse;
        }
        var frm=$('#sysUserForm').form();
        $("#dataFormSave").click(function() {
            frm.ajaxForm(options);
            $("#saveData").val(1);
            if(frm.valid()){
                //如果有编辑器的情况下
                $("textarea[name^='m:'].myeditor").each(function(num) {
                    var name=$(this).attr("name");
                    var data=getEditorData(editor[num]);
                    $("textarea[name^='"+name+"']").val(data);
                });
                
                //if(WebSignPlugin.hasWebSignField){
                //  WebSignPlugin.submit();
                //}
                if(OfficePlugin.officeObjs.length>0){
                    OfficePlugin.submit(function(){
                        frm.handleFieldName();
                        frm.sortList();
                        $('#sysUserForm').submit();
                    });
                }else{
                    frm.handleFieldName();
                    frm.sortList();
                    $('#sysUserForm').submit();
                }
            }
        });
        
        $("#orgPosAdd").click(function(){
            btnAddRow('orgPosTree');
        });
        $("#orgPosDel").click(function(){
            btnDelRow();
        });
        $("#demensionId").change(function(){
            loadorgTree();
        });
        //组织刷新按钮
        $("#treeReFresh").click(function() {
            loadorgTree();
        });
         //组织展开按钮
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
       var orgId="${orgId}";
       var departmentOrgId="${departmentOrgId}";
       loadorgPosTree(orgId);
       loadDepartmentTree(departmentOrgId); 
    });//function end

    function zTreeOnCheck(event, treeId, treeNode) {
        var target;
        if(treeId=="rolTree"){
            target = rolTree ;
            if(treeNode.isParent){
                var children=treeNode.children;
                if(children){
                    for(var i=0;i<children.length;i++){
                        target.checkNode(children[i], !treeNode.checked, false, true);
                    }
                }
            }
        } else if(treeId=='orgTree'){
            target = orgTree ;
        } else if(treeId=='orgPosTree'){
            target = orgPosTree ;
        }else{
            target = posTree ;
        }
        target.checkNode(treeNode, '', false, true);
    };
    //判断是否为子结点,以改变图标    
    function orgTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
        if(treeNode){
            var children=treeNode.children;
             if(children.length==0){
                treeNode.isParent=true;
                orgTree = $.fn.zTree.getZTreeObj("orgTree");
                orgTree.updateNode(treeNode);
             }
        }
            var treeNodes = orgTree.transformToArray(orgTree.getNodes());
            if(treeNodes.length>0){
                treeNodes[0].nocheck = true;
                orgTree.updateNode(treeNodes[0]);
            }
    };  
    
    function loadDepartmentTree(orgId){
        var setting = {
                data: {
                    key : {
                        name: "orgName",
                        title: "orgName"
                    }
                },
                view : {
                    selectedMulti : false
                },
                onRightClick: false,
                onClick:false,
                check: {
                    enable: true,
                    chkboxType: { "Y": "", "N": "" }
                },
                callback:{
                    onClick: zTreeOnCheck,
                    onCheck:orgPostTreeExpand 
                    }
            };
            if(!orgId) orgId =0;
            var url=__ctx + "/platform/system/grade/getOrgJsonByAuthOrgId.ht?orgId="+orgId;
           //一次性加载
           $.post(url,function(result){
               var zNodes=eval("(" +result +")");
               orgTree=$.fn.zTree.init($("#orgTree"), setting,zNodes);
               if(expandDepth!=0)
                {
                    orgTree.expandAll(false);
                    var nodes = orgTree.getNodesByFilter(function(node){
                        return (node.level < expandDepth);
                    });
                    if(nodes.length>0){
                        //nodes[0].nocheck = true; 
                        orgTree.updateNode(nodes[0]);
                        for(var i=0;i<nodes.length;i++){
                            orgTree.expandNode(nodes[i],true,false);
                        }
                    }
                }else {
                    orgTree.expandAll(true);
                    // justifyMargin(10);
                }
           });      
    };  
    
    //勾选组织的时候展开组织岗位树
    function orgPostTreeExpand(){
        var setting = {
                data: {
                    key : {
                        name: "posName",
                        title: "posName"
                    },
                
                    simpleData: {
                        enable: true,
                        idKey: "posId",
                        pIdKey: "orgId",
                        rootPId: -1
                    }
                },

                view: {
                    selectedMulti: true
                },
                onRightClick: false,
                onClick:false,
                check: {
                    enable: true,
                    chkboxType: { "Y": "", "N": "" }
                },
                callback:{
                    onClick: zTreeOnCheck,
                    onAsyncSuccess: zTreeOnAsyncSuccess,
                    onRightClick: zTreeOnRightClick
                }
        };
          //获取组织树的勾选节点
          var treeObj = $.fn.zTree.getZTreeObj('orgTree');
            var nodes = treeObj.getCheckedNodes(true);
            var a=[];
            for ( var key in nodes ){
                a.push(nodes[key].orgId);
            }
           var orgIds=a.join();
           if(!orgIds) return;
          var orgUrl=__ctx + "/platform/system/position/getOrgPosTreeData.ht?orgIds="+orgIds;
           //一次性加载
           $.post(orgUrl,function(result){
               orgPosTree=$.fn.zTree.init($("#orgPosTree"), setting,result);
               orgPosTree.expandAll(true);
               //去掉父节点勾选框,???
               var treeObj = $.fn.zTree.getZTreeObj("orgPosTree");
               var nodes = treeObj.getNodesByParam("orgId", -1, null);//为啥总是为空
               for(var key in nodes){
                        nodes[key].nocheck = true;
                        orgPosTree.updateNode(nodes[key]);
               }
           });  
           orgPostTree=$.fn.zTree.init($("#orgPostTree"), setting);
    };
    
    //生成组织树             
    function loadorgTree(orgId){
    	loadDepartmentTree(orgId);
    };
    
    //生成组织岗位树           
      function loadorgPosTree(orgIds) {
          var setting = {
                    data: {
                        key : {
                            name: "posName",
                            title: "posName"
                        },
                    
                        simpleData: {
                            enable: true,
                            idKey: "posId",
                            pIdKey: "orgId",
                            rootPId: -1
                        }
                    },

                    view: {
                        selectedMulti: true
                    },
                    onRightClick: false,
                    onClick:false,
                    check: {
                        enable: true,
                        chkboxType: { "Y": "s", "N": "ps" }
                    },
                    callback:{
                        onClick: zTreeOnCheck,
                        onAsyncSuccess: zTreeOnAsyncSuccess,
                        onRightClick: zTreeOnRightClick
                    }
            };
            var orgUrl=__ctx + "/platform/system/position/getOrgPosTreeData.ht?orgIds="+orgIds;
               //一次性加载
               $.post(orgUrl,function(result){
                   orgPosTree=$.fn.zTree.init($("#orgPosTree"), setting,result);
                   orgPosTree.expandAll(true);
               });  
                  orgPosTree = $.fn.zTree.init($("#orgPosTree"), setting);
    };
    
    //生成岗位树             
      function loadposTree() {
          var setting = {
                    data: {
                        key : {
                            name: "posName",
                            title: "posName"
                        },
                    
                        simpleData: {
                            enable: true,
                            idKey: "posId",
                            pIdKey: "parentId",
                            rootPId: -1
                        }
                    },
                    async: {
                        enable: true,
                        url:__ctx+"/platform/system/position/getChildTreeData.ht",
                        autoParam:["posId","parentId"],
                        dataFilter: filter
                    },
                    view: {
                        selectedMulti: true
                    },
                    onRightClick: false,
                    onClick:false,
                    check: {
                        enable: true,
                        chkboxType: { "Y": "", "N": "" }
                    },
                    callback:{
                        onClick: zTreeOnCheck,
                        onDblClick: posTreeOnDblClick,
                        onAsyncSuccess: zTreeOnAsyncSuccess
                    }
            };
            posTree = $.fn.zTree.init($("#posTree"), setting);
    };  
    
    
    
    //判断是否为子结点,以改变图标    
    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
        if(treeNode){
         var children=treeNode.children;
             if(children.length==0){
                treeNode.isParent=true;
                pos_Tree = $.fn.zTree.getZTreeObj("SEARCH_BY_POS");
                pos_Tree.updateNode(treeNode);
             }
        }
        var treeNodes = posTree.transformToArray(posTree.getNodes());
        if(treeNodes.length>0){
            //显示勾选框
            treeNodes[0].nocheck = true;
            posTree.updateNode(treeNodes[0]);
        }
    };
    
    //过滤节点,默认为父节点,以改变图标 
    function filter(treeId, parentNode, childNodes) {
            if (!childNodes) return null;
            for (var i=0, l=childNodes.length; i<l; i++) {
                if(!childNodes[i].isParent){
                    alert(childNodes[i].posName);
                    childNodes[i].isParent = true;
                }
            }
            return childNodes;
    };
    
    
     //生成角色树            
      function loadrolTree() {
        var setting = {                                             
            data: {
                key : {
                    name: "roleName",
                    title: "roleName"
                },
            
                simpleData: {
                    enable: true,
                    idKey: "roleId",
                    pIdKey: "systemId",
                    rootPId: null
                }
            },
            view: {
                selectedMulti: true
            },
            onRightClick: false,
            onClick:false,
            check: {
                enable: true,
                chkboxType: { "Y": "p", "N": "s" }
            },
            callback:{
                onClick: zTreeOnCheck,
                onDblClick: rolTreeOnDblClick}
           };
            if(action == 'grade'){
                var url="${ctx}/platform/system/sysRole/getGradeTreeData.ht";
            }
            else{
                var url="${ctx}/platform/system/sysRole/getTreeData.ht";
            }
                
            $.post(url,function(result){
                rolTree=$.fn.zTree.init($("#rolTree"), setting,result);
                
            });
    };  
    
     function btnDelRow() {
         var $aryId = $("input[type='checkbox'][class='pk']:checked");
         var len=$aryId.length;
         if(len==0){
             $.ligerDialog.warn("你还没选择任何记录!");
             return;
         }
         else{          
             $aryId.each(function(i){
                    var obj=$(this);
                    delrow(obj.val());
             });
         }
     };
     
     function delrow(id)//删除行,用于删除暂时选择的行
     {
         $("#"+id).remove();
     };

    //树按添加按钮
    function btnAddRow(treeName) {
        var treeObj = $.fn.zTree.getZTreeObj(treeName);
        var nodes = treeObj.getCheckedNodes(true);
        if(nodes==null||nodes=="")
        {
            $.ligerDialog.warn("你还没选择任何节点!");
            return;
        }
        if(treeName.indexOf("orgPos")!=-1) {
            $.each(nodes,function(i,treeNode){  
                orgPosAddHtml(treeNode);
            });
        }
        else if(treeName.indexOf("pos")!=-1){
             $.each(nodes,function(i,treeNode){
                  posAddHtml(treeNode);
             });
        }
        else if(treeName.indexOf("rol")!=-1){
             $.each(nodes,function(i,treeNode){
                  if(treeNode.roleId>0){
                      if (treeNode.subSystem==null || treeNode.subSystem=="")
                      {
                          treeNode.sysName="";
                       }
                      else{
                          treeNode.sysName=treeNode.subSystem.sysName;
                      }
                      rolAddHtml(treeNode);
                  }
             });
        }
    };
    
     function orgPosAddHtml(treeNode){
        
         if(treeNode.orgName==null) return;//去掉父节点
         //添加过的不再添加
         var obj=$("#" +treeNode.posId); 
         if(obj.length>0) return;
         //公司名称 
         if(typeof treeNode.companyId =="undefined" || treeNode.companyId==null){
             treeNode.companyId = 0;
         } 
         if(typeof treeNode.company =="undefined" || treeNode.company==null){
             treeNode.company = '';
         } 
            //用jquery获取模板
            var tpl = $("#orgPosAddHtml-template").html();
           
            var content = {treeNode:treeNode};
            //预编译模板
            var template = Handlebars.compile(tpl);
            //匹配json内容
            var html = template(content);
            //输入模板
         $("#orgItem").append(html);
         
     };
     
    //岗位树左键双击
     function posTreeOnDblClick(event, treeId, treeNode){   
         posAddHtml(treeNode);
         
     };
     
     function posAddHtml(treeNode){
         if(treeNode.parentId==-1) return;
         var obj=$("#" +treeNode.posId);
         if(obj.length>0) return;
         //用jquery获取模板
         var tpl = $("#posAddHtml-template").html();
         //json数据
         var content = {treeNode:treeNode};
         //预编译模板
         var template = Handlebars.compile(tpl);
         //匹配json内容
         var html = template(content);
         //输入模板
         $("#posItem").append(html);
         
     };
    //角色树左键双击
     function rolTreeOnDblClick(event, treeId, treeNode){   
         if(treeNode.subSystem!=null&&treeNode.subSystem!=""){
             treeNode.sysName=treeNode.subSystem.sysName;
         }else{
             treeNode.sysName=" ";
         }
         rolAddHtml(treeNode);
     };
     
     function rolAddHtml(treeNode){
        // if( systemId==0) return;
         var obj=$("#" +treeNode.roleId);
         if(obj.length>0) return;
         //用jquery获取模板
         var tpl = $("#rolAddHtml-template").html();
         var content = {treeNode:treeNode};
         //预编译模板
         var template = Handlebars.compile(tpl);
         //匹配json内容
         var html = template(content);
         //输入模板
         $("#rolItem").append(html);
        
     };  
    //右键菜单
     function zTreeOnRightClick(e, treeId, treeNode) {
        // alert(treeNode.orgId);
            if (treeNode.orgId=="-1") {
                orgPostTree.cancelSelectedNode();//取消节点选中状态
                menu_root.show({
                    top : e.pageY,
                    left : e.pageX
                });
            } else  {
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
                    [ {
                        text : '删除',
                        click : 'delNode',
                        alias:'delOrg'
                    }]
                    </f:menu>       
            });

            menu_root = $.ligerMenu({
                top : 100,
                left : 100,
                width : 100,
                items : [ {
                    text : '增加',
                    click : addNode
                }]
            });
        };
        
        //新增节点
        function addNode() {
            var treeNode=getSelectNode();
            var orgId = treeNode.posId;
            //var demId = treeNode.demId;
            var url = __ctx + "/platform/system/position/edit.ht?orgId="+ orgId;
            var url = "edit.ht?orgId=" + orgId + "&demId=" + demId + "&action=add";
            $("#viewFrame").attr("src", url);
        };
         //删除节点
        function delNode() {
            var treeNode=getSelectNode();
            var callback = function(rtn) {
                if (!rtn) return;
                var params = "orgId=" + treeNode.orgId;
                $.post("orgdel.ht", params, function() {
                    orgTree.removeNode(treeNode);
                });
            };
            $.ligerDialog.confirm("确认要删除此组织吗，其下组织也将被删除？", '提示信息', callback);

        };
        
        function showUserDlg(){
            var superior=$("#superior").val();
            var superiorId=$("#superiorId").val();
            var topOrgId= "${param.topOrgId}";
            var scope;
            if(topOrgId!=null && topOrgId!=""){
                var script="return " + topOrgId +";";
                scope="{type:\"script\",value:\""+script+"\"}";
            }
            else{
                scope="{type:\"system\",value:\"all\"}";
            }
            var script="return "+topOrgId; 
             UserDialog({
                selectUserIds:superiorId,
                selectUserNames:superior,
                scope:scope,
                callback:function(userIds,userNames){
                    $('#superior').val(userNames);
                    $('#superiorId').val(userIds);
                }
            });
        };
    </script>
    <script id="orgPosAddHtml-template" type="text/x-handlebars-template" >
        <tr id="{{treeNode.posId}}" >
            <td>
                {{treeNode.company}}<input type="hidden" name="orgId" value="{{treeNode.companyId}}" />
            </td>
            <td>
                {{treeNode.orgName}}<input type="hidden" name="orgId" value="{{treeNode.orgId}}" />
            </td>
            <td>
                {{treeNode.posName}}<input type="hidden" name="posId" value="{{treeNode.posId}}" />
            </td>
            <td>
                <input type="radio" name="posIdPrimary" value="{{treeNode.posId}}" />
            </td>
            <td>
                <input type="checkbox" name="posIdCharge" value="{{treeNode.posId}}" />
            </td>
            <td>
                <a href="#" onclick="delrow('{{treeNode.posId}}')" class="oa-button-label">移除</a>
            </td>
        </tr>
    </script>
    <script id="rolAddHtml-template"  type="text/x-handlebars-template" >
        <tr id="{{treeNode.roleId}}" >
            <td>
                {{treeNode.roleName}}<input type="hidden"  name="roleId" value="{{treeNode.roleId}}" />
            </td>
            <td>
                {{treeNode.sysName}}
            </td>
            <td>
                <a href="#" onclick="delrow('{{treeNode.roleId}}')" class="oa-button-label">移除</a>
            </td>
        </tr>
    </script>
    <script id="posAddHtml-template" type="text/x-handlebars-template" >
        <tr id="{{treeNode.posId}}" >
            <td>
                {{treeNode.posName}}<input type="hidden"  name="posId" value="{{treeNode.posId}}" />
            </td>
            <td>
                <input type='radio' name="posIdPrimary" value="{{treeNode.posId}}" />
            </td>
            <td>
                <a href="#" onclick="delrow('{{treeNode.posId}}')" class="oa-button-label">移除</a>
            </td>
        </tr>
    </script>
    <style>
        #tabMyInfo .l-tab-links {
            border: 1px solid #eceff8;
            background: #eceff8;
        }
        .l-tab-links li {
            cursor: pointer;
            float: left;
            font-size: 14px;
            height: 42px;
            line-height: 42px;
            margin: 0;
            overflow: hidden;
            position: relative;
        }
        #tabMyInfo .l-tab-links li.l-selected a {
            display: block;
            padding: 0 10px;
            border-radius: 0;
        }
        #tabMyInfo .l-tab-links li a {
            color: #657386;
            padding: 0 10px;
        }


        .oa-h3{
            text-align: left;
        }

        .oa-table--second td,
        .oa-table--second th{
            padding: 10px 20px;
            border: 1px solid #eceff8;
        }
        .oa-table--second th{
            font-weight: bold;
            width: 100px;
            background: #f6f7fb;
        }
        .profile{
            text-align: center;
            margin: 20px auto;
            width: 100px;
            height: 100px;
            line-height: 100px;
            border-radius: 50%;
            background: 50%/cover;
            background-color: #fff;
        }
        .profile img{
            max-width: 100%;
        }

        #orgPosTree{
            overflow: auto;
        }
        .tree-toolbar{
            background: #f6f7fb;
            border-bottom: 1px solid #eceff8;
        }
        .ztree{
            background: #f6f7fb;
        }
        #demensionId{
            border: 0;
            background: #f6f7fb;
            border-bottom: 1px solid #eceff8;

        }
        .tree-toolbar{
            padding: 10px 5px;
            border-bottom: 1px solid #eceff8;
        }
    </style>
</head>
<body>
    <div class="oa-pd20">
        <a class="oa-button oa-button--primary oa-button--blue" id="dataFormSave">保存</a>
        <a class="oa-button oa-button--primary oa-button--blue" href="${returnUrl}">返回</a>
    </div>
    <form id="sysUserForm" method="post" action="updateDepartment.ht">
        <div  id="tabMyInfo">  
            <%--不是修改本人信息则--%>
            <c:if test="${bySelf!=1}">
                <div title="组织岗位选择" tabid="orgdetail" icon="${ctx}/styles/default/images/icon/home.png" >                             
        
                    <div style="width: 49%; height: 300px; overflow-y: auto; float: left; background: #f6f7fb; border: 1px solid #eceff8;">
                        <select id="demensionId" style="width: 100% !important;">
                            <c:forEach var="dem" items="${demensionList}">
                                <option value="${dem.demId}" <c:if test="${dem.demId==1}">selected="selected"</c:if> >${dem.demName}</option>
                            </c:forEach>
                        </select>
                        <div class="tree-toolbar" id="pToolbar">
                            <div class="toolBar">
                                <div class="group">
                                    <a class="link reload" id="treeReFresh">刷新</a>
                                </div>
                                <div class="l-bar-separator"></div>
                                <div class="group">
                                    <a class="link expand" id="treeExpand">展开</a>
                                </div>
                                <div class="l-bar-separator"></div>
                                <div class="group">
                                    <a class="link collapse" id="treeCollapse">收起</a>
                                </div>
                            </div>
                        </div>  

						<ul  id="orgTree" class="ztree"></ul>

                    </div>
        
                    <div style="width: 49%; height: 300px; overflow-y: auto; float: right; background: #f6f7fb; border: 1px solid #eceff8;">
                        <div id="orgPosTree" class="ztree"></div>
                    </div>
                    
                    <div style="clear: both;"></div>
    
                    <div class="oa-pdv20">
                        <button type="button" class="oa-button-label" id="orgPosAdd">添加</button>
                    </div>
                    
                    <!-- 添加的列表 -->
                    <table id="orgItem" class="oa-table--default">
                        <thead>
                            <th>公司名称</th>
                            <th>组织名称</th>
                            <th>岗位名称</th>
                            <th>是否主岗位</th>
                            <th>主要负责人</th>
                            <th>操作</th>
                        </thead>
                        <c:forEach items="${userPosList}" var="orgItem">
                            <tr trName="${orgItem.orgName}"  id="${orgItem.posId}" style='cursor:pointer'>
                                <td>
                                    ${orgItem.company}<input type="hidden" name="companyId" value="${orgItem.companyId}">                                       
                                </td>
                                <td>
                                    ${orgItem.orgName}<input type="hidden" name="orgId" value="${orgItem.orgId}">                                       
                                <td>
                                    ${orgItem.posName}<input type="hidden" name="posId" value="${orgItem.posId}">                                       
                                </td>
                                <td>                                    
                                    <input type="radio" name="posIdPrimary" value="${orgItem.posId}" <c:if test='${orgItem.isPrimary==1}'>checked</c:if> />
                                </td>
                                <td>                                    
                                    <input type="checkbox" name="posIdCharge" value="${orgItem.posId}"  <c:if test='${orgItem.isCharge==1}'>checked</c:if>> 
                                </td>
                                <td>
                                    <a href="javascript:;" onclick="delrow('${orgItem.posId}')" class="oa-button-label">移除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <!-- 添加的列表 -->
                </div>
            
                <c:if test="${userId != 0}">
                    <div title="所属组织角色">
                        <table class="oa-table--default">
                            <thead>                             
                                <th>组织</th>
                                <th>角色</th>
                            </thead>
                            <c:forEach items="${sysOrgRoles}" var="sysOrgRole">
                                <tr>
                                    <td>${sysOrgRole.key.orgName}</td>
                                    <td>
                                        <c:forEach items="${sysOrgRole.value}" var="sysRole">
                                            ${sysRole.roleName} 
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>    
                </c:if>
            
                <div  title="上级配置" tabid="superior">
                    <c:set var="ids" value=""></c:set>
                    <c:set var="names" value=""></c:set>
                    <c:if test="${!empty userUnders }">
                        <c:forEach items="${userUnders}" var="user" varStatus="status">
                            <c:choose>
                                <c:when test="${!status.last}">
                                    <c:set var="ids" value='${ids }${user.userId  },'></c:set>
                                    <c:set var="names" value='${names}${user.fullname },'></c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="ids" value='${ids }${user.userId  }'></c:set>
                                    <c:set var="names" value='${names}${user.fullname }'></c:set>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <table id="superItem" class="oa-table--default"  cellpadding="1" cellspacing="1">
                        <tr>
                            <th>上级</th>
                            <td>
                                <input type="text" name="superior" size="80" id="superior" class="oa-new-input" value="${names}">
                                <input type="hidden" name="superiorId" id="superiorId" value="${ids}">
                                <button type="button" class='oa-button-label' onclick="showUserDlg()">配置上级</button>
                            </td>
                        </tr>
                    </table>
                </div>   

            </c:if>

        </div>
		<input type="hidden" value="${userId }" name="userId"/>
        <input type="hidden" id="saveData" name="saveData"/>
    </form>
</body>
</html>
