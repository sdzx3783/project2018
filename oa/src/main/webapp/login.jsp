<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.hotent.core.api.util.PropertyUtil"%>
<%@page import="org.springframework.security.authentication.AuthenticationServiceException"%>
<%@page import="org.springframework.security.authentication.AccountExpiredException"%>
<%@page import="org.springframework.security.authentication.DisabledException"%>
<%@page import="org.springframework.security.authentication.LockedException"%>
<%@page import="org.springframework.security.authentication.BadCredentialsException"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.springframework.security.web.WebAttributes"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
	String appName=PropertyUtil.getByAlias("appName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>登录</title>
    <link rel="stylesheet" href="${ctx}/styles/login/new/reset.css">
    <link rel="stylesheet" href="${ctx}/styles/login/new/login.css">
    <script type="text/javascript" src="${ctx}/js/custom/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/custom/jquery.validate.js"></script>
 	<script type="text/javascript" src="${ctx}/js/custom/cookie.js"></script>
 	<script type="text/javascript" src="${ctx}/js/common/browserinfo.js"></script>
</head>
<body>
    <div class="header">
        <div class="top">
            <h1>深水咨询OA管理系统</h1></div>
    </div>

    <div class="main">
        <img src="/images/login_bg2.png" alt="">
        <div class="form">
            <h2>用户登录</h2>
			<form id="form-login" action="${ctx}/login.ht" method="post">
                <div class="form-line user-line">
                    <input type="text" class="input" id="txt_username" name="username" placeholder="请输入用户名/手机号码">
                    <div class="clear"></div>
                    <div class="error-msg"></div>
                </div>
                <div class="form-line pswd-line">
                    <input type="password" name="password" id="txt_password" class="input" placeholder="请输入密码">
                    <div class="clear"></div>
                    <div class="error-msg"></div>
                </div>
             <c:if test="${sessionScope.validCodeEnabled=='true'}">
 					<div class="vcode column">
					 	<div>
							<span>验证码:</span><br>
						
							<input type="text" name="validCode"  />
						</div>
						<div class="vcode_img">
							<img id="validImg" src="${ctx}/servlet/ValidCode" /><br>
							<input type="hidden" name="validCodeEnabled" value="true"/>
							<a onclick="reload()">看不清，换一张</a>
						</div>
					</div>
			</c:if>
               <div class="line">
                    <span><label id="save-label" class="save-label"><input id="ck_rmbUser" class="save-input" name="savepswd" type="checkbox"></label>记住密码</span>
                    <a href="javascript:void(0)" class="forget" onclick="document.getElementById('form-login').reset();">重置</a>
                </div>
                	<%
				Object loginError=(Object)request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				
				if(loginError!=null ){
					String msg="";
					if(loginError instanceof BadCredentialsException){
						msg="密码输入错误";
					}
					else if(loginError instanceof AuthenticationServiceException){
						AuthenticationServiceException ex=(AuthenticationServiceException)loginError;
						msg=ex.getMessage();
					}
					else{
						msg=loginError.toString();
					}
				%>
				<div class="msg-line"><%=msg %></div>
				<%-- <div class="confirm"><span style="color:#ff0000;"><%=msg %></span></div> --%>
				<%
				request.getSession().removeAttribute("validCodeEnabled");//删除需要验证码的SESSION
				request.getSession().removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);//删除登录失败信息
				}
				%>
                <div class="form-line submit-line"><input type="submit" class="input" value="登录"></div>
                <div><a href='${ctx}/media/office/NtkoOfficeControlSetup.msi'>下载Office控件</a></div>
            </form>
        </div>
    </div>
    <!-- 浏览器判断弹窗  -->
    <div class="browser-tips">
    	<div class="tips-mask"></div>
    	<div class="tips-wrapper">
    		<div class="tips-header">
    			<h4>浏览器版本</h4>
    			<a href="javascript:;" class="tips-close">X</a>
    		</div>
    		<div class="tips-content">
    			<p class="tit">系统检测到您正在使用<span id="agentBrowser"></span>浏览器访问深水咨询OA管理系统</p>
    			<p>如需正常使用本网站，请确认您已经<span class="red">关闭兼容模式</span></p>
    			<p>如需更好体验本网站，我们推荐您下载使用如下浏览器：</p>
    			<div class="agents">
    				<a target="_blank" href="http://down.360safe.com/se/360se9.1.0.404.exe"><img src="/images/360.png" alt="360浏览器">360浏览器</a>
    				<!-- <a target="_blank" href="https://www.baidu.com/link?url=csFpIp27ubGjFFV73-xzZ4Jlhws0FvWn8QAtQYSKih8UZoZEbdNCHQ4WB14YqQZ9-KKTrUqTsMoik0EyoCjXq_xNgn81J6a-_Mv-xfmmRM_&wd=&eqid=884234ee00008b5f000000065a5da436"><img src="/images/qq.png" alt="QQ浏览器">QQ浏览器</a>    				 -->
    			</div>
    		</div>
    		<div class="tips-footer">
    			<a href="javascript:;" class="tips-confirm">确定</a>
    		</div>
    	</div>
    </div>
