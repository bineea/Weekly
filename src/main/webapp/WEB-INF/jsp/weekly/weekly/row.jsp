<%@ page language="java" contentType="text/html;charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/weekly/common/tag.jsp"%>

<%-- 表格样式 --%>
<tr>
	<td hidden="hidden">
		<input name="dailyId" type="radio" value="${data.id }">
	</td>
    <td style="padding: 0px;width: 100px;">
        <div class="checkbox checkbox-css">
            <input type="checkbox" id="checkbox_${data.id }" class="checkbox-select-single" value="${data.id }" />
            <label for="checkbox_${data.id }" style="margin-top: 6px;">
                <javatime:format value="${data.operateDate }" pattern="yyyy-MM-dd"  />
            </label>
        </div>
    </td>
    <td class="text-center" style="width: 350px;">
        【${data.demand.project.abbr }】${data.demand.title }
    </td>
    <td class="text-center">
        <c:choose>
            <c:when test="${fn:length(data.operateContent) > 60}">
                ${fn:substring(data.operateContent, 0, 60)}...
            </c:when>
            <c:otherwise>
                ${data.operateContent }
            </c:otherwise>
        </c:choose>
    </td>
</tr>