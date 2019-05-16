<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tag.jsp"%>

<%-- 表格样式
<tr>
	<td hidden="hidden">
		<input name="demandId" type="radio" value="${data.id }"> 
	</td>
    <td>
        ${data.title }
    </td>
    <td class="text-center">${data.summary }</td>
    <td class="text-center">
        ${data.demandType.value }
    </td>
    <td class="text-center">
    	${data.handleStatus.value }
    </td>
    <td class="text-center">
       ${data.user.name }
    </td>
    <td  class="text-center">
    	操作
    </td>
</tr> --%>

<li data-animation="true" data-animation-type="fadeInUp">
	<div hidden="hidden">
		<input name="demandId" type="radio" value="${data.id }"> 
	</div>
    <div class="pricing-container">
        <h3>${data.handleStatus.value }</h3>
        <div class="price">
            <div class="price-figure">
                <span class="price-number">${data.demandType.value }</span>
            </div>
        </div>
        <ul class="features">
        	<li>${data.title }</li>
            <li>${data.summary }</li>
        </ul>
        <div class="footer">
            <a href="#" class="btn btn-inverse btn-block">修改</a>
            <a href="#" class="btn btn-inverse btn-block">删除</a>
        </div>
    </div>
</li>