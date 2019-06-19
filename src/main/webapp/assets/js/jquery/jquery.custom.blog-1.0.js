;(function($,window,document,undefined){
	
	
	/**************************************************************************
	 * 
	 * jQuery.extend($.fn,{}) 等效于$.fn.extend()
	 * $.fn.extend(obj)表示给$的原型对象添加方法；而$.extend(obj)表示直接给$对象添加方法。
	 * 
	 *************************************************************************/ 
	$.extend($.fn, {
		myInit : function() {
			// 如果没有选择任何节点，直接返回
			if (!this.length) {
				if(window.console && window.console.warn){
					window.console.warn("没有选择任何节点，直接返回");
				}
				return;
			}
			var arg1=arguments[0] || {};
			// 如果初始化过了，直接返回缓存对象
			var aMy = $(this).data('aMy');
			if (!aMy) {
				// 初始化
				aMy = new $.My(arg1||{}, this);
				// 缓存到当前节点中
				$(this).data('aMy', aMy);
			}
			if(typeof arg1 =="string"){
				aMy[arg1](arguments[1],arguments[2]||{});
			}
			return this;
		}
	});
	
	$.My = function(opts, targetDiv){
		// 将默认设置与用户设置进行合并
		this.settings = $.extend(true, {}, $.My.defaults, opts);
		// 缓存当前节点
		this.currentTarget = $(targetDiv);
		// 缓存base64,用于前后台直接传送数据压缩
		this.base64 = new Base64();
		this.isRunning = false;
		// 缓存base64的解码函数
		this.decode = this.base64.decode;
		// 调用私有的_init(),执行初始化
		this._init();
	};
	
	$.extend($.My, {
		prototype : {
			/******************************************************************
			 * 
			 * 初始化函数
			 *
			 * 完成所有的初始化操作，包括各种事件的绑定等。
			 * 私有的，请勿在外部直接调用
			 * 
			 *****************************************************************/
			_init : function() {
				this._initAll();
				// 绑定事件处理函数
				this._bindAll();
			},
			
			/******************************************************************
			 *
			 * 初始化插件函数
			 * 
			 *****************************************************************/
			_initAll:function(){
				var $this = this;
				$.each($.My.inits,function(i,f){
					f.call($this);
				});
			},
			/******************************************************************
			 *
			 * 事件绑定函数
			 *
			 * 将所有的事件绑定到target上，减少绑定事件的麻烦
			 * TODO 未来将支持将事件绑定到其他的节点上
			 * 
			 *****************************************************************/
			_bindAll:function(){
				var $self = (this),
					events = {},
					classNames = {},
					$target = $(this.currentTarget);
				// 将插件需要绑定的事件处理函数提取出来
				$.each($.My.events,function(event,coll){
					if(!events[event]){
						events[event] = {};
					}
					$.each(coll,function(name,other){
						$.each(other,function(cn,handler){
							var className = $self.settings[name][cn];
							events[event][className] = handler;
						});
					});
				});
				// 绑定事件处理函数
				//TODO 需要进行重构，以便兼容可以绑定到其他
				/*
				  events={click:{".taiji_async":"_handleAsyncClick",
								".taiji_collapse","_handleCollapseClick"
								},
						  change:{...}		
						 }
				*/
				$.each(events,function(aEvent,cllol){
					$target.off(aEvent+".myInit").on(aEvent+".myInit",
						function(event){
						var $target = (
								$(event.target).is("a")||
								$(event.target).is("button")||
								$(event.target).is(":checkbox")||
								$(event.target).is("th"))? $(event.target):
										($(event.target).parents().is("a") && 
										$(event.target).parents("a"))?
												$(event.target).parents("a") : 
													$self._emptyTarget;
						$.each(cllol,function(className,handler){
							if($target.is(className)){
								$self[handler]($target);
								$.My.preventDefault(event);
							}
						});
					});
				});
				// 为当前实例扩展方法
				$.each($.My.customEvents,function(name,handler){
					$target.off(name).on(name,function(event,form,options){
						$self[handler](form,options);
					});
				});
				
			},
			/******************************************************************
			 * 
			 * 空的target对象，is返回false,
			 * 为bindAll的辅助方法
			 * 
			 *****************************************************************/
			_emptyTarget:{
				is:function(className){
					return false;
				}
			},
		
			/******************************************************************
			 * 
			 * 出错处理函数
			 * @param 
			 *		{String} statusCode 出错状态码
			 * @param
			 *		{Object} obj 出错操作是在那个节点被调用的
			 * 
			 *****************************************************************/
			_handleOperateError : function(statusCode, obj) {
				this.isRunning = false;
				$.My.hideLoading();
				if(statusCode===0||statusCode===12029){
					$.My.showMsg(false, $.My.Messages.ERR_CONNECT);
				}else{
					$.My.showMsg(false, $.My.Messages.ERR_RESPONSE + statusCode);
				}
				//$(obj).prop("disabled", false);
			},
			
			/******************************************************************
			 * 
			 * 日志输出函数
			 * 
			 * @param
			 *		{Object} msg 待输出的信息
			 * 
			 *****************************************************************/
			log : function(msg) {
				if(this.settings.debug === true && window.console &&
					window.console.log){
					window.console.log(msg);
				}
			},
			
			/******************************************************************
			 * 
			 * 警告日志输出函数
			 * 
			 * @param
			 *		{Object} msg 待输出的警告信息
			 *
			 *****************************************************************/
			warn : function(msg) {
				if(this.settings.debug === true && window.console &&
						window.console.warn){
					window.console.warn(msg);
				}
			},
			
		}
	});
	
	$.extend($.My, {
		
		extendMethod:function(aExtendMethod){
			//TODO 添加更多的检查和保护，别让插件替换掉已有的设置及功能。
			$.My.defaults[aExtendMethod.name] = aExtendMethod.config;
			$.each(aExtendMethod.event,function(event,coll){
				if(!$.My.events[event]) {
					$.My.events[event] = {};
				}
				if(!$.My.events[event][aExtendMethod.name]){
					$.My.events[event][aExtendMethod.name] = {};
				}
				if(coll){
					$.each(coll,function(className,handler){
						$.My.log("事件："+event+",插件："+
							aExtendMethod.name+"，class:"+
							className+",处理函数:"+handler);
						$.My.events[event][aExtendMethod.name][className] = handler;
					});
				}
			});
			if(aExtendMethod.customEvent){
				$.each(aExtendMethod.customEvent,function(i,event){
					$.My.customEvents[i] = event;
				});
			}
			
			$.each(aExtendMethod.eventHandler,function(i,handler) {
				$.My.prototype[i] = handler;
			});
			if(aExtendMethod.init){
				$.My.inits.push(aExtendMethod.init);
			}
		},
		
		/**********************************************************************
		 * 
		 * 插件的初始化方法，
		 * 
		 **********************************************************************/
		inits:[],
		/**********************************************************************
		 * 
		 * 插件添加的标准事件处理程序，
		 * 
		 **********************************************************************/
		events:{},
	
		/**********************************************************************
		 * 
		 * 插件添加的自定义事件处理程序，
		 * 
		 **********************************************************************/
		customEvents:{},
		
		/**********************************************************************
		 * 
		 * 目前使用的比较多的常量，
		 * 主要是与服务器交互返回的头部信息
		 * 
		 *********************************************************************/
		Constans:{
			RESPONSE_HEADER_NOTE:"header_note",
			RESPONSE_HEADER_ERROR:"header_error",
			RESPONSE_HEADER_CVE:"header_cve",
			RESPONSE_HEADER_JUMP:"header_jump",
			BIND_PAGE_CENTER:"bind_center",
			BIND_PAGE_RIGHT:"bind_right",
			HANDLE_RESULT_UL:"handle_ul",
			HANDLE_RESULT_TABLE:"handle_table",
			HANDLE_RESULT_ADD:"handle_add",
			HANDLE_RESULT_UPD:"handle_upd",
			HANDLE_RESULT_DEL:"handle_del"
		},
		/**********************************************************************
		 * 
		 * 取消默认事件
		 * 
		 *********************************************************************/
		preventDefault : function(event){
			if(event.preventDefault){
				event.preventDefault();
			}else{
				event.returnValue=false;
			}
		},
		
		/**********************************************************************
		 * 
		 * 提示信息，以后应该可以被覆盖，以支持国际化
		 * 
		 *********************************************************************/
		Messages : {
			//错误提示
			ERR_CVE : "校验失败，请看相关字段提示!",
			ERR_RESPONSE:"服务器返回了非预期的值，请联系技术人员，代码:",
			ERR_URL:"没有找到URL！",
			ERR_CONNECT:"您当前的网络连接异常！",
			//警告提示
			WAR_NOTNODE:"未选中任何节点！",
			WAR_OPERATE:"前一次操作尚未完成，请稍候！",
			//普通提示
			MSG_OPERATE:"操作进行中，请稍候！"
		},
		
		/**********************************************************************
		 * 
		 * 获取链接的URL
		 * 
		 * @param 
		 *		{Object} obj 待获取链接的节点
		 * 
		 *********************************************************************/
		getUrl : function(obj) {
			var $obj = $(obj);
			return $obj && $obj.is("a") && $obj.attr("href");
		},
		/**********************************************************************
		 * 
		 * 获得配置的 confirm_message
		 * 支持 meta方式 和 属性方式两种。
		 * 
		 *********************************************************************/
		getConfirmMessage : function(obj) {
			var $obj = $(obj);
			return $.metadata && $obj.metadata().confirm_message ? $obj
					.metadata().confirm_message : $obj.attr("confirm_message");
		},
		
		/**********************************************************************
		 * 
		 * 获得配置的元数据
		 * 支持 meta方式 和 属性方式两种。
		 * 
		 * @param {Object}
		 *			obj 节点
		 * @param {String}
		 *			key 元数据名称
		 * @return {String}
		 *			value
		 * 
		 *********************************************************************/
		getMetadata:function(obj,key){
			return $(obj).metadata()[key];
		},
		
		/**********************************************************************
		 * 
		 * 在控制台显示调试信息
		 * 
		 *********************************************************************/
		log : function(msg) {
			if(window.console && window.console.log){
				window.console.log(msg);
			}
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
		
		/******************************************************************
		 * 
		 * 显示提示信息的函数
		 * 
		 * @param 
		 *		percent:进度百分比，不带%
		 *		duration:进度间隔时间，单位为秒
		 *		message:显示消息
		 *
		 *****************************************************************/
		showProcess : function(percent,message,duration) {
			alert("暂未开发。。。");
		},
		
		/******************************************************************
		 * 
		 * 处理Ajax成功响应
		 *
		 *****************************************************************/
		handleSuccessRes : function(data, textStatus, jqXHR) {
			var responseHeader = {
					note:jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_NOTE),
					error:jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_ERROR),
					cve:jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_CVE),
					jump:jqXHR.getResponseHeader($.My.Constans.RESPONSE_HEADER_JUMP)
			};
			if (responseHeader.error) {
				// 操作失败,error返回，显示警告信息
				$.My.showMsg(false, data.msg);
				return false;
			} else if (responseHeader.cve) {
				var ejson = data.msg;
				var parent = this.currentTarget;
				var	ejsonContent = $.parseJSON(ejson);
				$.each(ejsonContent,
						function(i, n) {
							var eles=$("[name='"+i+"']",parent);
							if(eles.length>1){
								$("div[data-for='"+i+"']",parent).showPopover(n,{hideConcern:true});
							}else {
								$("[name='"+i+"']",parent).showPopover(n,{hideConcern:true});
							}
						});
				// 操作失败,cve返回，显示警告信息
				$.My.showMsg(false, $.My.Messages.ERR_CVE);
				return false;
			} else if (responseHeader.jump) {
				window.location = $.My.base64.decode(responseHeader.jump);
				return false;
			} else if (responseHeader.note) {
				$.My.showMsg(true, $.My.base64.decode(responseHeader.note));
				return true;
			} else {
				$.My.showMsg(false, "返回未知响应类型！！！");
				return false;
			}
		},
		
		/******************************************************************
		 * 
		 * 处理结果数据
		 *
		 *****************************************************************/
//		HANDLE_RESULT_UL:"handle_ul",
//		HANDLE_RESULT_TABLE:"handle_table",
//		HANDLE_RESULT_ADD:"handle_add",
//		HANDLE_RESULT_UPD:"handle_upd",
//		HANDLE_RESULT_DEL:"handle_del"
		handleResultData : function(responseText, handleType, hanleOperate) {
			if(handleType==$.My.Constans.HANDLE_RESULT_UL) {
				var $li = $(responseText).find("#result_data li");
				if(hanleOperate==$.My.Constans.HANDLE_RESULT_ADD){
					var $ubody=$('#data_result', this.currentTarget).find("> ul");
					$ubody.find(".custom_clicked").removeClass("custom_clicked");
					$ubody.prepend($li);
				} else if(hanleOperate==$.My.Constans.HANDLE_RESULT_UPD){
					$("#data_result",this.currentTarget).find(".custom_clicked")
													   .replaceWith($li.addClass("custom_clicked"));
				} else if(hanleOperate==$.My.Constans.HANDLE_RESULT_DEL){
					$('#data_result', this.currentTarget).find(".custom_clicked").remove();
				}
				this._bindULDataClick();
			} else if (handleType==$.My.Constans.HANDLE_RESULT_TABLE) {
				var $row = $(responseText).find("#result_data tr");
				if(hanleOperate==$.My.Constans.HANDLE_RESULT_ADD){
					var $tbody=$('#data-table', this.currentTarget).find("> tbody");
					$tbody.find(".custom_clicked").removeClass("custom_clicked");
					$tbody.prepend($row);
				} else if(hanleOperate==$.My.Constans.HANDLE_RESULT_UPD){
					$("#data-table",this.currentTarget).find(".custom_clicked")
													   .replaceWith($row.addClass("custom_clicked"));
				} else if(hanleOperate==$.My.Constans.HANDLE_RESULT_DEL){
					$('#data-table', this.currentTarget).find(".custom_clicked").remove();
				}
				this._bindTableDataClick();
			}
		},
		
		/**********************************************************************
		 * 
		 * 默认配置
		 * 可以被覆盖
		 * 
		 *********************************************************************/
		defaults : {
			debug : true   //是否启用DEBUG模式，true启用;false不启用           
		}
	});
	
	$.My.base64 = new Base64();
})(jQuery,window,document);


