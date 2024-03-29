<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>

<link href="${rootUrl}assets/e-commerceCss/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/e-commerceCss/style-responsive.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/e-commerceCss/theme/default.css" id="theme" rel="stylesheet" />
<link href="${rootUrl}assets/e-commerceCss/animate.min.css" rel="stylesheet" />

<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
<script src="${rootUrl}assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script>
	$(document).ready(function() {
		
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
                <!-- BEGIN checkout-header -->
                <div class="checkout-header">
                    <!-- BEGIN row -->
                    <div class="row">
                        <!-- BEGIN col-3 -->
                        <div class="col-md-3 col-sm-3">
                            <div class="step">
                                <a href="###">
                                    <div class="number">1</div>
                                    <div class="info">
                                        <div class="title">项目</div>
                                        <div class="desc">选择对应的项目</div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <!-- END col-3 -->
                        <!-- BEGIN col-3 -->
                        <div class="col-md-3 col-sm-3">
                            <div class="step">
                                <a href="###">
                                    <div class="number">2</div>
                                    <div class="info">
                                        <div class="title">需求事件</div>
                                        <div class="desc">选择对应的需求</div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <!-- END col-3 -->
                        <!-- BEGIN col-3 -->
                        <div class="col-md-3 col-sm-3">
                            <div class="step">
                                <a href="###">
                                    <div class="number">3</div>
                                    <div class="info">
                                        <div class="title">具体操作</div>
                                        <div class="desc">填写你的具体操作</div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <!-- END col-3 -->
                        <!-- BEGIN col-3 -->
                        <div class="col-md-3 col-sm-3">
                            <div class="step active">
                                <a href="###">
                                    <div class="number">4</div>
                                    <div class="info">
                                        <div class="title">完成</div>
                                        <div class="desc">日报记录完成</div>
                                    </div>
                                </a>
                            </div>
                        </div>
                        <!-- END col-3 -->
                    </div>
                    <!-- END row -->
                </div>
                <!-- END checkout-header -->
                <!-- BEGIN checkout-body -->
                <div class="checkout-body">
					<!-- BEGIN checkout-message -->
                    <div class="checkout-message">
                        <h1>Thank you! <small>Your daily report has been submitted successfully. It contains the following details.</small></h1>
                        <div class="table-responsive2">
                            <table class="table table-payment-summary">
                                <tbody>
                                    <tr>
                                        <td class="field">操作日期</td>
                                        <td class="value"><javatime:format value="${daily.operateDate}" pattern="yyyy-MM-dd"  /></td>
                                    </tr>
                                    <tr>
                                        <td class="field">处理状态</td>
                                        <td class="value">${daily.handleStatus.value }</td>
                                    </tr>
                                    <tr>
                                        <td class="field">所属项目</td>
                                        <td class="value">${daily.demand.project.name }</td>
                                    </tr>
                                    <tr>
                                        <td class="field">对应需求</td>
                                        <td class="value">${daily.demand.title }</td>
                                    </tr>
                                    <tr>
                                        <td class="field">具体操作</td>
                                        <td class="value">${daily.operateContent }</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END checkout-message -->
                </div>
                <!-- END checkout-body -->
                <!-- BEGIN checkout-footer -->
                <div class="checkout-footer text-center">
                	<a href="${rootUrl }app/weekly/homeIndex" class="btn btn-white btn-lg p-l-30 p-r-30 m-l-10">返回首页</a>
                </div>
                <!-- END checkout-footer -->
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