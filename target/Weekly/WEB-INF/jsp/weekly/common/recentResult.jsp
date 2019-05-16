<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>

<div>
<div id="query_result">
	<!-- begin sidebar-recent-list -->
	<ul class="sidebar-recent-post">
		<c:forEach items="${queryResult}" var="data"  varStatus="voStatus">
			<li>
				<div class="info">
					<h4 class="title"><a href="${rootUrl }app/blog/content/${data.id}">${data.title }</a></h4>
					<div class="date"><javatime:format value="${data.createTime}" pattern="yyyy.MM.dd"  /></div>
				</div>
			</li>
		</c:forEach>
	</ul>
	<!-- end sidebar-recent-list -->
</div>
</div>