<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>

<script>
	/* $(document).ready(function() {
		$.listAjax('${rootUrl}app/blog/category', $('#category_result', this.currentTarget));
		$.listAjax('${rootUrl}app/blog/recent', $('#recent_result', this.currentTarget));
	}); */
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
		<h4 class="section-title"><span>Recent Post</span></h4>
		<div id = "recent_result">
		</div>
	</div>
	<!-- end section-container -->
	<!-- begin section-container -->
	<div class="section-container">
		<h4 class="section-title"><span>Follow Us</span></h4>
		<ul class="sidebar-social-list">
			<li><a href="#"><i class="fa fa-facebook"></i></a></li>
			<li><a href="#"><i class="fa fa-twitter"></i></a></li>
			<li><a href="#"><i class="fa fa-google-plus"></i></a></li>
			<li><a href="#"><i class="fa fa-instagram"></i></a></li>
		</ul>
	</div>
	<!-- end section-container -->
<!-- end col-3 -->