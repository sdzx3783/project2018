<%@ page pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
    <%@include file="/commons/include/customForm.jsp" %>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/default/css/hotent/task.css"></link>
    <script type="text/javascript" src="${ctx}/js/util/form.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/ProcessUrgeDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowRightDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/CheckVersion.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/SelectUtil.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/FlowUtil.js"></script>
	<script type="text/javascript" src="${ctx}/js/hotent/appropinion.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/bpm/BpmRetransmissionDialog.js"></script>
    <style>
        .table_pictureShow{
            display: none;
        }
        .tabletd {
        	word-break: break-word;
        }
        .special-head{
            padding-top: 50px;
            text-align: center;
            padding-bottom: 30px;
            border-bottom: 2px solid #333;
        }
        .special-head h1{
            letter-spacing: .3em;
            font-size: 28px;
            font-weight: 500;
            line-height: 2;
        }
        .special-head h2{
            letter-spacing: 2px;
            font-size: 22px;
            font-weight: 500;
            line-height: 2;
        }
        .special-main {
            padding: 10px 0;
        }
        .special-main .special-label{
            text-align: right;
            margin-right: 50px;
            margin-bottom: 30px;
        }
        .special-main p{
            font-size: 16px;
            margin-bottom: 10px;
        }
        .special-main label{
            padding: 0 10px;
            min-width: 80px;
            border-bottom: 1px solid #333;
        }
        .special-foot dl{
            overflow: hidden;
        }
        .special-foot dt{
            float: left;
        }
        .special-foot dd{
            margin-left: 10px;
            float: left;
        }
        .special-foot h2{
            font-size: 16px;
            font-weight: 600;
        }
        .special-foot{
            font-size: 16px;
        }
        .special-foot ul{
            margin-bottom: 100px;
        }
        .special-foot li{
            margin-bottom: 10px;
        }
        .special-foot p{
            margin-right: 50px;
            text-align: right;
        }
        .special-space{
            display: inline-block;
        }

        .process-info-wrapper .table-grid{
            border: 1px solid #dadfed;
        }
        .process-info-wrapper .table-grid td{
            border: 1px solid #dadfed;
        }
        .process-info-wrapper .table-grid th{
            border: 1px solid #dadfed;
        }
        .l-dialog-content {
        	overflow: hidden;
        }
        .split {
        	page-break-after: always;
        }
        .gdh {
       	    text-align: right;
  			margin: 20px 10px -20px 0;
        }
        @media print {
            .subtable,
            .formTable td{
                border-color: #000 !important;
            }
            .process-info-wrapper .table-grid,
            .process-info-wrapper .table-grid td,
            .process-info-wrapper .table-grid th{
                border-color: #000;
            }
        }
    	table.ProjectNoticebook{width: 100%;}
    	table.ProjectNoticebook td{
    	padding: 10px 20px;
    	border: 1px solid #121213;
   		height:20px !important;}
    </style>
    <script type="text/javascript">
        /*KILLDIALOG*/
        var dialog = window;//调用页面的dialog对象(ligerui对象)
        if(frameElement){
            dialog = frameElement.dialog;
        }
    
        var isExtForm=eval('${isExtForm}');
        
        var runId=${processRun.runId};
        var status = ${processRun.status==1};
        $(function(){
            if(isExtForm){
                var formUrl=$('#divExternalForm').attr("formUrl");
                if(formUrl){
                    $('#divExternalForm').load(formUrl, function() {});
                }
            }
            $(".taskopinion").each(function(){
                $(this).removeClass("taskopinion");
                var actInstId=$(this).attr("instanceId");
                $(this).load("${ctx}/platform/bpm/taskOpinion/listform.ht?actInstId="+actInstId);
            });
            
            if(status){
                var add = $("tr.toolBar").find("a.add");
                add.hide();
            }
        });
        
    
        
        //显示审批历史
        function showProcessRunInfo(obj){
            var url=$(obj).attr("action"),
                title = $(obj).attr("title");
            url=url.getNewUrl();
            DialogUtil.open({
                url:url,
                title:title,
                height: 600,
                width: 800,
        		showMax: true              
            });
        };
        
        //催办
        function urge(id){
            ProcessUrgeDialog({actInstId : id});
        };
        //追回
        function recover(runId){
            FlowUtil.recover({runId:runId,backToStart:0,callback:function(){
                window.opener.location.href=window.opener.location.href.getNewUrl();
            }});
        };
        //重新提交
        function executeTask(procInstId){
             var url= "${ctx}/platform/bpm/task/toStart.ht?instanceId="+procInstId+"&voteArgee=34";
             jQuery.openFullWindow(url);
        };
        
        //打印表单
        function printForm(runId){
            var url="${ctx}/platform/bpm/processRun/printForm.ht?runId="+runId;
            jQuery.openFullWindow(url);
        }

        //删除 
        function delByInstId(instanceId){
            var url="${ctx}/platform/bpm/processRun/delDialog.ht?instanceId=" + instanceId;
            var winArgs="dialogWidth=500px;dialogHeight=250px;help=0;status=0;scroll=0;center=1";
            url=url.getNewUrl();
        
            DialogUtil.open({
                height:250,
                width: 500,
                title : '删除',
                url: url, 
                isResize: true,
                //自定义参数
                sucCall:function(rtn){
                    if(rtn!=undefined){
                        try{
                            window.opener.location.href=window.opener.location.href.getNewUrl();
                        }
                        catch(e){};
                        dialog.close();
                    }
                }
            });
            
        };
        function onClose(obj){
            if(window.opener ){
                try{
                    window.opener.location.href=window.opener.location.href.getNewUrl();
                }
                catch(e){}
            }
            dialog.close();
        };
        
        //转发
        function divert(){
            var runId = "${param.runId}";
            forward({runId:runId});
        }
        
        function forward(conf)
        {
            if(!conf) conf={};  
            var url=__ctx + '/platform/bpm/bpmProCopyto/forward.ht?runId=' + conf.runId;
            var dialogWidth=500;
            var dialogHeight=300;
            conf=$.extend({},{dialogWidth:dialogWidth ,dialogHeight:dialogHeight ,help:0,status:0,scroll:0,center:1,reload:true},conf);

            var winArgs="dialogWidth="+conf.dialogWidth+"px;dialogHeight="+conf.dialogHeight
                +"px;help=" + conf.help +";status=" + conf.status +";scroll=" + conf.scroll +";center=" +conf.center;
            url=url.getNewUrl();
            
            /*KILLDIALOG*/
            DialogUtil.open({
                height:conf.dialogHeight, 
                width: conf.dialogWidth,
                title : '转发窗口',
                url: url, 
                isResize: true,
                sucCall:function(rtn){
                }
            });
            
        }
        
        
        //追回
        function redo(runId)
        {
            FlowUtil.recover({runId:runId,backToStart:0,callback:function(){
            }});
        }
        
        //导出word文档
        function downloadToWord(runId){
            var cl=$(".panel-body").clone();
            var form=cl.children();
            handFile(form);
            var frm=new com.hotent.form.Form();
            frm.creatForm("bpmPreview",__ctx+"/platform/bpm/processRun/downloadToWord.ht");
            frm.addFormEl("form",cl.html());
            frm.addFormEl("runId",runId);
            frm.submit();
        }
        
        //处理附件上传框，变成只显示附件名称
        function handFile(form){
            $("div[name='div_attachment_container']",form).each(function(){
                var me=$(this);
                var attachment=$("a.attachment",me);
                var title = attachment.attr("title");
                me.empty();
                me.text(title);
            })
        }
        
        // 自定义打印
        function customPrint(printAlias){
            var url="${ctx}/platform/bpm/processRun/printForm.ht?runId=${processRun.runId}&printAlias="+printAlias;
            jQuery.openFullWindow(url);
        }

        function getProcessInfo(src, callback){
            var $iframe = $('<iframe>');
            $iframe.attr('src', src);
            $iframe.on('load', function(e){
                var iframeDocument = $(e.target.contentDocument).find('table');           
                var $wrapper = $('<div class="process-info-wrapper"><h2>审批历史</h2></div>').append(iframeDocument);
                callback && callback($wrapper);
            });
            $('body').append($iframe.hide());
        }
        
        //直接打印
        function justPrint(){  
            var oldstr = document.body.innerHTML;  
            var src = $("#JS_process_info").attr("action");
            var processInfo = getProcessInfo(src, function($table){
                $('body').find('.subtable:last, table:last').after($table);
                var headstr = "<html><head><title></title></head><body>";  
                var footstr = "</body>";  
                var printData = document.getElementById("justPrintDiv").innerHTML;
                document.body.innerHTML = headstr+printData+footstr;  
                window.print();  
                document.body.innerHTML = oldstr;
                return false;
            });
        }
        
        //直接打印,无审批记录
        function justPrintNoProcess(){  
            var oldstr = document.body.innerHTML;  
            //var src = $("#JS_process_info").attr("action");
          //  var processInfo = getProcessInfo(src, function($table){
                //$('body').find('.subtable:last, table:last').after($table);

                var headstr = "<html><head><title></title></head><body><div class='panel-body printForm'>";  
                var footstr = "</div></body>";  
                var printData = document.getElementById("justPrintDiv").innerHTML;
                document.body.innerHTML = headstr+printData+footstr;  
                window.print();  
                document.body.innerHTML = oldstr;  
               // return false;
           // });
        }
        
        $(function(){
        	setTimeout(function(){
        		$('#div_m_user_resignation_dispatch_content').hide();
        	}, 14);
        })
    </script>
    <style>
        .panel-toolbar{
            margin-top: 0;
        }
        .process-info-wrapper{
            padding: 20px 0;
        }
        .process-info-wrapper h2{
            font-size: 16px;
            text-align: center;
        }
        .subtable{
            width: auto;
        }
    </style>
