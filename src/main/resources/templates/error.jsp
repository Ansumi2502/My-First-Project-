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
</head>
<body>
	<div role="navigation">
		<div class="navbar navbar-inverse">
			<a href="/welcomepage" class="navbar-brand" th:text="${appName}"></a>
		</div>
	</div>
	<div class="container text-center">
	   <h3>Access Denied Exception</h3>
	   <hr>
	   <div>
		    Logged in user: <b th:inline="text"  class="user"> [[${#httpServletRequest.remoteUser}]] </b>
		    <form th:action="@{/logout}" method="POST">
		         <input type="submit" value="Logout"/>
		    </form>
	   </div> 	 
	   <p class="error" th:text="${errorMsg}">Error</p>
    </div>
</body>
</html>   