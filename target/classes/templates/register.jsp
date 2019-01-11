<!DOCTYPE html>
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
			<div class="panel-heading" style = "text-align:left;">USER REGISTRATION FORM</div>
			<div class="panel-body">
	        <form class="form-horizontal" th:action="@{/saveUser}" method="POST">
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Name</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="name" value="" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">User Id</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="userId" value="" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Password</font></label>
					<div class="col-md-7">
						<input type="password" class="form-control" name="password"
							value="" required />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Email Id</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="email"
							value=""  required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">User Type</font> </label>
					<div class="col-md-3" th:object="${userType}">
					    <select class="form-control selectpicker" id="userType" name="userType" data-live-search="true" required>
					        <option value="">--Select User Type--</option>
					        <option th:each="userType : ${userTypeList}"
					                th:value="${userType.value}"
					                th:text="${userType.text}"></option>
					    </select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Status</font></label>
					<div class="col-md-3" th:object="${status}">
					    <select class="form-control selectpicker" id="status" name="status" data-live-search="true" required>
					        <option value="">--Select Status--</option>
					        <option th:each="status : ${statusList}"
					                th:value="${status.value}"
					                th:text="${status.text}"></option>
					    </select>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Mobile No </font></label>
					<div class="col-md-3">
						<input type="text" class="form-control" name="mobileNo"
							value="" onkeypress="return validate(event)" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Depot Name</font></label>
					<div class="col-md-3" th:object="${depotObj}">
						<select class="form-control selectpicker" id="depotMaster" name="depotMaster" data-live-search="true" required>
							<option value="">--Select Depot--</option>
							<option th:each="depotObj : ${depotList}"
								th:value="${depotObj.id}" th:text="${depotObj.name}"></option>
						</select>
					</div>
				</div>
				
				<div class="form-group ">
					<input type="submit" class="btn btn-success" value="Register" />
				</div>
	        </form>
	        </div>
	        </div>
        </div>
    </body>
</html>