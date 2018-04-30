<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="main.java.com.excilys.computerdatabase.dto.ComputerDTO"%>


<%@include file="../partials/header.jsp"%>

<section id="main">
	<div class="container">
		<h1 id="homeTitle">
			<c:out value="${computerCount}" />
			Computers found
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">

					<input type="search" id="searchbox" name="search"
						class="form-control" placeholder="Search name" /> <input
						type="submit" id="searchsubmit" value="Filter by name"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer.jsp">Add
					Computer</a> <a class="btn btn-default" id="editComputer" href="#"
					onclick="$.fn.toggleEditMode();">Edit</a>
			</div>
		</div>
	</div>

	<form id="deleteForm" action="#" method="POST">
		<input type="hidden" name="selection" value="">
	</form>

	<div class="container" style="margin-top: 10px;">
		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<!-- Variable declarations for passing labels as parameters -->
					<!-- Table header for Computer Name -->

					<th class="editMode" style="width: 60px; height: 22px;"><input
						type="checkbox" id="selectall" /> <span
						style="vertical-align: top;"> - <a href="#"
							id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
								class="fa fa-trash-o fa-lg"></i>
						</a>
					</span></th>
					<th>Computer name</th>
					<th>Introduced date</th>
					<!-- Table header for Discontinued Date -->
					<th>Discontinued date</th>
					<!-- Table header for Company -->
					<th>Company</th>

				</tr>
			</thead>
			<!-- Browse attribute computers -->
			<tbody id="results">
				<c:forEach var="dto" items="${dtoList}">
					<tr>
						<td class="editMode"><input type="checkbox" name="cb"
							class="cb" value="0"></td>
						<td><a href="editComputer.jsp" onclick=""><c:out
									value="${dto.getName()}" /></a></td>
						<td><c:out value="${dto.getIntroduced()}" /></td>
						<td><c:out value="${dto.getDiscontinued()}" /></td>
						<td><c:out value="${dto.getCompanyName()}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</section>

<footer class="navbar-fixed-bottom">
	<div class="container text-center">
		<ul class="pagination">
			<li><a href="#" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span>
			</a></li>
			<li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
			</a></li>
		</ul>
	</div>

	<div class="btn-group btn-group-sm pull-right" role="group">
		<button type="button" class="btn btn-default">10</button>
		<button type="button" class="btn btn-default">50</button>
		<button type="button" class="btn btn-default">100</button>
	</div>

</footer>


<script src="${root}/js/jquery.min.js"></script>
<script src="${root}/js/dashboard.js"></script>
<script src="${root}/js/bootstrap.min.js"></script>

</body>
</html>