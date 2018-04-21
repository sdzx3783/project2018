
<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>员工收入汇总表</title>
<%@include file="/commons/include/customForm.jsp"%>
<script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
<script type="text/javascript">
	//放置脚本
</script>
<style>
    .oa-input-wrap.select{
        position: relative;
        margin-right: 50px;
    }
    .link.org{
        position: absolute;
        right: -50px;
        top: 7px;
    }
    .file-prefer label {
    	color: #657386;
    }
    a.my-file {
	    position: relative;
	    color: #337ab7;
	    margin: 0 20px 0 5px;
	    line-height: 18px;
	}
	.showFileName {
		margin-left: 20px;
	}
	.my-file input {
	    position: absolute;
	    display: block;
	    width: 100%;
	    height: 100%;
	    right: 0;
	    top: 0;
	    opacity: 0;
	    font-size: 0;
	}
</style>
</head>
<body>
	<div id="oa_list_title" class="oa-project-title">
	    <!-- <h2>员工收入汇总表</h2> -->
	</div>
	<div id="oa_list_search" class="oa-pd20 my-condition">
        <form id="searchForm" method="post" action="proIncomeStats.ht" class="oa-clear">
            <div class="oa-fl oa-mgb10">
                <div class="oa-label">月份</div>
                <div class="oa-input-wrap oa-mgl100">
                   <input id="queryTime" name="queryTime" type="text" datefmt="yyyy-MM" value="${queryTime }" class="oa-input Wdate" />
                </div>
            </div>

            <div class="oa-fl oa-mgb10">
                <div class="oa-label">部门</div>
                <div class="oa-input-wrap select oa-mgl100">
                    <input name="org"  type="text" required="required" ctltype="selector" class="org oa-input"  value="${param['org']}" initValue="${param['orgID']}" validate="{empty:false}" readonly="readonly" />
                </div>
            </div>
            <div class="oa-fl oa-mgb10 oa-mgh20 my-options">
                <button type="button" onclick="queryFunc()" class="oa-button oa-button--primary oa-button--blue" >查询</button>
                <button type="button" id="exportBtn" class="oa-button oa-button--primary oa-button--blue">导出</button>
            </div>
        </form>
    </div>
    <c:if test="${not empty pros}">
	    <div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap">
			<c:if test="${not empty pros }">
				<h3 style="padding:20px 0">${month }月份项目工时</h3>
			</c:if>
				<table class="oa-table--default oa-table--nowrap">
					<tr>
						<c:forEach items="${pros }" varStatus="vs" var="pro">
							<c:if test="${vs.index==0 }"><th>员工</th>
								<th>${pro.projectName }正常工时</th>
								<th>${pro.projectName }加班工时</th>
							</c:if>
							<c:if test="${vs.index>0}">
								<th>${pro.projectName }正常工时</th>
								<th>${pro.projectName }加班工时</th>
							</c:if>
						</c:forEach>
					</tr>
					<c:forEach items="${data }" var="trlist">
						<tr>
						<c:forEach items="${trlist}" varStatus="vst" var="map">
							<c:if test="${vst.index==0 }"><td>${map.username}</td>
							<td>${map.workhour}</td>
							<td>${map.overhour}</td>
							</c:if>
							<c:if test="${vst.index>0}">
								<td>${map.workhour }</td>
								<td>${map.overhour }</td>
							</c:if>
						</c:forEach>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty userCostData}">
		<div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap">
			<c:if test="${not empty userCostData }">
				<h3 style="padding:20px 0">${month }月份员工工时成本</h3>
			</c:if>
				<table class="oa-table--default oa-table--nowrap">					
					<tr>
						<th>员工</th>
						<th>正常单位工时成本</th>
						<th>加班单位工时成本</th>
					</tr>					
					<c:forEach items="${userCostData }"  var="cost">
						<tr>
							<td>${cost.username}</td>
							<td>${cost.workcost}</td>
							<td>${cost.overcost}</td>
						</tr>
					</c:forEach>				
				</table>
			</div>
		</div>
	</c:if>
	<div class="oa-pdb20 oa-mgh20">
	    <form method="POST"  enctype="multipart/form-data" id="form1" action="uploadProjectIncomeExcel.ht">  
	    	<div class="upfile-wrapper">
	    		<div class="file-prefer">
	    			<label>上传项目收入文件:</label>
	    			<span class="showFileName"></span>
	    			<a href="javascript:;" class="my-file link selectFile"><input id="upfile" type="file" name="upfile">选择文件</a>
	            	<input id="month" name="month" type="hidden"  value="">
	            	<input class="oa-button oa-button--primary oa-button--blue" type="button" value="提交" id="btn" name="btn" >
	    		</div>	    		
	    	</div>  
	    </form>  
	</div>
	<c:if test="${not empty proIncomeData}">
		<div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap">
			<c:if test="${not empty proIncomeData }">
				<h3 style="padding:20px 0">${month }月份项目收入</h3>
			</c:if>
			    <table class="oa-table--default oa-table--nowrap">			    	
		    		<tr>
		    			<th>项目</th>
		    			<th>${month }月份项目收入</th>
		    		</tr>			    	
			    	<c:forEach items="${proIncomeData }" var="trData">
			    		<tr>
			    			<td>${trData.projectName }</td>
			    			<td>${trData.income }</td>
			    		</tr>
			    	</c:forEach>
			    </table>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty userCostData}">
		<div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap">
			<c:if test="${not empty userCostData }">
				<h3 style="padding:20px 0">${month }月份项目工时成本</h3>
			</c:if>
			    <table class="oa-table--default oa-table--nowrap">
			    	<tr>
				    	<c:forEach items="${userCostData }" varStatus="vst" var="userCost">
				    		<c:if test="${vst.index==0}">
				    			<th></th>
				    		</c:if>
				    		<th>${userCost.username }</th>
				    	</c:forEach>
				    	<c:if test="${not empty userCostData}">
					    	<th>本月合计</th>
					    	<th>当年累计</th>
				    	</c:if>
			    	</tr>
			    	<c:forEach items="${dataStats }" var="trData">
			    		<tr>
			    			<c:forEach items="${trData}" var="data">
			    				<td>${data}</td>
			    			</c:forEach>
			    		</tr>
			    	</c:forEach>
			    </table>
		    </div>
		</div>
	</c:if>
	<c:if test="${not empty personalCreatedIncomelist}">
		<div class="oa-pdb20 oa-mgh20">
			<div class="oa-table-scroll-wrap">
			<c:if test="${not empty personalCreatedIncomelist }">
				<h3 style="padding:20px 0">${month }月份员工创收</h3>
			</c:if>
			    <table class="oa-table--default oa-table--nowrap">		   		
		   			<tr>
						<c:forEach items="${pros }" varStatus="vs" var="pro">
							<c:if test="${vs.index==0 }">
								<th></th>
								<th>${pro.projectName }</th>
							</c:if>
							<c:if test="${vs.index>0}">
								<th>${pro.projectName }</th>
							</c:if>
						</c:forEach>
						<th>当月合计</th>
						<th>当年累计</th>
		   			</tr>		   		
			    	<c:forEach items="${personalCreatedIncomelist }" var="personalCreatedIncome">
			    		<tr>
			    			<c:forEach items="${personalCreatedIncome }" varStatus="vs" var="data">
			    				<c:if test="${vs.index==0 }">
				    				<td>${data.username }</td>
				    				<td>${data.createdincome }</td>
			    				</c:if>
			    				<c:if test="${vs.index>0  }">
			    					<td>${data.createdincome }</td>
			    				</c:if>
			    			</c:forEach>
			    		</tr>
			    	</c:forEach>
			    </table>
			</div>
		 </div>
	 </c:if>
     <script type="text/javascript">  
     		function queryFunc(){
     			var orgID=$("input[name='orgID']").val();
     			if(orgID==undefined || orgID.length<=0){
     				alert("请选择部门");
     				return ;
     			}
     			$("#searchForm").submit();
     		}
     	
     
            //ajax 方式上传文件操作  
            function postData(){
                    if(checkData()){ 
                    	$("#btn").off("click");
                    	$("#month").val($("#queryTime").val());
                        $('#form1').ajaxSubmit({    
                            url:'uploadProjectIncomeExcel.ht',  
                            dataType: 'text',  
                            success: resutlMsg,  
                            error: errorMsg  
                        });   
                        function resutlMsg(msg){  
                        	var obj = new com.hotent.form.ResultMessage(msg);
            				if (obj.isSuccess()) {
            					$.ligerDialog.success('<p><font color="green">'+obj.getMessage()+'</font></p>');
            					setTimeout("window.location.reload(true);",2000);
            				}else {
            					$("#btn").on("click",postData);
            					$.ligerDialog.error(obj.getMessage(),"提示信息");
            					//setTimeout("window.location.reload(true);",2000);
            				}
                        }
                        function errorMsg(){ 
                        	$("#upfile").val("");
                            alert("导入excel出错！");      
                        }  
                    }  
                 
             }
             $(document).ready(function(){  
                $('#btn').click(postData);  
             });
             //JS校验form表单信息  
             function checkData(){  
                var fileDir = $("#upfile").val();  
                var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
                if("" == fileDir){  
                    alert("选择需要导入的Excel文件！");  
                    return false;  
                } 
                if(".xls" != suffix && ".xlsx" != suffix ){  
                    alert("选择Excel格式的文件导入！");  
                    return false;  
                }  
                return true;  
             }  
             
             $(function(){
            	 $("#exportBtn").click(function exportExcel(){
            		 var orgID=$("input[name='orgID']").val();
            		 if(orgID==undefined || orgID.length<=0){
      					alert("请选择部门");
      					return ;
      				 }
            		 $("#exportBtn").off("click");
            		 var url="exportProIncomeStatsExcel.ht"
            		 window.location.href=url+"?queryTime="+$("#queryTime").val()+"&orgID="+orgID;
            	 });
             })
          
             $(".my-file").on("change","input[type='file']",function(){
			    var filePath = $(this).val();
		        var arr = filePath.split('\\');
		        var fileName = arr[arr.length-1];
			    $(".showFileName").html(fileName);
			})
    </script>
    
</body>
</html>

