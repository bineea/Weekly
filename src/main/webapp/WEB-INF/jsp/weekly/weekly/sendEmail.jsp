<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>

    <link href="${rootUrl}assets/e-commerceCss/style.min.css" rel="stylesheet" />
    <link href="${rootUrl}assets/e-commerceCss/style-responsive.min.css" rel="stylesheet" />
    <link href="${rootUrl}assets/e-commerceCss/theme/default.css" rel="stylesheet" />
    <%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
    <link href="${rootUrl}assets/plugins/jquery-tag-it/css/jquery.tagit.css" rel="stylesheet" />
    <link href="${rootUrl}assets/manageCss/style.min.css" rel="stylesheet" />
    <script src="${rootUrl}assets/plugins/jquery-tag-it/js/tag-it.min.js"></script>
    <script>
    $(document).ready(function() {

        $('#emailTo').tagit({
            fieldName: "recipients",
            availableTags: ["c++", "java", "php", "javascript", "ruby", "python", "c"]
        });

        $("#configForm").ajaxForm({
            type:'post',
            beforeSubmit: function() {
                // 创建loading
                $.My.showLoading($.My.Messages.MSG_OPERATE);
            },
            success:function(data, textStatus, jqXHR) {
                $.My.hideLoading();
                if($.My.handleSuccessRes(data, textStatus, jqXHR)) {
                    window.location.href = '${rootUrl}app/weekly/daily/mailAttachment/result?mailAttachmentId='+data.resultObj;
                }
            },
            error:function(xhr, status, error) {
                $.My.hideLoading();
                $.My.showMsg(false,"系统异常，请稍后重试！");
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
                <form:form cssClass="form-horizontal" method="POST" id="configForm" name="configForm" modelAttribute="sendEmailConf" action="${rootUrl }app/weekly/daily/sendEmail">
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
                                    <div class="step active">
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
                                    <div class="step">
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
                    </div>
                    <!-- END checkout-header -->
                    <!-- BEGIN checkout-body -->
                    <div class="checkout-body">
                        <input type="hidden" name="mailAttachmentIds" value="${mailAttachmentId}">
                        <h4 class="checkout-title">Describe specific email</h4>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Email <span class="text-danger">*</span></label>
                            <div class="form-inline">
                                <div class="col-md-4" style="padding-right: 0px;">
                                    <input type="text" class="form-control required" style="width: 100%;" name="account" placeholder="邮箱账号"/>
                                </div>
                                <div class="col-md-2" style="padding-left: 0px;">
                                    <select name="confType" class="form-control required selectpicker" style="width: 100%;" data-size="10" data-live-search="true" data-style="btn-inverse">
                                        <option value="">请选择...</option>
                                        <c:forEach items="${emailConfTypes}" var="confType">
                                            <option value="${confType}">${confType.suffix}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Password <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="password" class="form-control required" name="passwd" placeholder="邮箱密码"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">To <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <ul id="emailTo" name="" class="inverse" style="border-radius: 4px;">
                                </ul>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Subject </label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" name="subject" placeholder="主题"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">Content </label>
                            <div class="col-md-6">
                                <textarea class="form-control required" name="content" placeholder="邮件内容" rows="10"></textarea>
                            </div>
                        </div>
                    </div>
                    <!-- END checkout-body -->
                    <!-- BEGIN checkout-footer -->
                    <div class="checkout-footer">
                        <a id="quit" href="${rootUrl}app/weekly/daily/mailAttachment/result?mailAttachmentId=${mailAttachmentId}" class="btn btn-white btn-lg pull-left">Quit</a>
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