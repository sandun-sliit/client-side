<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="com.fund"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Funding Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Fund.js"></script>
</head>

<body>


	<div class="container">
		<div class="row">
			<div class="col">
				<h1>Fund Management</h1>
				<form id="formFund" name="formFund">

					Researcher ID: <input id="resID" name="resID" type="text"
						class="form-control"><br> Researcher name: <input
						id="resName" name="resName" type="text" class="form-control"><br>

					Fund Amount: <input id="famount" name="famount" type="text"
						class="form-control"><br> Subject: <input
						id="subject" name="subject" type="text" class="form-control"><br>
						
					Description: <input id="description" name="description" type="text" class="form-control">
					<br>

					<input id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input 
						id="hidFundIDSave" name="hidFundIDSave" value="">


				</form>


				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

			</div>

			<br>

			<div id="divFundsGrid">
				<%
				fund fundObj = new fund();
				out.print(fundObj.readFunds());
				%>

			</div>
		</div>
	</div>

</body>
</html>