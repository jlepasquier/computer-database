<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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

				<form action="editComputer" method="POST">
					<input type="hidden" value="<c:out value="${id}" />" id="id"
						name="id" />
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" class="form-control" id="computerName"
								name="computerName" placeholder="Computer name"
								value="${ computer.name }">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" id="introduced"
								name="introduced" placeholder="Introduced date"
								value="${ computer.introduced }">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" id="discontinued"
								name="discontinued" placeholder="Discontinued date" value="${ computer.discontinued }"
								min = ${ computer.introduced }>
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select
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
							</select>
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" value="Edit" class="btn btn-primary">
						or <a href="dashboard" class="btn btn-default">Cancel</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</section>

<%@include file="../partials/imports.jsp"%>
<script src="${root}/js/formValidation.js"></script>
<%@include file="../partials/footer.jsp"%>