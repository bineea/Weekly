<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>
<link href="${rootUrl}assets/plugins/ionicons/css/ionicons.min.css" rel="stylesheet" />
<script>
	$(document).ready(function() {
		/*$.listAjax('${rootUrl}app/blog/category', $('#category_result', this.currentTarget));*/
	});
</script>

<!-- begin col-3 -->
	<!-- begin section-container -->
	<div class="section-container">
		<h4 class="section-title"><span>Categories</span></h4>
		<div id = "category_result">
		</div>
	</div>
	<!-- end section-container -->
	<!-- begin section-container -->
	<div class="section-container">
		<h4 class="section-title"><span>Functions</span></h4>
		<ul class="sidebar-social-list">
			<li><a href="#"><i class="fa fa-envelope" style="margin-right: 10px;"></i>Send Email</a></li>
		</ul>
	</div>
	<!-- end section-container -->
<!-- end col-3 -->