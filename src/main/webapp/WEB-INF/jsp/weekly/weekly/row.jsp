<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/weekly/common/tag.jsp"%>

<%-- 表格样式 --%>
<tr>
	<td hidden="hidden">
		<input name="dailyId" type="radio" value="${data.id }">
	</td>
    <td class="checkbox-select">
        <a href="#" data-click="checkbox-select-single"><i class="fa fa-square-o fa-fw"></i></a>
    </td>
    <td>
        ${data.name }
    </td>
    <td class="text-center">${data.summary }</td>
    <td class="text-center">
        ${data.area.value }
    </td>
    <td class="text-center">
       ${data.user.name }
    </td>
    <td  class="text-center">
    	操作
    </td>
</tr>