var app = angular.module("cryptoApp", []); 
app.controller("myController", function($scope) {
	function changeDate(){
		var datePicker = ts.ui.DatePicker({
			onselect: function(newval, oldval) {
				ts.ui.Notification.success(this.value);
				this.close();
			},
			onclosed: function() {
				this.dispose();
			}
		});
		datePicker.open();
	}
	
	function setCurrentDate(){
		var d = new Date();
		var day = d.getDate();
		var month = d.getMonth() + 1;
		var year = d.getFullYear();
		
		if(day<10) {
		    dd = '0'+ day
		} 

		if(month<10) {
			month = '0'+ month
		}
		
		var currentDate = new Date();
		currentDate = year + '-' + month + '-' + day;
		return currentDate;
	}
	
	function setFirstDay(){
		var d = new Date();
		var day = "01"
		var month = d.getMonth() + 1;
		var year = d.getFullYear();
		
		if(day<10) {
		    dd = '0'+ day
		} 

		if(month<10) {
			month = '0'+ month
		}
		
		var firstDay = new Date();
		firstDay = year + '-' + month + '-' + day;
		return firstDay;
	}
	
	document.getElementById('enddate').value = setCurrentDate();
	document.getElementById('enddate').setAttribute("max", setCurrentDate());
	document.getElementById('enddate').setAttribute("min", setCurrentDate());
	document.getElementById('startdate').value = setFirstDay();
	document.getElementById('startdate').setAttribute("max", setCurrentDate());
		
	ts.ui.get('#transactionslist', table => {
		table.cols(['One', 'Two', 'Tree']);
		table.rows([
			['A', 'D', 'G'],
			['B', 'E', 'H'],
			['C', 'F', 'I']
		]);
	});
	
	function showTable(){
		document.getElementById('transactionslist').setAttribute("style", "visibility:visible");
	}
	
	document.getElementById('generatetrans_button').onclick = function() {showTable()};
});

