(function() {
	angular.module('letterCounterApp').controller('UsersController',
			[ '$scope', 'NotifyService', 'UserService', ctrl ]);

	function ctrl($scope, NotifyService, UserService) {
		$scope.users = UserService.query();
		$scope.deleteUser = function(user) {
			user.$remove(function() {
				$scope.users = UserService.query();
				NotifyService.showRemovedItem();
			});
		};
	}
})();
