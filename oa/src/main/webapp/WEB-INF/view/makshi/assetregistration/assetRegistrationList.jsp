<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html" %>
<html>
<head>
<title>资产登记表管理</title>
     <%@include file="/commons/include/list_get.jsp"%> 
     <f:link href="Aqua/css/ligerui-all.css" ></f:link>
    <f:link href="tree/zTreeStyle.css" ></f:link>
    <script type="text/javascript" src="${ctx}/js/hotent/formdata.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/CustomValid.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/subform.js"></script>
    
     <script type="text/javascript" src="${ctx}/js/lg/newligerui.all.js" ></script>
     <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerComboBox.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/htDicCombo.js"></script> 
    
    <script type="text/javascript" src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerDrag.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/newligerDialog.js" ></script>
    <script type="text/javascript" src="${ctx}/js/lg/plugins/ligerResizable.js" ></script>
    <%@include file="/commons/include/ueditor.jsp" %>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/system/SysDialog.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
    <script type="text/javascript" src="${ctx}/js/hotent/platform/form/SelectorInit.js"></script>
    <script type="text/javascript" src="${ctx}/js/hotent/ajaxgrid.js"></script>
    <script type="text/javascript" src="${ctx}/js/lg/util/DialogUtil.js" ></script>
</head>

<script type="text/javascript">
var BPLA_OK                = 0;      //success
var BPLA_COMERROR          = -1;     //communication error
var BPLA_PARAERROR         = -2;     //parameter error
var BPLA_FILEOPENERROR     = -3;     //open file error
var BPLA_FILEREADERROR     = -4;     //read file error
var BPLA_FILEWRITEERROR    = -5;     //write file error
var BPLA_FILEERROR         = -6;     //font file is not eligible
var BPLA_NUMBEROVER        = -7;     //receive data length is bigger of length of setting
var BPLA_IMAGETYPEERROR    = -8;     //Image format is not bmp 
var BPLA_DRIVERERROR       = -9;     //driver error
var BPLA_LOADDLLERROR      = -10;    //load dll file error
var BPLA_LOADFUNCTIONERROR = -11;    //load function of dll file error
var BPLA_READINIFILEERROR  = -12;    //read ini file error
var BPLA_TIMEOUTERROR      = -13;    //timeout error

var iPortType = "0";          //Port type,0-COM,1-LPT,2-NibblePar,3-USB API,4-USB Class,5-Driver
var iComNameIndex = "COM1";   //COM name index
var iBaudrateIndex = 38400;   //baudrate index
var iFlowControlIndex = 2;    //Flow Control index
var iLptName = "LPT1";        //LPT name
var iWrTime = 5000;           //write port timeout
var iReTime = 5000;           //read port timeout
var USBPrinterNameList = "";  //class mode USB port device name list
var DriverNameList = "";      //printer driver name list
var USBPrinterName = "";      //class mode USB port device name 
var DriverName = "";          //printer driver name 

var m_hPort = -1;             //handle of opened port

var COM_PORT          = 0
var LPT_PORT          = 1
var USB_API_PORT      = 2
var USB_CLASS_PORT    = 3
var DRIVER_PORT       = 4

var statusResult = new String();//


/******************************
Function:onOpenPort()
Parameter:NULL
Description:
*******************************/
function onOpenPort()
{   
    
  if(BPLAOCX == null)
    {
        return;
    }
    //API mode USB port
    try {
     m_hPort = BPLAOCX.BPLAOpenUSB(0,0);
	} catch (e) {
		 alert("请检查标签打印机是否连接好,驱动是否安装!");return;
	}
    if(m_hPort == -1)
    {
        //alert("Open port error, please check connecting or port parameter setting.");
        alert("请检查标签打印机是否连接好,驱动是否安装");
        return;
    }

    //alert("Open port successfully!");
}


