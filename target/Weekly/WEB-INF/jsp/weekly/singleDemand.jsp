<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/weekly/common/tag.jsp"%>

<li>
    <!-- begin post-left-info -->
    <div class="post-left-info">
        <div class="post-date">
            <span class="day"><javatime:format value="${data.content.createTime}" pattern="dd"  /></span>
            <span class="month"><javatime:format value="${data.content.createTime}" pattern="yyyy.MM"  /></span>
        </div>
        <div class="post-likes">
            <i class="fa fa-heart-o"></i>
            <span class="number">${fn:escapeXml(data.content.voteUp) }</span>
        </div>
    </div>
    <!-- end post-left-info -->
    <!-- begin post-content -->
    <div class="post-content">
   		<c:if test="${data.content.cover ne null } && ${data.content.cover ne ''  }">
	       	<!-- begin post-image -->
	        <div class="post-image">
	            <a href="###"><img src="${rootUrl }app/blog/content/showCover/${data.content.id}" alt="cover" /></a>
	        </div>
	        <!-- end post-image -->
        </c:if>
        <!-- begin post-info -->
        <div class="post-info">
        	<blockquote>
	            <h4 class="post-title">
	                <a href="###">${fn:escapeXml(data.content.title) }</a>
	            </h4>
            </blockquote>
            <div class="post-by">
                Posted By <a href="###">BINEEA</a> <span class="divider">|</span> 
                <a href="#">Sports</a>, <a href="#">Mountain</a>, <a href="#">Bike</a> 
                <span class="divider">|</span>	${fn:escapeXml(data.content.commentCount) }  Comments
            </div>
            <div class="post-desc">
                ${fn:escapeXml(data.content.summany) }
            </div>
        </div>
        <!-- end post-info -->
        <!-- begin read-btn-container -->
        <div class="read-btn-container">
            <a href="${rootUrl }app/blog/content/${data.content.id}">Read More <i class="fa fa-angle-double-right"></i></a>
        </div>
        <!-- end read-btn-container -->
    </div>
    <!-- end post-content -->
</li>