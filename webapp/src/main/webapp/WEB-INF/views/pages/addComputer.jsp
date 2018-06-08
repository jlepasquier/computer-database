<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@include file="../partials/header.jsp"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
			
				<%@include file="../partials/displayUserMessage.jsp"%>
			
				<h1>Add Computer</h1>
				<form:form action="addComputer" method="POST" modelAttribute="computerDTO">
					<fieldset>
						<div class="form-group">
							<form:label for="computerName" path="name">Computer name</form:label> 
							<form:input
								type="text" class="form-control" id="computerName" path="name"
								name="computerName" placeholder="Computer name"/>
						</div>
						<div class="form-group">
							<form:label for="introduced" path="introduced">Introduced date</form:label> 
							<form:input
								type="date" class="form-control" id="introduced" path="introduced"
								name="introduced" placeholder="Introduced date"/>
						</div>
						<div class="form-group">
							<form:label for="discontinued" path="discontinued">Discontinued date</form:label> <form:input
								type="date" class="form-control" id="discontinued" path="discontinued"
								name="discontinued" placeholder="Discontinued date"/>
						</div>
						<div class="form-group">
							<form:label for="companyId" path="companyId">Company</form:label> <form:select
								class="form-control" id="companyId" path="companyId" name="companyId">
								<c:forEach var="company" items="${companyList}">
									<option value="<c:out value="${company.getId()}"/>">
										<c:out value="${company.getName()}" />
									</option>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary">
						or <a href="dashboard" class="btn btn-default">Cancel</a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>

<%@include file="../partials/imports.jsp"%>
<script src="static/js/formValidation.js"></script>
<%@include file="../partials/footer.jsp"%>