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

        if(${sysUser.userId==null}){
            //valid(showRequest,showResponse,1);
        }else{
            //valid(showRequest,showResponse);
        }
        
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
        
        //$("a.save").click(function() {
        //  $('#sysUserForm').submit(); 
        //});
        $("#orgPosAdd").click(function(){
            btnAddRow('orgPosTree');
        });
        $("#orgPosDel").click(function(){
            btnDelRow();
        });
        $("#posAdd").click(function(){
            btnAddRow('posTree');
        });
        $("#posDel").click(function(){
            btnDelRow();
        });
        
        $("#rolAdd").click(function(){
            btnAddRow('rolTree');
        });
        $("#rolDel").click(function(){
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
        if("grade"==action){
            loadorgGradeTree();
        }else{
            loadorgTree();
        }
        loadrolTree();
        
       var orgIds="${orgIds}";
       if( orgIds == undefined || orgIds == null || orgIds == ""){
       }else{
           //编辑页面才调用此方法
           loadorgPosTree(orgIds);
       }
    });//function end

    //添加个人照片
    function picCallBack(array){
        if(!array && array!="") return;
        var fileId=array[0].fileId,
            fileName=array[0].fileName,
            extName=array[0].extName;
        
        var path= __ctx + "/platform/system/sysFile/getFileById.ht?fileId=" + fileId;
        if(/(png|gif|jpg)/gi.test(extName)){
            $("#picture").val("/platform/system/sysFile/getFileById.ht?fileId=" + fileId);
            $("#personPic").attr("src",path);
        }
            
        else
            $.ligerDialog.warn("请选择*png,*gif,*jpg类型图片!");
                
    };
    //上传照片
    function addPic(){
            HtmlUploadDialog({max:1,size:10,callback:picCallBack});
    };
    //删除照片
    function delPic(){
        $("#picture").val("");
        $("#personPic").attr("src","${ctx}/commons/image/default_image_male.jpg");
    };
    
    //生成组织树             
    function loadorgTree(){
        if(action =='grade'){loadorgGradeTree();return;}
        
        var demId=$("#demensionId").val();
        var setting = {
            data: {
                key : {
                    
                    name: "orgName",
                    title: "orgName"
                },
                simpleData: {
                    enable: true,
                    idKey: "orgId",
                    pIdKey: "orgSupId",
                    rootPId: -1
                }
            },
            async: {
                enable: true,
                url:__ctx+"/platform/system/sysOrg/getTreeData.ht?type=system&typeVal=all&demId="+demId,
                autoParam:["orgId","orgSupId"]
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
                onCheck:orgPostTreeExpand,
                onAsyncSuccess: orgTreeOnAsyncSuccess
            }
        };
        orgTree=$.fn.zTree.init($("#orgTree"), setting);
    };
    
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
    
    function loadorgGradeTree(){
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
            var orgId = $("#orgAuth").val(); 
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
    	body {
    		min-width: 800px;
    	}
        .subtable {
        	overflow: auto;
        }
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

    <form id="sysUserForm" method="post" action="save.ht?selectedOrgId=${param.selectedOrgId}">
            
        <div  id="tabMyInfo">  
            <!-- tab -->
            <div title="基本信息" tabid="userdetail" icon="${ctx}/styles/default/images/resicon/user.gif">
                <div class="profile">
                    <img id="personPic" onerror="this.src='/commons/image/default_image_male.jpg'" src="${pictureLoad}" alt="个人相片" />
                </div>

                <div class="oa-text-center oa-pdb20">
                    <button type="button" class="oa-button-label" onclick="addPic();">上传照片</button>
                    <button type="button" class="oa-button-label" onclick="delPic();">删除照片</button>
                </div>

                <table class="oa-table--default oa-table--second">
                    <tr>
                        <th>帐   号: <span class="required red">*</span></th>
                        <td><c:if test="${bySelf==1}"><input type="hidden" name="bySelf" value="1"></c:if><input type="text" <c:if test="${bySelf==1}">disabled="disabled"</c:if> id="account" name="account" readonly="readonly" value="${sysUser.account}" class="oa-new-input"/></td>
                        <th>用户姓名: <span class="required red">*</span></th>
                        <td><input type="text" id="fullname" name="fullname" validate="{required:true}" value="${sysUser.fullname}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>用户性别: </th>
                        <td>
                            <select name="sex" class="oa-new-select">
                                <option value="1" <c:if test="${sysUser.sex==1}">selected</c:if> >男</option>
                                <option value="0" <c:if test="${sysUser.sex==0}">selected</c:if> >女</option>
                            </select>                       
                        </td>
                        <th>入职时间: </th>
                        <td>
                            <input type="text" id="entryDate" name="entryDate" value="<fmt:formatDate value='${sysUser.entryDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" />
                        </td>
                    </tr> 
                    <tr>
                        <th>转正时间: </th>
                        <td>
                            <input type="text" id="formalDate" name="formalDate" value="<fmt:formatDate value='${sysUser.formalDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" />
                        </td>
                        <th>离职时间: </th>
                        <td>
                            <input type="text" id="resignationDate" name="resignationDate" value="<fmt:formatDate value='${sysUser.resignationDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" />
                        </td>
                    </tr>        
                    <tr>
                        <th>劳动合同开始时间: </th>
                        <td>
                            <input type="text" name="laborcontstarttime" value="<fmt:formatDate value='${sysUser.laborcontstarttime}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" />
                        </td>
                        <th>劳动合同结束时间: </th>
                        <td>
                            <input type="text"  name="laborcontendtime" value="<fmt:formatDate value='${sysUser.laborcontendtime}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" />
                        </td>
                    </tr>                       
                    <tr>
                        <th>员工状态: </th>
                        <td>
                            <select name="userStatus" class="oa-new-select" >
                                <option value="正式员工" <c:if test="${sysUser.userStatus=='正式员工'}">selected</c:if> >正式员工</option>
                                <option value="试用员工" <c:if test="${sysUser.userStatus=='试用员工'}">selected</c:if> >试用员工</option>
                                <option value="返聘" <c:if test="${sysUser.userStatus=='返聘'}">selected</c:if> >返聘</option>
                                <option value="外聘" <c:if test="${sysUser.userStatus=='外聘'}">selected</c:if> >外聘</option>
                                <option value="实习" <c:if test="${sysUser.userStatus=='实习'}">selected</c:if> >实习</option>
                                <option value="离职" <c:if test="${sysUser.userStatus=='离职'}">selected</c:if> >离职</option>
                            </select>   
                        </td>
                        <th>是否锁定: </th>
                        <td>                               
                            <select name="isLock" class="oa-new-select" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
                                <option value="<%=SysUser.UN_LOCKED %>" <c:if test="${sysUser.isLock==0}">selected</c:if> >未锁定</option>
                                <option value="<%=SysUser.LOCKED %>" <c:if test="${sysUser.isLock==1}">selected</c:if> >已锁定</option>
                            </select>
                        </td>                 
                    </tr>
                    <tr>
                        <th>是否过期: </th>
                        <td>
                            <select name="isExpired" class="oa-new-select" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
                                <option value="<%=SysUser.UN_EXPIRED %>" <c:if test="${sysUser.isExpired==0}">selected</c:if> >未过期</option>
                                <option value="<%=SysUser.EXPIRED %>" <c:if test="${sysUser.isExpired==1}">selected</c:if> >已过期</option>
                            </select>
                        </td>
                        <%-- <th>当前状态: </th>
                        <td>
                            <select name="status"  class="oa-new-select" <c:if test="${bySelf==1}">disabled="disabled"</c:if>>
                                <option value="<%=SysUser.STATUS_OK %>" <c:if test="${sysUser.status==1}">selected</c:if> >激活</option>
                                <option value="<%=SysUser.STATUS_NO %>" <c:if test="${sysUser.status==0}">selected</c:if> >禁用</option>
                                <option value="<%=SysUser.STATUS_Del %>" <c:if test="${sysUser.status==-1}">selected</c:if>>删除</option>
                            </select>
                        </td> --%>                               
                    </tr>                       
                    <tr>
                        <th>邮箱地址: </th>
                        <td><input type="text" id="email" name="email" value="${sysUser.email}" validate="{email:true}" class="oa-new-input"/></td>
                        <th>微   信: </th>
                        <td ><input type="text" id="weixinid" name="weixinid" value="${sysUser.weixinid}"  class="oa-new-input"/></td>
                    </tr>
                    <tr>
                        <th>手   机: </th>
                        <td><input type="text" id="mobile" name="mobile" validate='{"maxlength":11,"手机号码":true}'  value="${sysUser.mobile}" class="oa-new-input"/></td>                        
                        <th>电   话: </th>
                        <td><input type="text" id="phone" name="phone" value="${sysUser.phone}"  class="oa-new-input"/></td>
                    </tr>
                    <tr style="<c:if test="${not empty sysUser.userId}">display:none</c:if>">
                        <th>密   码: <span class="required red">*</span></th>
                        <td colspan="3"><input type="text" id="password" name="password" validate="{required:true}"  value="${sysUser.password}" class="oa-new-input test"/></td>
                    </tr>
                </table>
                <input type="hidden" name="userId" value="${sysUser.userId}" />
                <input type="hidden" id="picture" name="picture" value="${sysUser.picture}" />
           		<h3 class="oa-h3" style="text-align: center;" <c:if test="${empty sysUser.userId}"> style="display:none" </c:if>>资料信息</h3>
                <table <c:if test="${empty sysUser.userId}"> style="display:none" </c:if> class="oa-table--default oa-table--second">
                    <tr>
                        <th>专业:</th>
                        <td>
                            <input type="hidden" id="id" name="id" value="${userInfomation.id}" />     
                            <input type="text" id="major" name="major" value="${userInfomation.major}" class="oa-new-input" />
                        </td>
                        <th>职称专业:</th>
                        <td><input type="text" id="positional_major" name="positional_major" value="${userInfomation.positional_major}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>职称名称:</th>
                        <td id="positionalField">
	                        <input type="text" id="positional" name="positional" value="${userInfomation.positional}" class="oa-new-input" />
                        </td>
                        <th>出生日期:</th>
                        <td><input type="text" id="birthday" name="birthday" value="<fmt:formatDate value='${userInfomation.birthday}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" /></td>
                    </tr>
                    <tr>
                        <th>婚姻状况:</th>
                        <td><input type="text" id="marriage_state" name="marriage_state" value="${userInfomation.marriage_state}" class="oa-new-input" /></td>
                        <th>曾用名:</th>
                        <td><input type="text" id="used_name" name="used_name" value="${userInfomation.used_name}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>民族:</th>
                        <td><input type="text" id="nation" name="nation" value="${userInfomation.nation}" class="oa-new-input" /></td>
                        <th>籍贯:</th>
                        <td><input type="text" id="address" name="address" value="${userInfomation.address}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>文化程度:</th>
                        <td><input type="text" id="education" name="education" value="${userInfomation.education}" class="oa-new-input" /></td>
                        <th>参加工作时间:</th>
                        <td><input type="text" id="start_work_time" name="start_work_time" value="<fmt:formatDate value='${userInfomation.start_work_time}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" /></td>
                    </tr>
                    <tr>
                        <th>毕业时间:</th>
                        <td><input type="text" id="graduate_time" name="graduate_time" value="<fmt:formatDate value='${userInfomation.graduate_time}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" /></td>
                        <th>毕业院校:</th>
                        <td><input type="text" id="graduate_school" name="graduate_school" value="${userInfomation.graduate_school}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>政治面貌:</th>
                        <td><input type="text" id="political_status" name="political_status" value="${userInfomation.political_status}" class="oa-new-input" /></td>
                        <th>身份证号码:</th>
                        <td><input type="text" id="identification_id" name="identification_id" value="${userInfomation.identification_id}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>户籍:</th>
                        <td><input type="text" id="address_type" name="address_type" value="${userInfomation.address_type}" class="oa-new-input" /></td>
                        <th>是否有传染病史:</th>
                        <td>
                            <input type="radio" name="infection_history" value="1" <c:if test='${userInfomation.infection_history==1}'>checked</c:if> />是
                            <input type="radio" name="infection_history" value="0" <c:if test='${userInfomation.infection_history==0}'>checked</c:if> />否
                        </td>
                    </tr>
                    <tr>
                        <th>是否有遗传病史:</th>
                        <td>
                            <input type="radio" name="disorders_history" value="1" <c:if test='${userInfomation.disorders_history==1}'>checked</c:if> />是
                            <input type="radio" name="disorders_history" value="0" <c:if test='${userInfomation.disorders_history==0}'>checked</c:if> />否
                        </td>
                        <th>社保登记编号:</th>
                        <td><input type="text" id="social_security_num" name="social_security_num" value="${userInfomation.social_security_num}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>社会保险电脑号:</th>
                        <td><input type="text" id="social_security_computer_id" name="social_security_computer_id" value="${userInfomation.social_security_computer_id}" class="oa-new-input" /></td>
                        <th>月工资总额:</th>
                        <td><input type="text" id="monthly_wages" name="monthly_wages" value="${userInfomation.monthly_wages}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>利手:</th>
                        <td><input type="text" id="handedness" name="handedness" value="${userInfomation.handedness}" class="oa-new-input" /></td>
                        <th>医疗险一档:</th>
                        <td>
                            <input type="radio" name="medical_insurance_first" value="1" <c:if test='${userInfomation.medical_insurance_first==1}'>checked</c:if> />是
                            <input type="radio" name="medical_insurance_first" value="0" <c:if test='${userInfomation.medical_insurance_first==0}'>checked</c:if> />否
                        </td>
                    </tr>
                    <tr>
                        <th>医疗险二档:</th>
                        <td>
                            <input type="radio" name="medical_insurance_second" value="1" <c:if test='${userInfomation.medical_insurance_second==1}'>checked</c:if> />是
                            <input type="radio" name="medical_insurance_second" value="0" <c:if test='${userInfomation.medical_insurance_second==0}'>checked</c:if> />否
                        </td>
                        <th>工伤险:</th>
                        <td>
                            <input type="radio" name="injury_insurance" value="1" <c:if test='${userInfomation.injury_insurance==1}'>checked</c:if> />是
                            <input type="radio" name="injury_insurance" value="0" <c:if test='${userInfomation.injury_insurance==0}'>checked</c:if> />否
                        </td>
                    </tr>
                    <tr>
                        <th>失业险:</th>
                        <td>
                            <input type="radio" name="unemployment_insurance" value="1" <c:if test='${userInfomation.unemployment_insurance==1}'>checked</c:if> />是
                            <input type="radio" name="unemployment_insurance" value="0" <c:if test='${userInfomation.unemployment_insurance==0}'>checked</c:if> />否
                        </td>
                        <th>养老险:</th>
                        <td>
                            <input type="radio" name="endowment_insurance" value="1" <c:if test='${userInfomation.endowment_insurance==1}'>checked</c:if> />是
                            <input type="radio" name="endowment_insurance" value="0" <c:if test='${userInfomation.endowment_insurance==0}'>checked</c:if> />否
                        </td>
                    </tr>
                    <tr>
                        <th>特长爱好:</th>
                        <td><input type="text" id="hobby" name="hobby" value="${userInfomation.hobby}" class="oa-new-input" /></td>
                        <th>户籍所在地:</th>
                        <td><input type="text" id="home_address" name="home_address" value="${userInfomation.home_address}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>配偶姓名:</th>
                        <td><input type="text" id="spouse_name" name="spouse_name" value="${userInfomation.spouse_name}" class="oa-new-input" /></td>
                        <th>父母居住地:</th>
                        <td><input type="text" id="parents" name="parents" value="${userInfomation.parents}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>配偶身份证号码:</th>
                        <td><input type="text" id="spouse_identification_id" name="spouse_identification_id" value="${userInfomation.spouse_identification_id}" class="oa-new-input" /></td>
                        <th>配偶居住地:</th>
                        <td><input type="text" id="spouse_address" name="spouse_address" value="${userInfomation.spouse_address}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>通讯地址:</th>
                        <td><input type="text" id="link_address" name="link_address" value="${userInfomation.link_address}" class="oa-new-input" /></td>
                        <th>手机短号:</th>
                        <td><input type="text" id="sjdh" name="sjdh" value="${userInfomation.sjdh}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>紧急联系人:</th>
                        <td><input type="text" id="emergency_link_person" name="emergency_link_person" value="${userInfomation.emergency_link_person}" class="oa-new-input" /></td>
                        <th>交行卡号:</th>
                        <td><input type="text" id="BOC_id" name="BOC_id" value="${userInfomation.BOC_id}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>工资卡号:</th>
                        <td><input type="text" id="ICBC_id" name="ICBC_id" value="${userInfomation.ICBC_id}" class="oa-new-input" /></td>
                        <th>紧急联系人电话:</th>
                        <td><input type="text" id="emergency_link_person_phone" name="emergency_link_person_phone" value="${userInfomation.emergency_link_person_phone}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>QQ号码:</th>
                        <td><input type="text" id="QQ" name="QQ" value="${userInfomation.QQ}" class="oa-new-input" /></td>
                        <th>微信:</th>
                        <td><input type="text" id="wechart" name="wechart" value="${userInfomation.wechart}" class="oa-new-input" /></td>
                    </tr>
                    <tr>
                        <th>剩余年假:</th>
                        <td><input type="text" id="yearVacation" name="yearVacation" readonly="readonly" value="${userInfomation.yearVacation}" class="oa-new-input" /></td>
                        <th></th>
                        <td></td>
                    </tr>
                </table>                        

                <!-- section -->
                <h3 class="oa-h3">家庭成员及主要社会关系</h3>
                <div class="subtable" type="subtable" tablename="entryFamily" tabledesc="家庭成员" show="true" parser="rowmodeedit" id="entryFamily" formtype="edit"> 
                    <table class="oa-table--default"> 
                        <thead>
                            <tr> 
                                <th>关系</th> 
                                <th>姓名</th> 
                                <th>性别</th> 
                                <th>出生年</th> 
                                <th>工作单位</th> 
                                <th>联系电话</th> 
                                <th>附件</th> 
                            </tr> 
                        </thead>
                        <tr> 
                            <td colspan="7"><a class="oa-button-label add" href="javascript:;">添加</a></td> 
                        </tr> 
                        <c:forEach items="${userInfomation.entryFamilyList}" var="entryFamily" varStatus="status">
                            <tr type="subdata"> 
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:relationship" lablename="关系" class="oa-new-input" value="${entryFamily.relationship}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_name" lablename="姓名" class="oa-new-input" value="${entryFamily.person_name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                                 <td style="white-space: nowrap;">
                                 	<select name="s:entryFamily:person_sex" class="oa-new-select" value="${entryFamily.person_sex}">
                                 		<option value="1" <c:if test='${entryFamily.person_sex==1}'>selected</c:if>>男</option>
                                 		<option value="0" <c:if test='${entryFamily.person_sex==0}'>selected</c:if>>女</option>
                                 	</select>
                                 </td>  
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_birthday_year" lablename="出生年" class="oa-new-input" value="${entryFamily.person_birthday_year}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_workspace" lablename="工作单位" class="oa-new-input" value="${entryFamily.person_workspace}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_phone" lablename="联系电话" class="oa-new-input" value="${entryFamily.person_phone}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                                 <td><input ctltype="attachment" name="s:entryFamily:person_attachment" isdirectupload="0" right="w" value='${entryFamily.person_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                            </tr>
                        </c:forEach> 
                        <tr type="append" style="display:none;"> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:relationship" lablename="关系" class="oa-new-input" value="${entryFamily.relationship}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_name" lablename="姓名" class="oa-new-input" value="${entryFamily.person_name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td style="white-space: nowrap;">
                             	<select name="s:entryFamily:person_sex" class="oa-new-select" value="${entryFamily.person_sex}">
                             		<option value="1" <c:if test='${entryFamily.person_sex==1}'>selected</c:if>>男</option>
                             		<option value="0" <c:if test='${entryFamily.person_sex==0}'>selected</c:if>>女</option>
                             	</select>
                            </td> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_birthday_year" lablename="出生年" class="oa-new-input" value="${entryFamily.person_birthday_year}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_workspace" lablename="工作单位" class="oa-new-input" value="${entryFamily.person_workspace}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryFamily:person_phone" lablename="联系电话" class="oa-new-input" value="${entryFamily.person_phone}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td><input ctltype="attachment" name="s:entryFamily:person_attachment" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td> 
                        </tr>
                    </table>

                    <input name="s:entry_family:id" type="hidden" pk="true" value="" />
                </div>
                <!-- section --> 
                        
                
                <!-- section -->  
                <h3 class="oa-h3">学习经历（包括所接受的专业培训，大学起填）</h3>
                <div class="subtable" type="subtable" tablename="entryEducationHistory" tabledesc="学习经历（包括所接受的专业培训，大学起填）" show="true" parser="rowmodeedit" id="entryEducationHistory" formtype="edit"> 
                    <table class="oa-table--default">
                        <thead>
                            <tr>
                                <th>开始日期</th>
                                <th>结束日期</th>
                                <th>就读学校或机构</th>
                                <th>专业</th>
                                <th>所获证书、学位、奖励</th>
                                <th>附件</th>
                            </tr>
                        </thead>
                        <tr>
                            <td colspan="7"><a class="oa-button-label add" href="javascript:;">添加</a></td>
                        </tr>
                        <c:forEach items="${userInfomation.entryEducationHistoryList}" var="entryEducationHistory" varStatus="status">
                        <tr  type="subdata">
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:startDate" lablename="起止时间"  value="<fmt:formatDate value='${entryEducationHistory.startDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:endDate"  value="<fmt:formatDate value='${entryEducationHistory.endDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date"/> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:education_school" lablename="就读学校或机构" class="oa-new-input" value="${entryEducationHistory.education_school}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:major" lablename="专业" class="oa-new-input" value="${entryEducationHistory.major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:achieve_certificate" lablename="所获证书、学位、奖励" class="oa-new-input" value="${entryEducationHistory.achieve_certificate}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td><input  ctltype="attachment" name="s:entryEducationHistory:attachment" isdirectupload="0" right="w" value='${entryEducationHistory.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                        </tr>
                        </c:forEach>
                        <tr  type="append" style="display:none;">
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:startDate" lablename="起止时间" class="oa-new-input date" value="${entryEducationHistory.startDate}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:endDate"  value="<fmt:formatDate value='${entryEducationHistory.endDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date"/> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:education_school" lablename="就读学校或机构" class="oa-new-input" value="${entryEducationHistory.education_school}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:major" lablename="专业" class="oa-new-input" value="${entryEducationHistory.major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryEducationHistory:achieve_certificate" lablename="所获证书、学位、奖励" class="oa-new-input" value="${entryEducationHistory.achieve_certificate}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td><input ctltype="attachment" name="s:entryEducationHistory:attachment" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                        </tr>
                    </table> 
                    <input name="s:entry_education_history:id" type="hidden" pk="true" value="" />
                </div>  
                        
                <h3 class="oa-h3">主要工作经历</h3>
                <div class="subtable" type="subtable" tablename="entryWorkHistory" tabledesc="主要工作经历" show="true" parser="rowmodeedit" id="entryWorkHistory" formtype="edit">
                    <table class="oa-table--default" border="0" cellpadding="2" cellspacing="0" width="-173">
                        <thead>
                            <tr>
                                <th>开始日期</th>
                                <th>结束日期</th>
                                <th>工作单位</th>
                                <th>部门岗位</th>
                                <th>技术职务</th>
                                <th>附件</th>
                            </tr>
                        </thead>
                        <tr>
                            <td colspan="7"><a class="oa-button-label add" href="javascript:;">添加</a></td>
                        </tr>
                        <c:forEach items="${userInfomation.entryWorkHistoryList}" var="entryWorkHistory" varStatus="status">
                            <tr  type="subdata">
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:startDate" lablename="起止时间"  value="<fmt:formatDate value='${entryWorkHistory.startDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" /> </span> </td>
                            	<td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:endDate"  value="<fmt:formatDate value='${entryWorkHistory.endDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date"/> </span> </td>
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:workplace" lablename="工作单位" class="oa-new-input" value="${entryWorkHistory.workplace}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:department_post" lablename="部门岗位" class="oa-new-input" value="${entryWorkHistory.department_post}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:positions" lablename="技术职务" class="oa-new-input" value="${entryWorkHistory.positions}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                <td><input ctltype="attachment" name="s:entryWorkHistory:attachment" isdirectupload="0" right="w" value='${entryWorkHistory.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                            </tr>
                        </c:forEach>
                        <tr  type="append" style="display:none;">
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:startDate" lablename="起止时间"  value="<fmt:formatDate value='${entryWorkHistory.startDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" /> </span> </td>
                           	<td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:endDate"  value="<fmt:formatDate value='${entryWorkHistory.endDate}' pattern='yyyy-MM-dd'/>" class="oa-new-input date"/> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:workplace" lablename="工作单位" class="oa-new-input" value="${entryWorkHistory.workplace}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:department_post" lablename="部门岗位" class="oa-new-input" value="${entryWorkHistory.department_post}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryWorkHistory:positions" lablename="技术职务" class="oa-new-input" value="${entryWorkHistory.positions}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td><input ctltype="attachment" name="s:entryWorkHistory:attachment" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                        </tr> 
                    </table>
                    <input name="s:entry_work_history:id" type="hidden" pk="true" value="" />
                </div>
                        
                <h3 class="oa-h3">职称专业</h3>
                <div class="subtable" type="subtable" tablename="entryProfessional" tabledesc="专业职称" show="true" parser="rowmodeedit" id="entryProfessional" formtype="edit">
                    <table class="oa-table--default" border="0" cellpadding="2" cellspacing="0" width="-173">
                        <thead>
                            <tr>
                                <th>职称编号</th>
                                <th>职称名称</th>
                                <th>发证机构</th>
                                <th>职称专业</th>
                                <th>取得职称时间</th>
                                <th>附件</th>
                            </tr>
                        </thead>
                        <tr>
                            <td colspan="7"><a class="oa-button-label add" href="javascript:;">添加</a></td>
                        </tr>
                        <c:forEach items="${userInfomation.entryProfessionalList}" var="entryProfessional" varStatus="status">
                        <tr  type="subdata">
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryProfessional:num" lablename="编号" class="oa-new-input" value="${entryProfessional.num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <input lablename="职称名称" class="dicComboTree entryProfessionalName l-text-field" nodekey="title" value="${entryProfessional.name}" validate="{empty:false}" name="s:entryProfessional:name" height="200" width="200" /> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryProfessional:organization" lablename="发证机构" class="oa-new-input" value="${entryProfessional.organization}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryProfessional:major" lablename="职称专业" class="oa-new-input" value="${entryProfessional.major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td><input type="text" name="s:entryProfessional:achieve_time" lablename="取得职称时间" value="<fmt:formatDate value="${entryProfessional.achieve_time}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td>
                            <td><input ctltype="attachment" name="s:entryProfessional:attachment" isdirectupload="0" right="w" value='${entryProfessional.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                        </tr>
                        </c:forEach>
                        <tr  type="append" style="display:none;">
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryProfessional:num" lablename="编号" class="oa-new-input" value="${entryProfessional.num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <input lablename="职称名称" class="dicComboTree l-text-field entryProfessionalName" nodekey="title" value="${entryProfessional.name}" validate="{empty:false}" name="s:entryProfessional:name" height="200" width="200" /> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryProfessional:organization" lablename="发证机构" class="oa-new-input" value="${entryProfessional.organization}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryProfessional:major" lablename="职称专业" class="oa-new-input" value="${entryProfessional.major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <input type="text" name="s:entryProfessional:achieve_time" lablename="取得职称时间" value="<fmt:formatDate value="${entryProfessional.achieve_time}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td>
                            <td><input ctltype="attachment" name="s:entryProfessional:attachment" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                        </tr>
                    </table>
                    <input name="s:entry_professional:id" type="hidden" pk="true" value="" />
                </div>
                 <!-- section -->
                <h3 class="oa-h3">子女信息</h3>
                <div class="subtable" type="subtable" tablename="entryChildren" tabledesc="子女信息" show="true" parser="rowmodeedit" id="entryChildren" formtype="edit"> 
                    <table class="oa-table--default"> 
                        <thead>
                            <tr> 
                                <th>姓名</th> 
                                <th>性别</th> 
                                <th>出生日期</th> 
                            </tr> 
                        </thead>
                        <tr> 
                            <td colspan="3"><a class="oa-button-label add" href="javascript:;">添加</a></td> 
                        </tr> 
                        <c:forEach items="${userInfomation.entryChildrenList}" var="entryChildren" varStatus="status">
                            <tr type="subdata"> 
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryChildren:name" lablename="姓名" class="oa-new-input" value="${entryChildren.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                                 <td style="white-space: nowrap;">
                                 	<select name="s:entryChildren:sex" class="oa-new-select" value="${entryChildren.sex}">
                                 		<option value="男" <c:if test='${entryChildren.sex=="男"}'>selected</c:if>>男</option>
                                 		<option value="女" <c:if test='${entryChildren.sex=="女"}'>selected</c:if>>女</option>
                                 	</select>
                                 </td>  
                                 <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryChildren:birthday" lablename="出生日期"  value="<fmt:formatDate value='${entryChildren.birthday}' pattern='yyyy-MM-dd'/>" class="oa-new-input date" /> </span> </td> 
                            </tr>
                        </c:forEach> 
                        <tr type="append" style="display:none;"> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryChildren:name" lablename="姓名" class="oa-new-input" value="${entryChildren.name}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td style="white-space: nowrap;">
                             	<select name="s:entryFamily:sex" class="oa-new-select" value="${entryFamily.person_sex}">
                             		<option value="男" <c:if test='${entryChildren.sex=="男"}'>selected</c:if>>男</option>
                             		<option value="女" <c:if test='${entryChildren.sex=="女"}'>selected</c:if>>女</option>
                             	</select>
                            </td> 
                            <td><span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryChildren:birthday" lablename="出生日期"  value="<fmt:formatDate value='${entryChildren.birthday}' pattern='yyyy-MM-dd'/>" class="oa-new-input date"  /> </span> </td> 
                        </tr>
                    </table>
                    <input name="s:entry_children:id" type="hidden" pk="true" value="" />
                </div>
                <!-- section -->
            </div>
            <div title="持证信息" tabid="otherInfo" icon="${ctx}/styles/default/images/resicon/user.gif">        
                <h3 class="oa-h3">执业资格</h3>
                <div class="subtable" type="subtable" tablename="entryVocationQualification" tabledesc="执业资格" show="true" parser="rowmodeedit" id="entryVocationQualification" formtype="edit" style="width: 100%;overflow: auto;">
                    <table class="oa-table--default" border="0" cellpadding="2" cellspacing="0" width="-173">
                        <thead>
                            <tr>
                                <th>执业资格编号</th>
                                <th>执业资格名称</th>
                                <th>发证机构</th>
                                <th>执业资格证专业</th>
                                <th>取得证书时间</th>
                                <th>是否转入本公司</th>
                                <th>附件</th>
                            </tr>
                        </thead>
                        <tr>
                            <td colspan="7"><a class="oa-button-label add" href="javascript:;">添加</a></td>
                        </tr>
                        <c:forEach items="${userInfomation.entryVocationQualificationList}" var="entryVocationQualification" varStatus="status">
                            <tr  type="subdata">
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryVocationQualification:num" lablename="编号" class="oa-new-input" value="${entryVocationQualification.num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                <td> <input lablename="执业资格证名称" class="dicComboTree l-text-field" nodekey="vocationQualification" value="${entryVocationQualification.name}" validate="{empty:false}" name="s:entryVocationQualification:name" height="200" width="200" />  </td>
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryVocationQualification:organization" lablename="发证机构" class="oa-new-input" value="${entryVocationQualification.organization}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryVocationQualification:major" lablename="执业资格证专业" class="oa-new-input" value="${entryVocationQualification.major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                                <td><input type="text" name="s:entryVocationQualification:achieve_time" lablename="取得证书时间" value="<fmt:formatDate value="${entryVocationQualification.achieve_time}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td>
                                <td>
                                    <select name="s:entryVocationQualification:switchs" class="oa-new-select">
                                        <option value="0" <c:if test="${entryVocationQualification.switchs==0}">selected</c:if> >否</option>
                                        <option value="1" <c:if test="${entryVocationQualification.switchs==1}">selected</c:if> >是</option>
                                    </select>
                                </td>
                                <td><input ctltype="attachment" name="s:entryVocationQualification:attachment" isdirectupload="0" right="w" value='${entryVocationQualification.attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                            </tr>
                        </c:forEach>
                        <tr  type="append" style="display:none;">
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryVocationQualification:num" lablename="编号" class="oa-new-input" value="${entryVocationQualification.num}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td><input lablename="执业资格证名称" class="dicComboTree l-text-field" nodekey="vocationQualification"  validate="{empty:false}" name="s:entryVocationQualification:name" height="200" width="200" />  </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryVocationQualification:organization" lablename="发证机构" class="oa-new-input" value="${entryVocationQualification.organization}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td> <span name="editable-input" isflag="tableflag"> <input type="text" name="s:entryVocationQualification:major" lablename="执业资格证专业" class="oa-new-input" value="${entryVocationQualification.major}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td>
                            <td><input type="text" name="s:entryVocationQualification:achieve_time" lablename="取得证书时间" value="<fmt:formatDate value="${entryVocationQualification.achieve_time}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /> </td>
                            <td>
                                <select name="s:entryVocationQualification:switchs" class="oa-new-select">
                                    <option value="0" <c:if test="${entryVocationQualification.switchs==0}">selected</c:if> >否</option>
                                    <option value="1" <c:if test="${entryVocationQualification.switchs==1}">selected</c:if> >是</option>
                                </select>
                            </td>
                            <td><input ctltype="attachment" name="s:entryVocationQualification:attachment" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" /></td>
                        </tr>
                    </table>
                    <input name="s:entry_vocation_qualification:id" type="hidden" pk="true" value="" />
                </div>
                
                <h3 class="oa-h3">注册资格</h3>
                <div class="subtable" type="subtable" tablename="registrationQualification" tabledesc="注册资格" show="true" parser="rowmodeedit" id="registrationQualification" formtype="edit" style="width: 100%;overflow: auto;"> 
                   <table class="oa-table--default"> 
                        <thead>
                            <tr>
                                <th>注册证书编号</th>
                                <th>注册专业</th>
                                <th>发证机构</th>
                                <th>发证日期</th>
                                <th>证书有效期</th>
                                <th>是否注册</th>
                                <th>附件</th>
                            </tr>
                        </thead>
                        <tr>
                            <td colspan=""><a class="oa-button-label add" href="javascript:;">添加</a></td>
                        </tr>
                        <c:forEach items="${userInfomation.registrationQualificationList}" var="registrationQualification" varStatus="status">
                            <tr  type="subdata"> 
                                <td>
                                    <span name="editable-input" isflag="tableflag"> 
                                    <input type="text" name="s:registrationQualification:certificate_regist_id" lablename="注册证书编号" class="oa-new-input" value="${registrationQualification.certificate_regist_id}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                                </td> 
                                <td>
                                    <span name="editable-input" isflag="tableflag">
                                    <input type="text" name="s:registrationQualification:regist_major" lablename="注册专业" class="oa-new-input" value="${registrationQualification.regist_major}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                                </td> 
                                <td>
                                    <span name="editable-input" isflag="tableflag"> 
                                    <input type="text" name="s:registrationQualification:regist_send_unit" lablename="发证机构" class="oa-new-input" value="${registrationQualification.regist_send_unit}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                                </td> 
                                <td>
                                    <input type="text" name="s:registrationQualification:get_date" lablename="发证日期" value="<fmt:formatDate value="${registrationQualification.get_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td> 
                                <td>
                                    <input type="text" name="s:registrationQualification:last_effectice_date" lablename="证书有效期" value="<fmt:formatDate value="${registrationQualification.last_effectice_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td> 
                                <td>
                                      <select name="s:registrationQualification:isregist" class="oa-new-select">
                                          <option value="0" <c:if test="${registrationQualification.isregist==0}">selected</c:if> >否</option>
                                          <option value="1" <c:if test="${registrationQualification.isregist==1}">selected</c:if> >是</option>
                                     </select>
                                </td>
                                <td>
                                    <input ctltype="attachment" name="s:registrationQualification:regist_attachment" isdirectupload="0" right="w" value='${registrationQualification.regist_attachment}' validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                                </td> 
                            </tr>
                        </c:forEach> 
                        <tr type="append" style="display:none;"> 
                            <td>
                                <span name="editable-input" isflag="tableflag"> 
                                <input type="text" name="s:registrationQualification:certificate_regist_id" lablename="注册证书编号" class="oa-new-input" value="${registrationQualification.certificate_regist_id}" validate="{maxlength:50}" isflag="tableflag" /> </span> </td> 
                            <td>
                                <span name="editable-input" isflag="tableflag"> 
                                <input type="text" name="s:registrationQualification:regist_major" lablename="注册专业" class="oa-new-input" value="${registrationQualification.regist_major}" validate="{maxlength:50}" isflag="tableflag" /> </span> 
                            </td>
                            <td>
                                <span name="editable-input" isflag="tableflag"> 
                                 <input type="text" name="s:registrationQualification:regist_send_unit" lablename="发证机构" class="oa-new-input" value="${registrationQualification.regist_send_unit}" validate="{maxlength:50}" isflag="tableflag" /> </span>
                            </td> 
                            <td>
                                 <input type="text" name="s:registrationQualification:get_date" lablename="发证日期" value="<fmt:formatDate value="${registrationQualification.get_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td> 
                            <td>
                                 <input type="text" name="s:registrationQualification:last_effectice_date" lablename="证书有效期" value="<fmt:formatDate value="${registrationQualification.last_effectice_date}" pattern='yyyy-MM-dd'/>" class="oa-new-input date" validate="{date:true}" isflag="tableflag" /></td> 
                            <td>
                                <select name="s:registrationQualification:isregist" class="oa-new-select">
                                    <option value="0" <c:if test="${registrationQualification.isregist==0}">selected</c:if> >否</option>
                                    <option value="1" <c:if test="${registrationQualification.isregist==1}">selected</c:if> >是</option>
                                </select>
                            </td>
                            <td>
                                <input ctltype="attachment" name="s:registrationQualification:regist_attachment" isdirectupload="0" right="w" value="" validatable="false" validate="{&quot;maxlength&quot;:&quot;500&quot;}" />
                            </td> 
                        </tr>
                    </table> 
                    <input name="s:registration_qualificationy:id" type="hidden" pk="true" value="" />
                </div>   
                
            </div>
           
            <%--不是修改本人信息则--%>
            <c:if test="${bySelf!=1}">
                <div title="组织岗位选择" tabid="orgdetail" icon="${ctx}/styles/default/images/icon/home.png" >                             
        
                    <div style="width: 49%; height: 300px; overflow-y: auto; float: left; background: #f6f7fb; border: 1px solid #eceff8;">

                        <c:choose>
                            <c:when test="${action=='global' }">
                                <select id="demensionId" style="width: 100% !important;">
                                    <c:forEach var="dem" items="${demensionList}">
                                        <option value="${dem.demId}" <c:if test="${dem.demId==1}">selected="selected"</c:if> >${dem.demName}</option>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <select id="orgAuth" style="width: 100% !important;" onchange="javascript:loadorgGradeTree();">  
                                    <c:forEach var="orgAuth" items="${orgAuthList}">  
                                        <option value="${orgAuth.orgId}" dimId="${orgAuth.dimId}" orgPerms="${orgAuth.orgPerms}" <c:if test="${orgAuth.dimId==1}">selected="selected"</c:if>>${orgAuth.orgName}—[${orgAuth.dimName}]</option>  
                                    </c:forEach> 
                                </select>
                            </c:otherwise>
                        </c:choose>

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

                        <ul id="orgTree" class="ztree"></ul>    

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
      
                <div title="角色选择" tabid="roldetail" icon="${ctx}/styles/default/images/resicon/customer.png"  >
                    <div style="width: 49%; height: 300px; overflow-y: auto; float: left; background: #f6f7fb; border: 1px solid #eceff8;">
                        <h4 class="oa-h4">所有角色</h4>
                        <div id="rolTree" class="ztree"></div>
                    </div>
                    
                    <div style="width: 49%; height: 300px; overflow-y: auto; float: right; background: #f6f7fb; border: 1px solid #eceff8;">
                        <h4 class="oa-h4">已选角色</h4>
                    
                        <table id="rolItem" class="oa-table--default">
                            <thead>                             
                                <th>角色名称</th>
                                <th>子系统名称</th>
                                <th>操作</th>
                            </thead>
                            <c:forEach items="${roleList}" var="rolItem">
                                <tr trName="${rolItem.roleName}" id="${rolItem.roleId}">
                                    <td>${rolItem.roleName}<input type="hidden" name="roleId" value="${rolItem.roleId}"></td>
                                    <td>${rolItem.systemName}</td>
                                    <td><a href="javascript:;" onclick="delrow('${rolItem.roleId}')" class="oa-button-label">移除</a></td>
                                </tr>
                            </c:forEach>
                        </table>                        
                    </div>

                    <div style="clear: both;"></div>
                    <div class="oa-pdv20">
                        <button class="oa-button-label" type="button" id="rolAdd">添加</button>
                    </div>                    
                    
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

        <input type="hidden" id="saveData" name="saveData"/>
    </form>
	<script type="text/javascript">
		$(function(){
			setEntryProfessionals();
			$(document).on("change",".entryProfessionalName",function(){
				setEntryProfessionals();
			});
			$(document).on("click","div[ligeruimenutemid='1']",function(){
				setEntryProfessionals();
			});
			$(document).on("click","div[ligeruimenutemid='2']",function(){
				setEntryProfessionals();
			});
			$(document).on("click","div[ligeruimenutemid='3']",function(){
				setEntryProfessionals();
			});
		});
		
		function setEntryProfessionals(){
			var entryProfessionals = $("#entryProfessional").find("input[lablename='职称名称']");
			var names = {};
			$.each(entryProfessionals,function(i,item){
				names[$(this).val()] = $(this).val();
			});
			if('高级工程师' in names ){
				$("#positional").val('高级工程师');
			}else if('中级工程师' in names ){
				$("#positional").val('中级工程师');
			}else if('初级工程师' in names ){
				$("#positional").val('初级工程师');
			}
		}
	</script>
</body>
</html>
