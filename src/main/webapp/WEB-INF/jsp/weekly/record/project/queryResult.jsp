<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/weekly/common/tag.jsp"%>
<div>
<%-- 表格样式
<div id="page_query">
	<table>
		<tbody>
			<c:forEach items="${queryResult}" var="data"  varStatus="voStatus">
				<%@ include file="row.jsp"%>
			</c:forEach>
		</tbody>
	</table>
</div>
<div id="page_query_pager">
    <label id="page_current_page">${currentPage}</label>
    <label id="page_total_page">${totalPages}</label>
    <label id="page_total_element">${totalElements}</label>
</div> --%>

<div id="query_result">
	<!-- begin post-list -->
	<ul class="pricing-table col-3">
	    <c:forEach items="${queryResult}" var="data"  varStatus="voStatus">
			<%@ include file="row.jsp"%>
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