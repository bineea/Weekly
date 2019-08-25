<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>My Weekly | Login Page</title>
	<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
	<%@ include file="/WEB-INF/jsp/manage/common/include.jsp"%>
	<script>
		$(document).ready(function() {
			App.init();
			$('#myForm').ajaxForm({
				type: "post", //提交方式 
				success: function(responseText, status, xhr){
					if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)) {
						$.showWarnMsg(responseText.msg);
					} else if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_JUMP)) {
						window.location = new Base64().decode(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_JUMP));
					}
				},
				error: function(xhr, status, error) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
			});
		});
	</script>
</head>
<body class="pace-top bg-white">
	<!-- begin #page-loader -->
	<div id="page-loader" class="fade in"><span class="spinner"></span></div>
	<!-- end #page-loader -->
	
	<!-- begin #page-container -->
	<div id="page-container" class="fade">
	    <!-- begin login -->
        <div class="login login-with-news-feed">
            <!-- begin news-feed -->
            <div class="news-feed">
                <div class="news-image">
                    <img src="${rootUrl }assets/img/login-bg/bg-7.jpg" data-id="login-cover-image" alt="" />
                </div>
                <div class="news-caption">
                    <h4 class="caption-title"><i class="fa fa-quote-left text-success"></i> My Weekly</h4>
                    <p>
                        Record something happened every day.
                    </p>
                </div>
            </div>
            <!-- end news-feed -->
            <!-- begin right-content -->
            <div class="right-content">
                <!-- begin login-header -->
                <div class="login-header">
                    <div class="brand">
                        <span class="logo"></span> My Weekly
                        <small>My place My rule</small>
                    </div>
                    <div class="icon">
                        <i class="fa fa-sign-in"></i>
                    </div>
                </div>
                <!-- end login-header -->
                <!-- begin login-content -->
                <div id="myManager" class="login-content">
                    <form:form modelAttribute="userInfoModel" id="myForm" name="myForm" action="${rootUrl }app/common/login" method="POST" class="margin-bottom-0">
                        <div class="form-group m-b-15">
                            <form:input type="text" path="loginName" class="form-control input-lg" placeholder="LoginName" />
                        </div>
                        <div class="form-group m-b-15">
                            <form:input type="password" path="passwd" class="form-control input-lg" placeholder="Password" />
                        </div>
                        <div class="checkbox m-b-30">
                            <label>
                                <form:checkbox path="rememberMe" /> Remember Me
                            </label>
                        </div>
                        <div class="login-buttons">
                            <button type="submit" class="btn btn-success btn-block btn-lg">Sign me in</button>
                        </div>
                        <div class="m-t-20 m-b-40 p-b-40 text-inverse">
                            Not a member yet? Click <a href="${rootUrl}app/common/register" class="text-success">here</a> to register.
                        </div>
                        <hr />
                        <p class="text-center">
                            &copy; My Weekly 2019
                        </p>
                    </form:form>
                </div>
                <!-- end login-content -->
            </div>
            <!-- end right-container -->
        </div>
        <!-- end login -->
	</div>
	<!-- end page container -->

</body>
</html>
