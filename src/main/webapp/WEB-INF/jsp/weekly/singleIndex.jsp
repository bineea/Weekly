<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/weekly/common/tag.jsp"%>

<li>
    <!-- begin post-left-info -->
    <div class="post-left-info">
        <div class="post-date">
            <span class="day"><javatime:format value="${data.createTime}" pattern="dd"  /></span>
            <span class="month"><javatime:format value="${data.createTime}" pattern="yyyy.MM"  /></span>
        </div>
    </div>
    <!-- end post-left-info -->
    <!-- begin post-content -->
    <div class="post-content">
        <!-- begin post-info -->
        <div class="post-info">
        	<blockquote>
	            <h4 class="post-title">
	            	${fn:escapeXml(data.operateContent) }
	            </h4>
            </blockquote>
            <div class="post-by">
                Posted By ${loginUser.name } 
                <span class="divider">|</span> 
                            状态：${fn:escapeXml(data.handleStatus.value) }
                <span class="divider">|</span> 
                            项目：${fn:escapeXml(data.demand.project.name) }
            </div>
            <div class="post-desc">
                 <h5>${fn:escapeXml(data.demand.title) }</h5>
                 <p>${fn:escapeXml(data.demand.summary) }</p>
            </div>
        </div>
        <!-- end post-info -->
        <!-- begin read-btn-container -->
        <div class="read-btn-container">
            <a class="update_op" href="${rootUrl }app/weekly/daily/edit/${data.id}">Edit&nbsp;<i class="fa fa-angle-double-right"></i></a>
            <a class="delete_op" href="${rootUrl }app/weekly/daily/del/${data.id}">Delete&nbsp;<i class="fa fa-angle-double-right"></i></a>
        </div>
        <!-- end read-btn-container -->
    </div>
    <!-- end post-content -->
</li>