/******************************
Function:onClosePort() 
Parameter:NULL
Description:
*******************************/
    function onClosePort()
    {
        BPLAOCX.BPLACloseUSB(m_hPort);      
        m_hPort = -1;
        //alert("Port closed!");
    }


    function print(obj){
        var index = 0;
        var dataKey = [];
        var result = $(obj).parents("table").find('thead th');
        result.each(function(){
            dataKey.push($.trim($(this).text()));
        });

        function getIndexFormData(data, key){
            var i = 0, len = data.length;

            for(; i < len; i++){
                if(data[i].indexOf(key) > -1){
                    return i;
                }
            }

            return -1;
        }   

        function verifyIndex(_index){
            if(_index < 0){
                alert('打印位置未找到,请检查!');
                return false; 
            }
            return true;
        }

        if (!!window.ActiveXObject || "ActiveXObject"  in window){
            
        }else{
            alert("标签打印只支持IE浏览器哦!"); 
            return;
        }
        onOpenPort();
        if(BPLAOCX==null)
          {
                return false;
            }
            
            var iState;
        
       	   iState = BPLAOCX.BPLASet(m_hPort,2,0,1);
       		if(iState != BPLA_OK)
            {
                alert("BPLASet error");
                return;
            }
       		
       		iState = BPLAOCX.BPLASetEnd(m_hPort,300);
       		if(iState != BPLA_OK)
            {
                alert("BPLASet error");
                return;
            }
       		
            //设置连续纸长度\寻找标签的最大长度
            try{
	            iState = BPLAOCX.BPLASetPaperLength(m_hPort,0,600); 
	            if(iState != BPLA_OK)
	            {
	                alert("BPLASetPaperLength error");
	                return;
	            }
            }catch(e){
            	alert("BPLASetPaperLength error");
                return;
            }
            
            //long BPLAStartArea(long m_hPort, long iUnitMode, long iPrintWidth, long iColumn, long iRow, long iDarkness, long iSpeedPrint) 
            //开始票面排版，并设置票面打印参数
            iState = BPLAOCX.BPLAStartArea(m_hPort, 2, 600,0,0,20,0);
            
            if(iState != BPLA_OK)
            {
                alert("BPLAStartArea text error");
                return;
            }


            index = getIndexFormData(dataKey, '保管人');
            if(!verifyIndex(index)){
                return;
            }
            var own=$(obj).parents("tr").find('td').eq(index).text();
            //    long BPLAPrintTruetype(long m_hPort, LPCTSTR cText, long iStartX, long iStartY, LPCTSTR cFontName, long iFontHeight, long iFontWidth, long iBold, long iItalic, long iUnderline, long iRowRotate) 
            iState = BPLAOCX.BPLAPrintTruetype(m_hPort,"保管人     "+own,100,135,"宋体",20,0,1,0,0,1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintTruetype1 error:"+ iState );
                return;
            }
            index = getIndexFormData(dataKey, '使用部门');
            if(!verifyIndex(index)){
                return;
            }
            var dep=$(obj).parents("tr").find('td').eq(index).text();
            iState = BPLAOCX.BPLAPrintTruetype(m_hPort,"使用部门   "+dep,100,170,"宋体",20,0,1,0,0,1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintTruetype2 error:"+ iState) ;
                return;
            }

            index = getIndexFormData(dataKey, '资产名称');
            if(!verifyIndex(index)){
                return;
            }
            var name=$(obj).parents("tr").find('td').eq(index).text();
            iState = BPLAOCX.BPLAPrintTruetype(m_hPort,"资产名称   "+name,100,205,"宋体",20,0,1,0,0,1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintTruetype3 error:"+ iState) ;
                return;
            }

            index = getIndexFormData(dataKey, '资产编号');
            if(!verifyIndex(index)){
                return;
            }
            var num=$(obj).parents("tr").find('td').eq(index).text();
            iState = BPLAOCX.BPLAPrintTruetype(m_hPort,"资产编号   "+num,100,240,"宋体",20,0,1,0,0,1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintTruetype4 error:"+ iState) ;
                return;
            }
            
            
            iState = BPLAOCX.BPLAPrintTruetype(m_hPort,"深圳市深水水务咨询有限公司",70,280,"宋体",30,0,1,0,0,1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintTruetype5 error:"+ iState) ;
                return;
            }
            
            index = getIndexFormData(dataKey, '条码编号');
            if(!verifyIndex(index)){
                return;
            }
            var codenum=$(obj).parents("tr").find('td').eq(index).text();
            //  long BPLAPrintBarcode(long m_hPort, LPCTSTR cCodeData, long iStartX, long iStartY, long iRotate, long iBarType, long iHeight, long iNumber, long iNumberBase, LPCTSTR cAddvalue, long iValueStartPlace, long iValueLen) 
            //设置一维条码的版面位置
            iState = BPLAOCX.BPLAPrintBarcode(m_hPort,codenum,100,40,1,4,55,3,2,"000",0,0); //9395000004909

            if(iState != BPLA_OK)
            {
                alert("Print barcode error:"+ iState) ;
                return;
            }

            iState = BPLAOCX.BPLAPrintLine(m_hPort, 60, 130, 520, 130, 1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintLine error1:" + iState);
                return false;
            }
            iState = BPLAOCX.BPLAPrintLine(m_hPort, 60, 165, 520, 165, 1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintLine error2:" + iState);
                return false;
            }
            iState = BPLAOCX.BPLAPrintLine(m_hPort, 60, 200, 520, 200, 1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintLine error3:" + iState);
                return false;
            }
            iState = BPLAOCX.BPLAPrintLine(m_hPort, 60, 235, 520, 235, 1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintLine error3:" + iState);
                return false;
            }
            iState = BPLAOCX.BPLAPrintLine(m_hPort, 200, 130, 200, 270, 1);
            if(iState != BPLA_OK)
            {
                alert("BPLAPrintLine error3:" + iState);
                return false;
            }
            //long BPLAPrintBox(long m_hPort, long iStartX, long iStartY, long iWidth, long iHeight, long iHorizontal, long iVertical, long iBitMode)
            iState = BPLAOCX.BPLAPrintBox(m_hPort,60,30,470,240,5,5,2);

            if(iState != BPLA_OK)
            {
                alert("BPLAPrintBox error:" + iState);
                return false;
            }
            
             
            // 启动标签打印。
            //BPLAOCX.BPLASetEnd(m_hPort,2);
            iState = BPLAOCX.BPLAPrint(m_hPort,1,0,1);
            
            if(iState != BPLA_OK)
            {
                alert("Print error");
                return;
            }
          	//iState = BPLAOCX.BPLAForBack(m_hPort,600,9999);
            alert("打印成功!");
            onClosePort();
    }


