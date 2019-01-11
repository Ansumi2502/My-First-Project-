<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <head>
        <title th:text="${appName}"></title>
        <link rel="stylesheet" href="/css/styles.css"/>
        <div th:fragment="header-css">
	        <!-- this is header-css -->
			<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css" />
			<link rel="stylesheet" type="text/css" href="/css/bootstrap-datetimepicker.min.css" />
			<link rel="stylesheet" type="text/css" href="/css/styles.css" />
			<link rel="stylesheet" type="text/css" href="/css/bootstrap-select.min.css" />
			
			<script type="text/javascript" src="/js/jquery-2.0.3.min.js"></script>
			<script type="text/javascript" src="/js/bootstrap-datetimepicker.min.js"></script>
			<script type="text/javascript" src="/js/bootstrap-select.min.js"></script>
			<script type="text/javascript" src="/js/bootstrap.min.js"></script>
	    </div>
    </head>
    <body>
       	<div role="navigation">
			<div class="navbar navbar-inverse">
				<a href="" class="navbar-brand" th:text="${appName}"></a>
			</div>
		</div>
		<div class="container text-center">
           <img src="/images/mainlogo.jpg" height="100" width="100">
        </div>
		<div class="container text-center">
	        <div th:if="${param.error}">
	            Invalid username and password.
	        </div>
	        <div th:if="${param.logout}">
	            You have been logged out.
	        </div>
	         
	        <div class="panel panel-primary">
				<div class="panel-heading" style="text-align: left;">LOGIN</div>
				<div class="panel-body panel-body-sm">
			        <form class="form-horizontal" th:action="@{/login}" method="POST">
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">User Id</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="userId" placeholder="Enter Username"
									value=""  required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Password</font></label>
							<div class="col-md-7">
								<input type="password" class="form-control" name="password" placeholder="Enter Password"
									value="" required/>
							</div>
						</div>
						<div class="form-group ">
							<input type="submit" class="btn btn-success" value="Login" />
						</div>
			        </form>
		        </div>
	        </div>
        </div>
    </body>
</html>