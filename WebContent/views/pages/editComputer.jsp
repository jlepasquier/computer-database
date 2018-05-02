<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="../partials/header.jsp"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<div class="label label-default pull-right">id: <c:out value="${id}" /></div>
				<h1>Edit Computer</h1>

				<form action="editComputer" method="POST">
					<input type="hidden" value="<c:out value="${id}" />" id="id" />
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" class="form-control" id="computerName"
								placeholder="Computer name">
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" id="introduced"
								placeholder="Introduced date">
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" id="discontinued"
								placeholder="Discontinued date">
						</div>
						<div class="form-group">
							<label for="companyId">Company</label> <select
								class="form-control" id="companyId">
								<c:forEach var="company" items="${companyList}">
									<option value="<c:out value="${company.getId()}"/>">
										<c:out value="${company.getName()}" />
									</option>
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
<%@include file="../partials/footer.jsp"%>