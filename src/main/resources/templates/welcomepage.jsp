<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<title th:text="${appName}"></title>
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
			
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="/welcomepage" class="navbar-brand" th:text="${appName}"></a></li>
					<li th:if="${userType=='ADMIN'}"><a href="/register">New Registration</a></li>
					<li><a href="/show-users">All Users</a></li>
					<li th:if="${userType=='ADMIN'}"><a href="/addBrand">Add Brand</a></li>
					<li><a href="/addCustomer">Add Customer</a></li>
					<li th:if="${userType=='ADMIN'}"><a href="/addDepoMaster">Add Depo Master</a></li>
					<li><a href="/secure/homepage">Bill Entry</a></li>
					<li><a href="/generateReceipt">Generate Receipt</a></li>
					<!--<li><a href="/searchBill">Search Bill</a></li>-->
					<li style="float: right; color: #ffffff;"><div>
							Logged in user: <b th:inline="text" class="user">
								[[${#httpServletRequest.remoteUser}]]</b>
						</div></li>
					<li><form th:action="@{/logout}" method="POST">
							<input type="submit" value="Logout" />
						</form></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container" id="homediv">
		<div class="jumbotron text-center">
			<img src="/images/mainlogo.jpg" height="100" width="100">
			<h1 th:text="${appName}"></h1>
		</div>
	</div>
</body>
</html>