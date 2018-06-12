<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@include file="../partials/header.jsp"%>

<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">

				<%@include file="../partials/displayUserMessage.jsp"%>

				<form class="form-signin" method="POST">

					<h1><i class="fas fa-sign-in-alt"></i> Sign in</h1>

					<div class="form-group">
						<label for="username">Username</label> 
						<input type="text"
							class="form-control" id="username" name="username"
							placeholder="Username..." />
					</div>
					
					<div class="form-group">
						<label for="password">Password</label> 
						<input type="password"
							class="form-control" id="password" name="password"
							placeholder="Password..." />
					</div>					

					<button class="btn btn-lg btn-primary btn-block" type="submit">Sign	in</button>
				</form>

			</div>
		</div>
	</div>
</section>

<%@include file="../partials/footer.jsp"%>