</script>
<script type="text/javascript">
$(function(){
	$(".oa-mw-page").on("click","a.oa-del",function(){
		  if($(this).hasClass('disabled')) return false;
		  var ele = this;
		  $.ligerDialog.confirm('确认删除吗？','提示信息',function(rtn) {
		  	 if(rtn) {
		    	if(ele.click) {
		    		window.location.href=ele.href;
		   		 } else {
		    		 location.href=ele.href;
		 	     }
		   	 }
		  });
		  return false;
		 });
});

</script>
<body class="oa-mw-page">
	<c:if test="${isEditor}">
    <div id="oa_search_table_button_wrap" class="oa-head-wrap">
        <a class="oa-button oa-button--primary oa-button--blue" href="edit.ht">登记</a>
       <!--  <a class="link import" action="/platform/form/bpmDataTemplate/importData_asset_registration.ht?" onclick="openLinkDialog({scope:this,width:450,height:200,title:'导入'})" href="javascript:;"><span></span>导入</a> --> 
    </div>
	</c:if>
    <!-- 在这里配置每个页面的简单查询模块 -->
    <div id="oa_simple_search" class="oa-simple-search-wrap oa-clear">
        <ul>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">资产编号</div>
                <div class="oa-input-wrap  oa-mgl100">
                    <input data-type="7"  date_name="condition" type="text" name="s.F_asset_id" class="oa-input" value="${param['asset_id']}"/>
                </div>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">保管人</div>
                <div class="oa-input-wrap  oa-mgl100">
                    <input date_name="condition" ctltype="selector" class="user oa-input oa-trigger-hidden"  type="text" name="s.F_care_person"  type="text" ctltype="selector" value="${param['care_person']}"/>
                </div>
                <button class="oa-button-label oa-trigger-hidden-button" type="button">选择用户</button>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">资产名称</div>
                <div class="oa-input-wrap  oa-mgl100">
                    <input data-type="7"  date_name="condition" type="text" name="s.F_asset_name" class="oa-input" value="${param['asset_name']}"/>
                </div>
            </li>
            
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">资产分类代码</div>
                <div class="oa-select-wrap  oa-mgl100">
                    <select id="assetType" class="oa-select" date_name="condition" name="s.F_asset_code">
                        <option value="">请选择</option>
                    </select>
                </div>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">使用部门</div>
                <div class="oa-input-wrap  oa-mgl100">
                    <input date_name="condition" data-type="7"  ctltype="selector" class="org oa-input oa-trigger-hidden"   type="text" name="s.F_use_department" value="${param['use_department']}"/>
                </div>
                <button id="nowDepartment" class="oa-button-label oa-trigger-hidden-button" type="button">选择部门</button>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">使用状况</div>
                <div class="oa-select-wrap  oa-mgl100">
                    <select class="oa-select" date_name="condition" name="s.F_state" >
                        <option value="">请选择</option>
                        <option value="0">在用</option>
                        <option value="1">报废</option>
                    </select>
                </div>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">规格型号</div>
                <div class="oa-input-wrap  oa-mgl100">
                    <input data-type="7" date_name="condition" type="text" name="s.F_specification" class="oa-input" value="${param['specification']}"/>
                </div>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                <div class="oa-label">取得日期从</div>
                <div class="oa-input-wrap  oa-mgl100 oa-input-wrap--ib">
                    <input date_name="condition" data-type="2" type="text"  name="s.F_get_date"  class="oa-input date" validate="{date:true}" value="${param['beginget_date']}"/>
                </div>
                <span>至</span>
                <div class="oa-input-wrap  oa-input-wrap--ib">
                    <input date_name="condition" data-type="4" type="text"  name="s.F_get_date"  class="oa-input date" validate="{date:true}" value="${param['endget_date']}"/>
                </div>
            </li>
            <li class="oa-search-item oa-fl oa-mgb10">
                    <input date_name="condition" type="hidden" name="s.oid" class="oa-input" value="${param['s.oid']}"/>
            </li>
        </ul>
    </div>

    <!-- 高级查询模块 -->
    <jsp:include page="/commons/include/list_common.jsp"></jsp:include> 
    
    <object id="BPLAOCX" classid="clsid:B12F8AAB-6124-4A83-B976-4C61CE2D952A" codebase="${ctx}/cab/BPLAOCX.cab" width="0" height="0" align="center" hspace="0" vspace="0" style="display: none;"></object>
