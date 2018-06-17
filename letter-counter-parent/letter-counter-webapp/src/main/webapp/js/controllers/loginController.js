(function() {
	angular.module('letterCounterApp').controller(
			'LoginController',
			[ '$scope', '$rootScope', '$location', '$cookieStore',
					'UserService', '$translate', '$rootScope', ctrl ]);

	function ctrl($scope, $rootScope, $location, $cookieStore, UserService, $translate, $rootScope) {
		
	  $scope.changeLanguage = function (langKey) {
		  $translate.use(langKey);
		  $rootScope.selectedLanguage = $translate.use().toUpperCase();
	  };		
		$scope.rememberMe = false;
		$scope.login = function() {
			$rootScope.selectedLanguage = $translate.use().toUpperCase();
			UserService.authenticate($.param({
				username : $scope.username,
				password : $scope.password
			}), function(authenticationResult) {
				var authToken = authenticationResult.token;
				$rootScope.authToken = authToken;
				if ($scope.rememberMe) {
					$cookieStore.put('authToken', authToken);
				}
				UserService.user_detail(function(user) {
					$rootScope.user = user;
					$location.path("/files");
				});
			});
		};
	}
})();