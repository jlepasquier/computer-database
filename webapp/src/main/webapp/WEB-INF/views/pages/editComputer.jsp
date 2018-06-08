<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@include file="../partials/header.jsp"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">

				<%@include file="../partials/displayUserMessage.jsp"%>

				<div class="label label-default pull-right">
					id:
					<c:out value="${id}" />
				</div>

				<h1>Edit Computer</h1>

				<form:form action="editComputer" method="POST"
				modelAttribute="computerDTO">
				
					<form:input type="hidden" value="${id}" id="id" path="id" name="id" />
					<fieldset>
						<div class="form-group">
							<form:label for="computerName" path="name">Computer name</form:label> 
							<form:input
								type="text" class="form-control" id="computerName" path="name"
								name="computerName" placeholder="Computer name"
								value="${ computer.name }"/>
						</div>
						<div class="form-group">
							<form:label for="introduced" path="introduced">Introduced date</form:label> 
							<form:input
								type="date" class="form-control" id="introduced" path="introduced"
								name="introduced" placeholder="Introduced date"
								value="${ computer.introduced }"/>
						</div>
						<div class="form-group">
							<form:label for="discontinued" path="discontinued">Discontinued date</form:label> 
							<form:input
								type="date" class="form-control" id="discontinued" path="discontinued"
								name="discontinued" placeholder="Discontinued date" value="${ computer.discontinued }"
								min="${ computer.introduced }"/>
						</div>
						<div class="form-group">
							<form:label for="companyId" path="companyId">Company</form:label> 
							<form:select  path="companyId"
								class="form-control" id="companyId" name="companyId">
								<c:forEach var="company" items="${companyList}">
									<c:choose>
										<c:when test='${computer.company.id==company.id}'>
											<option value='<c:out value="${company.id}"/>' selected>
												<c:out value="${company.name}" />
											</option>
										</c:when>
										<c:otherwise>
											<option value='<c:out value="${company.id}"/>'>
												<c:out value="${company.name}" />
											</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" class="btn btn-primary">
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