</head>
<body>
    <div class="panel">
        <div class="hide-panel noprint">
            <div class="panel-top">
                <div class="tbar-title">
                    <span class="tbar-label">流程明细--${processRun.subject}</span>
                    <input id="businessKey" type="hidden" value="${processRun.businessKey }" />
                    <input id="pkField" type="hidden" value="${processRun.runId }" />
                    <input id="pageType" type="hidden" value="processRuninfo" />
                    <input id="runId" type="hidden" value="${processRun.runId}" />
                    <input id="theFormKey" type="hidden" value="${formKey}" />
					<input type="hidden" id="curCtx" value="${ctx}" />
                    <%--<input id="thesealid" type="hidden" value="" />--%>
                    <%--<input id="thesealimguri" type="hidden" value="" />--%>
                </div>
                <div class="panel-toolbar">
                    <div class="toolBar">
                        <c:if test='${isCanRecover and  param.prePage =="myRequest"}'>
                            <div class="group"><a href="###" onclick="redo(${processRun.runId})" class="link redo"><span></span>追回</a></div>
                        </c:if>
                        <c:if test='${isCanRedo and (param.prePage =="myFinishedTask")}'></c:if>
                        <c:if test="${isFirst and (processRun.status==4 or processRun.status==5)}">
                            <div class="group">
                                <a href="javascript:executeTask('${processRun.actInstId}')" class="link run"><span></span>重新提交</a>
                            </div>
                            <div class="group">
                                <a href="javascript:delByInstId(${processRun.actInstId})" class="link del"><span></span>删除</a>
                            </div>
                        </c:if>
                        <%-- <div class="group"><a action="${ctx}/platform/bpm/processRun/get.ht?runId=${processRun.runId}" onclick="showProcessRunInfo(this)" class="link detail" title="运行明细"><span></span>运行明细</a></div> --%>
                        <div class="group"><a action="${ctx}/platform/bpm/processRun/processImage.ht?runId=${processRun.runId}" onclick="showProcessRunInfo(this)" class="link flowDesign" title="流程图"><span></span>流程图</a></div>
                        <div class="group"><a action="${ctx}/platform/bpm/taskOpinion/list.ht?action=process&runId=${processRun.runId}" id="JS_process_info" onclick="showProcessRunInfo(this)" class="link history" title="审批历史"><span></span>审批历史</a></div>
                        <c:if test="${isPrintForm}">
                            <a href="javascript:void(0);" onclick="printForm(${processRun.runId})" class="link print"><span></span>打印表单</a>
                        </c:if>
                        <!-- <div class="group"><a href="javascript:void(0);" onclick="downloadToWord(${processRun.runId})" class="link print"><span></span>导出成word文档</a></div> -->
                        <div class="group"><a class="link print my-print-sssss" onclick="window.print();"><span></span>打印</a></div>
                        <div class="group"><a class="link print" onclick="justPrintNoProcess();"><span></span>打印（纸质单据）</a></div>
                        <c:if test="${processRun.processName=='法定委托书盖章申请流程'}">
	                        <div class="group"><a class="link print" onclick="specialPrint();"><span></span>打印模板</a></div>
                        </c:if>
                        <c:if test="${processRun.processName=='收入证明盖章申请'}">
							<div class="group"><a class="link print" onclick="specialIncomePrint();"><span></span>打印模板</a></div>
						</c:if>
                        <c:if test="${bpmDefinition.subject=='总监任命流程'}">
							<div class="group"><a class="link print" onclick="specialDirectorPrint();"><span></span>打印模板</a></div>
						</c:if>
						<c:if test="${bpmDefinition.subject=='项目机构设置通知书'}">
							<div class="group"><a class="link print" onclick="specialProjectNoticebookPrint();"><span></span>打印模板</a></div>
						</c:if>
						<c:if test="${bpmDefinition.subject=='项目监理机构印章使用'}">
							<div class="group"><a class="link print" onclick="specialProjectOrganizationPrint();"><span></span>打印模板</a></div>
						</c:if>
                        <c:if test="${processRun.processName=='发文'}">
	                        <div class="group"><a class="link goForward" onclick="retransmission()"><span></span>分发</a></div>
							<div class="l-bar-separator"></div>
                        </c:if>
                        <%@include file="incHelp.jsp" %>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-body panel-detail printForm" id="justPrintDiv">
        	<div class="panel-wrapper">
	            <c:if test="${hasGlobalFlowNo }">
	                <div class="gdh">工单号：${processRun.globalFlowNo}</div>
	            </c:if>
	            <c:choose>
	                <c:when test="${isExtForm==true }">
	                    <div id="divExternalForm" formUrl="${form}"></div>
	                </c:when>
	                <c:otherwise>
	                    ${form}
	                </c:otherwise>
	            </c:choose>
            </div>
        </div>
        <div class="my-process-info-wrapper" style="padding: 0 16px;"></div>
        <input type="hidden" id="businessKey" name="businessKey" value="${processRun.businessKey}"/>
    </div> 

    

    <div id="JS_special_print" class="special" style="display: none;">
    	<div class="split">
	        <div class="special-head">
	            <!-- <h2>深圳市深水水务咨询有限公司</h2> -->
	            <h1>法 人 授 权 委 托 证 明 书</h1>
	        </div>
	        <div class="special-main">
	            <p class="special-label">字 第<label id="JS_data_gdh"></label>号</p>
	
	            <p>
	                &nbsp;&nbsp;&nbsp;&nbsp;兹授权<label><span id="JS_data_xm"></span></label>同志，为我方事务代理人，其权限是：<label><span id="JS_data_qx"></span></label>事宜。
	            </p>
	            <p>
	                授权单位： 深圳市深水水务咨询有限公司（盖章）法定代表人：黄琼（签名或盖章）
	            </p>
	            <p>
	                有效期限：<label><span id="JS_data_eyear"></span></label>年<label><span id="JS_data_emonth"></span></label>月<label><span id="JS_data_eday"></span></label>日  
	                <span class="special-space" style="width: 4em;"></span>签发日期：<label><span id="JS_data_syear"></span></label>年<label><span id="JS_data_smonth"></span></label>月<label><span id="JS_data_sday"></span></label>日 
	            </p>
	            <p>
	               附：代表人性别：<span class="special-space" style="width: 6em;"><span id="JS_data_xb"></span></span>年龄：<span class="special-space" style="width: 6em;"><span id="JS_data_nl"></span></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>工作证号码：<span class="special-space" style="width: 12em;"><span id="JS_data_gzzhm"></span></span> 职务：<span class="special-space" style="width: 12em;"><span id="JS_data_zw"></span></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 4em;"></span>营业执照号码：<span class="special-space" style="width: 12em;"><span id="JS_data_yyzzhm"></span></span>经济性质：<span id="JS_data_jjxz"></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 4em;"></span>主营(产)：<span id="JS_data_zyc"></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 4em;"></span>兼营(产)：<span id="JS_data_jyc"></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 4em;"></span>进口物品经营许可证号码:<span id="JS_data_xkzhm"></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 4em;"></span>主营：<span id="JS_data_zy"></span>
	            </p>
	            <p>
	                <span class="special-space" style="width: 4em;"></span>兼营：<span id="JS_data_jy"></span>
	            </p>
	        </div>
	        <div class="special-foot">
	            <dl>
	                <dt><h2>说明：</h2></dt>
	                <dd>
	                    <ul>
	                        <li>1、法定代表人为企业事业单位、国家机关、社会团体的主要行政负责人。</li>
	                        <li>2、内容必须填写真实、清楚、涂改无效、不得转让、买卖。</li>
	                        <li>3、将此证明提交对方作为合同附件。</li>
	                    </ul>                    
	                </dd>
	            </dl>
	
	
	            <p>深圳市行政管理局监制</p>
	        </div>
	    </div>
	    <div class="split">
	    	<div class="special-head">
	            <!-- <h2>深圳市深水水务咨询有限公司</h2> -->
	            <h1>法 定 代 表 人 证 明 书</h1>
	        </div>  
	        <div class="special-main">
	            <p class="special-label">字 第<label id="JS_data_gdh_1"></label>号</p>
	
	            <p>
	                <span class="special-space" style="width: 2em;"></span><label style="padding: 0;"><span class="special-space" style="width: 5em;">黄 琼</span></label>同志现任我单位<label style="padding: 0;"><span class="special-space" style="width: 7em; text-align: center;">董事长</span></label>职务，为法定代表人，特此证明。
	            </p>
	            <p>
	                有效期限：<span id="JS_data_yxyear_1"></span><label><span id="JS_data_yxyear_2"></span></label>年<label><span id="JS_data_yxmonth"></span></label>月<label><span id="JS_data_yxday"></span></label>日  
	                <span class="special-space" style="width: 2em;"></span>签发日期：<span id="JS_data_styear_1"></span><label><span id="JS_data_styear_2"></span></label>年<label><span id="JS_data_stmonth"></span></label>月<label><span id="JS_data_stday"></span></label>日 
	                <span class="special-space" style="width: 1em;"></span>单位：（盖章）
	            </p>
	            <p>
	               <span class="special-space" style="width: 1.6em;"></span>附：代表人性别：<span class="special-space" style="width: 6em;"></span>年龄：<span class="special-space" style="width: 7em;"></span>工作证号码：
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>机构代码：<span class="special-space" style="width: 8em;"></span>机构类别：
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>主营(产)：
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>兼营(产)：
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>进口物品经营许可证号码:
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>主营：
	            </p>
	            <p>
	                <span class="special-space" style="width: 2em;"></span>兼营：
	            </p>
	        </div>
	        <div class="special-foot">
	            <dl>
	                <dt><h2>说明：</h2></dt>
	                <dd>
	                    <ul>
	                        <li>1、法定代表人为企业事业单位、国家机关、社会团体的主要行政负责人。</li>
	                        <li>2、内容必须填写真实、清楚、涂改无效、不得转让、买卖。</li>
	                        <li>3、将此证明提交对方作为合同附件。</li>
	                    </ul>                    
	                </dd>
	            </dl>
	
	
	            <p>深圳市行政管理局监制</p>
	        </div>
	    </div>
    </div>


	<div id="JS_income_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 18px;line-height:55px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 25px;">个人收入证明</h2>
		    </div>
	    	 
		   <p  style="text-align:left;padding-left: 80px;">
		    	&nbsp; &nbsp;兹证明&nbsp;<span class='incomeWordName'></span>&nbsp;先生/女士（身份证号:&nbsp;<span class="incomeWordCardNo">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;
		    	为本单位正式职工， 自&nbsp;&nbsp;<span class='incomeWordYear'></span>至今一直在我单位，目前担任&nbsp;<span class='incomeWordZhiye' >办公室主任</span>&nbsp;&nbsp;,近一年度该员工税后月平均收入人民币&nbsp;&nbsp;<span class="incomeYearMaxIncome"></span>
		                      （小写：<span class="incomeYearMinIncome"></span>元）。
		    </p>
			<p  style="text-align:left;padding-left: 80px;">
			    &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 特此证明
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			     
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			    &nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 本证明仅用于证明我单位员工的工作情况和工资收入，不作为我公司对该员工任何形式的承诺或担保。
			</p>
			<p style="text-align:right;padding-right: 300px;line-height: 5em;">
			    <span >单位公章：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p style="text-align:right;">
			    <span>&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			   单位全称： 深圳市深水水务咨询有限公司
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			    单位地址：深圳市罗湖区黄贝街道延芳路63号
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			     单位电话：0755-22385939
			</p>
			<p  style="text-align:left;padding-left: 80px;">
			     人力资源部门联系人：梁晖 
			</p>
    	</div>
    </div>
    
    <div id="JS_director_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 18px;line-height:55px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 25px;">总监理工程师任命书</h2>
		    </div>
		    <div style="text-align:left;">
		    	<div style="width:55%;display: inline-block;vertical-align: top;padding-left: 6em;box-sizing: border-box;line-height: 1.6em;">工程名称：&nbsp;<span class='directorWordName'></span></div>
		    	<div style="width:40%;display: inline-block;vertical-align: top;padding-left: 6em;box-sizing: border-box;line-height: 1.6em;">任命书编号：&nbsp;<span class="directorWordCardNo"></span></div>
		    </div>
			<div style="border: 1px solid black;margin-left:25px;">
				<p  style="text-align:left;">
				   &nbsp; 致：<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="directorBuildName"></span>&nbsp;&nbsp;&nbsp;&nbsp;</u>
				</p>
				<p  style="text-align:left;padding-left: 20px;">    
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;兹授权 <u>&nbsp;&nbsp;&nbsp;&nbsp;<span class="directorGongchengName"></span>&nbsp;&nbsp;&nbsp;</u>（监理工程师注册号：
				   <u>&nbsp;&nbsp;&nbsp;&nbsp;<span class="directorGongchengNo"></span>&nbsp;&nbsp;&nbsp;&nbsp;</u>）为我单位
	               <u>&nbsp;&nbsp;&nbsp;&nbsp;<span class='directorWordName'></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>项目总监理工程师，负责履行建设工程监理合同，主持项目监理机构工作。
				</p>
				</br>
				</br>
				</br>
				<p  style="text-align:left;padding-left:260px;">
				  监理单位：深圳市深水水务咨询有限公司
				</p>
				<p  style="text-align:left;padding-left:260px;">
				法人代表（签字）：
				</p>
				<p  style="text-align:left;padding-left:260px;">
				 被任命人：
				</p>
				<p  style="text-align:left;padding-left:360px;">
				（签字、加盖注册印章）
				</p>
				<p  style="text-align:left;padding-left:260px;">
				 二O&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
				<p  style="text-align:left;padding-left:100px;">
				   附件：监理工程师资格及注册证书复印件
				</p>
				</br>
			</div>
			<p  style="text-align:center;">
			  填报说明：本表一式<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>份，建设单位、监理机构各<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>份，各承包人<u>&nbsp;&nbsp;&nbsp;&nbsp;</u>份。
			</p>
    	</div>
    </div>
    
    <div id="JS_ProjectNoticebook_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 18px;line-height:40px;">
		    <div>
				<h2 style="text-align:center;line-height: 4em;font-size: 25px;">项目监理机构设置通知书</h2>
		    </div>
		    <div style="text-align:left;">
		    	<div style="width:55%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;工程名称：&nbsp;<span class='projectNoticebookWordName'></span></div>
		    	<div style="width:40%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;编号：&nbsp;<span class="projectNoticebookWordCardNo"></span></div>
		    </div>
			<div style="border:1px solid black;margin-left:25px;border-bottom: 0px;">
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 致：<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectNoticebookBuildName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>（建设单位）:
				</p>
				<p  style="text-align:left;padding-left: 20px;">    
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;根据&nbsp;<u>&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectNoticebookWordName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;</u>&nbsp;工程实际监理业务需要，我司有针对性地设置和调配项目监理架构及其组成人员，现将本项目监理机构监理人员名单及其专业分工通知贵方，请贵方予以确认。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;附件：监理机构成员资格证明资料复印件
				</p>
				<p  style="text-align:left;padding-left:110px;">
				   项目监理机构组织架构
				</p>
				<p  style="text-align:left;padding-left:360px;">
				  监理机构：（项目章）
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 总监理工程师：  
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
			</div>
			<div style="border: 1px solid black;margin-left:25px;">
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 建设单位确认意见：
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 同意此项目监理组织架构及人员岗位分工。
				</p>
				<p  style="text-align:left;padding-left:360px;">
				  建设单位（盖项目章）
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 负责人（签字）：       
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 报：工程质量、安全监督部门各1份
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 抄送（仅此表）：（承包单位项目经理部）1份
				</p>
				<table class="ProjectNoticebook" border="1" style="border-left: 0px;" cellspacing="0" cellpadding="2">
					<tr><td style="text-align: center;">姓  名</td><td style="text-align: center;">专业分工</td><td style="text-align: center;">职  务</td><td style="text-align: center;">执业/岗位证书号</td><td style="text-align: center;">签  名</td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
					<tr><td></td><td></td><td></td><td></td><td></td></tr>
				</table>
			</div>
			<p  style="text-align:left;padding-left:40px;">
				 附注：1、本表根据《深圳市施工监理规程》4.1制定；
			</p>
			<p  style="text-align:left;padding-left:93px;">
      			2、本表一式四份，发包人、监理机构、承包人、档案馆各一份。
			</p>
    	</div>
    </div>
    
    
    <div id="JS_ProjectOrganization_print" class="" style="display: none;">
    	<p>&nbsp;</p>
    	<div style="font-size: 16px;line-height:35px;">
		    <div>
				<h2 style="text-align:center;line-height: 3em;font-size: 25px;">项目监理机构设置通知书</h2>
		    </div>
		    <div style="text-align:left;">
		    	<div style="width:55%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;工程名称：&nbsp;<span class='projectOrganizationName'></span></div>
		    	<div style="width:40%;display: inline-block;">&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;编号：&nbsp;<span class="projectOrganizationCardNo"></span></div>
		    </div>
			<div style="border:1px solid black;margin-left:25px;">
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 致：&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationBuildName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>（建设单位）:
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 致：&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationContractingName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>（承包单位项目经理部）:
				</p>
				<p  style="text-align:left;padding-left: 20px;">    
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;一、现授权总监&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationDirectorName">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>&nbsp;同志在工程中使用“&nbsp;<u >&nbsp;&nbsp;&nbsp;&nbsp;<span class="projectOrganizationSealNum">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;</u>&nbsp;”印章（如下图）。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;二、授权期限：从贵单位收到本授权书之日起至监理合同及监理业务完成终止之日止。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;三、印章使用范围：所有应由监理审核签认的工程资料和来往文件。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;1.监理合同履行期间，授权人更换项目总监理工程师的，被授权人在本授权书上的授权行为在贵单位收到授权人更换项目总监通知之日起执行终止，由新任项目总监自动履行本授权书的权利和义务，本公司不再另行通知。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;2.在递交贵单位的需加盖本授权书印章的文件，还应由（总）监理工程师签字方可生效；仅加盖印章无（总）监理工程师签字的无效。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;3.总监代表、专业监理工程师在监理合同履行过程中使用该印章，必须有总监理工程师的授权，且不得超越授权书规定的使用范围，超越授权书的规定范围使用无效。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;4.除《开工报告》、《设计图纸会审记录》、《专项工程验收记录》系列表、《分部（子单位）质量验收记录》系列表、《工程验收及备案资料》系列表由企业法人出具的文件资料及现行法律法规规定要盖法人章的均盖“企业法人章”外，其他均加盖“项目章”也为有效文函。
				</p>
				<p  style="text-align:left;padding-left:20px;">
				   &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;项目监理机构印章样板
				</p>
				<p  style="text-align:left;padding-left:360px;">
				监理单位（法人章）
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 法人代表（签字）：
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 被授权人（签字）：         
				</p>
				<p  style="text-align:left;padding-left:360px;">
				 日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 报：建设单位、工程质量、安全监督部门各1份
				</p>
				<p  style="text-align:left;">
				   &nbsp;&nbsp;&nbsp; 发：承包单位1份
				</p>
			</div>
			<p  style="text-align:left;padding-left:40px;">
				 附注：1、本表根据《深圳市施工监理规程》4.1制定；
			</p>
			<p  style="text-align:left;padding-left:93px;">
      			2、本表一式四份，发包人、监理机构、承包人、档案馆各一份。
			</p>
    	</div>
    </div>
