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
			<div class="panel-heading" style = "text-align:left;">DEPOT ENTRY FORM</div>
				<div class="panel-body">
			        <form class="form-horizontal" th:action="@{/saveDepot}" method="POST">
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Depot Name</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="name" value="" required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Depot Code</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="code" value="" required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Address</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="address" value="" required/>
							</div>
						</div>
						<!--<div class="form-group">
							<label class="control-label col-md-3">PAN/TAN No</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="pan_tan_no" value="" required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Mobile No</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="mobile_no" value="" onkeypress='return validate(event)' required/>
							</div>
						</div>-->
						<div class="form-group ">
							<input type="submit" class="btn btn-success" value="Save Depot" />
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
							<th>Depot Name</th>
							<th>Depot Code</th>
							<th>Address</th>
							<!--<th>PAN/TAN No</th>
							<th>Mobile No</th>-->
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						
						<tr th:each="depotObj,iter : ${allDepots}">
							<td th:text="${iter.index+1}"></td>
							<td th:text="${depotObj.name}"></td>
							<td th:text="${depotObj.code}"></td>
							<td th:text="${depotObj.address}"></td>
							<!--<td th:text="${depotObj.pan_tan_no}"></td>
							<td th:text="${depotObj.mobile_no}"></td>-->
							<td><a th:href="@{'/delete-depotObj/' + ${depotObj.id}}"><span
									class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
									
								<a th:href="@{'/edit-depotObj/' + ${depotObj.id}}"><span
									class="glyphicon glyphicon-pencil"></span></a></td>
						</tr>
					</tbody>
				</table>
			</div>
        </div>
    </body>
</html>