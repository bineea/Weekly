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

<link href="${rootUrl}assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" />

<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
<script src="${rootUrl}assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script>
	$(document).ready(function() {
		
		$("#operateTime").datepicker({
			opens: 'right',
			format: 'yyyy-mm-dd',
			endDate: '0d', 
			autoclose: true
		});
		
		$("#submit").click(function() {
			$.confirm({
		    	theme: 'white',
		        title: 'Are you sure',
		        content: '确定提交？',
		        buttons: {   
		        	confirm: {
		            	text: '确认',
		                keys: ['enter'],
		                action: function(){
		 					$("#dailyForm").ajaxSubmit({
		 						type: 'POST',
		 						success: function(data, textStatus, jqXHR) {
									if($.My.handleSuccessRes(data, textStatus, jqXHR)) {
										window.location.href = "${rootUrl }app/weekly/daily/record?dailyId="+data.msg;
									}
		 						},
		 						error:function(XMLHttpRequest, textStatus, errorThrown) {
		 							$.showWarnMsg("系统异常，请稍后重试！");
		 						}
		 					});
		                }
		            },
		            cancel: {
		            	text: '取消'
		            }
		        }
		    });
			return false;
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
	            <form:form cssClass="form-horizontal" method="POST" id="dailyForm" name="dailyForm" modelAttribute="dailyModel" action="${rootUrl }app/weekly/daily/edit">
	            	<input type="hidden" id="dailyId" name="dailyId" value="${daily.id }" />
	            	<input type="hidden" id="projectId" name="projectId" value="${project.id }" />
	            	<input type="hidden" id="demandId" name="demandId" value="${demand.id }" />
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
	                            <div class="step active">
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
	                            <div class="step">
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
						<h4 class="checkout-title">Describe specific operations</h4>
						<div class="form-group">
                            <label class="col-md-3 control-label">所属项目 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" value="${project.name }" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-3 control-label">对应需求 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <input type="text" class="form-control required" value="${demand.title }" readonly="readonly"/>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-md-3 control-label">操作日期<span class="text-danger">*</span></label>
							<div class="col-md-6">
	                            <div id="operateTime" class="input-group date">
	                                <input type="text" class="form-control required" name="operateDate" placeholder="具体操作执行日期" value="${daily.operateDate }"/>
	                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                            </div>
							</div>
                        </div>
						<div class="form-group">
                            <label class="col-md-3 control-label">具体状态 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
	                            <select name="handleStatus" class="form-control required selectpicker" data-size="10" data-live-search="true" data-style="btn-inverse">
				                    <option value="">请选择...</option>
				                    <c:forEach items="${handleStatues}" var="handleSta">
										<option value="${handleSta}" ${handleSta eq daily.handleStatus?'selected':''}  >${handleSta.value}</option>
									</c:forEach>
				                </select>
                            </div>
                        </div>
						<div class="form-group">
                            <label class="col-md-3 control-label">具体操作 <span class="text-danger">*</span></label>
                            <div class="col-md-6">
                                <textarea class="form-control required" name="operateContent" placeholder="" rows="10">${daily.operateContent }</textarea>
                            </div>
                        </div>
                        <c:if test="${demand.demandType eq 'ZSSJXG' }">
	                        <div class="form-group">
	                            <label class="col-md-3 control-label">SQL语句 <span class="text-danger">*</span></label>
	                            <div class="col-md-6">
	                                <textarea class="form-control required" name="sqlContent" placeholder="" rows="10">${daily.sqlContent }</textarea>
	                            </div>
	                        </div>
                        </c:if>
	                </div>
	                <!-- END checkout-body -->
	                <!-- BEGIN checkout-footer -->
	                <div class="checkout-footer">
	                    <a id="back" href="${rootUrl}app/weekly/daily/demand?projectId=${project.id }&demandId=${demand.id }" class="btn btn-white btn-lg pull-left">Back</a>
                        <button id="submit" class="btn btn-inverse btn-lg p-l-30 p-r-30 m-l-10">Submit</button>
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