<script>
    // 打印模板

    var flag = '${processRun.processName}';
    function specialPrint(){
        var oldstr = document.body.innerHTML;  
        if('法定委托书盖章申请流程' !== flag){return;}

        var data = getDataFromTable();
        writeSpecialPrint(data);

        var headstr = "<html><head><title></title></head><body>";  
        var footstr = "</body>";  
        var printData = document.getElementById("JS_special_print").innerHTML;
        document.body.innerHTML = headstr+printData+footstr;  
        window.print();  
        document.body.innerHTML = oldstr; 
        return false;
    }

    function getKeyVal(key, data){
        var i = 0, len = data.length;

        for(; i < len; i++){
            if(data[i].key === key){
                return data[i].val;
            }
        }

        return '';
    }

    function writeSpecialPrint(data){
        $('#JS_data_xm').text(getKeyVal('姓名', data));
        $('#JS_data_qx').text(getKeyVal('权限', data));
        $('#JS_data_yyzzhm').text(getKeyVal('营业执照号码', data));
        $('#JS_data_gdh').text(getKeyVal('证书字号', data));
        $('#JS_data_gdh_1').text(getKeyVal('证书字号', data));
        $('#JS_data_zw').text(getKeyVal('职务', data));
        $('#JS_data_nl').text(getKeyVal('年龄', data));
        $('#JS_data_gzzhm').text(getKeyVal('工作证号码', data));
        $('#JS_data_jjxz').text(getKeyVal('经济性质', data));
        $('#JS_data_zyc').text(getKeyVal('主营(产)', data));
        $('#JS_data_jyc').text(getKeyVal('兼营(产)', data));
        $('#JS_data_xkzhm').text(getKeyVal('进口物品经营许可证号码', data));
        $('#JS_data_zy').text(getKeyVal('主营', data));
        $('#JS_data_jy').text(getKeyVal('兼营', data));
        $('#JS_data_xb').text(getKeyVal('代表人性别', data));
        
        var eDate = getKeyVal('有效期限', data);
        var sDate = getKeyVal('签发日期', data);

        eDate = splitDate(eDate);
        sDate = splitDate(sDate);

        $('#JS_data_eyear').text(eDate.y);
        $('#JS_data_emonth').text(eDate.m);
        $('#JS_data_eday').text(eDate.d);
        
        $('#JS_data_yxyear_1').text(Math.floor(eDate.y / 10));
        $('#JS_data_yxyear_2').text(eDate.y % 10);
        $('#JS_data_yxmonth').text(eDate.m);
        $('#JS_data_yxday').text(eDate.d);

        $('#JS_data_syear').text(sDate.y);
        $('#JS_data_smonth').text(sDate.m);
        $('#JS_data_sday').text(sDate.d);
        
        $('#JS_data_styear_1').text(Math.floor(sDate.y / 10));
        $('#JS_data_styear_2').text(sDate.y % 10);
        $('#JS_data_stmonth').text(sDate.m);
        $('#JS_data_stday').text(sDate.d);
    }

    function splitDate(str){
        var arr = str.split('-');
        return {
            y: arr[0],
            m: arr[1],
            d: arr[2]
        }
    }

    function getDataFromTable(){
        var $table = $('table');
        var data = [];
        $table.find('tr').each(function(index, item){
            var tds = $(item).find('td');
            var key = $.trim($(tds[0]).text()).replace(':', "");
            var val = $.trim($(tds[1]).text());

            data.push({
                key: key,
                val: val
            });

            if($.trim($(tds[2]).text()).length){
                key = $(tds[2]).text().replace(':', "");
                val = $(tds[3]).text();    
                data.push({
                    key: key,
                    val: val
                });
            }
   
        });

        return data;
    }
    $(function(){
        //如果是那种新的表单样式 ，则不显示下面的【审批历史】
        if(apprOp.ifFormKeyInTheMap($("#dirSubDisOp").val()))
            return;

  	  var oldstr = document.body.innerHTML;  

        var src ="${ctx}/platform/bpm/taskOpinion/list.ht?runId=${processRun.runId}&isOpenDialog=1";
        var processInfo = getProcessInfo(src, function($table){
            $('body').find('.my-process-info-wrapper').append($table);
        });
    });

    //设置印章图片
	$(function () {
        if($("#dirSubDisOp").length > 0 && $("#dirSubDisOp").val() == 'seal_cancel') {

            var sealId = $("#td_sealid").text();
            var imgUri = $("#td_sealimguri").text();

            $("#td_sealid").text('');
            $("#td_sealimguri").text('');

            //设置印章图片
			if(imgUri == 'undefined' || typeof (imgUri) == 'undefined' || imgUri == '') {
				return;
			}

			var fUrl = $('#curCtx').val() + imgUri;
			var newSealImg = '<img id="newSealImg" style="height: 300px;width: auto;" alt="印章图片" src="' + fUrl + '"/>';

			if($('#td-sealImg').length > 0) {
				if ($('#td-sealImg img').length > 0 ) {
					$('#td-sealImg img').remove();
				}
				$('#td-sealImg').append(newSealImg);
			}


		}
    });
    
