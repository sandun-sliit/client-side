$(document).ready(function() 
		{  
	if ($("#alertSuccess").text().trim() == "")  
    {   
		$("#alertSuccess").hide();  
     }  
	     $("#alertError").hide(); 
	  
});

$(document).on("click", "#btnSave", function(event) 
		{  
			$("#alertSuccess").text("");  
			$("#alertSuccess").hide();  
			$("#alertError").text("");  
			$("#alertError").hide(); 
			
			
			var status = validateItemForm();  
			if (status != true)  
			{   
				$("#alertError").text(status);   
				$("#alertError").show();   
				return;  
			} 
			
			var type = ($("#hidFundIDSave").val() == "") ? "POST" : "PUT"; 
			
			$.ajax( 
			{  
				url : "fundAPI",  
				type : type,  
				data : $("#formFund").serialize(),  
				dataType : "text",  
				complete : function(response, status)  
				{   
					onFundSaveComplete(response.responseText, status);  
					
				} 
			
		}); 
}); 
		
function onFundSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divFundsGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

		} else if (status == "error")  
		{   
			$("#alertError").text("Error while saving.");   
			$("#alertError").show();  
		} else  
		{   
			$("#alertError").text("Unknown error while saving..");   
			$("#alertError").show();  
		} 

		$("#hidFundIDSave").val("");  
		$("#formFund")[0].reset(); 
		
}

$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "fundAPI",   
		type : "DELETE",   
		data : "resID=" + $(this).data("itemid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onItemDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 


function onItemDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 

			$("#divFundsGrid").html(resultSet.data);   
			} else if (resultSet.status.trim() == "error")   
			{    
				$("#alertError").text(resultSet.data);    
				$("#alertError").show();   
			} 

			} else if (status == "error")  
			{   
				$("#alertError").text("Error while deleting.");   
				$("#alertError").show();  
			} else  
			{   
				$("#alertError").text("Unknown error while deleting..");   
				$("#alertError").show();  
			} 
	} 

$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidFundIDSave").val($(this).closest("tr").find('#hidFundIDUpdate').val());     
	$("#resName").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#famount").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#subject").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#desc").val($(this).closest("tr").find('td:eq(3)').text()); 
}); 


function validateItemForm() 
{  
	// CODE  
	if ($("#resID").val().trim() == "")  
	{   
		return "Insert Item Code.";   
	}

	 
	 // NAME  
	if ($("#resName").val().trim() == "")  
	{   
		return "Insert Item Name.";  
	}
	
	if ($("#famount").val().trim() == "")  
	{   
		return "Insert Item Price.";  
	} 
	 
	 // is numerical value  
	var tmpPrice = $("#famount").val().trim();  
	if (!$.isNumeric(tmpPrice))  
	{   
		return "Insert a numerical value for Item Price.";  
	} 
	 

	 // convert to decimal price  
	$("#famount").val(parseFloat(tmpPrice).toFixed(2)); 
	 
	 // DESCRIPTION------------------------  
	if ($("#Desc").val().trim() == "")  
	{   
		return "Insert Item Description.";  
		
	} 
	 
	 return true;
	
}