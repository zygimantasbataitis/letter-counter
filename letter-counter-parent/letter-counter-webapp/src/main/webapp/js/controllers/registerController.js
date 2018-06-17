(function() {
	angular.module('letterCounterApp').controller(
			'RegisterController',
			[ '$scope', '$rootScope', '$location', '$cookieStore',
					'UserService', '$translate', '$rootScope', ctrl ]);

	function ctrl($scope, $rootScope, $location, $cookieStore, UserService, $translate, $rootScope) {
		
	  $scope.changeLanguage = function (langKey) {
		  $translate.use(langKey);
		  $rootScope.selectedLanguage = $translate.use().toUpperCase();
	  };		
		$scope.rememberMe = false;
		$scope.register = function() {
			$rootScope.selectedLanguage = $translate.use().toUpperCase();
			UserService.register($.param({
				username : $scope.username,
				password : $scope.password,
				firstname : $scope.firstname,
				lastname : $scope.lastname
			}), function(authenticationResult) {
				var authToken = authenticationResult.token;
				$rootScope.authToken = authToken;
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
				}
				UserService.get(function(user) {
					$rootScope.user = user;
					$location.path("/files");
				});
			});
		};
	}
})();