<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>

<link href="${rootUrl}assets/css/e-commerce/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/e-commerce/style-responsive.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/e-commerce/theme/default.css" id="theme" rel="stylesheet" />
<link href="${rootUrl}assets/css/e-commerce/animate.min.css" rel="stylesheet" />

<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
<script>
	$(document).ready(function() {
		$("#addForm").ajaxForm({
			type:'post',
			success:function(data, textStatus, jqXHR) {
				if(jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_ERROR)) {
					$.showMsg(false,data.msg);
				}else if(jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_JUMP)) {
					window.location.href = new Base64().decode(jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_JUMP));
				}
			},
			error:function(xhr, status, error) {
				$.showWarnMsg("系统异常，请稍后重试！");
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
	            <form:form cssClass="form-horizontal" method="POST" id="addForm" name="addForm" modelAttribute="demandModel" action="${rootUrl }app/weekly/daily/demandAdd">
	                <input type="hidden" id="projectId" name="projectId" value="${project.id }" />
	                <!-- BEGIN checkout-header -->
	                <div class="checkout-header">
	                    <!-- BEGIN row -->
	                    <div class="row">
	                        <!-- BEGIN col-4 -->
	                        <div class="col-md-4 col-sm-4">
	                            <div class="step active">
	                                <a href="###">
	                                    <div class="number">1.5</div>
	                                    <div class="info">
	                                        <div class="title">创建需求</div>
	                                        <div class="desc">创建新的需求事件</div>
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
	                	<h4 class="checkout-title">Describe specific demand</h4>
	                	<div class="form-group">
                            <label class="col-md-3 control-label">项目名称 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" value="${project.name }" readonly="readonly"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-md-3 control-label">需求标题 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" name="title"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-md-3 control-label">需求类型 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
	                            <select name="demandType" class="form-control required selectpicker" data-size="10" data-live-search="true" data-style="btn-inverse">
				                    <option value="">请选择...</option>
				                    <c:forEach items="${demandTypes}" var="type">
										<option value="${type}">${type.value}</option>
									</c:forEach>
				                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">需求描述 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                            	<textarea class="form-control required" name="summary" placeholder="" rows="10"></textarea>
                            </div>
                        </div>
	                </div>
	                <!-- END checkout-body -->
	                <!-- BEGIN checkout-footer -->
	                <div class="checkout-footer">
	                    <a id="back" href="${rootUrl }app/weekly/daily/demand?projectId=${project.id}" class="btn btn-white btn-lg pull-left">Back</a>
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