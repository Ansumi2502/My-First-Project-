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
		function calculateAmount() {     
          var quantity=document.getElementsByName("quantity"); 
          var pieces=document.getElementsByName("pieces");
          var brandAmount=document.getElementsByName("amount");
          var len=document.getElementsByName("quantity").length;
          var totAmount=0;
          
          if(len != 0){
       	   for(var i=0;i<len;i++){
       	 		var amt=0;
       	 	    var pieceAmt=0;
       	 	    pieceAmt=pieces[i].value  * (brandAmount[i].value/45);
       	 	    pieceAmt=+pieceAmt.toFixed(2);
       	 		amt=quantity[i].value  * brandAmount[i].value;
       	 		amt=+amt.toFixed(2);
       	 		var totA=pieceAmt+amt;
       	 		totA=+totA.toFixed(2);
       	 		document.getElementById("itemDistributionDetails["+i+"].amount").value=totA;
       			document.getElementById("itemDistributionDetails["+i+"].cases").value=quantity[i].value;
       			document.getElementById("itemDistributionDetails["+i+"].pieces").value=pieces[i].value;
       			totAmount=totAmount+pieceAmt+amt;
       			totAmount=+totAmount.toFixed(2);
       		}
     	  }
          document.getElementById("total_amount").value=totAmount;
        }
		
         $(function () {
         	var date = new Date();
         	var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
         	
             $('#entry_date1').datetimepicker({
             	format: "mm/dd/yyyy",
             	todayHighlight: true,
             	autoclose: true
             });
             $('#entry_date1').datetimepicker('setDate', today);
             
             $('#date_s1').datetimepicker({
              	format: "mm/dd/yyyy",
              	todayHighlight: true,
              	autoclose: true
              });
              $('#date_s1').datetimepicker('setDate', today);
         });
         
         $(function() {
       	  $('.selectpicker').selectpicker();
       	});
         
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
				<div class="panel-heading">BILL ENTRY FORM</div>
				<div class="panel-body">
					<form class="form-horizontal" method="POST" th:action="@{/saveBillEntry}">
						<div class="form-group">
							<label class="control-label col-md-2"><font color="red">Depot Name</font></label>
							<div class="col-md-3" th:object="${depotObj}">
								<select class="form-control selectpicker" id="depotMaster" name="depotMaster" data-live-search="true" required>
									<option value="">--Select Depot--</option>
									<option th:each="depotObj : ${depotList}" th:if="${userType=='USER'}"
										th:value="${depotObj.id}" selected th:text="${depotObj.name}"></option>
									<option th:each="depotObj : ${depotList}" th:if="${userType=='ADMIN'}"
										th:value="${depotObj.id}" th:text="${depotObj.name}"></option>
								</select>
							</div>
							<label class="control-label col-md-2"><font color="red">Customer Name</font></label>
							<div class="col-md-3" th:object="${customerObj}">
								<select class="form-control selectpicker" id="customerMaster"
									name="customerMaster" data-live-search="true" required>
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
							<label class="control-label col-md-2"><font color="red">Bill Date</font></label>
							<div class="col-md-3">
								<input type="text" id="date_s1" name="date_s1" class="form-control"  required />
							</div>
							<label class="control-label col-md-2"><font color="red">Invoice Date</font></label>
							<div class="col-md-3">
								<input type="text" id="entry_date1" name="entry_date1" class="form-control"  required />
							</div>
							<div class="col-md-2"></div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">Gstin</label>
							<div class="col-md-3">
								<input type="text" class="form-control" name="gstin" value=""/>
							</div>
							<!-- <label class="control-label col-md-2">Invoice No</label>
							<div class="col-md-3" th:if="${userType=='USER'}">
								<input type="text" class="form-control" name="invoiceNo" th:value="${invoiceNumber}" />
							</div>
							<div class="col-md-3" th:if="${userType=='ADMIN'}">
								<input type="text" class="form-control" name="invoiceNo" value="" />
							</div> -->
							<div class="col-md-8"></div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2"><font color="red">Truck / Tampo No.</font></label>
							<div class="col-md-3">
								<input type="text" class="form-control" name="truck_temp_no" value=""
									required />
							</div>
							<label class="control-label col-md-2"><font color="red">Batch No.</font></label>
							<div class="col-md-3">
								<input type="text" class="form-control" name="batch_no" value=""
									required />
							</div>
							<div class="col-md-2"></div>
						</div>
						<div id="billEntryDiv">
							<h4>
								<b>Select Products</b>
							</h4>
							<div class="table-responsive">
								<table id="dataTable" class="table table-striped table-bordered"
									width="50%">
									<thead>
										<tr th:if="${not #lists.isEmpty(brandList)}" style="background-color:grey;color:white;">
											<th align="left" width="1%">Sl.No</th>
											<th align="left" width="6%">Brand Name</th>
											<th align="left" width="3%">Cases</th>
											<th align="left" width="3%">Pieces</th>
											<th align="left" width="3%">Price</th>
											<th align="left" width="4%">Amount</th>
										</tr>
									</thead>
									<tbody>
										<tr th:each="brandObj,iter : ${brandList}">
											<td th:text="${iter.index+1}">
											</td>
											<td>
												<input hidden th:name="|itemDistributionDetails[${iter.index}].brandMaster.id|" th:id="|itemDistributionDetails[${iter.index}].brandMaster.id|" th:value="${brandObj.getId()}"/>
												<input class="form-control" type="text" th:value="${brandObj.getName()}"  readOnly required/>
											</td>
											<td align="left" nowrap>
												<input type="text" class="form-control" name="quantity" value="0" onblur="javaScript:calculateAmount();" onkeypress='return validate(event)' />
												<input hidden th:name="|itemDistributionDetails[${iter.index}].cases|" th:id="|itemDistributionDetails[${iter.index}].cases|"/>
											</td>
											<td align="left" nowrap>
												<input type="text" class="form-control" name="pieces" value="0" onblur="javaScript:calculateAmount();" onkeypress='return validate(event)' />
												<input hidden th:name="|itemDistributionDetails[${iter.index}].pieces|" th:id="|itemDistributionDetails[${iter.index}].pieces|"/>
											</td>
											<td>
												<input class="form-control" type="text" th:value="${brandObj.getAmount()}"  name="amount" readOnly required/>
											</td>
											<td align="left" nowrap>
												<input class="form-control" th:name="|itemDistributionDetails[${iter.index}].amount|" th:id="|itemDistributionDetails[${iter.index}].amount|" th:value="0" readOnly/>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2"></label>
							<div class="col-md-3">
								
							</div>
							<label class="control-label col-md-2">Total Amount</label>
							<div class="col-md-3">
								<input type="text" class="form-control" id="total_amount" name="total_amount" value="0" required readOnly/>
							</div>
							<div class="col-md-2">
								<input type="submit" class="btn btn-success" value="Save" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- Bill Entry List Starts -->
		<div class="panel panel-primary" th:if="${not #lists.isEmpty(billDetailsList)}">
			<div class="panel-heading" style="text-align: left;">ALL BILL ENTRIES</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-striped table-bordered">
						<thead>
							<tr style="background-color:grey;color:white;">
								<th>Sl. No.</th>
								<th>Depot Name</th>
								<th>Customer Name</th>
								<th>Bill Date</th>
								<th>Invoice Date</th>
								<th>Invoice No</th>
								<th>Gstin</th>
								<th>Truck/Tempo No.</th>
								<th>Batch No.</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							
							<tr th:each="billDetailsObj,iter : ${billDetailsList}">
								<td th:text="${iter.index+1}"></td>
								<td th:text="${billDetailsObj.depotMaster.name}"></td>
								<td th:text="${billDetailsObj.customerMaster.name_of_shop}"></td>
								<td th:text="${#dates.format(billDetailsObj.date_s, 'dd-MMM-yyyy')}"></td>
								<td th:text="${#dates.format(billDetailsObj.entry_date, 'dd-MMM-yyyy')}"></td>
								<td th:if="${billDetailsObj.invoice_seq<10}" th:text="${billDetailsObj.invoiceNo} +'000'+ ${billDetailsObj.invoice_seq}"></td>
								<td th:if="${billDetailsObj.invoice_seq>=10 && billDetailsObj.invoice_seq<100}" th:text="${billDetailsObj.invoiceNo} +'00'+ ${billDetailsObj.invoice_seq}"></td>
								<td th:if="${billDetailsObj.invoice_seq>=100 && billDetailsObj.invoice_seq<1000}" th:text="${billDetailsObj.invoiceNo} +'0'+ ${billDetailsObj.invoice_seq}"></td>
								<td th:if="${billDetailsObj.invoice_seq>=1000}" th:text="${billDetailsObj.invoiceNo} + ${billDetailsObj.invoice_seq}"></td>
								<td th:text="${billDetailsObj.gstin}"></td>
								<td th:text="${billDetailsObj.truck_temp_no}"></td>
								<td th:text="${billDetailsObj.batch_no}"></td>
								<td th:text="${billDetailsObj.status}"></td>
								<td>
									<a th:href="@{'/download-billDetail/' + ${billDetailsObj.id}}" target="_blank"><span
											class="glyphicon glyphicon-file"></span></a>|
									<a th:href="@{'/cancel-billDetail/' + ${billDetailsObj.id}}" target="_blank"><span
											class="glyphicon glyphicon-remove"></span></a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!-- Bill Entry List End -->
	</div>
</body>
</html>