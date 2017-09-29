var app = angular.module("cryptoApp", []); 
app.controller("myController", function($scope){
	
	$scope.showMe = false;
	$scope.showTable = function(){
		$scope.showMe = !$scope.showMe;
	};
	
	$scope.showMe1 = false;
	$scope.showTable1 = function(){
		$scope.showMe1 = !$scope.showMe1;
	};
	
	$scope.showMe2 = false;
	$scope.showTable2 = function(){
		$scope.showMe2 = !$scope.showMe2;
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
	ts.ui.get('#transactionslist1', table => {
		table.cols(['One', 'Two', 'Tree']);
		table.rows([
			['A', 'D', 'G'],
			['B', 'E', 'H'],
			['C', 'F', 'I']
		]);
	});
	ts.ui.get('#transactionslist2', table => {
		table.cols(['One', 'Two', 'Tree']);
		table.rows([
			['A', 'D', 'G'],
			['B', 'E', 'H'],
			['C', 'F', 'I']
		]);
	});
	ts.ui.get('#transactionslist3', table => {
		table.cols(['Total ', 'Received', 'Send']);
		table.rows([
			['4000', '1500', '2500'],
			['B', 'E', 'H'],
			['C', 'F', 'I']
		]);
	});
	
	ts.ui.get('#transactionslist4', table => {
		table.cols(['One', 'Two', 'Tree']);
		table.rows([
			['A4444', 'D', 'G'],
			['B', 'E', 'H'],
			['C', 'F', 'I']
		]);
	});
	
});

