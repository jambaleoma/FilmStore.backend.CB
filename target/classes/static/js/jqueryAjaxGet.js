$(document).ready(function() {

	/**
	 * Load List of Customers then showing on Table view
	 */
	$(function loadData(){

			console.log("--- Load From Back-End service ---");

			$.ajax({
				type : "GET",
				url : window.location + "rest/customers/all",

				success: function(listOfCustomers){

					// store listOfCustomers to sessionStorage
					sessionStorage.setItem('listOfCustomers', JSON.stringify(listOfCustomers));

					// render List of Customers on view
					renderCustomersByTableView(listOfCustomers);
				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});

	});

	/**
	 * Render List of Customers by Table view
	 */
	function renderCustomersByTableView(listOfCustomers){
		if(listOfCustomers){

			$.each(listOfCustomers, function(i, customer){

				var customerRow = '<tr>' +
									'<td>' + customer.id + '</td>' +
									'<td>' + customer.firstName.toUpperCase() + '</td>' +
									'<td>' + customer.lastName.toUpperCase() + '</td>' +
								  '</tr>';

				$('#customerTable tbody').append(customerRow);

	        });

			$( "#customerTable tbody tr:odd" ).addClass("info");
			$( "#customerTable tbody tr:even" ).addClass("success");
		}
	}
})