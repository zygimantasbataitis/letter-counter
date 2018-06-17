(function() {
	angular.module('letterCounterApp').controller('EditUserController',
			[ '$scope', '$location', '$routeParams', 'NotifyService', 'UserService', ctrl ]);

	function ctrl($scope, $location, $routeParams, NotifyService, UserService) {
		$scope.user = UserService.get({action: $routeParams.id});	
		$scope.documentIdTypes = UserService.documentIdTypes();
		$scope.save = function() {
			$scope.user.$save(function() {
				$location.path('/users');
				NotifyService.showSavedItem();
			});
		};
	}
})();