//匿名函数
/*******************************************************************
 * 
 * 分页跳转
 * 
 ******************************************************************/
(function($){
	$.My.extendMethod({
		name:"pager",
		    event:{
			click:{
				"className":"_handlePagerClick",
				"gotoClassName":"_handleGotoClick"
			}
		},
	    eventHandler:{
			/**
			 * 
			 * 分页上标签click事件处理函数
			 * 
			 * @param 
			 *		{Object} element 当前点击的HTML节点
			 * 
			 */
			_handlePagerClick : function(element) {
				var pageclickednumber = $(element).attr("value");
				this._handlePageClick(pageclickednumber);
			},
			/**
			 * 
			 * 分页上标签click事件处理函数
			 * 
			 * @param 
			 *		{Number} pageclickednumber 当前点击的页数
			 * 
			 */
			_handlePageClick : function(pageclickednumber) {
				// 分页标签点击处理
				var $pageNo = $("input[name='pageNo']", this.currentTarget);
				if ($pageNo.attr("name")) {
					// 如果pageNo存在，则直接修改其值
					$pageNo.val(pageclickednumber);
				} else {
					// 否则，创建pageNo，并追加到form标签下。
					$pageNo = $(
					"<input type='hidden' id='pageNo' name='pageNo'/>")
					.val(pageclickednumber)
					.appendTo($(this.searchFormClassName, this.currentTarget));
				}
				// 提交表单
				$(this.searchFormClassName, this.currentTarget).trigger("submit");
			},
			/**
			 * 
			 * 跳转到标签click事件处理函数
			 * 
			 * @param 
			 *		{Object} element 当前点击的HTML节点
			 * 
			 */
			_handleGotoClick : function(element) {
				alert("暂未实现该功能。。。");
				var $ele = $(element);
				var $pageNo = $ele.parent().find(".my_pager_input");
				//var $pageNo = $("input[name='pageNo']",	this.currentTarget);
				if($pageNo.is(".my_pager_input")){
					//---------验证
					var clickValue = $.trim($pageNo.val());
					//空
					if(!clickValue){
						$.My.showMsg(false, "请填写跳转页数");
						return;
					}
					//数字
					if(!/^\d+$/.test(clickValue)){
						$.My.showMsg(false, "请填写数字");
						return;
					}
					var pageclickednumber = parseInt(clickValue,10);
					//大小
					if(pageclickednumber > 100000){
						$.My.showMsg(false, "页数太大，请重新填写");
						return;
					}
					//-----------验证
					
					this._handlePageClick(pageclickednumber);
				}else{
					this.warn("没有name为pageNo的input框");
				}
			}
	    },
	    //可配置项
	   config : {
		    //分页标签上的的class
		    className:'.my_pager_item',
		    gotoClassName:'.my_pager_goto'
		}
		
	});
})(jQuery);

