<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>

<link href="${rootUrl}assets/e-commerceCss/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/e-commerceCss/style-responsive.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/e-commerceCss/theme/default.css" id="theme" rel="stylesheet" />
<link href="${rootUrl}assets/e-commerceCss/animate.min.css" rel="stylesheet" />

<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
<script>
    $(document).ready(function() {
        $("#configForm").ajaxForm({
            type:'post',
            success:function(data, textStatus, jqXHR) {
                if($.My.handleSuccessRes(data, textStatus, jqXHR)) {
                    window.localtion.href = '${rootUrl}app/weekly/daily/project'
                }
            },
            error:function(xhr, status, error) {
                $.My.showWarnMsg("系统异常，请稍后重试！");
            }
        });
    });
</script>
</head>
<body>
<!-- begin #header -->
<%@ include file="/WEB-INF/jsp/weekly/common/head.jsp"%>
<!-- end #header -->

<div id="content">
    <!-- BEGIN #checkout-cart -->
    <div class="bg-silver section-container">
        <!-- BEGIN container -->
        <div class="container">
            <!-- BEGIN checkout -->
            <div class="checkout">
                <form:form cssClass="form-horizontal" method="POST" id="configForm" name="configForm" modelAttribute="project" action="${rootUrl }app/weekly/daily/sendEmail">
                <!-- BEGIN checkout-header -->
                <div class="checkout-header">
                    <!-- BEGIN row -->
                    <div class="row">
                        <!-- BEGIN row -->
                        <div class="row">
                            <!-- BEGIN col-4 -->
                            <div class="col-md-4 col-sm-4">
                                <div class="step">
                                    <a href="###">
                                        <div class="number">1</div>
                                        <div class="info">
                                            <div class="title">日报数据</div>
                                            <div class="desc">选择需要汇总的日报数据</div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            <!-- END col-4 -->
                            <!-- BEGIN col-4 -->
                            <div class="col-md-4 col-sm-4">
                                <div class="step">
                                    <a href="###">
                                        <div class="number">2</div>
                                        <div class="info">
                                            <div class="title">推送邮件</div>
                                            <div class="desc">填写推送邮件相关信息</div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            <!-- END col-4 -->
                            <!-- BEGIN col-4 -->
                            <div class="col-md-4 col-sm-4">
                                <div class="step active">
                                    <a href="###">
                                        <div class="number">3</div>
                                        <div class="info">
                                            <div class="title">完成</div>
                                            <div class="desc">日报汇总完成</div>
                                        </div>
                                    </a>
                                </div>
                            </div>
                            <!-- END col-4 -->
                        </div>
                        <!-- END row -->
                    </div>
                    <!-- END checkout-header -->
                    <!-- BEGIN checkout-body -->
                    <div class="checkout-body">
                        <h4 class="checkout-title">Describe specific project</h4>
                        <div class="form-group">
                            <label class="col-md-3 control-label">项目名称 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" name="name" placeholder="项目全称"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">项目简称 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" name="abbr" placeholder="项目简称（长度不能超过6）"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">项目描述 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" name="summary" placeholder="项目简单描述"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">所属地区 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <select name="area" class="form-control required selectpicker" data-size="10" data-live-search="true" data-style="btn-inverse">
                                    <option value="">请选择...</option>
                                    <c:forEach items="${areas}" var="a">
                                        <option value="${a}">${a.value}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <!-- END checkout-body -->
                    <!-- BEGIN checkout-footer -->
                    <div class="checkout-footer">
                        <a id="back" href="${rootUrl }app/weekly/daily/project" class="btn btn-white btn-lg pull-left">Back</a>
                        <button type="submit" class="btn btn-inverse btn-lg p-l-30 p-r-30 m-l-10">Submit</button>
                    </div>
                    <!-- END checkout-footer -->
                </form:form>
            </div>
            <!-- END checkout -->
        </div>
        <!-- END container -->
    </div>
    <!-- END #checkout-cart -->
</div>

<!-- begin #footer -->
<%@ include file="/WEB-INF/jsp/weekly/common/foot.jsp"%>
<!-- end #footer -->
</body>
</html>