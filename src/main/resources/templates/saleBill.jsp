<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org">

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
<head>
	<title th:text="${appName}"></title>
	<script type="text/javascript">
	$.fn.selectpicker.Constructor.BootstrapVersion = '4';

     $(function () {
     	var date = new Date();
     	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
     	
         $('#receiptDate').datetimepicker({
          	format: "mm/dd/yyyy",
          	todayHighlight: true,
          	startDate: today,
          	autoclose: true
          });
          $('#receiptDate').datetimepicker('receiptDate', today);
     });
     
     $(function() {
   	  $('.selectpicker').selectpicker();
   	});
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
	
	<div class="container">
		<p class="error" th:text="${errorMsg}">Error</p>
		<p class="success" th:text="${success}">Success</p>
		<div class="panel-group">
			<div class="panel panel-primary">
				<div class="panel-heading">Search</div>
				<div class="panel-body">
					<form class="form-horizontal" method="" th:action="@{/searchReceipt}">
						<div class="form-group">
							<label class="control-label col-md-2">Invoice Number</label>
							<div class="col-md-3" th:object="${billDetailsObj}">
								<select class="form-control selectpicker" id="billDetails" name="billDetails" data-live-search="true" >
									<option value="">--Select Invoice--</option>
									<option th:each="billDetailsObj : ${billDetailsList}" th:if="${billDetails==billDetailsObj.invoiceNo}"
										th:value="${billDetailsObj.invoiceNo}" selected th:text="${billDetailsObj.invoiceNo}"></option>
									<option th:each="billDetailsObj : ${billDetailsList}" th:if="${billDetails!=billDetailsObj.invoiceNo}"
										th:value="${billDetailsObj.invoiceNo}" th:text="${billDetailsObj.invoiceNo}"></option>
								</select>
							</div>
							<label class="control-label col-md-2">Receipt No</label>
							<div class="col-md-3" th:object="${receiptObj}">
								<select class="form-control selectpicker" id="receiptMaster" name="receiptMaster" data-live-search="true" >
									<option value="">--Select Invoice--</option>
									<option th:each="receiptObj : ${receiptMainList}" th:if="${receiptMaster==receiptObj.receiptNo}"
										th:value="${receiptObj.receiptNo}" selected th:text="${receiptObj.receiptNo}"></option>
									<option th:each="receiptObj : ${receiptMainList}" th:if="${receiptMaster!=receiptObj.receiptNo}"
									    th:value="${receiptObj.receiptNo}"  th:text="${receiptObj.receiptNo}"></option>
								</select>
							</div>
							<div class="col-md-2">
							
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-2">Depot Name</label>
							<div class="col-md-3" th:object="${depotObj}">
								<select class="form-control selectpicker" id="depotMaster" name="depotMaster" data-live-search="true">
									<option value="">--Select Depot--</option>
									<option th:each="depotObj : ${depotList}" th:if="${userType=='USER'}"
										th:value="${depotObj.name}" selected th:text="${depotObj.name}"></option>
									<option th:each="depotObj : ${depotList}" th:if="${userType=='ADMIN' and depotMaster==depotObj.name}"
										th:value="${depotObj.name}" selected th:text="${depotObj.name}"></option>
									<option th:each="depotObj : ${depotList}" th:if="${userType=='ADMIN' and depotMaster!=depotObj.name}"
										th:value="${depotObj.name}" th:text="${depotObj.name}"></option>
								</select>
							</div>
							<label class="control-label col-md-2">Customer Name</label>
							<div class="col-md-3" th:object="${customerObj}">
								<select class="form-control selectpicker" id="customerMaster" name="customerMaster" data-live-search="true" >
									<option value="">--Select Customer--</option>
									<option th:each="customerObj : ${customerList}" th:if="${customerMaster==customerObj.name_of_shop}"
										th:value="${customerObj.name_of_shop}" selected th:text="${customerObj.name_of_shop}"></option>
									<option th:each="customerObj : ${customerList}" th:if="${customerMaster!=customerObj.name_of_shop}"
										th:value="${customerObj.name_of_shop}" th:text="${customerObj.name_of_shop}"></option>
								</select>
							</div>
							<div class="col-md-2">
								
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">Date</label>
							<div class="col-md-3">
								<input type="text" id="receiptDate" name="receiptDate" class="form-control" th:value="${#dates.format(receiptDate, 'dd-MMM-yyyy')}" />
							</div>
							<div class="col-md-2">
								<input type="submit" class="btn btn-success" value="Search" />
							</div>
							<label class="control-label col-md-2"></label>
							<div class="col-md-3">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
	<!-- Receipt List Starts -->
	<form id="receiptform"  role="form" th:action="@{/download-bulkReceipt}" method="post">
		<div class="panel panel-primary" th:if="${not #lists.isEmpty(receiptList)}">
			<div class="panel-heading" style="text-align: left;">ALL RECEIPT</div>
			<div class="panel-body">
				<button type="submit" class="btn btn-success btn-icon btn-xs" name="action" value="bulkReceipt" title="Generate Bulk Receipt"  formtarget="_blank">Generate Bulk Receipt</button>
				<button type="submit" class="btn btn-success btn-icon btn-xs" name="action" value="singleReceipt" title="Generate Single Receipt"  formtarget="_blank">Generate Single Receipt</button>
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr>
								<th>Sl. No.</th>
								<th>Invoice Number</th>
								<th>Customer Name</th>
								<th>Depot Name</th>
								<th>Date</th>
								<th>Receipt No.</th>
								<th>Amount</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<tr th:each="receiptObj,iter : ${receiptList}">
								<td> <input type="checkbox" th:name="receiptIds" th:value="${receiptObj.id}"/></td>
								<td th:text="${receiptObj.billDetails.invoiceNo}"></td>
								<td th:text="${receiptObj.billDetails.customerMaster.name_of_shop}"></td>
								<td th:text="${receiptObj.billDetails.depotMaster.name}"></td>
								<td th:text="${#dates.format(receiptObj.receiptDate, 'dd-MMM-yyyy')}"></td>
								<td th:text="${receiptObj.receiptNo}"></td>
								<td th:text="${receiptObj.amount}"></td>
								<td>
								<a th:href="@{'/download-billDetail/' + ${receiptObj.billDetails.id}}" target="_blank"><span
										class="glyphicon glyphicon-file"></span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
									<a th:href="@{'/print-billDetail-receipt/' + ${receiptObj.id}}" target="_blank"><span
										class="glyphicon glyphicon-print"></span></a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
	<!-- Receipt List End -->
	</div>
</body>
</html>