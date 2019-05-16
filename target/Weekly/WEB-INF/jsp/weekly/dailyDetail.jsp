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
		ajaxAllComments();
		$("#commentForm").ajaxForm({
			type: "post", //提交方式 
	        success: function (responseText, status, xhr) { //提交成功的回调函数
	        	if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_ERROR)){
					$.showWarnMsg(responseText.msg);
				}else if(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)){
					$.showMsg(true,new Base64().decode(xhr.getResponseHeader($.Constans.RESPONSE_HEADER_NOTE)));
					ajaxAllComments();
				}
	        },
	        error: function (xhr, status, error) {
	        	$.showWarnMsg("系统异常，请稍后重试！");
	        }
		});
	});
	
	function ajaxAllComments() {
		$.ajax({
			url: '${rootUrl}app/blog/comment/all',
			type: 'POST',
			data: {
				'contentId' : $('#contentId').val()
			},
			success: function(data, textStatus, jqXHR) {
				var allComments = eval(data);
				if(allComments.length === 0) {
					$('#allComments').append("暂无评论");
				} else {
					$('#allComments').append('<ul class="comment-list"></ul>');
					$('#allComments ul').append(toAllCommentsHtml(allComments));
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				$.showWarnMsg("系统异常，请稍后重试！");
			}
		});
	}
	
	function toAllCommentsHtml(allComments) {
		var allCommentHtml = '';
		for(var i=0; i<allComments.length; i++) {
			allComments[i].hasParentComment && !allComments[i].parentIsReplay ? allCommentHtml += '<ul class="comment-list">' : allCommentHtml;
			allCommentHtml += 	'<li>';
			allCommentHtml += 		'<div class="comment-avatar">';
			allCommentHtml +=			'<i class="fa fa-user"></i>';
			allCommentHtml +=		'</div>';
			allCommentHtml +=		'<div class="comment-container">';
			allCommentHtml +=			'<div class="comment-author">';
			allCommentHtml +=				allComments[i].comment.author;
			allCommentHtml +=				'<span class="comment-date">';
			allCommentHtml +=					' on <span class="underline">' + allComments[i].comment.createTime + '</span>';
			allCommentHtml +=				'</span>';
			allCommentHtml +=			'</div>';
			allCommentHtml +=			'<div class="comment-content">';
			allCommentHtml +=				allComments[i].text;
			allCommentHtml +=			'</div>';
			allCommentHtml +=			'<div class="comment-btn pull-left">';
			allCommentHtml +=				'<a href="#"><i class="fa fa-reply"></i> Reply</a>';
			allCommentHtml +=			'</div>';
			allCommentHtml +=			'<div class="comment-rating">';
			allCommentHtml +=				'Like or Dislike: ';
			allCommentHtml +=				'<a href="#" class="m-l-10 text-inverse"><i class="fa fa-thumbs-up text-success"></i> 154</a>';
			allCommentHtml +=				'<a href="#" class="m-l-10 text-inverse"><i class="fa fa-thumbs-down text-danger"></i> 112</a>';
			allCommentHtml +=			'</div>';
			allComments[i].hasReplies ? allCommentHtml += toAllCommentsHtml(allComments[i].replyComment) : allCommentHtml;
			allCommentHtml +=		'</div>';
			allCommentHtml += 	'</li>';
			allComments[i].hasParentComment && !allComments[i].parentIsReplay ? allCommentHtml += '</ul>' : allCommentHtml;
		}
		return allCommentHtml;
	}
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
                	<!-- begin post-detail -->
                    <div class="post-detail section-container">
                        <input id="contentId" type="text" value="${content.id }" hidden="hidden"/>
                        <ul class="breadcrumb">
                            <li><a href="#">Home</a></li>
                            <li class="active">${content.title }</li>
                        </ul>
                        <h4 class="post-title">
                            <a href="#">${content.title }</a>
                        </h4>
                        <div class="post-by">
                            Posted By <a href="#">admin</a> <span class="divider">|</span> <javatime:format value="${content.createTime}" pattern="yyyy.MM.dd"  /> <span class="divider">|</span>  <span id="commentCount"></span> Comments
                        </div>
                        <c:if test="${content.cover ne null } && ${content.cover ne ''  }">
	                        <!-- begin post-image -->
	                        <div class="post-image">
	                            <img src="${rootUrl }app/" alt="cover" />
	                        </div>
	                        <!-- end post-image -->
                        </c:if>
                        <!-- begin post-desc -->
                        <div class="post-desc">
                        	${content.text }
                        </div>
                        <!-- end post-desc -->
                    </div>
                    <!-- end post-detail -->
                    
                    <!-- begin section-container -->
                    <div id="allComments" class="section-container">
                        <h4 class="section-title"><span>All Comments</span></h4>
                    </div>
                    <!-- end section-container -->
                    
                    <!-- begin section-container -->
                    <div id="addComment" class="section-container">
                        <h4 class="section-title m-b-20"><span>Add a Comment</span></h4>
                        <div class="alert alert-warning f-s-12">
                            	请开始你的表演
                        </div>
                        <form:form id="commentForm" modelAttribute="comment" cssClass="form-horizontal" action="${rootUrl }app/blog/comment/add" method="POST">
                        	<input type="text" name="parentCommentId" style="display: none;"/>
                        	<input type="text" name="commentType" style="display: none;"/>
                        	<input type="text" name="contentId" value="${content.id }" style="display: none;"/>
                            <div class="form-group">
                                <label class="control-label f-s-12 col-md-2">Your Name <span class="text-danger">*</span></label>
                                <div class="col-md-10">
                                    <form:input path="author" cssClass="form-control" placeholder="你的名字哦" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label f-s-12 col-md-2">Your Email <span class="text-danger">*</span></label>
                                <div class="col-md-10">
                                    <form:input path="email" cssClass="form-control" placeholder="邮箱地址哦" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label f-s-12 col-md-2">Comment <span class="text-danger">*</span></label>
                                <div class="col-md-10">
                                    <form:textarea path="text" class="form-control" rows="10" placeholder="留下来~~~~" ></form:textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-10 col-md-offset-2">
                                    <div class="checkbox f-s-12">
                                        <label>
                                            <input type="checkbox" name="notify" value="0" />
                                            Notify me of follow-up comments by email.
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-10 col-md-offset-2">
                                    <button type="submit" class="btn btn-inverse btn-lg">Submit Comment</button>
                                </div>
                            </div>
                        </form:form> 
                    </div>
                    <!-- end section-container -->
                </div>
                <!-- end col-9 -->
                <!-- begin col-3 -->
				<div class="col-md-3">
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