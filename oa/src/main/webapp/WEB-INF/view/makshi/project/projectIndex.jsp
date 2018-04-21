<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/commons/include/html_doctype.html"%>
<html>
<head>
<title>项目首页</title>
<%@include file="/commons/include/get.jsp"%>
<script type="text/javascript" src="/js/echarts/echarts.min.js"></script>
<style>
	.oa-main-wrap{
		overflow: hidden;
	}
	body{
		min-width: 1100px;
	}
</style>
</head>
<body>

    <div class="oa-project-title">
        <h2>项目首页</h2>
    </div>

    <div class="oa-pd20">
    	<select id="status" name="status" onchange="changeStatus()" >
    		<option value="-1" <c:if test="${status==-1 }">selected</c:if>>请选择</option>
    		<option value="0" <c:if test="${status==0 }">selected</c:if>>未开工</option>
    		<option value="1" <c:if test="${status==1 }">selected</c:if>>在建</option>
    		<option value="2" <c:if test="${status==2 }">selected</c:if>>已停工</option>
    		<option value="3" <c:if test="${status==3 }">selected</c:if>>已完工</option>
    	</select>
		<div id="left" style="width: 500px; height: 600px;float:left"></div>
		<div id="right" style="width: 500px; margin-left: 20px; height: 530px;float:left"></div>
	</div>
	


<script type="text/javascript">
var myChartLeft = echarts.init(document.getElementById('left'));
var myChartRight = echarts.init(document.getElementById('right'));
 $(function(){ 
	//console.log('${classifyArray}');
	
	optionLeft = {
		    title : {
		        text: '项目分布',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'horizontal',
		        x : 'center',
		        y : 50,
		        data:[${claData}]
		    },
		    toolbox: {
		        show : false,
		        feature : {
		            mark : {show: true},
		            dataView : {show: false, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: false},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'分类名称',
		            type:'pie',
		            radius : '55%',
		            center: ['52%', '65%'],
		            data:[
						${serDate}
		            ]
		        }
		    ]
		};
	myChartLeft.setOption(optionLeft); 
	
	
	optionRight = {
		    title : {
		        text: '近7天任务完成趋势',
		        x: "center"
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		    	show:false,
		    	x : 'center',
		        data:['任务完成数']
		    },
		    toolbox: {
		        show : true,
		        x:520,
		        feature : {
		            mark : {show: true},
		            dataView : {show: false, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: false},
		            saveAsImage : {show: false}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		            data : [${xAxisData}],
			        axisLabel:{  
	                    interval:0,//横轴信息全部显示  
	                    //rotate:-30,//-30度角倾斜显示  
	               }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series : [
		        {
		            name:'任务完成数',
		            type:'line',
		            data:[${countStr}],
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            }
		            
		        }
		    ]
		};
	myChartRight.setOption(optionRight); 
	
	myChartLeft.on('click', function (params) {
	    var status = $("#status").val();
	    $.get('/makshi/project/project/getTaskDoCount.ht?classifyId=' + params.data.id+'&status='+status, function (count) {
	    	var optionsAjax = myChartRight.getOption();
	    	optionsAjax.series[0].data = count.split(",");
	    	optionsAjax.series[0].name = params.data.name+"任务完成数";
	    	myChartRight.setOption(optionsAjax);
	    });
	    
	});
	
}); 
 
 
 function changeStatus(){
	 var status = $("#status").val();
	 window.location.href='/makshi/project/project/projectIndex.ht?status='+status;
 }
 
 
</script>
</body>
</html>