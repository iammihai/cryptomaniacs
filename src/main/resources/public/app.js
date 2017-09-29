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
		table.cols(['Hash    ', 'blockNumber    ', 'blockHash    ', 'from    ', 'to    ', 'value (ethereum)   ', 'gas    ', 'gasPrice    ']);
		table.rows([
			['0x7a83e53cc62ad95dfa6ad1c00ec49214999461d9bbac748e8b5251f1ed2142ea', '4321196', '0x8e7bc14e782fcc6b4836d52c1cf0f2ecdb6e5e615c7f6ef59c128308fba487df', '0xd30848ca6553fc34aa342add28907e1ecef2cb6a', '0x17473c7e2afcc56c984287ff7d5a79276894f58d', '0.0006', '30000', '20000000000'],
			['0x49214999461d9b62ad95dfa6ad1c00ecbac748e8b5251f1ed2142ea', '9682108', '0x1f59c128308fba48e7bc14e782fcc6b4836d52c1cf0f2ecdb6e5e615c7f687df', '0xd30848ca6553fc34aa342add28907e1ecef2cb6a', '0x17473c7e2afcc56c984287ff7d5a79276894f58d', '0.0004', '20000', '20000000000'],
			['0x7e8b5251f1ed2142ea214999461d9bbac748e214999461d9bbac748e', '8701156', '0x2ecdb6e5e68e7bc14e782fcc6b4836d52c1cf0f15c7f6ef59c128308fba487df', '0xd30848ca6553fc34aa342add28907e1ecef2cb6a', '0x17473c7e2afcc56c984287ff7d5a79276894f58d', '0.00031316', '15658', '20000000000'],
			['0x7a83e53cc62ad95dfa6ad1c00ec49214999461d9bbac748e8b5251f', '5834196', '0x8e7bc14e782fcc6b4836d52c1cf0f2ecdb6e5e615c7f6ef59c128308fba487df', '0xd30848ca6553fc34aa342add28907e1ecef2cb6a', '0x87ff7d5a717473c7e2afcc56c98429276894f58d', '0.000696', '34800', '20000000000']
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



