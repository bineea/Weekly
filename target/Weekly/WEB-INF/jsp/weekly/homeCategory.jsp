<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>My blog</title>
<%@ include file="/WEB-INF/jsp/weekly/common/include.jsp"%>

<script>
	$(document).ready(function() {
		$("#contentForm").pageAjaxSubmit({});
		$("#contentForm").pageAjaxForm({});
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
                	<h4 class="section-title m-b-20"><span>CATEGORY：${category.name }</span></h4>
                	<!-- 若翻页时，删除输入框内容，则为全文搜索。。。 -->
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
						<form:form id="contentForm" name="contentForm" method="post" action="${rootUrl}app/blog/contentCategory">
							<input name="categoryId" value="${category.id }" hidden="hidden"/>
							<div class="input-group sidebar-search">
								<input id="keywords" name="keywords" type="text" class="form-control" placeholder="Search blogs..." />
								<span class="input-group-btn">
									<button class="btn btn-inverse" type="submit"><i class="fa fa-search"></i></button>
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