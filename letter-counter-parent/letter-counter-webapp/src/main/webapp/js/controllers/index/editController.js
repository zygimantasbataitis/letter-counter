(function() {
	angular.module('letterCounterApp').controller('EditController',
			[ '$scope', '$location', '$routeParams', 'NotifyService', 'NewsService', ctrl ]);

	function ctrl($scope, $location, $routeParams, NotifyService, NewsService) {
		$scope.newsEntry = NewsService.get({id: $routeParams.id});	
		$scope.update = function() {
			$scope.newsEntry.$save(function() {
				$location.path('/news');
				NotifyService.showSavedItem();
			});
		};
	}
})();
