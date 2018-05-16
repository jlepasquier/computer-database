<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${userMessage != null}">
	<c:choose>
		<c:when test='${success}'>
			<div class="alert alert-success">
				<c:out value="${userMessage}" />
				<br />
			</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-danger">
				<c:out value="${userMessage}" />
				<br />
			</div>
		</c:otherwise>
	</c:choose>
</c:if>