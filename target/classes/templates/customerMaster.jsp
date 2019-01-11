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
			<div class="panel-heading" style = "text-align:left;">CUSTOMER ENTRY FORM</div>
				<div class="panel-body">
			        <form class="form-horizontal" th:action="@{/saveCustomer}" method="POST">
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Name Of Licence</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="name_of_licence" value="" required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3"><font color="red">Name Of Shop</font></label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="name_of_shop" value="" required/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Address</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="address" value="" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Licence No Of Shop</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="licence_no_of_shop" value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">Firm Name</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="firm_name" value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">PAN No Of Firm</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="pan_no_of_firm_name" value=""/>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3">PAN No of Licence</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="pan_no_of_licencee" value=""  />
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
						<div class="form-group">
							<label class="control-label col-md-3">Mobile No</label>
							<div class="col-md-7">
								<input type="text" class="form-control" name="mobileNo"
									value="" onkeypress="return validate(event)" />
							</div>
						</div>
						<div class="form-group ">
							<input type="submit" class="btn btn-success" value="Save Customer" />
						</div>
			        </form>
		        </div>
	        </div>
	        <hr>
	        <div class="table-responsive">
				<table class="table table-striped table-bordered">
					<thead>
						<tr th:if="${not #lists.isEmpty(allCustomers)}">
							<th>Sl. No.</th>
							<th>Name Of Licence</th>
							<th>Name Of Shop</th>
							<th>Address</th>
							<th>Licence No Of Shop</th>
							<th>Firm Name</th>
							<th>PAN No Of Firm</th>
							<th>Depot Name</th>
							<th>PAN No of Licencee</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						
						<tr th:each="cutomerObj,iter : ${allCustomers}">
							<td th:text="${iter.index+1}"></td>
							<td th:text="${cutomerObj.name_of_licence}"></td>
							<td th:text="${cutomerObj.name_of_shop}"></td>
							<td th:text="${cutomerObj.address}"></td>
							<td th:text="${cutomerObj.licence_no_of_shop}"></td>
							<td th:text="${cutomerObj.firm_name}"></td>
							<td th:text="${cutomerObj.pan_no_of_firm_name}"></td>
							<td th:text="${cutomerObj.depotMaster.name}"></td>
							<td th:text="${cutomerObj.pan_no_of_licencee}"></td>
							<td><a th:href="@{'/delete-cutomerObj/' + ${cutomerObj.id}}"><span
									class="glyphicon glyphicon-trash"></span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
									
								<a th:href="@{'/edit-cutomerObj/' + ${cutomerObj.id}}"><span
									class="glyphicon glyphicon-pencil"></span></a></td>
						</tr>
					</tbody>
				</table>
			</div>
        </div>
    </body>
</html>