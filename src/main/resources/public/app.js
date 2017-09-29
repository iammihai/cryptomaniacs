var app = angular.module("cryptoApp", []); 
app.controller("myController", function($scope){
	
	$scope.showMe = false;
	$scope.showTable = function(){
		$scope.showMe = !$scope.showMe;
	};
	
	$scope.today = new Date();
	
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
		
	ts.ui.get('#transactionslist', table => {
		table.cols(['One', 'Two', 'Tree']);
		table.rows([
			['A', 'D', 'G'],
			['B', 'E', 'H'],
			['C', 'F', 'I']
		]);
	});
	
});

