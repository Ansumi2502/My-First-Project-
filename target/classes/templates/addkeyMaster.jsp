<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <head>
        <title th:text="${appName}"> </title>
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
						<li th:if="${userType=='ADMIN'}"><a href="/addKey">Add Brand</a></li>
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
			<div class="panel-heading" style = "text-align:left;">KEY MASTER FORM</div>
				<div class="panel-body">
			        <form class="form-horizontal" th:action="@{/saveKey}" method="POST">
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Key</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" id="name" name="name" value="" required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Value</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" id="value" name="value" value="" required/>
							</div>
						</div>
						<div class="form-group ">
							<input type="submit" class="btn btn-success" value="Save" />
						</div>
			        </form>
		        </div>
	        </div>
	        <hr>
     	    <div class="table-responsive">
				<table class="table table-striped table-bordered">
					<thead>
						<tr th:if="${not #lists.isEmpty(allDepots)}">
							<th>Sl. No.</th>
							<th>Key</th>
							<th>Value</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						
						<tr th:each="keyObj,iter : ${allKeys}">
							<td th:text="${iter.index+1}"></td>
							<td th:text="${keyObj.name}"></td>
							<td th:text="${keyObj.value}"></td>
							<td><a th:href="@{'/delete-keyObj/' + ${keyObj.id}}"><span
									class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
									
								<a th:href="@{'/edit-keyObj/' + ${keyObj.id}}"><span
									class="glyphicon glyphicon-pencil"></span></a></td>
						</tr>
					</tbody>
				</table>
			</div>
        </div>
    </body>
</html>