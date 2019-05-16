<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My Weekly</title>

<link href="${rootUrl}assets/css/e-commerce/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/one-page-parallax/style.min.css" rel="stylesheet" />
<link href="${rootUrl}assets/css/one-page-parallax/theme/default.css" id="theme" rel="stylesheet" />

<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>
<script>
	$(document).ready(function() {
		$("#projectForm").pageManage({
			autoSearch:true,
			bindPage:$.Constans.BIND_PAGE_CENTER,
    		handleResult:$.Constans.HANDLE_RESULT_UL
		});
		
		$("#next").click(function(){
			var val=$('input:radio[name="projectId"]:checked').val();
			if(val === null || val === '' || val == null || val == '') {
				$.showMsg(false,"请选择对应的项目");
			}
			else {
				$.ajax({
					url:'${rootUrl}app/weekly/daily/check',
					type:'POST',
					data:{projectId: val},
					success:function(data, textStatus, jqXHR) {
						if(jqXHR.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)) {
							$.showMsg(false,data.msg);
						} else {
							window.location.href = '${rootUrl}app/weekly/daily/demand?projectId='+val;
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown) {
						$.showMsg(false,"系统异常，请稍后重试！");
					}
				});
			}
			return false;
		});
	});
	
</script>

<style type="text/css">

/* 表格点击样式
table>tbody>tr.custom_clicked{
	background-color: #cceeee;
	font-size:1.25em;
	font-weight:bold;
	box-shadow: 10px 10px 5px #888888;
} */

/* ul点击样式 */
.pricing-table .custom_clicked {
    padding: 0px;
    margin-top: -30px;
}
.pricing-table .custom_clicked .features > li {
    padding: 15px 0;
}
.pricing-table .custom_clicked h3 {
    padding: 20px 30px;
}
.pricing-table .custom_clicked .price .price-figure {
    height: 90px;
}
.pricing-table .custom_clicked .price .price-number {
    color: #fff;
}
.pricing-table .custom_clicked h3,
.pace-progress {
    background: #008a8a;
}
.pricing-table .custom_clicked .price {
    background: #00acac;
}

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
	            <form:form id="projectForm" name="projectForm" modelAttribute="spe" method="POST" action="${rootUrl}app/weekly/daily/project">
	                <!-- BEGIN checkout-header -->
	                <div class="checkout-header">
	                    <!-- BEGIN row -->
	                    <div class="row">
	                         <!-- BEGIN col-3 -->
	                        <div class="col-md-3 col-sm-3">
	                            <div class="step active">
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
	                    <!-- 表格样式
	                    <div class="table-responsive">
	                        <table id="data-table" class="table table-cart">
	                            <thead>
	                                <tr>
	                                    <th>项目名称</th>
	                                    <th class="text-center">项目简介</th>
	                                    <th class="text-center">地区</th>
	                                    <th class="text-center">创建者</th>
	                                    <th class="text-center">操作</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                            </tbody>
	                        </table>
	                        <div id="page_pager">
						    </div>
	                    </div> -->
						
						<!-- ul样式 -->
						<div id="data_result" class="container" style="width:auto;margin-top: 50px;">
                		</div>
						<div id="page_pager" class="text-center">
						</div>
						
	                </div>
	                <div style="text-align:right;margin-right: 30px;">
                    	<a id="projectAdd" href="${rootUrl }app/weekly/daily/projectAdd"><h6>需要增加新的项目</h6></a>
                    </div>
	                <!-- END checkout-body -->
	                <!-- BEGIN checkout-footer -->
	                <div class="checkout-footer">
	                    <button id="next" class="btn btn-inverse btn-lg p-l-30 p-r-30 m-l-10">Next</button>
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