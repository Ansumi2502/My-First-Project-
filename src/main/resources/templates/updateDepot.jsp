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
			<div class="panel-heading" style = "text-align:left;">UPDATE DEPOT FORM</div>
			<div class="panel-body">
	        <form class="form-horizontal" method="POST" th:action="@{/updateDepot}" th:object="${depotMaster}">
				<input type="hidden" name="id" th:field="*{id}" />
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Depot Name</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="name" th:field="*{name}" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Depot Code</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="code" th:field="*{code}" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3"><font color="red">Address</font></label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="address" th:field="*{address}" required/>
					</div>
				</div>
				<!--<div class="form-group">
					<label class="control-label col-md-3">PAN/TAN No</label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="pan_tan_no" th:field="*{pan_tan_no}" required/>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-md-3">Mobile No</label>
					<div class="col-md-7">
						<input type="text" class="form-control" name="mobile_no" th:field="*{mobile_no}" onkeypress='return validate(event)' required/>
					</div>
				</div>-->
				<div class="form-group ">
					<input type="submit" class="btn btn-success" value="Update Depot" />
				</div>
			</form>
		</div>
		</div>
        </div>
    </body>
</html>