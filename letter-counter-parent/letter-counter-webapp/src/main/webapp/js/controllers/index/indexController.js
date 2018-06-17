(function() {
	angular.module('letterCounterApp').controller('IndexController',
			[ '$scope', 'NotifyService', 'NewsService', ctrl ]);

	function ctrl($scope, NotifyService, NewsService) {
		$scope.newsEntries = NewsService.query();
		$scope.deleteEntry = function(newsEntry) {
			newsEntry.$remove(function() {
				$scope.newsEntries = NewsService.query();
				NotifyService.showRemovedItem();
			});
		};
	}
})();
