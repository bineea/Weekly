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
			<a href="index.html" class="navbar-brand">
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