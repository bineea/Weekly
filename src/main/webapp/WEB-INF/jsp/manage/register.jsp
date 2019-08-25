<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>My Weekly | Register</title>
    <meta charset="utf-8" />
    <title>My Weekly | Register Page</title>
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

            $("#getCode").click(function () {
                var emailVal = $("#email").val();
                if(emailVal == '' || emailVal == null) {
                    $.showWarnMsg("邮箱地址不能为空");
                    return false;
                }
                $.ajax({
                    url:'${rootUrl}app/common/checkCode',
                    type:'POST',
                    data:{
                        email: emailVal
                    },
                    beforeSend: function () {
                        $(this).attr({ disabled: "disabled" });
                    },
                    complete: function () {
                        $(this).removeAttr("disabled");
                    },
                    success: function (data, textStatus, jqXHR) {
                        if(jqXHR.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)) {
                            $.showWarnMsg(data.msg);
                        } else if(jqXHR.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)) {
                            $.showMsg(true, data.msg);
                        } else {
                            $.showWarnMsg("未知返回类型，请稍后重试！");
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $.showWarnMsg("系统异常，请稍后重试！");
                    }
                });
                return false;
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
    <!-- begin register -->
    <div class="register register-with-news-feed">
        <!-- begin news-feed -->
        <div class="news-feed">
            <div class="news-image">
                <img src="${rootUrl}assets/img/login-bg/bg-1.jpg" alt="" />
            </div>
            <div class="news-caption">
                <h4 class="caption-title"><i class="fa fa-edit text-success"></i> Announcing the Color Admin app</h4>
                <p>
                    As a Color Admin Apps administrator, you use the Color Admin console to manage your organization’s account, such as add new users, manage security settings, and turn on the services you want your team to access.
                </p>
            </div>
        </div>
        <!-- end news-feed -->
        <!-- begin right-content -->
        <div class="right-content">
            <!-- begin register-header -->
            <h1 class="register-header">
                Sign Up
                <small>Create your Account. It’s free and always will be.</small>
            </h1>
            <!-- end register-header -->
            <!-- begin register-content -->
            <div id="myManager" class="register-content">
                <form:form modelAttribute="userInfoModel" id="myForm" name="myForm" action="${rootUrl}app/common/register" method="POST" class="margin-bottom-0">
                    <label class="control-label">Login Name <span class="text-danger">*</span></label>
                    <div class="row m-b-15">
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="loginName" name="loginName" id="loginName" required />
                        </div>
                    </div>
                    <label class="control-label">Name <span class="text-danger">*</span></label>
                    <div class="row m-b-15">
                        <div class="col-md-12">
                            <input type="text" class="form-control" placeholder="name" name="name" id="name" required />
                        </div>
                    </div>
                    <label class="control-label">Email <span class="text-danger">*</span></label>
                    <div class="row m-b-15">
                        <div class="col-md-12">
                            <input type="email" class="form-control" placeholder="Email address" name="email" id="email" required />
                        </div>
                    </div>
                    <label class="control-label">Password <span class="text-danger">*</span></label>
                    <div class="row m-b-15">
                        <div class="col-md-12">
                            <input type="password" class="form-control" placeholder="Password" name="passwd" id="passwd" required />
                        </div>
                    </div>
                    <label class="control-label">Confirm Password <span class="text-danger">*</span></label>
                    <div class="row m-b-15">
                        <div class="col-md-12">
                            <input type="password" class="form-control" placeholder="Confirm Password" name="confirmPw" id="confirmPw" required />
                        </div>
                    </div>
                    <label class="control-label">Check code<span class="text-danger">*</span></label>
                    <div class="row row-space-10">
                        <div class="col-md-8 m-b-15">
                            <input type="text" class="form-control" placeholder="Check code" name="checkCode" id="checkCode" required />
                        </div>
                        <div class="col-md-4 m-b-15">
                            <button id="getCode" type="button" class="btn btn-block btn-default">Get Code</button>
                        </div>
                    </div>
                    <div class="register-buttons">
                        <button type="submit" class="btn btn-primary btn-block btn-lg">Sign Up</button>
                    </div>
                    <div class="m-t-20 m-b-40 p-b-40 text-inverse">
                        Already a member? Click <a href="${rootUrl }app/common/login">here</a> to login.
                    </div>
                    <hr />
                    <p class="text-center">
                        &copy; My Weekly 2019
                    </p>
                </form:form>
            </div>
            <!-- end register-content -->
        </div>
        <!-- end right-content -->
    </div>
    <!-- end register -->

</div>
<!-- end page container -->
</body>
</html>