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
				<div class="panel-heading">Payment Details</div>
				<div class="panel-body">
					<form class="form-horizontal" method="POST" th:action="@{/payAndGenerateReceipt}">
						<div class="form-group">
							<label class="control-label col-md-2"><font color="red">Invoice Number</font></label>
							<div class="col-md-3" th:object="${billDetailsObj}">
								<select class="form-control selectpicker" id="billDetails" name="billDetails"  multiple="multiple" data-live-search="true">
									<option value="">--Select Invoice--</option>
									<option th:each="billDetailsObj : ${billDetailsList}" th:if="${userType=='USER'}"
										th:value="${billDetailsObj.id}" th:text="${billDetailsObj.invoiceNo} + ${billDetailsObj.invoice_seq}"></option>
									<option th:each="billDetailsObj : ${billDetailsList}" th:if="${userType=='ADMIN'}"
										th:value="${billDetailsObj.id}" th:text="${billDetailsObj.invoiceNo} + ${billDetailsObj.invoice_seq}"></option>
								</select>
							</div>
							<label class="control-label col-md-2">OR&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">Customer Name</font></label>
							<div class="col-md-3" th:object="${customerObj}">
								<select class="form-control selectpicker" id="customerObject" name="customerObject" data-live-search="true" >
									<option value="">--Select Customer--</option>
									<option th:each="customerObj : ${customerList}"
										th:value="${customerObj.id}"
										th:text="${customerObj.name_of_shop}"></option>
								</select>
							</div>
							<div class="col-md-2">
							
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2"><font color="red">Receipt Date</font></label>
							<div class="col-md-3">
								<input type="text" id="receiptDate" name="receiptDate" class="form-control"  required />
							</div>
							
							<label class="control-label col-md-2"><font color="red">Payment Mode</font></label>
							<div class="col-md-3">
								<input type="text" class="form-control" id="paymode" name="paymode" required />
							</div>
							<div class="col-md-2">
								
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">RTGS/UTR No</label>
							<div class="col-md-3">
								<input type="text" class="form-control" id="rtgs_utr_no" name="rtgs_utr_no" />
							</div>
							
							<label class="control-label col-md-2"><font color="red">Paying Amount</font></label>
							<div class="col-md-3">
								<input type="text" class="form-control" id="amount" name="amount" value="0" required />
							</div>
							<div class="col-md-2">
								<input type="submit" class="btn btn-success" value="Pay" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
	<!-- Receipt List Starts -->
	<div class="panel panel-primary" th:if="${not #lists.isEmpty(receiptList)}">
		<div class="panel-heading" style="text-align: left;">ALL RECEIPT</div>
		<div class="panel-body">
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
							<th>Status</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
                        
						<tr th:each="receiptObj,iter : ${receiptList}">
							<td th:text="${iter.index+1}"></td>
							<td th:text="${receiptObj.invoiceNumber}"></td>
							<td th:text="${receiptObj.customerName}"></td>
							<td th:text="${receiptObj.DepotName}"></td>
							<td th:text="${#dates.format(receiptObj.receiptDate, 'dd-MMM-yyyy')}"></td>
							<td th:text="${receiptObj.ReceiptNumber}"></td>
							<td th:text="${receiptObj.amount}"></td>
							<td th:text="${receiptObj.status}"></td>
							<td>
								<a th:href="@{'/print-billDetail-receipt/' + ${receiptObj.receiptMasterId}}" target="_blank"><span
									class="glyphicon glyphicon-print"></span></a>|
								<a th:href="@{'/cancel-billDetail-receipt/' + ${receiptObj.receiptMasterId}}" target="_blank"><span
										class="glyphicon glyphicon-remove"></span></a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Receipt List End -->
	</div>
</body>
</html>
