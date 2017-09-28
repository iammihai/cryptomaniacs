var app = angular.module("cryptoApp", []); 
app.controller("myController", function($scope) {
	ts.ui.TopBar.title('Cryptomaniacs');
	ts.ui.TopBar.blue();

	ts.ui.TopBar.tabs([
		{label: '',
		icon: 'assets/LogoUpdate.png'},
		{label: 'Overview'},
		{label: 'TopBar'},
		{label: 'TabBar'},
		{label: 'ToolBar'},
		{label: 'StatusBar'}
	]);
});