/*******************************************************************
 * 
 * 查询分页展示
 * 
 ******************************************************************/
(function($){
	
	$.My.extendMethod({
		name:"search",
		init:function(){
			// 缓存一些设置中的值，减少获取链
			this.searchFormClassName = this.settings.search.formClassName;
			this.searchSubmitClassName = this.settings.search.submitClassName;
			this.searchAutoRefreshEnable = this.settings.search.autoRefresh.enable;
			this.searchAutoRefreshInterval = this.settings.search.autoRefresh.interval;
			
			var $this=this;
			$(this.searchFormClassName,
					this.currentTarget).on("submit",function(event){
						$this._search($(this));
						// 阻止元素发生默认的行为
						$.My.preventDefault(event);
					});
			// _bindSearchFormInputEnter
			// 绑定查询表单的input:text的回车事件的处理函数，会触发查询表单的submit事件
			$(this.searchFormClassName,
					this.currentTarget)
				.find(":input:text")
				.keydown(function(event){
					var $target = $(event.target);
					if (event.keyCode === 13 ) {
							$($this.searchSubmitClassName,
									$this.currentTarget).trigger("click");
					}
				});
			// autoSearch
			// 如何设置为false，则不执行。
			if(this.settings.search.autoSearch === true){
				$(this.searchFormClassName,
					this.currentTarget).trigger("submit");
			}else{
					$(this.settings.search.resultClassName,
						this.currentTarget).find("> table > tbody")
						.empty().append("<tr><td colspan='15' ><div class='taiji_not_found'>请先填写查询条件！</div></td></tr>");
			}
		},
		
		event:{
			click:{
				submitClassName:"_handleSearchSubmit"
			}
		},
		
		eventHandler:{
			
			_handleSearchSubmit:function(element){
				$(this.searchFormClassName,
						this.currentTarget).trigger("submit");
			},
			
			/******************************************************************
			 * 
			 * 查询函数，
			 * 私有的,请勿在外部直接调用
			 * 
			 *****************************************************************/ 
			_search : function(form) {
				if (this.isRunning === true) {
					this.warn($.My.Messages.WAR_OPERATE);
					return;
				} else {
					this.isRunning = true;
				}
				var $this = this,
					options = { 
					// 查询配置项
					// 查询成功处理函数
					success : function success(responseText,
							status, xhr) {
						var responseHeader = {
								note:xhr.getResponseHeader($.My.Constans.RESPONSE_HEADER_NOTE),
								error:xhr.getResponseHeader($.My.Constans.RESPONSE_HEADER_ERROR),
								cve:xhr.getResponseHeader($.My.Constans.RESPONSE_HEADER_CVE),
								jump:xhr.getResponseHeader($.My.Constans.RESPONSE_HEADER_JUMP)
						};
						var $responseText = $(responseText);
						// 移出loading
						$.My.hideLoading();
						// 恢复查询按钮
						$($this.searchSubmitClassName,
							$this.currentTarget).prop("disabled",
								false);
						$this.isRunning = false;
						
						if(responseHeader.note){
							$.My.showMsg(true, $.My.base64.decode(responseHeader.note));
						}else if(responseHeader.error){
							$.My.showMsg(false, data.msg);
						}else if(responseHeader.cve){
							var ejson = data.msg;
							var parent = this.currentTarget;
							var	ejsonContent = $.parseJSON(ejson);
							$.each(ejsonContent,
									function(i, n) {
										var eles=$("[name='"+i+"']",parent);
										if(eles.length>1){
											$("div[data-for='"+i+"']",parent).showPopover(n,{hideConcern:true});
										}else {
											$("[name='"+i+"']",parent).showPopover(n,{hideConcern:true});
										}
									});
							// 操作失败,cve返回，显示警告信息
							$.My.showMsg(false, $.My.Messages.ERR_CVE);
						}else if (responseHeader.jump) {
							window.location = $.My.base64.decode(responseHeader.jump);
						}else{
							if($this.settings.search.bindPage === $.My.Constans.BIND_PAGE_CENTER) {
								$this._bindPager($responseText.find("#query_pager"), $.My.Constans.BIND_PAGE_CENTER);
							}else if($this.settings.search.bindPage === $.My.Constans.BIND_PAGE_RIGHT) {
								$this._bindPager($responseText.find("#page_query_pager"), $.My.Constans.BIND_PAGE_RIGHT);
							}
							if($this.settings.search.handleResult === $.My.Constans.HANDLE_RESULT_UL) {
								$this._handleSearchReasult2Ul(null,$responseText);
							}else if($this.settings.search.handleResult === $.My.Constans.HANDLE_RESULT_TABLE) {
								$this._handleSearchReasult2Table($responseText);
							}
						}	
					},
					// 查询失败处理函数
					error : function error(xhr) {
						$this._handleOperateError(xhr.status,$($this.searchSubmitClassName,$this.currentTarget));
						$($this.currentTarget).triggerHandler("myERROR",$.My.Messages.ERR_RESPONSE + xhr.status);
					}
				};
				// 创建loading
				$.My.showLoading($.My.Messages.MSG_OPERATE);	
				// 禁用查询按钮，防止客户猛击
				$(this.searchSubmitClassName,
						this.currentTarget).prop("disabled",true);
				// 异步查询请求
				$(this.searchFormClassName, 
						this.currentTarget).ajaxSubmit(options);
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
			 * 处理分页
			 * 
			 *************************************************************************/ 
			_bindPager: function($searchPager, bindAlign) {
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
						if(bindAlign === $.My.Constans.BIND_PAGE_RIGHT) {
							var $ul = $("<ul class='pagination m-t-0 m-b-10 pull-right'>");
						}else{
							var $ul = $("<ul class='pagination m-t-0 m-b-10'>");
						}
						var $li_prev = $("<li ><a href='javascript:;' class='my_pager_item' value='"+(currentPage-1)+"'>«</a></li>").appendTo($ul);
						if(currentPage === 0){
							$li_prev.addClass("disabled").find("a").removeClass("my_pager_item");
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
							var $li_point = $("<li ><a href='javascript:;' class='my_pager_item' value='"+point+"'>"+(point+1)+"</a></li>").appendTo($ul);
							if(point === currentPage){
								$li_point.addClass("active").find("a").removeClass("my_pager_item");
							}
						}
						var $li_next = $("<li ><a href='javascript:;' class='my_pager_item' value='"+(currentPage + 1)+"'>»</a></li>").appendTo($ul); 
						if(currentPage === totalPages - 1 ){
							$li_next.addClass("disabled").find("a").removeClass("my_pager_item");
						}
						$(this).html($ul);
					}).show();
				}
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
			
		},
		//可配置项
		config:{
			formClassName : '.my_search_form', // 分页查询表单的class
			rules : {}, // 分页查询表单验证的规则
			messages : {}, // 分页查询表单验证失败的提示信息
			submitClassName : '.my_search_submit', // 分页查询，查询链接的class
			autoRefresh : {
				enable : false,
				interval : 60000
			},
			autoSearch : true,
			bindPage : $.My.Constans.BIND_PAGE_CENTER,
    		handleResult : $.My.Constans.HANDLE_RESULT_UL
		}
	});
	
})(jQuery);

