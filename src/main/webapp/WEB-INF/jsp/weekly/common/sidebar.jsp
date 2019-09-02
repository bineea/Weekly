<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/weekly/common/tag.jsp"%>
<link href="${rootUrl}assets/plugins/ionicons/css/ionicons.min.css" rel="stylesheet" />
<script>
	$(document).ready(function() {

		$.My.listSearchAjax("${rootUrl }app/weekly/category/demand", "#category_demand_list", $.My.Constans.HANDLE_RESULT_UL);
		$.My.listSearchAjax("${rootUrl }app/weekly/category/user", "#category_user_list", $.My.Constans.HANDLE_RESULT_UL)

	});
</script>

<!-- begin col-3 -->
	<!-- begin section-container -->
	<div class="section-container">
		<h4 class="section-title"><span>Categories</span></h4>
		<div id="category_demand_list">
		</div>
	</div>
	<!-- end section-container -->

	<!-- begin section-container -->
	<div class="section-container">
		<h4 class="section-title"><span>Users</span></h4>
		<div id = "category_user_list">
		</div>
	</div>
	<!-- end section-container -->
<!-- end col-3 -->