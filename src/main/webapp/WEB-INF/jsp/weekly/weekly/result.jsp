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
					<!-- BEGIN checkout-message -->
                    <div class="checkout-message">
                        <c:choose>
                            <c:when test="${mailAttachment.sendEmail != null }">
                                <h1>Thank you! <small>Your daily report has been summarized successfully. It contains the following details.</small></h1>
                                <div class="table-responsive2">
                                    <table class="table table-payment-summary">
                                        <tbody>
                                        <tr>
                                            <td class="field">邮件主题</td>
                                            <td class="value">${mailAttachment.sendEmail.subject }</td>
                                        </tr>
                                        <tr>
                                            <td class="field">发件人</td>
                                            <td class="value">${mailAttachment.sendEmail.account }</td>
                                        </tr>
                                        <tr>
                                            <td class="field">操作时间</td>
                                            <td class="value"><javatime:format value="${mailAttachment.sendEmail.createTime}" pattern="yyyy-MM-dd HH:mm:ss"  /></td>
                                        </tr>
                                        <tr>
                                            <td class="field">收件人</td>
                                            <td class="value">${mailAttachment.sendEmail.recipients }</td>
                                        </tr>
                                        <tr>
                                            <td class="field">邮件内容</td>
                                            <td class="value">${mailAttachment.sendEmail.content }</td>
                                        </tr>
                                        <tr>
                                            <td class="field">附件</td>
                                            <td class="value"><a href="${rootUrl }app/weekly/daily/mailAttachment/download?mailAttachmentId=${mailAttachment.id}">${mailAttachment.name }</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <h1>Thank you! <small>Your daily report has been summarized successfully. It contains the following details.</small></h1>
                                <div class="table-responsive2">
                                    <table class="table table-payment-summary">
                                        <tbody>
                                        <tr>
                                            <td class="field">操作时间</td>
                                            <td class="value"><javatime:format value="${mailAttachment.createTime}" pattern="yyyy-MM-dd HH:mm:ss"  /></td>
                                        </tr>
                                        <tr>
                                            <td class="field">文件</td>
                                            <td class="value"><a href="${rootUrl }app/weekly/daily/mailAttachment/download?mailAttachmentId=${mailAttachment.id}">${mailAttachment.name }</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
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