/* 	$("#sendDoc").click(function(){
		var runId = $("#runId").val();
		var businessKey = $("#businessKey").val();
		$.ligerDialog.open({ url: '/makshi/dispatch/dispatch/setSendPerson.ht?type=dispatch&runId='+runId+'&businessKey='+businessKey,title:'选择人员',width:600,height: 600, isResize:true});
	}); */
	
    function retransmission(){
    	var runId = $("#runId").val();
         BpmRetransmissionDialog({runId:runId});
    };
    
    function getDataFromIncomeTable(){
        var $table = $('table');
        var data = [];
        $table.find('tr').each(function(index, item){
            var tds = $(item).find('td');
            var key = $.trim($(tds[0]).text()).replace(':', "");
            var val = $.trim($(tds[1]).text());

            data.push({
                key: key,
                val: val
            });

            if($.trim($(tds[2]).text()).length){
                key = $(tds[2]).text().replace(':', "");
                val = $(tds[3]).text();    
                data.push({
                    key: key,
                    val: val
                });
            }
   
        });

        return data;
    }
	function specialIncomePrint(){
        var oldstr = document.body.innerHTML;  
        var data = getDataFromIncomeTable();
        writeSpecialIncomePrint(data);
        var headstr = "<html><head><title></title></head><body>";  
        var footstr = "</body>";  
        var printData = document.getElementById("JS_income_print").innerHTML;
        document.body.innerHTML = headstr+printData+footstr;  
        window.print();  
        document.body.innerHTML = oldstr; 
        return false;
    }
	function writeSpecialIncomePrint(data){
   	 $('.incomeWordName').html(getKeyVal('申请人', data));
   	 $('.incomeWordCardNo').html(getKeyVal('身份证号', data));
   	 $('.incomeYearMaxIncome').html(getKeyVal('大写', data));
   	 $('.incomeYearMinIncome').html(getKeyVal('月平均收入', data));
   	 $('.incomeWordZhiye').html(getKeyVal('岗位', data));
   	 $('.incomeWordYear').html(getKeyVal('入职日期', data).replace('-','&nbsp;&nbsp;年&nbsp;&nbsp;').replace('-','&nbsp;&nbsp;月&nbsp;&nbsp;')+'&nbsp;&nbsp;日');
   }
	
	function specialDirectorPrint(){
        var oldstr = document.body.innerHTML;  
        var data = getDataFromIncomeTable();
        writeDirectorPrint(data);
        var headstr = "<html><head><title></title></head><body>";  
        var footstr = "</body>";  
        var printData = document.getElementById("JS_director_print").innerHTML;
        document.body.innerHTML = headstr+printData+footstr;  
        window.print();  
        document.body.innerHTML = oldstr; 
        return false;
	}
	
	function writeDirectorPrint(data){
       	 $('.directorWordName').html(getKeyVal('工程名称', data));
       	 $('.directorWordCardNo').html(getKeyVal('任命书编号', data));
       	 $('.directorBuildName').html(getKeyVal('致：', data));
       	 $('.directorGongchengName').html(getKeyVal('总监姓名', data));
       	 $('.directorGongchengNo').html(getKeyVal('监理工程师注册号', data));
        }
	
	
	function specialProjectNoticebookPrint(){
        var oldstr = document.body.innerHTML;  
        var data = getDataFromIncomeTable();
        writeProjectNoticebookPrint(data);
        var headstr = "<html><head><title></title></head><body>";  
        var footstr = "</body>";  
        var printData = document.getElementById("JS_ProjectNoticebook_print").innerHTML;
        document.body.innerHTML = headstr+printData+footstr;  
        window.print();  
        document.body.innerHTML = oldstr; 
        return false;
	}
	
	function writeProjectNoticebookPrint(data){
      	 $('.projectNoticebookWordName').html(getKeyVal('工程名称', data));
      	 $('.projectNoticebookWordCardNo').html(getKeyVal('编号', data));
      	 $('.projectNoticebookBuildName').html(getKeyVal('建设单位', data));
       }
	
	function specialProjectOrganizationPrint(){
        var oldstr = document.body.innerHTML;  
        var data = getDataFromIncomeTable();
        writeProjectOrganizationPrint(data);
        var headstr = "<html><head><title></title></head><body>";  
        var footstr = "</body>";  
        var printData = document.getElementById("JS_ProjectOrganization_print").innerHTML;
        document.body.innerHTML = headstr+printData+footstr;  
        window.print();  
        document.body.innerHTML = oldstr; 
        return false;
	}
	
	function writeProjectOrganizationPrint(data){
      	 $('.projectOrganizationName').html(getKeyVal('工程名称', data));
      	 $('.projectOrganizationCardNo').html(getKeyVal('编号', data));
      	 $('.projectOrganizationDirectorName').html(getKeyVal('授权总监', data));
      	 $('.projectOrganizationBuildName').html(getKeyVal('建设单位', data));
      	 $('.projectOrganizationSealNum').html(getKeyVal('印章', data));
      	 $('.projectOrganizationContractingName').html(getKeyVal('承包单位项目经理部', data));
       }
</script>
</body>
</html>