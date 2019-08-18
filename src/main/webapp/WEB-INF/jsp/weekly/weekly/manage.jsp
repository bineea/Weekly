<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>

<link href="${rootUrl}assets/e-commerceCss/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/manageCss/style.min.css" rel="stylesheet" />

<link href="${rootUrl}assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/plugins/bootstrap-datepicker/css/bootstrap-datepicker3.min.css" rel="stylesheet" />

<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
<script src="${rootUrl}assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js"></script>
<script>
	$(document).ready(function() {

		$("#startOpDate").datepicker({
			opens: 'right',
			format: 'yyyy-mm-dd',
			endDate: '0d',
			todayHighlight: true,
			autoclose: true
		});

		$("#endOpDate").datepicker({
			opens: 'right',
			format: 'yyyy-mm-dd',
			endDate: '0d',
			todayHighlight: true,
			autoclose: true
		});

		$("#content").myInit({
			search:{
				autoSearch:true,
				bindPage:$.My.Constans.BIND_PAGE_CENTER,
	    		handleResult:$.My.Constans.HANDLE_RESULT_TABLE
	    	}
		});

		$("#next").click(function(){
			var val=$('input:radio[name="projectId"]:checked').val();
			if(val === null || val === '' || val == null || val == '') {
				$.My.showMsg(false,"请选择对应的项目");
			}
			else {
				$.ajax({
					url:'${rootUrl}app/weekly/daily/check',
					type:'POST',
					data:{projectId: val},
					success:function(data, textStatus, jqXHR) {
						if(jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_ERROR)) {
							$.My.showMsg(false,data.msg);
						} else {
							window.location.href = '${rootUrl}app/weekly/daily/demand?projectId='+val;
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
						$.My.showMsg(false,"系统异常，请稍后重试！");
					}
				});
			}
			return false;
		});
		

	});
	
</script>

<style type="text/css">

</style>
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
							<div class="step active">
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
				<!-- END checkout-header -->
				<!-- BEGIN checkout-body -->
				<div class="checkout-body">
					<form:form id="dailyForm" name="dailyForm" cssClass="form-inline my_search_form" modelAttribute="spe" method="POST" action="${rootUrl}app/weekly/daily/combine">
						<div class="form-group m-r-10 m-b-5">
							<div id="startOpDate" class="input-group date">
								<input type="text" class="form-control" name="startOpDate" placeholder="START DATE" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="form-group m-r-10 m-b-5">
							<div id="endOpDate" class="input-group date">
								<input type="text" class="form-control" name="endOpDate" placeholder="END DATE" />
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
						<div class="input-group col-md-6 m-r-10 m-b-5">
							<input type="text" class="form-control" placeholder="Search Daily Content" />
							<span class="input-group-btn">
                                <button class="btn btn-sm btn-inverse" type="submit"><i class="fa fa-search"></i></button>
                            </span>
						</div>
					</form:form>
					<!-- 表格样式 -->
					<div class="table-responsive">
						<table id="data-table" class="table table-cart">
							<thead>
								<tr>
									<th>
										<div class="checkbox checkbox-css">
											<input type="checkbox" id="checkbox_css_1" data-click="checkbox-select-all" value="" />
											<label for="checkbox_css_1">
												Select All
											</label>
										</div>
									</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<div id="page_pager">
						</div>
					</div>

				</div>
				<!-- END checkout-body -->
				<!-- BEGIN checkout-footer -->
				<div class="checkout-footer">
					<button id="next" class="btn btn-inverse btn-lg p-l-30 p-r-30 m-l-10">Next</button>
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