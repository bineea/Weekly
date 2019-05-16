;(function($,window,document,undefined){
    $.extend({
    	Constans:{
			RESPONSE_HEADER_NOTE:"header_note",
			RESPONSE_HEADER_ERROR:"header_error",
			RESPONSE_HEADER_JUMP:"header_jump",
			BIND_PAGE_CENTER:"bind_center",
			BIND_PAGE_RIGHT:"bind_right",
			HANDLE_RESULT_UL:"handle_ul",
			HANDLE_RESULT_TABLE:"handle_table"
		},
		
		/**************************************************************************
		 * 
		 * 居中---处理分页
		 * 
		 *************************************************************************/ 
		_bindPagerCenter: function($searchPager) {
			// 添加分页样式
			var $this = this,
				currentPage = window.parseInt($searchPager.find("#page_current_page").text(),10),
				totalPages = window.parseInt($searchPager.find("#page_total_page").text(),10),
				totalElements = window.parseInt($searchPager.find("#page_total_element").text(),10);
			//当没有查询到数据或返回数据异常的时候，页面显示0条记录，1页，当前页在第1页。
			if(isNaN(totalPages) || totalPages < 2 ||isNaN(currentPage) || currentPage < 0 ||
					isNaN(totalElements) || totalElements <= 0){
				$("#page_pager", this.currentTarget).hide();
			}else{
				$("#page_pager", this.currentTarget).each(function() {
					var $ul = $("<ul class='pagination m-t-0 m-b-0'>");
					var $li_prev = $("<li ><a href='javascript:;' value='"+(currentPage-1)+"'>Prev</a></li>").appendTo($ul);
					if(currentPage === 0){
						$li_prev.addClass("disabled").find("a");
					}
					var startPoint = 0,endPoint = 4;
					if(currentPage > 2){
						startPoint = currentPage -2;
						endPoint = currentPage +2;
					}
					if(endPoint >= totalPages){
						startPoint = totalPages -4;
						endPoint = totalPages -1;
					}
					if(startPoint < 0){
						startPoint = 0;
					}
					for(var point = startPoint;point<=endPoint;point++){
						var $li_point = $("<li ><a href='javascript:;' value='"+point+"'>"+(point+1)+"</a></li>").appendTo($ul);
						if(point === currentPage){
							$li_point.addClass("active").find("a");
						}
					}
					var $li_next = $("<li ><a href='javascript:;' value='"+(currentPage + 1)+"'>Next</a></li>").appendTo($ul); 
					if(currentPage === totalPages){
						$li_next.addClass("disabled").find("a");
					}
					$(this).html($ul);
				}).show();
			}
		},
		
		/**************************************************************************
		 * 
		 * 靠右---处理分页
		 * 
		 *************************************************************************/ 
		_bindPagerRight: function($searchPager) {
			// 添加分页样式
			var $this = this,
				currentPage = window.parseInt($searchPager.find("#page_current_page").text(),10),
				totalPages = window.parseInt($searchPager.find("#page_total_page").text(),10),
				totalElements = window.parseInt($searchPager.find("#page_total_element").text(),10);
			//当没有查询到数据或返回数据异常的时候，页面显示0条记录，1页，当前页在第1页。
			if(isNaN(totalPages) || totalPages < 2 ||isNaN(currentPage) || currentPage < 0 ||
					isNaN(totalElements) || totalElements <= 0){
				$("#page_pager", this.currentTarget).hide();
			}else{
				$("#page_pager", this.currentTarget)
					.each(function() {
					var $ul = $("<ul class='pagination m-t-0 m-b-10 pull-right'>");
					var $li_prev = $("<li ><a href='javascript:;' class='pager_item' value='"+(currentPage-1)+"'>«</a></li>").appendTo($ul);
					if(currentPage === 0){
						$li_prev.addClass("disabled").find("a").removeClass("pager_item");
					}
					var startPoint = 0,endPoint = 4;
					if(currentPage > 2){
						startPoint = currentPage -2;
						endPoint = currentPage +2;
					}
					if(endPoint >= totalPages){
						startPoint = totalPages -4;
						endPoint = totalPages -1;
					}
					if(startPoint < 0){
						startPoint = 0;
					}
					for(var point = startPoint;point<=endPoint;point++){
						var $li_point = $("<li ><a href='javascript:;' class='pager_item' value='"+point+"'>"+(point+1)+"</a></li>").appendTo($ul);
						if(point === currentPage){
							$li_point.addClass("active").find("a").removeClass("pager_item");
						}
					}
					var $li_next = $("<li ><a href='javascript:;' class='pager_item' value='"+(currentPage + 1)+"'>»</a></li>").appendTo($ul); 
					if(currentPage === totalPages){
						$li_next.addClass("disabled").find("a").removeClass("pager_item");
					}
					$(this).html($ul);
				}).show();
			}
		},
		
		/**************************************************************************
		 * 
		 * 处理分页跳转
		 * 
		 *************************************************************************/
		_handlePageClick: function(pageclickednumber) {
			// 分页标签点击处理
			var $pageNo = $("input[name='pageNo']", this.currentTarget);
			if ($pageNo.attr("name")) {
				// 如果pageNo存在，则直接修改其值
				$pageNo.val(pageclickednumber);
			} else {
				// 否则，创建pageNo，并追加到form标签下。
				$pageNo = $(
				"<input type='text' id='pageNo' name='pageNo'/>")
				.val(pageclickednumber)
				.appendTo($("#pageQueryForm"), this.currentTarget);
			}
			// 提交表单
			$("#pageQueryForm", this.currentTarget).trigger("submit");
		},
		
		/**************************************************************************
		 * 
		 * UL---处理列表
		 * 
		 *************************************************************************/
		_handleSearchReasult2Ul: function($dataResultTarget, $responseText) {
			if($dataResultTarget === null || $dataResultTarget === "") {
				$dataResultTarget = $("#data_result", this.currentTarget);
			}
			var hasText = $responseText.find("#query_result ul").children().size() > 0;
			if(hasText) {
				var $ubody=$responseText.find("#query_result");
				$dataResultTarget.empty().append($ubody.html());
			}
			else {
				$dataResultTarget.empty()
				.append("<div class='data-not-found'>没有检索到符合条件的数据！</div>");
			}
			this._bindULDataClick();
		},
		
		/**************************************************************************
		 * 
		 * 表格---处理列表
		 * 
		 *************************************************************************/
		_handleSearchReasult2Table: function($responseText) {
			var hasText = $responseText.find("#page_query tbody").children().size() > 0;
			if(hasText) {
				var $tbody=$responseText.find("#page_query tbody");
				$('#data-table').find("> tbody").empty().append($tbody.html());
			}
			else {
				$('#data-table').find("> tbody").empty()
				.append("<tr><td colspan='15' ><div class='taiji_not_found'>没有检索到符合条件的数据！</div></td></tr>");
			}
			this._bindTableDataClick();
		},
		
		/**************************************************************************
		 * 
		 * 表格点击事件
		 * 
		 *************************************************************************/
		_bindTableDataClick: function() {
			$('#data-table',this.currentTarget)
					.find(" > tbody > tr")
					.off("click")
					.on("click",function(event) {
								$(this).addClass("custom_clicked")
										.siblings().removeClass("custom_clicked");
								$(this).find("input[type='radio']").prop("checked",true);
							});
		},
		
		/**************************************************************************
		 * 
		 * UL点击事件
		 * 
		 *************************************************************************/
		_bindULDataClick: function() {
			$('#data_result',this.currentTarget)
					.find(" > ul > li")
					.off("click")
					.on("click",function(event) {
								$(this).addClass("custom_clicked")
										.siblings().removeClass("custom_clicked");
								$(this).find("input[type='radio']").prop("checked",true);
							});
		},
		
		/**************************************************************************
		 * 
		 * 显示loading
		 * 
		 *************************************************************************/
		showLoading: function() {
			$loadingDiv = $("#page_loader");
			if(!$loadingDiv.length) {
				var html = '<div id="page_loader" class="fade in" style="filter:alpha(opacity=60);opacity:0.6;">'
						 +		'<span class="spinner"></span>'
						 + '</div>';
				$loadingDiv=$(html).prependTo(document.body);
			}
		},
		
		/**************************************************************************
		 * 
		 * 隐藏loading
		 * 
		 *************************************************************************/
		hideLoading: function() {
			$("#page_loader").addClass("hide");
		},
		
		/**************************************************************************
		 * 
		 * 显示警告信息
		 * 
		 *************************************************************************/
		showWarnMsg: function(message) {
			console.log(message);
			alert(message);
		},
		
		/**************************************************************************
		 * 
		 * 显示信息
		 * isSuccess:true/false
		 * type:blue/green/red/orange/purple/dark
		 *************************************************************************/
		showMsg: function(isSuccess,message) {
			if(message == null || message == undefined) {
				message = '';
			}
			console.log(isSuccess+','+message);
			if(isSuccess) {
				$.dialog({
					theme: 'light',
					title: '操作成功！',
					content: message,
					backgroundDismiss: true,
					type: 'dark',
				});
			} else {
				$.dialog({
					theme: 'light',
					title: '操作失败！',
					content: message,
					backgroundDismiss: true,
					type: 'red',
				});
			}
		},
		
		/**************************************************************************
		 * 
		 * 查询列表信息
		 * 
		 *************************************************************************/
	    listAjax : function(url, $dataResultTarget) {
	    	$.ajax({
	    		url:url,
	    		type:'post',
	    		success:function(data, textStatus, jqXHR) {
					var $responseText = $(data);
		        	//显示列表
		        	$._handleSearchReasult($dataResultTarget,$responseText);
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
					$.showWarnMsg("系统异常，请稍后重试！");
				}
	    	});
	    },	
	});
    
    //分页查询
    $.fn.pageManage = function(options) {
    	var defaults = {
    		autoSearch:true,
    		bindPage:$.Constans.BIND_PAGE_CENTER,
    		handleResult:$.Constans.HANDLE_RESULT_UL
    	};
    	var ops = $.extend({}, defaults, options);
    	if(ops.autoSearch) {
    		this.ajaxSubmit({
    			type: "post", //提交方式 
    	        success: function (responseText, status, xhr) { //提交成功的回调函数
    	        	var $responseText = $(responseText);
    	        	if(ops.bindPage === $.Constans.BIND_PAGE_CENTER) {
    	        		//处理分页
    	        		$._bindPagerCenter($responseText.find("#query_pager"));
    	        	}
    	        	if(ops.bindPage === $.Constans.BIND_PAGE_RIGHT) {
    	        		//处理分页
    	        		$._bindPagerRight($responseText.find("#page_query_pager"));
    	        	}
    	        	if(ops.handleResult === $.Constans.HANDLE_RESULT_UL) {
    	        		//显示列表
    	        		$._handleSearchReasult2Ul(null,$responseText);
    	        	}
    	        	if(ops.handleResult === $.Constans.HANDLE_RESULT_TABLE) {
    	        		//显示列表
    	        		$._handleSearchReasult2Table($responseText);
    	        	}
    	        },
    	        error: function (xhr, status, error) {
    	        	$.showWarnMsg("系统异常，请稍后重试！");
    	        }
    		});
    	}
		this.ajaxForm({
			type: "post", //提交方式 
	        success: function (responseText, status, xhr) { //提交成功的回调函数
	        	var $responseText = $(responseText);
	        	if(ops.bindPage === $.Constans.BIND_PAGE_CENTER) {
	        		//处理分页
	        		$._bindPagerCenter($responseText.find("#query_pager"));
	        	}
	        	if(ops.bindPage === $.Constans.BIND_PAGE_RIGHT) {
	        		//处理分页
	        		$._bindPagerRight($responseText.find("#page_query_pager"));
	        	}
	        	if(ops.handleResult === $.Constans.HANDLE_RESULT_UL) {
	        		//显示列表
	        		$._handleSearchReasult2Ul(null,$responseText);
	        	}
	        	if(ops.handleResult === $.Constans.HANDLE_RESULT_TABLE) {
	        		//显示列表
	        		$._handleSearchReasult2Table($responseText);
	        	}
	        },
	        error: function (xhr, status, error) {
	        	$.showWarnMsg("系统异常，请稍后重试！");
	        }
		});
		return false;
    };
    
})(jQuery,window,document);


//js插件
//

//匿名函数
//(function($){})(jQuery)