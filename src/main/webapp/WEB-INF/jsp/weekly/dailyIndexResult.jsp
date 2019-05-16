<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>

<div>
<div id="query_result">
	<!-- begin post-list -->
	<ul class="post-list">
	    <c:forEach items="${queryResult}" var="data"  varStatus="voStatus">
			<%@ include file="singleIndex.jsp"%>
		</c:forEach>
	</ul>
	<!-- end post-list -->
</div>

<div id="query_pager">
    <label id="page_current_page">${currentPage}</label>
    <label id="page_total_page">${totalPages}</label>
    <label id="page_total_element">${totalElements}</label>
</div>
</div>