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
	                <a href="###">${fn:escapeXml(data.summany) }</a>
	            </h4>
            </blockquote>
            <div class="post-by">
                Posted By <a href="###">BINEEA</a> <span class="divider">|</span> 
                <a href="#">Sports</a>, <a href="#">Mountain</a>, <a href="#">Bike</a> 
                <span class="divider">|</span>
            </div>
            <div class="post-desc">
                ${fn:escapeXml(data.summany) }
            </div>
        </div>
        <!-- end post-info -->
        <!-- begin read-btn-container -->
        <div class="read-btn-container">
            <a href="${rootUrl }app/blog/content/${data.id}">Read More <i class="fa fa-angle-double-right"></i></a>
        </div>
        <!-- end read-btn-container -->
    </div>
    <!-- end post-content -->
</li>