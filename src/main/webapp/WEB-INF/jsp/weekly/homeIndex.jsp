<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>
<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>

<script>
	$(document).ready(function() {
		$("#content").myInit({
			search:{
				autoSearch:true,
				bindPage:$.My.Constans.BIND_PAGE_CENTER,
				handleResult:$.My.Constans.HANDLE_RESULT_UL
			}
		});

		$("#data_result").on("click", ".delete_op", function() {
			var trNode = this.parentNode.parentNode.parentNode;
			var hrefUrl = this.href;
			$.confirm({
				theme: 'white',
				title: 'Are you sure',
				content: '确定删除该数据？',
				buttons: {
					confirm: {
						text: '确认',
						keys: ['enter'],
						action: function(){
							$.ajax({
								url: hrefUrl,
								type: 'POST',
								success: function(data, textStatus, jqXHR) {
									if($.My.handleSuccessRes(data, textStatus, jqXHR)) {
										$.My.handleResultData(data, $.My.Constans.HANDLE_RESULT_UL, $.My.Constans.HANDLE_RESULT_DEL);
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
	
	<!-- begin #content -->
    <div id="content" class="content">
        <!-- begin container -->
        <div class="container">
            <!-- begin row -->
            <div id="myManager" class="row row-space-30">
                <!-- begin col-9 -->
                <div class="col-md-9">
                	<div id="data_result">
                	</div>
                	<div class="section-container">
                		<!-- begin pagination -->
                		<div id="page_pager" class="pagination-container text-center">
                		</div>
                		<!-- end pagination -->
                	</div>
                </div>
                <!-- end col-9 -->
                <!-- begin col-3 -->
				<div class="col-md-3">
					<!-- begin section-container -->
					<div class="section-container">
						<!-- 若翻页时，删除输入框内容，则为全文搜索。。。 -->
						<form:form id="dailyForm" name="dailyForm" cssClass="my_search_form" modelAttribute="spe" method="post" action="${rootUrl}app/weekly/dailyIndex">
							<div class="input-group sidebar-search">
								<input name="operateContent" type="text" class="form-control" placeholder="Search dailies..." />
								<span class="input-group-btn">
									<button class="btn btn-inverse my_search_submit" type="submit"><i class="fa fa-search"></i></button>
								</span>
							</div>
						</form:form>
					</div>
               	<%@ include file="/WEB-INF/jsp/weekly/common/sidebar.jsp"%>
				</div>
                <!-- end col-3 -->
            </div>
            <!-- end row -->
        </div>
        <!-- end container -->
    </div>
    <!-- end #content -->
    
    <!-- begin #footer -->
    <%@ include file="/WEB-INF/jsp/weekly/common/foot.jsp"%>
    <!-- end #footer -->
</body>
</html>