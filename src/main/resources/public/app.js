var app = angular.module("cryptoApp", []); 

app.service('dataService', function($http) {
	
	this.getData = function(method, url) {
	   $http({
	        method: method,
	        url: url
	     }).then(function(response){
	         return response.data;
	    });
	 };
});


app.controller("myController", function($scope,$http, dataService){
	
	$scope.users = [];
	$scope.getUsers =  function(){
		$http({
	        method: 'GET',
	        url: 'http://localhost:8080/api/user/list'
	     }).then(function(response){
	    	 console.log(response.data);
	    	 $scope.users = response.data;
	    });
	}();

	$scope.showMe = false;
	$scope.showMe1 = false;
	$scope.showMe2 = false;
	$scope.showTable = function(){
		$scope.showMe = !$scope.showMe;
	};
	$scope.showTable1 = function(){
		$scope.showMe1 = !$scope.showMe1;
	};
	$scope.showTable2 = function(){
		$scope.showMe2 = !$scope.showMe2;
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