/******************************************************************************
 *
 * 借助bootstrap的popover实现提示
 * 
 *****************************************************************************/
(function($){
	$.fn.extend({
		isVisible: function() {
			if($(this).isChosenSelect()) {return true;}
			if(this.hasClass("selectpicker")&&this.hasClass("bs-select-hidden")){return true;}
			return $(this).attr("type") !== "hidden" && $(this).css("display") !== "none" && $(this).css("visibility") !== "hidden";
		},
		isChosenSelect:function(){
			return $(this).is("select") && $(this).is(":hidden") && $(this).next().is("div.chosen-container");
		},
		showPopover:function(content, options) {
			// 如果元素不可见，不处理
			if (!content || !$(this).isVisible()) {
				return;
			}
			var control=this;
			if(this.isChosenSelect()){
				control=this.next();
			}
			if(this.hasClass("selectpicker")&&this.hasClass("bs-select-hidden")){
				control=this.next(".bootstrap-select");
			}
			var errorEle=control.parent();
			errorEle.addClass("has-error");
			
			var opts = $.extend({}, $.fn.showPopover.defaults, options);
			control.removeAttr("title").attr("data-content",content);
			control.popover(opts)
					.popover("show");
			if(options&&options.hideConcern){
				control.off("click focus keydown");
				control.on("click focus keydown",function(){$(this).hidePopover();});
			}
		},
		hidePopover:function(){
			this.popover("hide");
			var errorEle=this.parent();
			errorEle.removeClass("has-error");
		}
	});
	
	$.fn.showPopover.defaults = {    
		placement: "auto",
		animation: true,
		trigger:"manual"
	}; 

	if($.validator){
		$.validator.prototype.prepareElement=function( element ) {
			this.reset();
			var $ele=$(element);
			if($ele.is(":radio")||$ele.is(":checkbox")){
				this.toHide = $ele.closest("div[data-for='"+$ele.attr("name")+"']");
			}else{
				this.toHide =$ele;
			}
		};
		jQuery.validator.setDefaults({
			showErrors: function(errorMap, errorList) {
				if(this.toHide.jquery&&errorList.length===0){
					this.toHide.hidePopover();
				}
				$.each(errorList,function(i,obj){
					var $ele=$(obj.element);
					var msg=obj.message;
					var pop=$ele;
					if($ele.is(":radio")||$ele.is(":checkbox")){
						pop=$ele.closest("div[data-for='"+$ele.attr("name")+"']");
					}
					pop.showPopover(msg);
				});
			  },
			  debug: false
		});
	}
})(jQuery);
