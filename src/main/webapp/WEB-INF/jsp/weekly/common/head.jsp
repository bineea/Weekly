<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>

<!-- begin #header -->
<div id="header" class="header navbar navbar-default navbar-fixed-top">
	<!-- begin container -->
	<div class="container">
		<!-- begin navbar-header -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#header-navbar">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a href="${rootUrl }app/weekly/homeIndex" class="navbar-brand">
				<span class="brand-logo"></span>
				<span class="brand-text">
					My Weekly
				</span>
			</a>
		</div>
		<!-- end navbar-header -->
		<!-- begin navbar-collapse -->
		<div class="collapse navbar-collapse" id="header-navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="${rootUrl }app/weekly/homeIndex" >HOME</a>
				</li>
				<li>
					<a href="${rootUrl }app/weekly/daily/project" >RECORD</a>
				</li>
				<li>
					<a href="${rootUrl }app/weekly/daily/combine" >WEEKLY</a>
				</li>
				<li style="width:2px;height:24px;margin-top:13px;background:black;"></li>
				<li>
					<a href="javascript:;" data-toggle="dropdown">
						<img src="${rootUrl }assets/img/user-1.jpg" style="float:left;width:20px;height:20px;border-radius:40px;margin-right:10px;" alt="用户头像" />
						<span class="hidden-md hidden-sm hidden-xs">ABOUT ME</span>
						<b class="caret"></b>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">Edit Profile</a></li>
						<li><a href="#">Modify Password</a></li>
						<li><a href="${rootUrl }app/common/logout">Log Out</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!-- end navbar-collapse -->
	</div>
	<!-- end container -->
</div>
<!-- end #header -->

<!-- begin #page-title -->
<div id="page-title" class="page-title has-bg">
	<div class="bg-cover"><img src="${rootUrl}assets/img/cover.jpg" alt="" /></div>
	<div class="container">
		<h1>My place My rule</h1>
		<p>record something happened every day</p>
	</div>
</div>
<!-- end #page-title -->