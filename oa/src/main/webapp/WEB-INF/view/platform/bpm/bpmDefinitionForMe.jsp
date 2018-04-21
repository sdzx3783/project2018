<%@page import="com.hotent.platform.model.system.GlobalType"%>
<%@page import="com.hotent.core.util.AppConfigUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/commons/include/html_doctype.html" %>
<html>
    <head>
		<%@include file="/commons/include/get.jsp" %>
        <title>新建流程</title>
		<f:link href="tree/zTreeStyle.css"></f:link>
		<script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
		<script type="text/javascript" src="${ctx}/js/lg/plugins/ligerLayout.js"></script>
		<script type="text/javascript" src="${ctx}/js/hotent/platform/form/GlobalType.js"></script>
		<style type="text/css">

        html, body{
            height: 100%;
             background: #e1e5f0;
        }
		body{
            font-family: '微软雅黑';
            background: #e1e5f0;
            min-width: 1062px;
        }
        .clear:before,
        .clear:after {
            content: " ";
            display: table;
        }
        .clear:after {
            clear: both;
        }
        .clear {
            *zoom: 1;
        }
        .sections{
            margin: 0 12px auto 12px;
            background: #fff;
            border-radius: 5px;
			border-bottom: 1px solid #c6cad5;
        }
        .section__item-head{
            position: relative;
            color: #657386;
            font-size: 14px;
            line-height: 37px;
            margin-bottom: 8px;
            border-bottom: 2px solid #e1e5f0;
        }
        .section__item-head:after{
            content: "";
            position: absolute;
            left: 0;
            bottom: -2px;
            width: 56px;
            height: 2px;
            background: #478de4;
        }
        .section__item-body{
			color: #666;
            font-size: 12px;
        }
        .section__item-body a{
            display: block;
            text-decoration: none;
            color: #666;
            font-size: 12px;
        }
        .section__line{
            padding-bottom: 30px;
        }
        .section__item{
            width: 25%;
            float: left;
        }
        .section__wrap{
            margin: 0 15px;
        }

        .section__links{
            list-style: none;
        }
        .section__link{
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            cursor: pointer;
            position: relative;
            line-height: 34px;
            padding-left: 30px;
            padding-right: 30px;
        }
        .section__link:before{
            content: "";
            position: absolute;
            top: 0;
            left: 10px;
            bottom: 0;
            margin: auto;
            width: 0;
            height: 0;
            border-top: 5px solid transparent;
            border-bottom: 5px solid transparent;
            border-left: 5px solid #b5b5b5;
        }
        /*.section__delete{
            display: none;
            content: "";
            position: absolute;
            top: 0;
            right: 0;
            bottom: 0;
            margin: auto;
            width: 40px;
            height: 100%;
            background: url(/images/delete_icon.png) center no-repeat;
        }
        .section__link:hover .section__delete{
            display: block;
        }*/
        .section__link:hover{
            background: #f5f6fa;
        }
        div.l-dialog-content {
        	padding-right: 20px;
        }
		</style>	
        <script type="text/javascript">
        		var catKey="<%=GlobalType.CAT_FLOW%>";
        		var globalType=new GlobalType(catKey,"glTypeTree",{onClick:onClick,url:'${ctx}/platform/system/globalType/getByCatKeyForBpmNewPro.ht',expandByDepth:1});
                $(function (){
                	//布局
                    $("#defLayout").ligerLayout({ leftWidth:210,height: '100%',allowLeftResize:false});
                	//加载菜单 
                    globalType.loadGlobalTree();
                	
                });
              	//左击
            	function onClick(treeNode){
                
	            		var url="${ctx}/platform/bpm/bpmDefinition/myList.ht?typeId="+treeNode.typeId;
	            		
	            		$("#defFrame").attr("src",url);
            		
            	};
            	
            	//展开收起
            	function treeExpandAll(type){
            		globalType.treeExpandAll(type);
            	};
         </script> 
    </head>
    <body>

	<div class="sections">
        <div class="section__line clear">
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">人事流程</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                            
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000000050327">入职流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000000280178">转正流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000009830004">请假流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000003400050">加班流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000009490429">人事信息变更流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a onclick="checkPosId(this)"; href="javascript:void(0)" >岗位薪酬调整申请</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000002200654">部门调动流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" onclick="checkAsset(this)";  href="javascript:void(0)">离职流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000000520157">党员档案转入流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000000520438">党员档案转出流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">职称申报、人才引进、招聘</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000002180203">职称申报流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000002180253">人才引进流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000002180270">招聘需求申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">合同</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001600044">合同盖章申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001600184">合同作废申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001600201">合同借阅申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001770018">合同信息变更申请</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000012900087">合同开票申请</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <!-- <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000013410474">合同付款申请</a></span>
                                <div class="section__delete"></div>
                            </li> -->
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000012900302">合同发票作废申请</a></span>
                                <div class="section__delete"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">财务流程</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                        	<li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=50000000000260">付款申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=50000000000313">借款申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=50000000000278">领款申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=50000000000346">费用报销申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>                          
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=50000000000299">差旅费报销申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>                                                       
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">资产</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000008020002">固定资产申购(领)流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001671686">固定资产移交流程</a></span>
                                <div class="section__delete"></div>
                            </li> 
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001740314">固定资产报废流程</a></span>
                                <div class="section__delete"></div>
                            </li> 
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001741249">办公用品及耗材申购流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000004360008">租房申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                           <!--  <li class="section__link">
                               <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001740976">固定资产维护流程</a></span>
                               <div class="section__delete"></div>
                           </li> -->
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000002100102">刻章流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=50000000000329">印章注销流程</a></span>
                                <div class="section__delete"></div>
                            </li> 
                        </ul>
                    </div>
                </div>
            </div>
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">执业资格、个人证书</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000003950821">个人证书(印章)借阅申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000004630004">个人执业印章使用申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                          <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000003950846">个人执业资格初始注册流程</a></span>
                                <div class="section__delete"></div>
                              </li>
                             <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001130191">个人执业资格转入注册流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                             <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000001130259">个人执业资格转出注册流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=30000001631512">公司各类资质原件借用流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="section__line clear">
            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">公章</div>
                    <div class="section__item-body">
                        <ul class="section__links" id="officialSeal">
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000005340065">公章使用流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=10000003391075">公章外借申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=30000001704448">法定委托书盖章申请流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                            <li class="section__link">
                                <span><a target="_blank" href="/bpm/bpmDefinition/me/forward.ht?key=srzmgzsq">收入证明盖章流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>


            <div class="section__item">
                <div class="section__wrap">
                    <div class="section__item-head">其他</div>
                    <div class="section__item-body">
                        <ul class="section__links">
                            <li class="section__link">
                                <span><a target="_blank" href="/platform/bpm/task/startFlowForm.ht?defId=40000000140062">网站投稿流程</a></span>
                                <div class="section__delete"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div> 

    <!--旧版式-->
   <%--  <div id="defLayout" >
        <div position="left" title="流程分类" style="overflow: auto;float:left;width:100%;height:96%;">
            <div class="tree-toolbar">
                <span class="toolBar">
                    <div class="group"><a class="link reload" id="treeFresh" href="javascript:globalType.loadGlobalTree();" title="刷新"></a></div>
                    <div class="l-bar-separator"></div>
                    <div class="group"><a class="link expand" id="treeExpandAll" href="javascript:treeExpandAll(true)" title="展开"></a></div>
                    <div class="l-bar-separator"></div>
                    <div class="group"><a class="link collapse" id="treeCollapseAll" href="javascript:treeExpandAll(false)"  title="收起"></a></div>
                </span>
            </div>
            <ul id="glTypeTree" class="ztree" ></ul>
        </div>
        <div position="center" title="新建流程">
            <iframe id="defFrame" height="100%" width="100%" frameborder="0" src="${ctx}/platform/bpm/bpmDefinition/myList.ht"></iframe>
        </div>
    </div> --%>
    <%-- <script type="text/javascript" src="${ctx}/js/makshi/main/bpmDefinitionForMe.js"></script> --%>
    <script type="text/javascript">
	    function checkAsset(obj){
	        $.post("/makshi/assetregistration/assetRegistration/getAssetByUserId.ht",function(data){
	        	if(!data){
	    	        var url="/platform/bpm/task/startFlowForm.ht?defId=10000000480116";
	    	   	    var rtn = jQuery.openFullWindow(url);
	        	}else{
	        		 $.ligerDialog.warn(" 请先将使用的资产移交,再启动离职流程！","提示信息");
	        		 return false;
	        	}
	        });	
	    }
	    function checkPosId(obj){
	        $.post("/platform/system/sysUser/judgeChargerList.ht",function(data){
	        	if(data==1){
	    	        var url="/platform/bpm/task/startFlowForm.ht?defId=10000011431989";
	    	   	    var rtn = jQuery.openFullWindow(url);
	        	}else{
	        		 $.ligerDialog.warn("岗位薪资费用调整申请，暂由部门经理发起","提示信息");
	        		 return false;
	        	}
	        });	
	    }
    </script>
    </body>
</html>
