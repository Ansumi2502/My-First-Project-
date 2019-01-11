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
   	    <script type="text/javascript">
	   	 	function validate(evt) {
				evt = (evt) ? evt : window.event;
			    var charCode = (evt.which) ? evt.which : evt.keyCode;
			    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			        return false;
			    }
			    return true;
			}
		</script>
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
						<li style="float:right;color:#ffffff;"><div >Logged in user: <b th:inline="text"  class="user"> [[${#httpServletRequest.remoteUser}]]</b></div></li>
						<li><form th:action="@{/logout}" method="POST">
					         <input type="submit" value="Logout"/>
					    </form></li>
					</ul>
				</div>
			</div>
		</div>
        <div class="container text-center">
        	<p class="error" th:text="${errorMsg}">Error</p>
        	<p class="success" th:text="${success}">Success</p>
        	
        	
			<hr>
			<div class="panel panel-primary">
			<div class="panel-heading" style = "text-align:left;">UPDATE USER FORM</div>
			<div class="panel-body">
	        <form class="form-horizontal" method="POST" th:action="@{/updateUser}" th:object="${applnUser}">
				<input type="hidden" name="id" th:field="*{id}" />
				<input type="hidden" name="password" th:field="*{password}" />
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Name</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="name"
							th:field="*{name}"  required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">User Id</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="userId"
							th:field="*{userId}" readonly required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Password</font></label>
					<div class="col-md-7">
						<input type="password" class="form-control" name="passwordTemp"
							value=""  required />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Email Id</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="email"
							th:field="*{email}" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">User Type</font> </label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="userType"
							th:field="*{userType}" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Status</font></label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="status"
							th:field="*{status}" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Mobile No </font></label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="mobileNo"
							th:field="*{mobileNo}" onkeypress='return validate(event)' required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Depot Name</font></label>
					<div class="col-md-3" th:object="${depotObj}">
					    <select class="form-control selectpicker" id="depotMaster" name="depotMaster" data-live-search="true" required>
					        <option value="">--Select Depot--</option>
					        <option th:each="depotObj : ${depotList}" th:if="${userType=='USER'}"
					                th:value="${depotObj.id}" selected
					                th:text="${depotObj.name}"></option>
			                <option th:each="depotObj : ${depotList}" th:if="${userType=='ADMIN'}"
			                th:value="${depotObj.id}" 
			                th:text="${depotObj.name}"></option>
					    </select>
					</div>
				</div>
				<div class="form-group ">
					<input type="submit" class="btn btn-success" value="Update User" />
				</div>
			</form>
			</div>
			</div>
        </div>
    </body>
</html>