<script>
    $(function(){
        $('.form').on('keyup', '.input', function(event){
            var $this = $(this);
            var $clear = $this.parents('.form-line').find('.clear');

            if($this.val().length > 0){
                $clear.addClass('view');
            }else{
                $clear.removeClass('view');
            }
        });

        $('.form').on('click', '.clear', function(event){
            var $this = $(this);
            var $input = $this.parents('.form-line').find('.input');

            $input.val('');
            $input.trigger('keyup');
        });

        $('#ck_rmbUser').on('click', function(){
            var state = $(this).prop('checked');
            var $label = $(this).parents('.save-label');
            if(state){
                $label.addClass('active');
            }else{
                $label.removeClass('active');
            }
        });

        $('#form-login').validate({
            rules: {
                username: {
                    required: true
                },
                password: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                },
                password: {
                    required: "请输入密码"
                }
            },
 			submitHandler:function(form){
 				if ($("#ck_rmbUser").prop("checked")) {//设置cookie
 		            var str_username = $("#txt_username").val();
 		            var str_password = $("#txt_password").val();
 		           setCookie("rmbUser", "true",7,"/");
 		           setCookie("username", str_username, 7,"/");
 		           setCookie("password", str_password,7,"/");
 		        }else {//清除cookie
 		        	delCookie("rmbUser");
 		        	delCookie("username");
 		        	delCookie("password");
 		        }
            	form.submit();
            },
            errorPlacement: function(error, element) {
                element.parent('.form-line').find('.error-msg').html(error);
            }
        });
        
        var rmbUser=getCookie("rmbUser");
        if (rmbUser == "true") {
	           $("#ck_rmbUser").prop("checked", true);
	           $("#save-label").addClass('active');
	           $("#txt_username").val(getCookie("username"));
	           $("#txt_password").val(getCookie("password"));
         }
    });
    function browserClose() {
    	localStorage.setItem("isFirstView", "none");
    	$(".browser-tips").hide();
    }
    $(".tips-close, .tips-confirm").on("click", function() {
    	browserClose();
    });
    /* 判断是否初次访问  */
    function firstVisit() {
    	if(localStorage.getItem("isFirstView") == "none") {
    		return false;
    	}else {   		
    		return true;
    	}
    }
    /* 根据浏览器类型判断  */
   	if(firstVisit()) {
   		var browserInfo = new browserInfo();
   	    if(browserInfo.browser == 'IE' && browserInfo.osVersion < 9) {
   	    	$("#agentBrowser").text(browserInfo.browser); 
   	    	$(".browser-tips").show();
   	    } 
   	}       
</script>
<script   language="javascript">    
      if (top != window)    
      top.location.href = window.location.href;    
  </script>   
</body>
</html>