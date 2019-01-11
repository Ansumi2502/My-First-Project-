<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">
<head>
	<title th:text="${appName}"></title>
	<link rel="stylesheet" href="/css/styles.css" />
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
					<li>
						<form th:action="@{/logout}" method="POST">
							<input type="submit" value="Logout" />
						</form>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container text-center">
		<p class="error" th:text="${errorMsg}">Error</p>
		<p class="success" th:text="${success}">Success</p>

		<hr>
		<div class="panel panel-primary">
			<div class="panel-heading" style="text-align: left;">ALL USERS</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr th:if="${not #lists.isEmpty(applnUsers)}">
								<th>Sl. No.</th>
								<th>User Id</th>
								<th>Name</th>
								<th>Email Id</th>
								<th>Status</th>
								<th>Mobile No</th>
								<th>User Type</th>
								<th>Depot Name</th>
								<th th:if="${userType=='ADMIN'}">Action</th>
							</tr>
						</thead>
						<tbody>

							<tr th:each="applnUser,iter : ${applnUsers}">
								<td th:text="${iter.index+1}">Index</td>
								<td th:text="${applnUser.userId}">userId</td>
								<td th:text="${applnUser.name}">name</td>
								<td th:text="${applnUser.email}">email</td>
								<td th:text="${applnUser.status}">status</td>
								<td th:text="${applnUser.mobileNo}">mobileNo</td>
								<td th:text="${applnUser.userType}">userType</td>
								<td th:text="${applnUser.depotMaster.name}">userType</td>
								<td th:if="${userType=='ADMIN'}"><a th:href="@{'/delete-user/' + ${applnUser.id}}"><span
										class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;|&nbsp;&nbsp;

									<a th:href="@{'/edit-user/' + ${applnUser.id}}"><span
										class="glyphicon glyphicon-pencil"></span></a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>