<script type="text/javascript">
        $(function() {
            var tableParam = {
                columns : [ {
                	title : '序号',//显示的title
    				formatter :'addNum' 
    			}, {
                    field : 's.F_asset_id',//查询的字段
                    title : '资产编号',//显示的title
                    sortable : true
                //是否支持排序
                }, {
                    field : 's.F_asset_name',//查询的字段
                    title : '资产名称',//显示的title
                    sortable : true
                //是否支持排序
              /*   }, {
                    field : 's.oid',//查询的字段
                    title : '资产sfasefasf名称',//显示的title
                    sortable : true
                //是否支持排序 */
                },{
                    field : 's.F_specification',//查询的字段
                    title : '规格型号',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_asset_code',//查询的字段
                    title : '资产分类代码',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_version',//查询的字段
                    title : '资产类别',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_get_type',//查询的字段
                    title : '取得方式',//显示的title
                    sortable : true,
                    selectType:"select",
                    formatter : 'setGetType'//显示的时候执行edit函数
                //是否支持排序
                },{
                    field : 's.F_get_date',//查询的字段
                    title : '取得日期',//显示的title
                    sortable : true,
                    dateFlag : true,//是否是时间，这个会弹出时间控件选择器
                    dateFormat : "yyyy-MM-dd",//时间的显示格式
                //是否支持排序
                },{
                    field : 's.F_use_department',//查询的字段
                    title : '使用部门',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_care_person',//查询的字段
                    title : '保管人',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_use_date',//查询的字段
                    title : '使用日期',//显示的title
                    sortable : true,
                    dateFlag : true,//是否是时间，这个会弹出时间控件选择器
                    dateFormat : "yyyy-MM-dd",//时间的显示格式
                //是否支持排序
                },{
                    field : 's.F_number',//查询的字段
                    title : '数量',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_state',//查询的字段
                    title : '使用状况   ',//显示的title
                    sortable : true,
                    selectType:"select",
                    formatter : 'setState'//显示的时候执行edit函数
                //是否支持排序
                },{
                    field : 's.F_card_number',//查询的字段
                    title : '条码编号',//显示的title
                    sortable : true
                //是否支持排序
                },{
                    field : 's.F_abandonment_date',//查询的字段
                    title : '报废时间   ',//显示的title
                    sortable : true,
                    dateFlag : true,//是否是时间，这个会弹出时间控件选择器
                    dateFormat : "yyyy-MM-dd",//时间的显示格式
                //是否支持排序
                },{
                    field : '管理',//即使不去查询这个字段也必须。用来记录改变列的顺序用。如果不去查询必须带nosearch字段
                    title : '管理',
                    nosearch : true,//不去数据库查询
                    formatter : 'edit'//显示的时候执行edit函数
                }, ],
                uniqueId : "s.ID",//唯一主键字段
                type : "assetRegistrationList",//类型，用户存储在数据库中，每个项目的不同页面保证唯一
                tableName : 'assetRegistration s', //表明
                userId : '${sessionScope.LOGIN_USER_ID }', //用户ID
            //sortName : 's.seller_name',//初始化排序
            //sortOrder : 'desc'//初始化排序
            }
            initCommonTable(tableParam,function() {
    			var orgId = '<%=session.getAttribute("chooseOrgId") %>';
    			var orgName = '<%=session.getAttribute("orgName") %>';
    			if((orgId!=='null')&&(-1!=orgId)){
    			    $("input[name='s.oid']").val(orgId);
    			    totalSearch();
    			    $("input[name='s.F_use_department']").val(orgName);
    			    $("input[name='s.F_use_department']").attr("disabled",true);
    			    $("#nowDepartment").attr("disabled",true);
    		    }else{
    		    	totalSearch();
    		    }
    		});
        });
        
       
        
    	function addNum(value, row, index) {  
    		var page = $('#tb_common_show').bootstrapTable("getPage"); 
            return page.pageSize * (page.pageNumber - 1) + index + 1;  
    	}
    	
        function edit(value, row, index) {
        	var isEditor = '${isEditor}';
    		if(isEditor=="true"){
            var html = "";
                    html += '<a href="edit.ht?id=' + row["s.ID"] + ' "target ="_blank" >编辑</a>' +
                            '<a href="del.ht?&id=' + row["s.ID"] + '" class="oa-del">&nbsp删除</a>'+
                            '<a onClick="print(this)" href="#">&nbsp打印标签</a>';
            return html;
       		 }
        }
        function setGetType(value, row, index){
            if (value == 0) {
                return "领取旧设备";
            } 
            if (value == 1) {
                return "委托办公室购买";
            }
            if (value == 2) {
                return "项目部自行购买";
            }
        }
        
        function setState(value, row, index){
            if (value == 0) {
                return "在用";
            } 
            if (value == 1) {
                return "报废";
            }
            return "其他使用状态";
        }
        
        function loadDate(){
	       $.post('${ctx}/platform/system/dictionary/getMapByNodeKey.ht', {nodeKey: 'assetType'}, function(data, textStatus, xhr) {
	       		var parents = [];
	       		var childs = [];

	       		$.each(data.dicList, function(index, item) {
	       			if(item.nodePath.split(".").length <= 3){
	       				parents.push(item);
	       			}else{
	       				childs.push(item);
	       			}
	       		});

	       		$.each(childs, function(index, child) {
	       			$.each(parents, function(index, parent) {
	       				if(child.parentId === parseInt(parent.nodePath.split(".")[1])){
	       					parent.child = parent.child || [];
	       					parent.child.push(child);
	       				}
	       			});
	       		});

	       		var html = "";
	       		$.each(parents, function(index, item) {
	       			html += "<optgroup label='" + item.itemName + "'>";
	       				$.each(item.child, function(index, _item){
	       					html += "<option>" + _item.itemName + "</option>";
	       				});
	       			html += '</optgroup>';
	       		});

	       		$("#assetType").append(html);
	       });
        }
		loadDate();
    </script>
</body>
</html>


